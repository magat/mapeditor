package info.magat.mapeditor;

import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Drawable;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map extends Drawable {

    private int side;
    private Cell[][] grid;

    public Map(int side) {
        this.side = side;
        grid = new Cell[side][side];
        IntStream.range(0, side).forEach(i -> IntStream.range(0, side)
                .forEach(j -> grid[i][j] = new Cell()));
    }

    public int getSide() {
        return side;
    }

    public Stream<Cell> cells() {
        return Stream.of(grid).map(Stream::of).flatMap(Function.identity());
    }

    @Override
    public void draw() {
        float cellSide = width / side;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                grid[i][j].draw(x + i * cellSide, y + j * cellSide, cellSide, cellSide);
            }
        }
    }
}
