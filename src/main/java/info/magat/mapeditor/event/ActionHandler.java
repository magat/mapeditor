package info.magat.mapeditor.event;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Toolbar;
import info.magat.mapeditor.input.Mouse;
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
    private Mouse mouse;

    @Autowired
    public ActionHandler(@Qualifier("map") Grid grid, FileStore fileStore, Window window, Mouse mouse) {
        this.grid = grid;
        this.fileStore = fileStore;
        this.mouse = mouse;
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
        clickables.stream().filter(mouse::isOver).forEach(d -> {
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
        if (e.apply(grid)) {
            history.past.add(e);
        }
    }

    public void back() {
        if (!history.past.isEmpty()) {
            originalState();
            history.future.add(history.past.remove(history.past.size() - 1));
            history.past.forEach(e -> e.apply(grid));
        }
    }

    public void forward() {
        if (!history.future.isEmpty()) {
            Event event = history.future.remove(history.future.size() - 1);
            event.apply(grid);
            history.past.add(event);
        }
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
            history.past.forEach(e -> e.apply(grid));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
