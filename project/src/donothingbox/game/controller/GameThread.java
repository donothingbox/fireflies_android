package donothingbox.game.controller;

import donothingbox.game.view.GameSurfaceView;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class GameThread extends Thread {

private SurfaceHolder mSurfaceHolder;
private GameSurfaceView mSurfaceView;
private Canvas canvas;
private boolean m_isRunning = false;

/*
 *   Game Thread that pushes drawing on GameSurfaceView
 *   TODO setup with throttled FPS mgmt based off of max (60FPS) and performance (downgrade FPS on slower devices)
 */

public GameThread(SurfaceHolder holder,GameSurfaceView surfaceView) {

	mSurfaceHolder = holder;
	mSurfaceView = surfaceView;
}

public void setRunnable(boolean run) {
	m_isRunning = run;
}

public void run() {
	//TODO this try is a "patch" to prevent a thrown error on exit. Thread not properly cleaned up, need to trace down real reason
	try{
	    while(m_isRunning) {
	        canvas = null;
	        try {
	            canvas = mSurfaceHolder.lockCanvas(null);
	            synchronized(mSurfaceHolder) {
	            	mSurfaceView.onDraw(canvas);
	            }
	        } finally {
	            if(canvas != null && mSurfaceHolder != null) {
	            	mSurfaceHolder.unlockCanvasAndPost(canvas);
	            }
	        }
	    }
    } catch(RuntimeException e){
    	System.out.println("This needs to be cleaned up better: " + e);
    }
}

public Canvas getCanvas() {

    if(canvas != null) {

        return canvas;

    } else {

        return null;
    }
}
}