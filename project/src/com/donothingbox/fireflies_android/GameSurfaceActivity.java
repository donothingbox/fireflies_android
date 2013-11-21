package com.donothingbox.fireflies_android;

import donothingbox.game.controller.AudioController;
import donothingbox.game.controller.StateController;
import donothingbox.game.view.GameLayout;
import donothingbox.game.view.GameSurfaceView;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.example.fireflies_android.R;

/*
 *  This is the blitted game surface, for direct bitmap rendering
 * 
 */

public class GameSurfaceActivity extends Activity {

    protected CoreApp m_CoreApp;
    private GameSurfaceView m_gameSurface;
    boolean loaded = false;

    @Override
    //This activity spawns a Blitable surface with it's own thread
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //Init some controllers
        StateController.init(this);
        AudioController.init(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameLayout gameLayout = new GameLayout(this);
        m_gameSurface = gameLayout.mGameSurface;
        setContentView(gameLayout);
    }
    
    protected void onDestroy() {        
        //clearReferences();
        super.onDestroy();
    }
    
    protected void onPause() {
    	m_gameSurface.pauseGameSurfaceThread();
		System.out.println ("pause called");
        super.onPause();
    }
    
    protected void onResume() {
        super.onResume();
       // mCoreApp.setCurrentActivity(this);
    }

    private void clearReferences(){
        Activity currActivity = m_CoreApp.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
        	m_CoreApp.setCurrentActivity(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
