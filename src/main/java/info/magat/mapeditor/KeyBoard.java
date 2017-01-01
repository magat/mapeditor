package info.magat.mapeditor;

import info.magat.mapeditor.ui.ActionHandler;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBoard {

    private long window;

    private static KeyBoard instance;

    public KeyBoard(long window) {
        this.window = window;
        instance = this;

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            instance.handle(key, action);
        });
    }

    private void handle(int key, int action) {
        // Quit on escape key
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }
        // UNDO on space
        if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE) {
            ActionHandler.get().back();
        }
    }
}
