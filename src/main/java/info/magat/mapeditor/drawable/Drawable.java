package info.magat.mapeditor.drawable;


import java.util.stream.Stream;

public abstract class Drawable {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected abstract void draw();

    public void draw(float x, float y, float width, float height){
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        draw();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Stream<Drawable> elements(){
        return Stream.of(this);
    }
}
