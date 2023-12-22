package rebel.graphics;

/***
 * A fast array-based implementation for looking up Textures in existing slots
 */
public class FastTextureLookup {
    public Texture2D[] textures;
    public int capacity;
    public FastTextureLookup(int capacity) {
        this.capacity = capacity;
        textures = new Texture2D[capacity];
    }
    public boolean hasTexture(Texture2D texture){
        for(Texture2D t : textures){
            if(t == texture) return true;
        }
        return false;
    }
    public void registerTexture(Texture2D texture, Integer glSlot){
        textures[glSlot] = texture;
    }
    public int getTexture(Texture2D texture){
        for (int i = 0; i < textures.length; i++) {
            Texture2D t = textures[i];
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
