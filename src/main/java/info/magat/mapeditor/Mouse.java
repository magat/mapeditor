package info.magat.mapeditor;


import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.ui.ActionHandler;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private long window;
    private double[] xpos = new double[1];
    private double[] ypos = new double[1];

    private static Mouse instance;

    public Mouse(long window) {
        this.window = window;
        instance = this;

        glfwSetMouseButtonCallback(window, (long w, int button, int action, int mods) -> {
                    if (button == GLFW_MOUSE_BUTTON_LEFT && action > 0) {
                        ActionHandler.get().click();
                    }
                }
        );
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

    public static boolean isOver(Drawable d) {
        return (instance.getX() > d.getX() && instance.getX() < d.getX() + d.getWidth())
                && (instance.getY() > d.getY() && instance.getY() < d.getY() + d.getHeight());
    }

    public static boolean isOver(float x, float y, float xMax, float yMax) {
        return (instance.getX() > x && instance.getX() < xMax) && (instance.getY() > y && instance.getY() < yMax);
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
