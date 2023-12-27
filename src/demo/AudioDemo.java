package demo;

import rebel.Input;
import rebel.Time;
import rebel.audio.Audio;
import rebel.audio.AudioEngine;
import rebel.graphics.*;

public class AudioDemo {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080, true);
        AudioEngine audioEngine = new AudioEngine();

        boolean b = true;
        float rotation = 0f;
        Texture2D logo = new Texture2D("project/logo.png");
        Audio cheer = new Audio("cheering.wav");
        Audio crash = new Audio("synth.wav");

        Font2D font2D = new Font2D("Arial", Font2D.NORMAL, 20);

        float waitMs = 0f;


        while(!window.shouldClose()){
            renderer2D.clear(new Color(1f, 1f, 1f, 1f));

            if(window.isKeyPressed(Input.REBEL_KEY_Q) && waitMs >= 3000){
                audioEngine.play(cheer);
                waitMs = 0;
            }
            if(window.isKeyPressed(Input.REBEL_KEY_W) && waitMs >= 3000){
                audioEngine.play(crash);
                waitMs = 0;
            }

            waitMs += Time.deltaTime * 1000;

            {
                renderer2D.setOrigin(1000 + 250 / 2f, 300 + 250 / 2f);
                renderer2D.scale(2f, 2f, 1f);
                renderer2D.rotate((float) Math.toRadians(rotation));
                renderer2D.drawTexture(1000, 300, 250, 250, logo);
                renderer2D.resetTransform();
                renderer2D.setOrigin(0, 0);

                rotation += 5 * Time.deltaTime;

                renderer2D.drawText(0, 0, String.valueOf(waitMs), Color.BLUE, font2D);

            }


            renderer2D.render();
            window.update();
        }

        window.close();






    }
}
