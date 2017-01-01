package info.magat.mapeditor.color;


@FunctionalInterface
public interface ColoredAlphaMethod {

    void apply(float red, float green, float blue, float alpha);
}
