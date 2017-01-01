package info.magat.mapeditor.drawable;

public class Layout extends Drawable {

    private Map map = new Map(10);
    private Toolbar toolbar = new Toolbar();

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
