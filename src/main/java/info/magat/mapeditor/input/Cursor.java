package info.magat.mapeditor.input;


import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.window.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;

@Component
public class Cursor {

    private double xpos;
    private double ypos;

    @Autowired
    public Cursor(Window window) {
        glfwSetCursorPosCallback(window.getId(), (w, xpos, ypos) -> {
            this.xpos = xpos;
            this.ypos = ypos;
        });
    }

    public double getX() {
        return xpos;
    }

    public double getY() {
        return ypos;
    }

    public boolean isOver(Drawable d) {
        return (getX() > d.getX() && getX() < d.getX() + d.getWidth())
                && (getY() > d.getY() && getY() < d.getY() + d.getHeight());
    }
}
