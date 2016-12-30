package info.magat.mapeditor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class EmptyCell implements Cell {

    @Override
    public void draw(float x, float y, float cellSide) {
        glBegin(GL_QUADS);
        glColor3f(1.0f, 0.0f, 0.0f);

        glVertex2f(x, y);
        glVertex2f(x + cellSide, y);
        glVertex2f(x + cellSide, y - cellSide);
        glVertex2f(x, y - cellSide);
        glEnd();
    }
}
