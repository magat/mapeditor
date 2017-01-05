package info.magat.mapeditor.event;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Toolbar;
import info.magat.mapeditor.input.Cursor;
import info.magat.mapeditor.store.FileStore;
import info.magat.mapeditor.window.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.lwjgl.glfw.GLFW.*;

@Component
public class ActionHandler {

    private static ArrayList<Drawable> clickables = new ArrayList<>();
    private Color selectedColor = Color.BLACK;
    private Grid grid;
    private History history = new History();
    private FileStore fileStore;
    private Cursor cursor;

    @Autowired
    public ActionHandler(@Qualifier("map") Grid grid, FileStore fileStore, Window window, Cursor cursor) {
        this.grid = grid;
        this.fileStore = fileStore;
        this.cursor = cursor;
        loadFromFile();
        glfwSetKeyCallback(window.getId(), this::handle);

        glfwSetMouseButtonCallback(window.getId(), (long w, int button, int action, int mods) -> {
                    if (button == GLFW_MOUSE_BUTTON_LEFT && action > 0) {
                        click();
                    }
                }
        );
    }

    private void click() {
        Optional<ColorChangeEvent> action = clickables.stream().filter(this.cursor::isOver).map(d -> {
            if (d instanceof Cell) {
                Cell cell = (Cell) d;

                if (d instanceof Toolbar.ToolBarCell) {
                    selectedColor = cell.getColor();
                } else {
                    return new ColorChangeEvent(selectedColor, this.grid.positionOf(cell));
                }
            }
            return null;
        }).filter(Objects::nonNull).findFirst();
        action.ifPresent(this::throwEvent);
    }

    public static void registerClickable(Drawable drawable) {
        clickables.add(drawable);
    }

    private void split() {
        Optional<SplitEvent> action = clickables.stream().filter(this.cursor::isOver).map(d -> {
            if (!Cell.class.isAssignableFrom(d.getClass()) || Toolbar.ToolBarCell.class.isAssignableFrom(d.getClass())) {
                return null;
            }
            return new SplitEvent(this.grid.positionOf(d));
        }).filter(Objects::nonNull).findFirst();
        action.ifPresent(this::throwEvent);
    }

    public void throwEvent(Event e) {
        if (apply(e)) {
            history.add(e);
        }
    }

    public void back() {
        // go back to the beginning
        throwEvent(new ResetEvent());
        // and apply all events except for the last one
        history.previous();
        history.past().forEach(this::apply);
    }

    public void forward() {
        history.next().ifPresent(this::apply);
    }

    public void save() {
        try {
            fileStore.storeHistory(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try {
            history = fileStore.readHistory();

            throwEvent(new ResetEvent());

            history.past().forEach(this::apply);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean apply(Event e) {
        return e.apply(grid);
    }

    private void handle(long window, int key, int scancode, int action, int mods) {
        // Quit on escape key
        if (action == GLFW_RELEASE) {
            if (key == GLFW_KEY_ESCAPE) {
                glfwSetWindowShouldClose(window, true);
            }
            // UNDO on space
            if (key == GLFW_KEY_SPACE) {
                back();
            }
            // REDO on F
            if (key == GLFW_KEY_F) {
                forward();
            }
            // SAVE on S
            if (key == GLFW_KEY_S) {
                save();
            }
            // SPLIT on X
            if (key == GLFW_KEY_X) {
                split();
            }
        }
    }

    public static void unregisterClickable(Cell cell) {
        clickables.remove(cell);
    }
}
