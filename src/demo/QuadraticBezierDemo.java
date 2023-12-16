package demo;

import org.joml.Vector2f;
import rebel.Input;
import rebel.Math;
import rebel.Time;
import rebel.graphics.Color;
import rebel.graphics.Line;
import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

public class QuadraticBezierDemo {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Bezier Curves");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        Line a = new Line(new Vector2f(600, 600), new Vector2f(1000, 1000));
        Line b = new Line(a.end, new Vector2f(1200, 600));

        Line c = new Line(null, null);

        float t = 0;


        while(!window.shouldClose()){
            renderer2D.clear(0f, 0f, 0f, 1f);

            c.start = a.tValue(t);
            c.end = b.tValue(t);



            renderer2D.drawLine(a.start.x, a.start.y, a.end.x, a.end.y, Color.RED, 4);
            renderer2D.drawLine(b.start.x, b.start.y, b.end.x, b.end.y, Color.GREEN, 4);
            renderer2D.drawLine(c.start.x, c.start.y, c.end.x, c.end.y, Color.BLUE, 4);


            Vector2f tracer = c.tValue(t);

            renderer2D.drawFilledEllipse(tracer.x - 10, tracer.y - 10, 20, 20, Color.BLUE);

            if(window.isKeyPressed(Input.REBEL_KEY_LEFT)) t = Math.clamp(t - (2 * Time.deltaTime), 0, 1);
            if(window.isKeyPressed(Input.REBEL_KEY_RIGHT)) t = Math.clamp(t + (2 * Time.deltaTime), 0, 1);




            renderer2D.render();
            window.update();
        }

        window.close();



    }
}
