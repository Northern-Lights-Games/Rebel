package rebel.graphics;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
/***
 * Represents a quadratic Bézier curve with the specified control points
 */
public class QuadraticBezierCurve implements BezierCurve {

    private Vector2f p0, p1, p2;

    public QuadraticBezierCurve(Vector2f p0, Vector2f p1, Vector2f p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public List<Line2D> calculate(int lines) {

        float step = 1f / lines;




        Line2D a = new Line2D(p0, p1);
        Line2D b = new Line2D(a.end, p2);
        Line2D c = new Line2D(null, null);
        ArrayList<Line2D> line2DList = new ArrayList<>();

        float lastX = a.start.x, lastY = a.start.y;

        for (float i = 0; i < 1; i += step) {
            c.start = a.tValue(i);
            c.end = b.tValue(i);
            Vector2f tracer = c.tValue(i);

            line2DList.add(new Line2D(new Vector2f(lastX, lastY), tracer));
            lastX = tracer.x;
            lastY = tracer.y;
        }

        line2DList.add(new Line2D(new Vector2f(lastX, lastY), new Vector2f(b.end.x, b.end.y)));

        return line2DList;
    }
}
