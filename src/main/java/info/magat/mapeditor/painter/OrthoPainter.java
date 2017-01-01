package info.magat.mapeditor.painter;


import info.magat.mapeditor.drawable.Drawable;
import org.lwjgl.opengl.GL11;

import static info.magat.mapeditor.color.Color.BLACK;
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
        BLACK.apply(GL11::glClearColor);
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
    public void paint(Drawable drawable) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        drawable.draw(10,10,xMax-20, yMax-20);

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
