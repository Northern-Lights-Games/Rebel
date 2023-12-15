package rebel.graphics;

import org.joml.Vector2f;

public class Line {
    public Vector2f start;
    public Vector2f end;

    public Line(Vector2f start, Vector2f end) {
        this.start = start;
        this.end = end;
    }

    public Vector2f tValue(float t){
        return new Vector2f(start.x + ((end.x - start.x) * t), start.y + ((end.y - start.y) * t));
    }


}
