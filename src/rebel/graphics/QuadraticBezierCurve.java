package rebel.graphics;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class QuadraticBezierCurve implements BezierCurve {

    private Vector2f p0, p1, p2;

    public QuadraticBezierCurve(Vector2f p0, Vector2f p1, Vector2f p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public List<Line> calculate(int lines) {

        float step = 1f / lines;




        Line a = new Line(p0, p1);
        Line b = new Line(a.end, p2);
        Line c = new Line(null, null);
        ArrayList<Line> lineList = new ArrayList<>();

        float lastX = a.start.x, lastY = a.start.y;

        for (float i = 0; i < 1; i += step) {
            c.start = a.tValue(i);
            c.end = b.tValue(i);
            Vector2f tracer = c.tValue(i);

            lineList.add(new Line(new Vector2f(lastX, lastY), tracer));
            lastX = tracer.x;
            lastY = tracer.y;
        }

        lineList.add(new Line(new Vector2f(lastX, lastY), new Vector2f(b.end.x, b.end.y)));

        return lineList;
    }
}
