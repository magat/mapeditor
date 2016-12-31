package info.magat.mapeditor;


import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.opengl.GL11.*;

/**
 * The painter's canvas is an orthogonal space
 *            y(1)
 *             |
 *             |
 *             |
 * (-1)--------+----------x(1)
 *             |
 *             |
 *             |
 *           (-1)
 *
 * It draws a square grid of square cells, in the center of the canvas.
 */
public class Painter {

    private static final float spacePercent = 0.1f;
    private Map map;
    // cell width relative to the orthogonal space
    private float cellWidth, cellHeight;

    private float cellWidthPixels;
    private int minCellWidthPixels = 40;

    public Painter(Map map, long window) {
        this.map = map;
        this.cellWidth = 2f / (map.getSide() + spacePercent * (map.getSide() + 1));

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque

        initResizeCapabilities(window, this);
    }

    private static void initResizeCapabilities(long window, Painter painter) {
        glfwSetFramebufferSizeCallback(window, (long w, int width, int height) -> painter.resize(width, height));
        int[] w = new int[1];
        int[] h = new int[1];
        glfwGetWindowSize(window, w, h);
        painter.resize(w[0], h[0]);

        glOrtho(0, w[0], h[0], 0,0,0);
    }

    public void resize(int width, int height) {
        float aspectRatio = 1.0f * height / width;
        // take up all space
//        glViewport(0, 0, width, height);

        cellWidthPixels = 2 * width / (map.getSide() + spacePercent * (map.getSide() + 1));

        cellWidth = cellWidthPixels / width;
        cellHeight = cellWidth * aspectRatio;
    }

    public void paint() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        int gridSide = map.getSide();
        float startX = -1.0f + cellWidth * spacePercent;
        float startY = 1.0f - cellHeight * spacePercent;

        for (int i = 0; i < gridSide; i++) {
            for (int j = 0; j < gridSide; j++) {
                float x = startX + i * (cellWidth * (1 + spacePercent));
                float y = startY - j * (cellHeight * (1 + spacePercent));
                map.getCell(i, j).draw(x, y, cellWidth, cellHeight);
            }
        }

        glFlush();  // Render now
    }

}
