package demo;

import org.joml.Vector2f;
import rebel.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class QuadraticBezierDemo2 {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Bezier Curves");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        QuadraticBezierCurve quadraticBezierCurve = new QuadraticBezierCurve(
                new Vector2f(100, 100),
                new Vector2f(200, 700),
                new Vector2f(700, 300)
        );

        List<Line> lines = quadraticBezierCurve.calculate(7);






        while(!window.shouldClose()){
            renderer2D.clear(0f, 0f, 0f, 1f);

            for(Line line : lines){
                renderer2D.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, Color.WHITE, 3);
            }


            renderer2D.render();
            window.update();
        }

        window.close();



    }
}
