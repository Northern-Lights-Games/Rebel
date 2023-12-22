package rebel.graphics;

/***
 * Represents an OpenGL color. The RGBA values range from 0-1
 */
public class Color {
    public float r, g, b, a;

    public static final Color RED = new Color(1f, 0f, 0f, 1f);
    public static final Color GREEN = new Color(0f, 1f, 0f, 1f);
    public static final Color BLUE = new Color(0f, 0f, 1f, 1f);
    public static final Color BLACK = new Color(0f, 0f, 0f, 1f);
    public static final Color WHITE = new Color(1f, 1f, 1f, 1f);
    public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1f);
    public static final Color LIGHT_GRAY = new Color(0.8f, 0.8f, 0.8f, 1f);

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color fromRGB(float r, float g, float b, float a){
        float scale = 1 / 255f;
        return new Color(scale * r, scale * g, scale * b, scale * a);
    }


}
