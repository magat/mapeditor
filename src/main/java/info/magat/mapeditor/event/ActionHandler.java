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

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

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
                        this.click();
                    }
                }
        );
    }

    public void click() {
        clickables.stream().filter(cursor::isOver).forEach(d -> {
            if (d instanceof Cell) {
                Cell cell = (Cell) d;

                if (d instanceof Toolbar.ToolBarCell) {
                    selectedColor = cell.getColor();
                    return;
                }

                throwEvent(new ColorChangeEvent(selectedColor, grid.positionOf(cell)));
            }
        });
    }

    public static void registerClickAble(Drawable drawable) {
        clickables.add(drawable);
    }

    public void throwEvent(Event e) {
        if (apply(e)) {
            history.add(e);
        }
    }

    public void back() {
        // go back to the beginning
        originalState();
        // and apply all events except for the last one
        history.previous();
        history.past().forEach(this::apply);
    }

    public void forward() {
        history.next().ifPresent(this::apply);
    }

    public void originalState() {
        grid.cells().forEach(c -> c.setColor(Color.BLACK));
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

            originalState();

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
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }
        // UNDO on space
        if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE) {
            back();
        }
        // REDO on F
        if (key == GLFW_KEY_F && action == GLFW_RELEASE) {
            forward();
        }
        // SAVE on S
        if (key == GLFW_KEY_S && action == GLFW_RELEASE) {
            save();
        }
    }
}
