package info.magat.mapeditor.event;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Position;

public class SplitEvent implements Event {

    private Position position;

    public SplitEvent(Position position) {
        this.position = position;
    }

    @Override
    public boolean apply(Grid grid) {
        Grid split = new Grid(grid.getSide());
        grid.set(split, position);

        split.set(new Cell(Color.RED), new Position(0,0));
        return false;
    }
}
