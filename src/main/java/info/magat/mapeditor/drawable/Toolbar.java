package info.magat.mapeditor.drawable;

import static info.magat.mapeditor.color.Color.SLATE_GREY;

public class Toolbar extends Drawable {
    @Override
    public void draw() {
        new Rectangle(SLATE_GREY).draw(x, y, width, height);
    }
}
