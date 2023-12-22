package rebel.graphics;

import org.joml.Vector2f;


public class Line2D {
    public Vector2f start;
    public Vector2f end;

    public Line2D(Vector2f start, Vector2f end) {
        this.start = start;
        this.end = end;
    }

    /***
     * This is used to calculate Bézier curves, where any point on this Line can be calculated using the formula:
     * start + t(end - start)
     * @param t Between 0-1
     * @return The point along the curve represented by t
     */
    public Vector2f tValue(float t){
        return new Vector2f(start.x + ((end.x - start.x) * t), start.y + ((end.y - start.y) * t));
    }


}
