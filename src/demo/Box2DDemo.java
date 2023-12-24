package demo;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import rebel.graphics.*;
import rebel.physics.Fixtures;

public class Box2DDemo {


    public static void main(String[] args) {
        Window window = new Window(640, 480, "Rebel");
        Renderer2D renderer2D = new Renderer2D(640, 480, true);
        World world = new World(new Vec2(0, 9.8f * 12));

        //meters to pixels, p = m * 2
        float box2DToScreen = 2f;


        Block block = new Block(BodyType.DYNAMIC, world, new Rect2D(20, 0, 50, 20));
        Block block2 = new Block(BodyType.STATIC, world, new Rect2D(0, 150, 100, 70));
        Block block3 = new Block(BodyType.DYNAMIC, world, new Rect2D(30, 70, 250, 13));


        while (!window.shouldClose()) {
            renderer2D.clear(0f, 0f, 0f, 1.0f);


            block.render(renderer2D, box2DToScreen);
            block2.render(renderer2D, box2DToScreen);
            block3.render(renderer2D, box2DToScreen);

            world.step(1/60f, 8, 3);
            renderer2D.render();
            window.update();
        }


        window.close();
    }

    static class Block {

        private Rect2D rect2D;
        private Color color;
        private Body body;

        public Block(BodyType bodyType, World world, Rect2D rect2D) {
            this.rect2D = rect2D;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = bodyType;
            bodyDef.position.set(rect2D.x, rect2D.y);
            body = world.createBody(bodyDef);
            body.createFixture(Fixtures.Box(rect2D.w / 2f, rect2D.h / 2f, 0.1f, 0.4f, 0.9f));

            color = new Color((float) Math.random() + 0.5f, (float) Math.random() + 0.5f, (float) Math.random() + 0.5f, 1f);
        }

        public void render(Renderer2D renderer2D, float box2DToScreen){
            Vec2 playerBodyPos = body.getPosition().mul(box2DToScreen);
            renderer2D.setOrigin(playerBodyPos.x/* + ((50 * box2DToScreen) / 2)*/, playerBodyPos.y/* + ((20 * box2DToScreen) / 2)*/);
            renderer2D.rotate(body.getAngle());
            renderer2D.drawFilledRect(playerBodyPos.x, playerBodyPos.y, (rect2D.w) * box2DToScreen, (rect2D.h) * box2DToScreen, color);
            renderer2D.resetTransform();
            renderer2D.setOrigin(0, 0);
        }
    }


}