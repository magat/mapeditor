package info.magat.mapeditor.drawable;

import java.util.stream.IntStream;

public class Grid extends Drawable {

    private int side;
    private Drawable[][] grid;

    public Grid(int side) {
        this.side = side;
        grid = new Drawable[side][side];
        IntStream.range(0, side).forEach(i -> IntStream.range(0, side)
                .forEach(j -> grid[i][j] = new Cell()));
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
    public void draw() {
        float cellSide = width / side;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                grid[i][j].draw(x + i * cellSide, y + j * cellSide, cellSide, cellSide);
            }
        }
    }

}
