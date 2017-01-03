package info.magat.mapeditor.drawable;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid extends Drawable {

    private int side;
    private Cell[][] grid;

    public Grid(int side) {
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

    public Cell get(Position pos) {
        return grid[pos.x][pos.y];
    }

    public Position positionOf(Cell cell){
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
    public void draw() {
        float cellSide = width / side;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                grid[i][j].draw(x + i * cellSide, y + j * cellSide, cellSide, cellSide);
            }
        }
    }

}
