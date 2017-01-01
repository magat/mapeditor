package info.magat.mapeditor.ui;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;

public class ColorChangeEvent implements Event {

    private final Color color;
    private final Cell cell;

    public ColorChangeEvent(Color color, Cell cell) {
        this.color = color;
        this.cell = cell;
    }


    @Override
    public boolean apply() {
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
                ", cell=" + cell +
                '}';
    }
}
