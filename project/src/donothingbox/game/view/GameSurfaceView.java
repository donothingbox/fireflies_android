package donothingbox.game.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import donothingbox.game.controller.AudioController;
import donothingbox.game.controller.GameThread;
import donothingbox.game.controller.HUDController;
import donothingbox.game.controller.StateController;
import donothingbox.game.model.DepthSortComparator;
import donothingbox.game.utils.Utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/*
 * 
 *  This is the core blitting graphic class
 * 
 * 
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

private static boolean DISPLAY_TRACES = true; //used for class debugging

private GameThread mGameThread = null;
private Bitmap bitmap;
private Bitmap mBackgroundBitmap;
private Bitmap mBugJar;
private Sprite bgSprite;
private int mScore = 0;
private Map<String, Integer> mGraphicHash;

public ArrayList<Sprite> childVector = new ArrayList<Sprite>();

	public GameSurfaceView(Context context, Map<String, Integer> graphicHash) {
	    super(context);
	    mGraphicHash = graphicHash;
	    scaleGraphicsForScreen();
	    
	    bgSprite = new Sprite(mBackgroundBitmap);

	    for(int l=0; l<=10; l++){
	    	FireflySprite sprite = new FireflySprite(bitmap);
		    addChild(sprite);
			Random rand = new Random();
		    sprite.alphaChange = ((rand.nextFloat() * (500 - 100) + 100))/100;
		    sprite.setAlpha(Utils.getRandomNumber(0, 255));
		    
		    classTrace("random widht: " + Utils.getRandomNumber(0, StateController.getScreenSize().width()));
		    classTrace("random height: " + Utils.getRandomNumber(0, StateController.getScreenSize().height()));

		    sprite.x = Utils.getRandomNumber(0, StateController.getScreenSize().width());
			sprite.y = Utils.getRandomNumber(0, StateController.getScreenSize().height());
			sprite.vx = Utils.getRandomNumber(1, 3);
			sprite.vy = Utils.getRandomNumber(1, 3);
	    }

	    getHolder().addCallback(this);
	    mGameThread = new GameThread(getHolder(), this);

	    this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				System.out.println("touched");
				GameSurfaceView.this.canvasTouched(arg1);
				return false;
			}
       });
	    
	    //testing depth sort system
	    addChild(bgSprite);
	    swapChildren(bgSprite, childVector.get(0));
	}
	
	//populates bitmap vars with proper scaled graphics
	public void scaleGraphicsForScreen(){
		 bitmap = getImage(getGraphicId("firefly"), (int) (100*StateController.sScaleFactor), (int) (100*StateController.sScaleFactor));  
		 int bgWidth = (int) (StateController.getScreenSize().height()*1.777777);
		 mBackgroundBitmap = getImage(getGraphicId("background"), bgWidth,StateController.getScreenSize().height());
		 mBugJar = getImage(getGraphicId("bug_jar"), 100,100);
	}
	
    //Used to looking grpahics by readable strings
	public int getGraphicId(String key){
		int returnInt = 0;
		try{
			returnInt = mGraphicHash.get(key);
		}
		catch(NullPointerException e){
		    throw new RuntimeException("Invalid key for graphic lookup: " + key, e);
		}
	    classTrace("looking up graphic: " + key + " = " + returnInt);
		return returnInt;
	}
	
	//Quicker swap, but assumes depths have already been validated
	public void swapChildren(Sprite childA, Sprite childB){
	    Collections.swap(childVector, childA.depth, childB.depth);
	}
	
	//slower swap, forces validate run first
	public void swapChildrenWithValidate(Sprite childA, Sprite childB){
		validateDepthMarkers();
	    Collections.swap(childVector, childA.depth, childB.depth);
	}
	
	//havent set this up yet, so stub
	public void setDepth(Sprite sprite, int depth){}
	
	//adds "child" to "stage". will be rendered on next update loop
	public void addChild(Sprite sprite){
		childVector.add(sprite);
		sprite.depth = childVector.size()-1;
	}
	
	//removes child. this will need to force a depth sort too
	public void removeChild(Sprite sprite){
	    for(int i=0; i<=childVector.size(); i++){
	    	if(childVector.get(i) == sprite){
		    	childVector.remove(i);
		    	break;
	    	}
	    }
	}
	
	/*
	 * The heart of user interactions goes here. TODO There is room to optimize here for for sure. 
	 * 
	 */
	
	public void canvasTouched(MotionEvent touchEvent) {
		Boolean isHit = false;
		//loop through all sprites on "stage"
	    for(Sprite sprite : childVector){
	    	if(sprite.visible && sprite instanceof FireflySprite)//for now, only interact with firefly instances
	    	{
	    		//for now, use a "touch area" instead of a touch point, allowing for adjustments in focus
				Rect collision = new Rect();
				collision.set((int)touchEvent.getX()-3, (int)touchEvent.getY()-3, (int)touchEvent.getX()+3, (int)touchEvent.getY()+3);
				Boolean touched = Rect.intersects(collision, sprite.getRect());
				//TODO move logic into "TouchableSprite class"
				if(touched){
					
					if(sprite instanceof FireflySprite)
					{
						if(((FireflySprite)sprite).isActive)
						{
							sprite.vx = 0;
							sprite.vy = 0;
							AudioController.playAudio();
							mScore++;
							HUDController.setPointCounter(mScore);
							((FireflySprite)sprite).isActive = false;
							sprite.x = StateController.getScreenSize().width() - 100;
							sprite.y = StateController.getScreenSize().height() - 100;
							isHit = true;
						}
					}
					
				}
	    	}
	    }
	    if(!isHit)
			AudioController.playMiss();
	}
	
	/*
	 * This is the core update loop, called by the draw request
	 * 
	 */
	
	public void update(Canvas canvas) {
		//first we clear the canvas bitmap out (draw to black)
		canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
		//for now we revalidate depths, TODO move to a "on dirty" bool check, this is a bit waste of resources
		validateDepthMarkers();
		//adjust acceleration vectors
	    checkCollisions(canvas);
	    //now update all sprites with acc vectors 
	    for(Sprite sprite : childVector){
	    	sprite.x += sprite.vx;
	    	sprite.y += sprite.vy;
	    }
	}
	
	/*
	 * This is the core draw loop, called by the GameThread
	 * 
	 */
	
	public void onDraw(Canvas canvas) {
		//update everything
	    update(canvas);
	    //blit every sprite onto the main canvas bitmap
	    for(Sprite sprite : childVector){
	        if(sprite.visible)
	        {
	        	sprite.mPaint.setAlpha(sprite.getAlpha());
	        	canvas.drawBitmap(sprite.getBitmap(), sprite.x, sprite.y, sprite.mPaint);
	        }
	        sprite.updateGraphic();
	    }
	    //TODO scale this bitmap, and migrate bitmap scales to external class functions
	    canvas.drawBitmap(mBugJar, StateController.getScreenSize().width() - 100, StateController.getScreenSize().height() - 100, null);
	}
	
	//Stupid simple "bounce off walls" code
	public void checkCollisions(Canvas canvas) {
		for(Sprite sprite : childVector){
		    if(sprite.x - sprite.vx < 0) {
		    	sprite.vx = Math.abs(sprite.vx);
		    } else if(sprite.x + sprite.vx > canvas.getWidth() - sprite.getBitmap().getWidth()) {
		    	sprite.vx = -Math.abs(sprite.vx);
		    }
		    if(sprite.y - sprite.vy < 0) {
		    	sprite.vy = Math.abs(sprite.vy);
		    } else if(sprite.y + sprite.vy > canvas.getHeight() - sprite.getBitmap().getHeight()) {
		    	sprite.vy = -Math.abs(sprite.vy);
		    }
		}
	}
	
	//This will be expanded, but basically, index id is also depth set
	public  void validateDepthMarkers(){
		  for(int i=0; i<childVector.size(); i++){
			  childVector.get(i).depth = i;
		  }
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		System.out.println("surface changed");
	    // TODO Auto-generated method stub
	}
	
	//TODO abandoned code, we're doing this better, so dont need really
	public void adjustDepthList(){
		Collections.sort(childVector, new DepthSortComparator());
	}
	

	public void surfaceCreated(SurfaceHolder holder) {
	    // TODO Auto-generated method stub
		System.out.println("surface created");
		mGameThread.setRunnable(true);
		mGameThread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
	    boolean retry = false;
	    mGameThread.setRunnable(false);
		System.out.println("surface destroyed");
	    while(retry) {
	        try {
	        	mGameThread.join();
	            retry = false;
	        } catch(InterruptedException ie) {
	            //Try again and again and again
	        }
	        break;
	    }
	    mGameThread = null;
	}
	
	 public Bitmap getImage (int id, int width, int height) {
        Bitmap bmp = BitmapFactory.decodeResource( getResources(), id );
        classTrace("bitmap data: " + bmp.getWidth() + ":" + bmp.getHeight());
        Bitmap img = Bitmap.createScaledBitmap( bmp, width, height, true );
        bmp.recycle();
        return img;
    }
	 
	public void pauseGameSurfaceThread(){
	    //mGameThread.setRunnable(false);
	}

	//private classTrace call, to make class debugging easier
	private static void classTrace(String text){
		if(DISPLAY_TRACES)
			System.out.println(text);
	}
}