package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.ui.ActionHandler;

public class Cell extends Drawable {

    private Color color = Color.BLACK;

    public Cell() {
        ActionHandler.registerClickAble(this);
    }

    @Override
    public void draw() {
        new Rectangle(color).draw(x, y, width, height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
