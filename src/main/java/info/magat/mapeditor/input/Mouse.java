package info.magat.mapeditor.input;


import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.event.ActionHandler;
import info.magat.mapeditor.window.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

@Component
public class Mouse {

    private long window;
    private double[] xpos = new double[1];
    private double[] ypos = new double[1];

    @Autowired
    public Mouse(Window window) {
        this.window = window.getId();
    }

    public void read() {
        glfwGetCursorPos(window, xpos, ypos);
    }

    public double getX() {
        return xpos[0];
    }

    public double getY() {
        return ypos[0];
    }

    public boolean isOver(Drawable d) {
        return (getX() > d.getX() && getX() < d.getX() + d.getWidth())
                && (getY() > d.getY() && getY() < d.getY() + d.getHeight());
    }

    public boolean isOver(float x, float y, float xMax, float yMax) {
        return (getX() > x && getX() < xMax) && (getY() > y && getY() < yMax);
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "window=" + window +
                ", xpos=" + Arrays.toString(xpos) +
                ", ypos=" + Arrays.toString(ypos) +
                '}';
    }
}
