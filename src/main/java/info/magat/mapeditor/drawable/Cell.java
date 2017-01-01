package info.magat.mapeditor.drawable;

import info.magat.mapeditor.Mouse;
import info.magat.mapeditor.color.Color;

public class Cell extends Drawable {

    private Color color = Color.BLACK;

    @Override
    public void draw() {
        Color color = this.color;
        if (Mouse.isOver(this)) {
            color = Color.SLATE_GREY;
        }
        new Rectangle(color).draw(x, y, width, height);
    }
}
