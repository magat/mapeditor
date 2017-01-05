package info.magat.mapeditor.drawable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Layout extends Drawable {

    private Grid grid;
    private Toolbar toolbar = new Toolbar();

    @Autowired
    public Layout(@Qualifier("map") Grid grid) {
        this.grid = grid;
    }

    @Override
    public void draw() {
        // compute the map dimensions
        // the map has X rows
        // and the toolbar is one row
        float mapWidth = Math.min(grid.getSide() * height / (grid.getSide() + 1), width);

        float separator = (width - mapWidth) / 2;

        grid.draw(separator, y, mapWidth, mapWidth);
        toolbar.draw(separator, y + mapWidth, mapWidth, mapWidth / grid.getSide());
    }
}
