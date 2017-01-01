package info.magat.mapeditor;


import static org.lwjgl.opengl.GL11.*;

/**
 * Draws in a orthogonal plane starting from (0,0) in the top-left corner
 * Coordinates are noted (x,y)
 * (0,0)------------------------------> (xMax, 0)
 *   |
 *   |
 *   |
 *   |
 *   |
 *   |
 *   v
 * (0, yMax)                           (xMax,yMax)
 */
public class OrthoPainter extends ResizeablePainter implements Painter {

    private int xMax = 500;
    private int yMax = 500;

    public OrthoPainter(long window) {
        super(window);
        initPlane();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque
    }

    private void initPlane() {
        float aspectRatio = 1.0f * currentHeight / currentWidth;
        yMax = Math.round(xMax * aspectRatio);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, xMax, yMax, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        initPlane();
    }

    @Override
    public void paint() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        new EmptyCell().draw(10, 10, 200, 200);
        glFlush();  // Render now
    }

    @Override
    public String toString() {
        return "OrthoPainter{" +
                "xMax=" + xMax +
                ", yMax=" + yMax +
                '}';
    }
}
