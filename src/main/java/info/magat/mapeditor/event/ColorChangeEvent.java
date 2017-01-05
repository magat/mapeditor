package info.magat.mapeditor.event;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Position;

import java.util.Objects;

public class ColorChangeEvent implements Event {

    public final Color color;
    public final Position position;

    public ColorChangeEvent(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    @Override
    public boolean apply(Grid grid) {
        grid.set(new Cell(color), position);
        return true;
    }

    @Override
    public String toString() {
        return "ColorChangeEvent{" +
                "color=" + color +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColorChangeEvent that = (ColorChangeEvent) o;
        return Objects.equals(color, that.color) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, position);
    }
}
