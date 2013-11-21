package donothingbox.game.controller;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

/*
 *  StateController, eventually this will manage state switching, from new activities, to states within the same activity. 
 * 
 *  Also, all scaling logic will eventually live here. 
 * 
 */

public class StateController {
	private static Context sContext;
	private static boolean DISPLAY_TRACES = true; //used for class debugging
	
	private static Rect sScreenRect;
	public static float sScaleFactor = 1.0f;

	private StateController(){}
	
	//StateController holds a static ref globally accessable
	public static void init(Context context){
		sContext = context;
	}
	
	//get the screen rectangle
	public static Rect getScreenSize(){
		if(sScreenRect == null){
			WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			
			Rect returnRect = new Rect();
			//depricated, but use prior to API Level 13
			
			//Point dispPoint = new Point();
			//display.getSize(dispPoint);
			//returnRect.set(0, 0, dispPoint.x, dispPoint.y);
			returnRect.set(0, 0, display.getWidth(), display.getHeight());
			StateController.classTrace("Screen Rect: " + returnRect.left + ":" + returnRect.bottom);
			sScreenRect = returnRect;
		}
		return sScreenRect;
	}
	
	//private classTrace call, to make class debugging easier
	private static void classTrace(String text){
		if(DISPLAY_TRACES)
			System.out.println(text);
	}
}
