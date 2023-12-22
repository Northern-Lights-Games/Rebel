package rebel.graphics;

/***
 * A fast array-based implementation for looking up Textures in existing slots
 */
public class FastTextureLookup {
    public Texture[] textures;
    public int capacity;
    public FastTextureLookup(int capacity) {
        this.capacity = capacity;
        textures = new Texture[capacity];
    }
    public boolean hasTexture(Texture texture){
        for(Texture t : textures){
            if(t == texture) return true;
        }
        return false;
    }
    public void registerTexture(Texture texture, Integer glSlot){
        textures[glSlot] = texture;
    }
    public int getTexture(Texture texture){
        for (int i = 0; i < textures.length; i++) {
            Texture t = textures[i];
            if (t == texture) {
                return i;
            }
        }
        return 0;
    }

    public void clear(){
        for (int i = 0; i < capacity; i++) {
            textures[i] = null;
        }
    }
}
