package info.magat.mapeditor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class EmptyCell implements Cell {

    @Override
    public void draw(float x, float y, float cellWidth, float cellHeight) {
        glBegin(GL_QUADS);
        glColor3f(1.0f, 0.0f, 0.0f);

        glVertex2f(x, y);
        glVertex2f(x + cellWidth, y);
        glVertex2f(x + cellWidth, y - cellHeight);
        glVertex2f(x, y - cellHeight);
        glEnd();
    }
}
