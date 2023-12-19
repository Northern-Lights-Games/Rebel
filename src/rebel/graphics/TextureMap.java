package rebel.graphics;

public class TextureMap {
    public Texture[] keys;
    public Integer[] values;

    public int capacity;

    public TextureMap(int capacity) {
        this.capacity = capacity;
        keys = new Texture[capacity];
        values = new Integer[capacity];


    }

    public boolean containsKey(Texture texture){
        for(Texture t : keys){
            if(t == texture) return true;
        }
        return false;
    }

    public void put(Texture texture, Integer slot){
        keys[slot] = texture;
        values[slot] = slot;
    }

    public int get(Texture texture){

        int index = 0;

        for (int i = 0; i < keys.length; i++) {
            Texture t = keys[i];
            if (t == texture) index = i;
        }

        return values[index];
    }

    public void clear(){
        for (int i = 0; i < capacity; i++) {
            keys[i] = null;
            values[i] = null;
        }
    }


}
