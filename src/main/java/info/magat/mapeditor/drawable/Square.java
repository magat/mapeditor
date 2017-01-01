package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Square implements Drawable {

    private Color color;

    public Square(Color color) {
        this.color = color;
    }

    @Override
    public void draw(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
        color.apply(GL11::glColor3f);

        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }
}
