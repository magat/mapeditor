package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static info.magat.mapeditor.color.Color.SLATE_GREY;

public class Toolbar extends Drawable {

    private List<Drawable> cells;

    public Toolbar(Stream<Color> colors) {
        cells = colors.map(ToolBarCell::new).collect(Collectors.toList());
    }

    @Override
    public void draw() {
        new Rectangle(SLATE_GREY).draw(x, y, width, height * 0.2f);
        float cellHeight = height * 0.8f;

        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).draw(x + i * cellHeight, y + height - cellHeight, cellHeight, cellHeight);
        }
    }

    @Override
    public Stream<Drawable> elements() {
        return cells.stream();
    }

    public static Stream<Color> defaultPalette(){
        return Stream.of(
                Color.WHITE,
                Color.BLACK,
                Color.NAVY_BLUE,
                Color.INDIAN_RED,
                Color.FOREST_GREEN,
                Color.SLATE_GREY
        );
    }

    public class ToolBarCell extends Cell{
        public ToolBarCell(Color color) {
            super(color);
        }
    }
}
