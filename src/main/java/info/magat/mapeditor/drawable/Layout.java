package info.magat.mapeditor.drawable;

import info.magat.mapeditor.Map;

public class Layout extends Drawable {

    private Map map;
    private Toolbar toolbar = new Toolbar();

    public Layout(Map map) {
        this.map = map;
    }

    @Override
    public void draw() {

        // compute the map dimensions
        // the map has X rows
        // and the toolbar is one row
        float mapWidth = Math.min(map.getSide() * height / (map.getSide() + 1), width);

        float separator = (width - mapWidth) / 2;

        map.draw(separator, y, mapWidth, mapWidth);
        toolbar.draw(separator, y + mapWidth, mapWidth, mapWidth / map.getSide());
    }
}
