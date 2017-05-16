package kp.project.brainteaser;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by KP Team on 15/05/2017.
 */

public class SoundPlayer {

    private static SoundPool sound;
    private static int correctSound;
    private static int wrongSound;
    private float soundVolume;

    public SoundPlayer(Context context, float soundVolume)
    {
        sound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        correctSound = sound.load(context, R.raw.correct,1);
        wrongSound = sound.load(context, R.raw.wrong,1);
        this.soundVolume = soundVolume;
    }


    public void playCorrect()
    {

        sound.play(correctSound, soundVolume, soundVolume, 1,  0,1.0f);
    }

    public void playWrong()
    {

        sound.play(wrongSound, soundVolume, soundVolume, 1,  0,1.0f);
    }





}
