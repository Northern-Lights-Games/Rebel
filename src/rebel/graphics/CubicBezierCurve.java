package rebel.graphics;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class CubicBezierCurve implements BezierCurve {

    private Vector2f p0, p1, p2, p3;

    public CubicBezierCurve(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public List<Line> calculate(int lines) {

        float step = 1f / lines;

        Line a = new Line(p0, p1);
        Line b = new Line(p2, p3);
        Line c = new Line(a.end, b.start);

        Line ab = new Line(null, null);
        Line bc = new Line(null, null);
        Line abToBc = new Line(null, null);

        ArrayList<Line> lineList = new ArrayList<>();


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

            lineList.add(new Line(new Vector2f(lastX, lastY), tracer));

            lastX = tracer.x;
            lastY = tracer.y;
        }

        lineList.add(new Line(new Vector2f(lastX, lastY), new Vector2f(b.end.x, b.end.y)));

        return lineList;
    }
}
