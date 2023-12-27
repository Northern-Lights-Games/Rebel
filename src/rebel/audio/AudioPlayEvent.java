package rebel.audio;

/***
 * Represents an event that signals to an AudioEngine that the user would like to play the specified sound.
 */
public class AudioPlayEvent extends AudioEvent {
    public Audio audio;

    public AudioPlayEvent(Audio audio) {
        this.audio = audio;
    }
}
