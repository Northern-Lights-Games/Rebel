package newapi;

import rebel.engine.*;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Game {
    private static HashMap<String, Texture> textureCache = new HashMap<>();
    private static Renderer2D renderer2D;

    public static void clearBackground(float r, float g, float b, float a){
        renderer2D.clear(r, g, b, a);
    }

    public static void drawTexture(String path, float x, float y, float w, float h){
        if(!textureCache.containsKey(path)) textureCache.put(path, new Texture(path));
        renderer2D.drawTexture(x, y, w, h, textureCache.get(path));
    }

    public static int gameWidth(){
        return renderer2D.getWidth();
    }

    public static int gameHeight(){
        return renderer2D.getHeight();
    }



    public static void play(String title, int width, int height, GameLoop gameLoop){

        Window window = new Window(width, height, "Rebel");
        renderer2D = new Renderer2D(width, height);


        window.setTitle(title);



        double start = glfwGetTime();




        while (!window.shouldClose()) {

            double timeNow = glfwGetTime();
            double deltaTime = timeNow - start;
            start = timeNow;


            Time.deltaTime = (float) deltaTime;
            gameLoop.draw();


            renderer2D.render();

            Tools.logRenderCalls(renderer2D);




            renderer2D.finished();
            window.update();
        }


        window.close();

    }


    public interface GameLoop {
        void draw();
    }


}
