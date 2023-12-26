package rebel.audio;

public class AudioPlayEvent extends AudioEvent {
    public Audio audio;

    public AudioPlayEvent(Audio audio) {
        this.audio = audio;
    }
}
