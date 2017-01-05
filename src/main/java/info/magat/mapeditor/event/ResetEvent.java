package info.magat.mapeditor.event;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Position;

public class ResetEvent implements Event {
    @Override
    public boolean apply(Grid grid) {
        for (int i = 0; i < grid.getSide(); i++) {
            for (int j = 0; j < grid.getSide(); j++) {
                grid.set(new Cell(Color.BLACK), new Position(i, j));
            }
        }
        return false;
    }
}
