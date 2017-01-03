package info.magat.mapeditor;

import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.drawable.Grid;

public class Map extends Drawable {

    private Grid grid;

    public Map(int side) {
        this.grid = new Grid(side);
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void draw() {
        grid.draw(x, y, width, height);
    }
}
