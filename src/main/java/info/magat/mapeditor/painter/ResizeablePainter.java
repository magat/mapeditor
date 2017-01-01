package info.magat.mapeditor.painter;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 * Window-resize aware painter
 */
public abstract class ResizeablePainter {

    protected int currentWidth;
    protected int currentHeight;

    public ResizeablePainter(long window) {
        // Set a resize event callback
        glfwSetWindowSizeCallback(window, (long w, int width, int height) -> resize(width, height));

        // read size for the first time
        int[] w = new int[1];
        int[] h = new int[1];
        glfwGetWindowSize(window, w, h);
        resize(w[0], h[0]);
    }

    public void resize(int width, int height) {
        this.currentHeight = height;
        this.currentWidth = width;
        // fill the entire window
        glViewport(0, 0, width, height);
    }
}
