package rebel.graphics;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/***
 * Represents a cubic Bézier curve with the specified control points
 */
public class CubicBezierCurve2D implements BezierCurve2D {

    private Vector2f p0, p1, p2, p3;

    public CubicBezierCurve2D(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public List<Line2D> calculate(int lines) {

        float step = 1f / lines;

        Line2D a = new Line2D(p0, p1);
        Line2D b = new Line2D(p2, p3);
        Line2D c = new Line2D(a.end, b.start);

        Line2D ab = new Line2D(null, null);
        Line2D bc = new Line2D(null, null);
        Line2D abToBc = new Line2D(null, null);

        ArrayList<Line2D> line2DList = new ArrayList<>();


        float lastX = a.start.x, lastY = a.start.y;

        for (float i = 0.0f; i < 1.0f; i += step) {
            System.out.println(i);

            ab.start = a.tValue(i);
            ab.end = c.tValue(i);

            bc.start = c.tValue(i);
            bc.end = b.tValue(i);

            abToBc.start = ab.tValue(i);
            abToBc.end = bc.tValue(i);

            Vector2f tracer = abToBc.tValue(i);

            line2DList.add(new Line2D(new Vector2f(lastX, lastY), tracer));

            lastX = tracer.x;
            lastY = tracer.y;
        }

        line2DList.add(new Line2D(new Vector2f(lastX, lastY), new Vector2f(b.end.x, b.end.y)));

        return line2DList;
    }
}
