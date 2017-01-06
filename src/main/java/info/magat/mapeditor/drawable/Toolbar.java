package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static info.magat.mapeditor.color.Color.SLATE_GREY;

public class Toolbar extends Drawable {

    int SIZE = 12;
    private List<Drawable> cells;

    public Toolbar() {
        cells = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            Cell e = new ToolBarCell();
            e.setColor(Color.random());
            cells.add(e);
        }
    }

    @Override
    public void draw() {
        new Rectangle(SLATE_GREY).draw(x, y, width, height * 0.2f);

        float cellHeight = height * 0.8f;

        for (int i = 0; i < SIZE; i++) {
            cells.get(i).draw(x + i * cellHeight, y + height - cellHeight, cellHeight, cellHeight);
        }
    }

    @Override
    public Stream<Drawable> elements() {
        return cells.stream();
    }

    public class ToolBarCell extends Cell{
    }
}
