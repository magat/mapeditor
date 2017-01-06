package info.magat.mapeditor.event;

import info.magat.mapeditor.drawable.*;

public class SplitEvent implements Event {

    public final Position position;

    public SplitEvent(Position position) {
        this.position = position;
    }

    @Override
    public boolean apply(State state) {
        Grid parent = state.getCurrentGrid();

        Drawable current = parent.get(position);

        if (Grid.class.isInstance(current)) {
            state.setCurrentGrid((Grid) current);
            return true;
        }

        if (!Cell.class.isInstance(current) || Toolbar.ToolBarCell.class.isInstance(current)) {
            return false;
        }

        Grid split = new Grid(parent.getSide(), ((Cell) current).getColor());
        parent.set(split, position);

        state.setCurrentGrid(split);
        return true;
    }
}
