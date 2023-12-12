package demo;

import rebel.engine.Rect2D;
import rebel.engine.Texture;

public class Tile {
    public Rect2D rect2D;
    public Texture texture;
    public Tile(Rect2D rect2D, Texture texture){
        this.rect2D = rect2D;
        this.texture = texture;

    }
}
