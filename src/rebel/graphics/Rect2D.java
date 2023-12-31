package rebel.graphics;


public class Rect2D {
    public float x, y, w, h;

    public Rect2D(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rect2D mul(float f){
        x *= f;
        y *= f;
        w *= f;
        h *= f;

        return this;
    }

    public boolean overlaps(Rect2D other){
        return contains(other.x, other.y)
                || contains(other.x + other.w, other.y)
                || contains(other.x + other.w, other.y + other.h)
                || contains(other.x, other.y + other.h);
    }

    public boolean contains(float x, float y){
        return (x >= this.x && x <= this.x + this.w) && (y >= this.y && y <= this.y + this.h);
    }

    @Override
    public String toString() {
        return "Rect2D [" + x + " " + y + " " + w + " " + h + "]";
    }
}
