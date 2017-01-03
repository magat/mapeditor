package info.magat.mapeditor.ui;

import info.magat.mapeditor.Map;
import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Position;

public class ColorChangeEvent implements Event {

    private final Color color;
    private final Position position;

    public ColorChangeEvent(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    @Override
    public boolean apply(Map map) {
        Cell cell = map.getGrid().get(position);
        if(cell.getColor().equals(color)){
            return false;
        }
        cell.setColor(this.color);
        return true;
    }

    @Override
    public String toString() {
        return "ColorChangeEvent{" +
                "color=" + color +
                ", position=" + position +
                '}';
    }
}
