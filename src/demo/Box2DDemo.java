package demo;

import org.joml.Vector2f;
import rebel.graphics.*;
import rebel.physics.*;

public class Box2DDemo {


    public static void main(String[] args) {
        Window window = new Window(640, 480, "Rebel");
        Renderer2D renderer2D = new Renderer2D(640, 480, true);


        Scene2D scene2D = new Scene2D(1f, new Vector2f(0, 9.8f * 12));

        RectBody2D r1 = scene2D.newRectBody2D(new Rect2D(50, 0, 200, 40), RigidBody2D.Type.DYNAMIC);
        r1.setPhysics(0.5f, 0.4f, 0.01f);

        RectBody2D r2 = scene2D.newRectBody2D(new Rect2D(0, 300, 250, 140), RigidBody2D.Type.STATIC);
        r2.setPhysics(0.5f, 0.4f, 0.01f);

        RectBody2D r3 = scene2D.newRectBody2D(new Rect2D(50, 140, 500, 26), RigidBody2D.Type.DYNAMIC);
        r3.setPhysics(0.5f, 0.4f, 0.06f);

        CircleBody2D c1 = scene2D.newCircleBody2D(new Vector2f(150, 70), 50, RigidBody2D.Type.DYNAMIC);
        c1.setPhysics(0.5f, 0.0f, 0.06f);




        Texture2D texture2D = new Texture2D("project/logo.png");

        while (!window.shouldClose()) {
            renderer2D.clear(1f, 1f, 1f, 1.0f);

            {
                renderer2D.setOrigin(r1.getOrigin());
                renderer2D.rotate(r1.getRotation());
                renderer2D.drawTexture(r1.getPosition().x, r1.getPosition().y, r1.getWidth(), r1.getHeight(), texture2D, Color.WHITE);
                renderer2D.resetTransform();
                renderer2D.setOrigin(0, 0);
            }

            {

                renderer2D.setOrigin(r2.getOrigin());
                renderer2D.rotate(r2.getRotation());
                renderer2D.drawFilledRect(r2.getPosition().x, r2.getPosition().y, r2.getWidth(), r2.getHeight(), Color.RED);
                renderer2D.resetTransform();
                renderer2D.setOrigin(0, 0);

            }
            {

                Vector2f r3Pos = r3.getPosition();
                renderer2D.setOrigin(r3Pos.x, r3Pos.y);
                renderer2D.rotate(r3.getRotation());
                renderer2D.drawFilledRect(r3Pos.x, r3Pos.y, r3.getWidth(), r3.getHeight(), Color.LIGHT_GRAY);
                renderer2D.resetTransform();
                renderer2D.setOrigin(0, 0);

            }
            {


                renderer2D.setOrigin(c1.getOrigin());
                renderer2D.rotate(r3.getRotation());
                renderer2D.drawEllipse(c1.getPosition().x, c1.getPosition().y, c1.getRadius() * 2, c1.getRadius() * 2, Color.GREEN, 0.05f);
                renderer2D.resetTransform();
                renderer2D.setOrigin(0, 0);

            }


            scene2D.update(1/60f);
            renderer2D.render();
            window.update();
        }


        window.close();
    }

}