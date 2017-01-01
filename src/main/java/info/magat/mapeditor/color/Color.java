package info.magat.mapeditor.color;

public class Color {

    public static final Color RED = new Color(1.0f, 0.0f,0.0f,1.0f);
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color SLATE_GREY = new Color(112,128,144);

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    private Color(int red, int green, int blue){
        this.red = red / 255f;
        this.green = green / 255f;
        this.blue = blue / 255f;
        this.alpha = 1.0f;
    }

    private Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public void apply(ColoredMethod method){
        method.apply(this.red, this.green, this.blue);
    }

    public void apply(ColoredAlphaMethod method){
        method.apply(this.red, this.green, this.blue, this.alpha);
    }

    public static Color random(){
        return new Color((float) Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random());
    }
}
