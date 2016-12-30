package info.magat.mapeditor;

import java.util.stream.IntStream;

public class Map {

    private int side;
    private Cell[][] grid;

    public Map(int side) {
        this.side = side;
        grid = new Cell[side][side];
        IntStream.range(0, side).forEach(i -> IntStream.range(0, side).forEach(j -> grid[i][j] = new EmptyCell()));
    }

    public Cell getCell(int x, int y){
        return grid[x][y];
    }

    public int getSide() {
        return side;
    }
}
