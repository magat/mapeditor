package info.magat.mapeditor.event;

import info.magat.mapeditor.drawable.*;
import info.magat.mapeditor.input.Cursor;
import info.magat.mapeditor.store.FileStore;
import info.magat.mapeditor.window.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.lwjgl.glfw.GLFW.*;

@Component
public class ActionHandler {

    private History history = new History();
    private FileStore fileStore;
    private Cursor cursor;
    private Layout layout;
    private State state;

    @Autowired
    public ActionHandler(FileStore fileStore, Window window, Cursor cursor, Layout layout, State state) {
        this.fileStore = fileStore;
        this.cursor = cursor;
        this.layout = layout;
        this.state = state;
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
        findTarget().ifPresent(d -> {
            if (d instanceof Cell) {
                Cell cell = (Cell) d;

                if (d instanceof Toolbar.ToolBarCell) {
                    throwEvent(new SelectColorEvent(cell.getColor()));
                } else {
                    throwEvent(new ColorChangeEvent(state.getSelectedColor(), this.state.getCurrentGrid().positionOf(cell)));
                }
            }
        });
    }

    private Optional<Drawable> findTarget() {
        return layout.elements().filter(cursor::isOver).findFirst();
    }

    public void throwEvent(Event e) {
        if (e.apply(state)) {
            history.add(e);
        }
    }

    public void back() {
        // go back to the beginning
        throwEvent(new ResetEvent());
        // and apply all events except for the last one
        history.previous();
        history.past().forEach((e) -> e.apply(state));
    }

    public void forward() {
        history.next().ifPresent((e) -> e.apply(state));
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
            throwEvent(new ResetEvent());

            history = fileStore.readHistory();
            history.past().forEach((e) -> e.apply(state));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(long window, int key, int scancode, int action, int mods) {
        // Quit on escape key
        if (action == GLFW_RELEASE) {
            if (key == GLFW_KEY_ESCAPE) {
                glfwSetWindowShouldClose(window, true);
            }
            // UNDO on Z
            if (key == GLFW_KEY_Z) {
                back();
            }
            // REDO on X
            if (key == GLFW_KEY_X) {
                forward();
            }
            // SAVE on S
            if (key == GLFW_KEY_S) {
                save();
            }
            // SPLIT on C
            if (key == GLFW_KEY_C) {
                split();
            }
            // BACK TO ROOT on SPACE
            if(key == GLFW_KEY_SPACE){
                resetView();
            }
        }
    }

    private void resetView() {
        throwEvent(new ResetViewEvent());
    }

    private void split() {
        findTarget().map(c -> this.state.getCurrentGrid().positionOf(c))
                .filter(Objects::nonNull)
                .ifPresent(pos -> throwEvent(new SplitEvent(pos)));
    }
}
