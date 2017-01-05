package info.magat.mapeditor.color;

import java.util.Objects;

public class Color {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color SLATE_GREY = new Color(112, 128, 144);
    public static final Color WHITE = new Color(255, 255, 255);

    public final int red;
    public final int green;
    public final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(float red, float green, float blue) {
        this.red = Math.round(red * 255f);
        this.green = Math.round(green * 255f);
        this.blue = Math.round(blue * 255f);
    }

    public void apply(ColoredMethod method) {
        method.apply(this.red / 255f, this.green / 255f, this.blue / 255f);
    }

    public void apply(ColoredAlphaMethod method) {
        method.apply(this.red / 255f, this.green / 255f, this.blue / 255f, 1.0f);
    }

    public static Color random() {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        return Float.compare(color.red, red) == 0 &&
                Float.compare(color.green, green) == 0 &&
                Float.compare(color.blue, blue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

}
