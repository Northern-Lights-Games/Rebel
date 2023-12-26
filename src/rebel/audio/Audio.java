package rebel.audio;

public class Audio {
    public float duration;
    private boolean ready;
    protected int id;
    protected int source;

    public String audioPath;

    public Audio(String audioPath) {
        this.audioPath = audioPath;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }


}
