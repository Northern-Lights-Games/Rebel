package rebel;

import rebel.graphics.Rect2D;
import rebel.graphics.Texture2D;

import java.util.ArrayList;


/***
 * An Animation is made from a spritesheet texture. You can specify the delay, number of frames and set the Playmode.
 */
public class Animation {
    private Texture2D texture;
    private ArrayList<Rect2D> frames = new ArrayList<>();
    private float delay;
    private float start = 0;
    private int currentIndex = 0;
    private int numOfFrames = 0;

    /***
     * Updates the Animation
     * @param deltaTime
     */
    public void update(float deltaTime) {
        start += deltaTime * 1000;
    }


    /***
     * The Animation Playmode determines if the Animation should loop
     */
    public enum Playmode {
        PLAY,
        PLAY_REPEAT
    }

    private Playmode playmode;

    /***
     * Create An Animation object from the following Texture
     * @param texture
     */
    public Animation(Texture2D texture) {
        this.texture = texture;
    }

    /***
     * Constructs the individual frames of an Animation from the source Texture.
     * @param rows The rows in the spritesheet
     * @param columns The columns in the spritesheet
     * @param num The number of actual frames. Some spritesheets can have a few empty frames at the end
     */
    public void create(int rows, int columns, int num){
        this.numOfFrames = num;
        float frameWidth = 1f / columns;
        float frameHeight = 1f / rows;


        int currentFrame = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                frames.add(new Rect2D(
                        col * frameWidth,
                        row * frameHeight,
                        (col * frameWidth) + frameWidth,
                        (row * frameHeight) + frameHeight
                ));

                currentFrame++;

                if(currentFrame == num) return;
            }
        }
    }

    /***
     * Gets the UV coordinates of the current frame
     * @return
     */
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

    public Texture2D getTexture() {
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
