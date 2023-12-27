package rebel.audio;

/***
 * An Audio instance represents a sound loaded into an OpenAL buffer.
 * NOTE! This class is not thread-safe and should only be managed by an instance of AudioEngine.
 * Do not attempt to call OpenAL functions on the application/main thread!
 */
public class Audio {
    float duration;
    private boolean ready;
    protected int id;
    protected int source;

    public String audioPath;

    /***
     * Creates an Audio instance from a .wav file specified in the following path.
     * @param audioPath
     */

    public Audio(String audioPath) {
        this.audioPath = audioPath;
    }

    /***
     * Returns whether the Audio sample has been processed through an Audio Engine at least once.
     * This method is useful for determining if the OpenAL buffers and other data have been initialized
     * for this particular Audio sample
     *
     * @return ready
     */
    public boolean isReady() {
        return ready;
    }

    protected void setReady(boolean ready) {
        this.ready = ready;
    }
}
