package info.magat.mapeditor.drawable;

import static info.magat.mapeditor.color.Color.SLATE_GREY;

public class Toolbar implements Drawable {
    @Override
    public void draw(float x, float y, float width, float height) {
        new Rectangle(SLATE_GREY).draw(x, y, width, height);
    }
}
