package demo;


import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import rebel.Rect2D;
import rebel.Time;
import rebel.graphics.*;
import rebel.particles.Particle;
import rebel.particles.ParticleSource;
import rebel.particles.ParticleSourceConfig;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;

public class NotPianoTiles {

    enum State {
        PLAYING,
        GAME_OVER,
        YOU_WON
    }

    ArrayList<Tile> tiles = new ArrayList<>();

    public void createTiles(Renderer2D renderer2D, Texture logo){

        tiles.clear();


        for (int i = 0; i < 10; i++) {
            Rect2D rect2D = new Rect2D((float) (
                    renderer2D.getWidth() * Math.random()),
                    (float) ((renderer2D.getHeight() / 2) * Math.random()),
                    100,
                    100
            );

            tiles.add(new Tile(rect2D, logo));
        }
    }


    public void play(){
        Window window = new Window(1920, 1080, "Not Piano Tiles");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        Texture logo = new Texture("project/logo.png");
        FontRes font = new FontRes("Consolas", FontRes.NORMAL, 40, true);

        State gameState = State.PLAYING;
        int tries = 10;


        createTiles(renderer2D, logo);

        ParticleSourceConfig p = new ParticleSourceConfig();
        p.w = 20;
        p.h = 20;
        p.vx = 80;
        p.vy = 80;
        p.particleLifetime = 3000;
        p.scale = 7;

        ParticleSource particleSource = new ParticleSource(p, new Vector2f(0, 0));
        particleSource.addParticles(50);


        int fails = 0;

        float time = 500;


        while (!window.shouldClose()) {
            renderer2D.clear(1f, 1f, 1f, 1.0f);

            if(gameState == State.YOU_WON){
                renderer2D.drawText(0, 0, "You won! Press Space to restart!", Color.BLUE, font);
                if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    fails = 0;
                    gameState = State.PLAYING;
                    createTiles(renderer2D, logo);
                }
            }

            if(gameState == State.PLAYING) {


                for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
                    Tile tile = iterator.next();


                    renderer2D.drawTexture(tile.rect2D.x, tile.rect2D.y, tile.rect2D.w, tile.rect2D.h, tile.texture);

                    if (tile.rect2D.y >= renderer2D.getHeight()) {
                        tile.rect2D.y = -tile.rect2D.h;
                        fails++;
                    }

                    tile.rect2D.y += 300 * Time.deltaTime;

                    if (tile.rect2D.contains(window.getMouseX(), window.getMouseY())) {
                        iterator.remove();
                        time = 500;
                        particleSource.setSource(new Vector2f(window.getMouseX(), window.getMouseY()));
                    }
                }



                if(time > 0) {
                    particleSource.update();
                    time -= Time.deltaTime * 1000;

                    for(Particle particle : particleSource.getParticles()){
                        renderer2D.drawFilledEllipse(particle.rect2D.x, particle.rect2D.y, particle.rect2D.w, particle.rect2D.h, Color.BLACK);
                    }
                }
                else {
                    particleSource.getParticles().clear();
                    particleSource.addParticles(50);
                }







                if(tiles.isEmpty()) {
                    gameState = State.YOU_WON;
                }




                renderer2D.drawText(0, 0, "Destroy the " + tiles.size() + " invading tiles!" + " // Chances: " + (tries - fails), Color.BLUE, font);

                if(fails == tries) gameState = State.GAME_OVER;
            }
            if(gameState == State.GAME_OVER){
                renderer2D.drawText(0, 0, "Game over! :( Press Space to restart!", Color.BLUE, font);
                if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    fails = 0;
                    gameState = State.PLAYING;
                    createTiles(renderer2D, logo);
                }
            }

            renderer2D.render();


            window.update();
        }


        window.close();
    }

    public static void main(String[] args) {
        new NotPianoTiles().play();
    }


}