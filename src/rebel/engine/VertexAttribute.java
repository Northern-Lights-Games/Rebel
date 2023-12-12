package rebel.engine;

public class VertexAttribute {
    public int index;
    public int size;
    public boolean normalized;
    public String name;

    public VertexAttribute(int index, int size, boolean normalized, String name) {
        this.index = index;
        this.size = size;
        this.normalized = normalized;
        this.name = name;
    }
}
