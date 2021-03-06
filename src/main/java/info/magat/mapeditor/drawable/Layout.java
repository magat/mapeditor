package info.magat.mapeditor.drawable;

import info.magat.mapeditor.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Layout extends Drawable {

    private State state;

    @Autowired
    public Layout(State state) {
        this.state = state;
    }

    @Override
    public void draw() {
        Grid grid = state.getCurrentGrid();
        // compute the map dimensions
        // the map has X rows
        // and the toolbar is one row
        float mapWidth = Math.min(grid.getSide() * height / (grid.getSide() + 1), width);

        float separator = (width - mapWidth) / 2;

        grid.draw(separator, y, mapWidth, mapWidth);
        state.getToolbar().draw(separator, y + mapWidth, mapWidth, mapWidth / grid.getSide());
    }

    @Override
    public Stream<Drawable> elements() {
        return Stream.of(state.getCurrentGrid(), state.getToolbar()).flatMap(Drawable::elements);
    }
}
