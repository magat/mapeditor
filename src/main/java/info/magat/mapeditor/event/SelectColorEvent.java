package info.magat.mapeditor.event;

import info.magat.mapeditor.color.Color;

public class SelectColorEvent implements Event {

    private Color color;

    public SelectColorEvent(Color color) {
        this.color = color;
    }

    @Override
    public boolean apply(State state) {
        state.setSelectedColor(color);
        return false;
    }
}
