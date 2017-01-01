package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;

public class Cell implements Drawable {

    private Rectangle representation = new Rectangle(Color.BLACK);

    @Override
    public void draw(float x, float y, float width, float height) {
        representation.draw(x, y, width, height);
    }

    public void setColor(Color color){
        representation.setColor(color);
    }
}
