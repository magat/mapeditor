package info.magat.mapeditor.drawable;

import info.magat.mapeditor.color.Color;

import java.util.stream.IntStream;

public class Map implements Drawable{

    private int side;
    private Drawable[][] grid;

    public Map(int side) {
        this.side = side;
        grid = new Drawable[side][side];
        IntStream.range(0, side).forEach(i -> IntStream.range(0, side).forEach(j -> grid[i][j] = new Square(Color.random())));
    }

    public int getSide() {
        return side;
    }

    @Override
    public void draw(float x, float y, float width, float height) {
        float cellSide = Math.min(width, height) / side;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                grid[i][j].draw(x + i * cellSide, y + j * cellSide, cellSide, cellSide);
            }
        }
    }
}
