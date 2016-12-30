package info.magat.mapeditor;


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
    // Number of cells in a row
    private int gridSide;
    // cell width relative to the orthogonal space
    private float cellWidth;

    public Painter(Map map) {
        this.map = map;
        this.gridSide = map.getSide();
        this.cellWidth = 2f / (gridSide + spacePercent * (gridSide + 1));
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque
    }

    public void paint() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        float startX = -1.0f + cellWidth * spacePercent;
        float startY = 1.0f - cellWidth * spacePercent;

        for (int i = 0; i < gridSide; i++) {
            for (int j = 0; j < gridSide; j++) {
                float x = startX + i * (cellWidth * (1 + spacePercent));
                float y = startY - j * (cellWidth * (1 + spacePercent));
                map.getCell(i, j).draw(x, y, cellWidth);
            }
        }

        glFlush();  // Render now
    }

}
