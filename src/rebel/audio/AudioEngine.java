package rebel.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.openal.ALC11.ALC_DEFAULT_ALL_DEVICES_SPECIFIER;
import static org.lwjgl.openal.EXTEfx.ALC_MAX_AUXILIARY_SENDS;
import static org.lwjgl.system.MemoryUtil.NULL;

/***
 * Represents an OpenAL context and plays Audio instances. All Audio samples and Audio Events are processed on a
 * separate thread that manages the OpenAL context.
 *
 * NOTE! Do not attempt to call OpenAL functions on the application/main thread!
 */

public class AudioEngine {
    private Thread thread;
    private BlockingQueue<AudioEvent> audioQueue = new LinkedBlockingQueue<>();
    public AudioEngine(){
        thread = new Thread(() -> {

            long device = ALC10.alcOpenDevice((ByteBuffer) null);
            ALCCapabilities deviceCapabilities = ALC.createCapabilities(device);
            IntBuffer contextAttribList = BufferUtils.createIntBuffer(16);

            contextAttribList.put(ALC_REFRESH);
            contextAttribList.put(60);

            contextAttribList.put(ALC_SYNC);
            contextAttribList.put(ALC_FALSE);

            contextAttribList.put(ALC_MAX_AUXILIARY_SENDS);
            contextAttribList.put(2);

            contextAttribList.put(0);
            contextAttribList.flip();

            long newContext = ALC10.alcCreateContext(device, contextAttribList);

            if(!ALC10.alcMakeContextCurrent(newContext)) {
                throw new RuntimeException("Failed to create OpenAL Context");
            }

            AL.createCapabilities(deviceCapabilities);

            System.out.println(alcGetString(NULL, ALC_DEFAULT_ALL_DEVICES_SPECIFIER));


            {
                AL10.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f);
                AL10.alListener3f(AL10.AL_ORIENTATION, 0f, 0f, -1f);
            }



            while(true) {

                Iterator<AudioEvent> iter = audioQueue.iterator();
                while (iter.hasNext()) {
                    AudioEvent event = iter.next();

                    if(event instanceof AudioPlayEvent audioPlayEvent){

                        if(!audioPlayEvent.audio.isReady()){
                            audioPlayEvent.audio.id = AL10.alGenBuffers();

                            float duration = loadAudioWAV(audioPlayEvent.audio);

                            audioPlayEvent.audio.duration = duration;
                            audioPlayEvent.audio.source = AL10.alGenSources();

                            AL10.alSourcei(audioPlayEvent.audio.source, AL10.AL_BUFFER, audioPlayEvent.audio.id);
                            AL10.alSource3f(audioPlayEvent.audio.source, AL10.AL_POSITION, 0f, 0f, 0f);
                            AL10.alSource3f(audioPlayEvent.audio.source, AL10.AL_VELOCITY, 0f, 0f, 0f);
                            AL10.alSourcef(audioPlayEvent.audio.source, AL10.AL_PITCH, 1);
                            AL10.alSourcef(audioPlayEvent.audio.source, AL10.AL_GAIN, 1f);
                            AL10.alSourcei(audioPlayEvent.audio.source, AL10.AL_LOOPING, AL10.AL_FALSE);

                            audioPlayEvent.audio.setReady(true);
                        }

                        AL10.alSourcePlay(audioPlayEvent.audio.source);

                        try {
                            Thread.sleep((long) audioPlayEvent.audio.duration);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        AL10.alSourceStop(audioPlayEvent.audio.source);
                    }




                    iter.remove();
                }

                //The Audio Queue is empty
            }

        });

        thread.setName("Audio Thread");
        thread.start();
    }

    /***
     * Dispatches an AudioPlayEvent to play the specified Audio instance on the OpenAL thread.
     * This method doesn't block the main thread.
     * @param audio
     */

    public void play(Audio audio){
        audioQueue.add(new AudioPlayEvent(audio));
    }

    private float loadAudioWAV(Audio audio){


        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(new File(audio.audioPath));
        }
        catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        AudioFormat audioFormat = stream.getFormat();
        WaveData waveData = WaveData.create(stream);
        AL10.alBufferData(audio.id, waveData.format, waveData.data, waveData.samplerate);

        return (long)(1000f * stream.getFrameLength() / audioFormat.getFrameRate());
    }




}
