package info.magat.mapeditor;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Toolbar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class State {

    private final Grid root;
    private Color selectedColor = Color.BLACK;
    private Grid currentGrid;
    private Toolbar toolbar = new Toolbar(Toolbar.defaultPalette());

    @Autowired
    public State(@Qualifier("map") Grid root) {
        this.root = root;
        this.currentGrid = root;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Grid getCurrentGrid() {
        return currentGrid;
    }

    public void setCurrentGrid(Grid currentGrid) {
        this.currentGrid = currentGrid;
    }

    public Grid getRoot() {
        return root;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
