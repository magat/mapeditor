package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid extends Drawable {

    private int side;
    private Drawable[][] grid;

    public Grid(int side, Color color) {
        this.side = side;
        grid = new Drawable[side][side];
        IntStream.range(0, side).forEach(i -> IntStream.range(0, side)
                .forEach(j -> grid[i][j] = new Cell(color)));
    }

    public int getSide() {
        return side;
    }

    public Drawable get(Position pos) {
        return grid[pos.x][pos.y];
    }

    public void set(Drawable drawable, Position pos){
        grid[pos.x][pos.y] = drawable;
    }

    public Position positionOf(Drawable cell){
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (grid[i][j].equals(cell)) {
                    return new Position(i, j);
                }
            }
        }

        return null;
    }

    @Override
    public Stream<Drawable> elements() {
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
