package donothingbox.game.view;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
	
	public Bitmap mBitmap;
	public float x = 0;
	public float y = 0;
	public float vx = 0;
	public float vy = 0;
	public int rotation = 0;
	public Boolean visible = true;
	private float mAlpha = 255;
	public Paint mPaint;
	public float scale = 1.0f;
	public int depth = 0;
	
	
	public Sprite(Bitmap bitmap) {
		mBitmap = bitmap;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setFilterBitmap(true);
		mPaint.setDither(true);
	}
	
	public Bitmap getBitmap(){
		return mBitmap;	
	}
	
	public Rect getRect(){
		Rect bounds = new Rect();
		bounds.set((int)x, (int)y, (int)x + mBitmap.getWidth(), (int)y + mBitmap.getHeight());
		return bounds;
	}
	
	public int getAlpha(){
		return (int) mAlpha;
	}
	
	public void setAlpha(float alpha){
		if(alpha>100)
		{
			mAlpha = 100;
		}
		else if(alpha<=0)
		{
			mAlpha = 0;
		}
		else
			mAlpha = (int) alpha;
	}
	
	public void onTouched(){
		
	}
	
	public void updateGraphic(){
		
	}
}
