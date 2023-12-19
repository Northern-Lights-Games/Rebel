package rebel;

import rebel.graphics.Rect2D;
import rebel.graphics.Texture;

import java.util.ArrayList;

public class Animation {
    private Texture texture;
    private ArrayList<Rect2D> frames = new ArrayList<>();
    private float delay;
    private float start = 0;
    private int currentIndex = 0;
    private int numOfFrames = 0;
    public void update(float deltaTime) {
        start += deltaTime * 1000;
    }



    public enum Playmode {
        PLAY,
        PLAY_REPEAT
    }

    private Playmode playmode;

    public Animation(Texture texture) {
        this.texture = texture;
    }

    public void create(int rows, int columns, int num){
        this.numOfFrames = num;
        float frameWidth = 1f / rows;
        float frameHeight = 1f / columns;


        int currentFrame = 0;

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                frames.add(new Rect2D(row * frameWidth, col * frameHeight, frameWidth, frameHeight));
                currentFrame++;

                if(currentFrame == num) return;
            }
        }
    }

    public Rect2D getCurrentFrame(){

        if(start >= delay){

            if(playmode == Playmode.PLAY_REPEAT){
                currentIndex++;
                if(currentIndex == numOfFrames) currentIndex = 0;
            }
            if(playmode == Playmode.PLAY){
                if(currentIndex != (numOfFrames - 1))
                    currentIndex++;
                System.out.println(currentIndex);
            }

            start = 0;
        }

        return frames.get(currentIndex);
    }

    public ArrayList<Rect2D> getFrames() {
        return frames;
    }

    public Texture getTexture() {
        return texture;
    }


    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public Playmode getPlaymode() {
        return playmode;
    }

    public void setPlaymode(Playmode playmode) {
        this.playmode = playmode;
    }

}
