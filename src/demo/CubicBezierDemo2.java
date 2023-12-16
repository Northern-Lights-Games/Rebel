package demo;

import org.joml.Vector2f;
import rebel.Input;
import rebel.Math;
import rebel.Time;
import rebel.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class CubicBezierDemo2 {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Bezier Curves");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        CubicBezierCurve cubicBezierCurve = new CubicBezierCurve(
                new Vector2f(200, 500),
                new Vector2f(600, 100),
                new Vector2f(900, 1100),
                new Vector2f(1500, 500)
        );


        List<Line> lines = cubicBezierCurve.calculate(15);













        while(!window.shouldClose()){
            renderer2D.clear(0f, 0f, 0f, 1f);



            for(Line line : lines){
                renderer2D.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, Color.WHITE, 10);
            }



            renderer2D.render();
            window.update();
        }

        window.close();



    }
}
