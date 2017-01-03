package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.ui.ActionHandler;

import java.util.Objects;
import java.util.UUID;

public class Cell extends Drawable {

    private Color color = Color.BLACK;
    private UUID id = UUID.randomUUID();

    public Cell() {
        ActionHandler.registerClickAble(this);
    }

    @Override
    public void draw() {
        new Rectangle(color).draw(x, y, width, height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return Objects.equals(id, cell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
