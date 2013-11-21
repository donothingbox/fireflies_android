package donothingbox.game.controller;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import com.example.fireflies_android.*;

/*
 * Long term, this class will manage all audio, abstracting its mgmt from normal usage, as well as mem management 
 * 
 * Most of it's hacky for now, TODO make this "actually" work the way it should
 * 
 */

public class AudioController {
	
	private static SoundPool soundPool;
	private static int soundID;
	private static int soundIDMiss;
	private static int mMuiscId;
    private static MediaPlayer mp = new MediaPlayer();
	static boolean loaded = false;
	private static Context sContext;

	public static void init(final Context context){
		
		sContext = context;
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	          @Override
	          public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
	            loaded = true;
	          }
	        });

	    soundID = soundPool.load(sContext, R.raw.beep, 1);
	    soundIDMiss = soundPool.load(sContext, R.raw.miss, 1);
	}

	public static void playAudio()
	{
        AudioManager audioManager = (AudioManager) sContext.getSystemService(android.content.Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager
            .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
        soundPool.play(soundID, volume, volume, 1, 0, 1f);
	}
	

	public static void playMedia()
	{
		  mp = MediaPlayer.create(sContext, mMuiscId); 
	      mp.setLooping(true);
	      mp.start();
	}
	
	public static void playMiss()
	{
        AudioManager audioManager = (AudioManager) sContext.getSystemService(android.content.Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager
            .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
        soundPool.play(soundIDMiss, volume, volume, 1, 0, 1f);

	}
}


