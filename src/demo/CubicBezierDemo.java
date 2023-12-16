package demo;

import org.joml.Vector2f;
import rebel.Input;
import rebel.Math;
import rebel.Time;
import rebel.graphics.Color;
import rebel.graphics.Line;
import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

public class CubicBezierDemo {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Bezier Curves");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);







        Line a = new Line(new Vector2f(200, 500), new Vector2f(600, 100));
        Line b = new Line(new Vector2f(500 + 600, 1100), new Vector2f(500 + 1000, 500));
        Line c = new Line(a.end, b.start);

        Line ab = new Line(null, null);
        Line bc = new Line(null, null);
        Line abToBc = new Line(null, null);








        float t = 0;


        while(!window.shouldClose()){
            renderer2D.clear(0f, 0f, 0f, 1f);

            ab.start = a.tValue(t);
            ab.end = c.tValue(t);

            bc.start = c.tValue(t);
            bc.end = b.tValue(t);

            abToBc.start = ab.tValue(t);
            abToBc.end = bc.tValue(t);


            renderer2D.drawLine(a.start.x, a.start.y, a.end.x, a.end.y, Color.RED, 4);
            renderer2D.drawLine(b.start.x, b.start.y, b.end.x, b.end.y, Color.GREEN, 4);
            renderer2D.drawLine(c.start.x, c.start.y, c.end.x, c.end.y, Color.BLUE, 4);

            renderer2D.drawLine(ab.start.x, ab.start.y, ab.end.x, ab.end.y, Color.RED, 4);
            renderer2D.drawLine(bc.start.x, bc.start.y, bc.end.x, bc.end.y, Color.GREEN, 4);
            renderer2D.drawLine(abToBc.start.x, abToBc.start.y, abToBc.end.x, abToBc.end.y, Color.BLUE, 4);

            Vector2f tracer = abToBc.tValue(t);
            renderer2D.drawFilledEllipse(tracer.x - 20, tracer.y - 20, 40, 40, Color.GREEN);












            if(window.isKeyPressed(Input.REBEL_KEY_LEFT)) t = Math.clamp(t - (2 * Time.deltaTime), 0, 10);
            if(window.isKeyPressed(Input.REBEL_KEY_RIGHT)) t = Math.clamp(t + (2 * Time.deltaTime), 0, 10);




            renderer2D.render();
            window.update();
        }

        window.close();



    }
}
