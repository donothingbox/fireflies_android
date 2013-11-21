package donothingbox.game.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.View;

import java.util.Random;


public class Utils {

	public static float getRandomNumber(float minF, float maxF) {
		return (new Random().nextFloat() * (maxF - minF) + minF);
	}


	public static Bitmap getBitmapFromView(View paramView) {
		paramView.setDrawingCacheEnabled(true);
		paramView.measure(View.MeasureSpec.makeMeasureSpec(0, 0),
				View.MeasureSpec.makeMeasureSpec(0, 0));
		paramView.layout(0, 0, paramView.getMeasuredWidth(),
				paramView.getMeasuredHeight());
		paramView.buildDrawingCache(true);
		Bitmap localBitmap = Bitmap.createBitmap(paramView.getDrawingCache());
		paramView.setDrawingCacheEnabled(false);
		return localBitmap;
	}

	public static Bitmap getResizedBitmap(Bitmap paramBitmap, int paramInt1,
			int paramInt2) {
		int i = paramBitmap.getWidth();
		int j = paramBitmap.getHeight();
		float f1 = paramInt2 / i;
		float f2 = paramInt1 / j;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(f1, f2);
		return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, false);
	}

	public static float lerp(float paramFloat1, float paramFloat2,
			float paramFloat3) {
		if (paramFloat3 < 0F)
			return paramFloat1;
		if (paramFloat3 > 1F)
			return paramFloat2;
		return (paramFloat1 + paramFloat3 * (paramFloat2 - paramFloat1));
	}

	public static int lerp(int paramInt1, int paramInt2, float paramFloat) {
		if (paramFloat < 0F)
			return paramInt1;
		if (paramFloat > 1F)
			return paramInt2;
		return Math.round(paramInt1 + paramFloat * (paramInt2 - paramInt1));
	}

	public static Point lerp(Point paramPoint1, Point paramPoint2,
			float paramFloat) {
		if (paramFloat < 0F)
			return paramPoint1;
		if (paramFloat > 1F)
			return paramPoint2;
		return new Point(lerp(paramPoint1.x, paramPoint2.x, paramFloat), lerp(
				paramPoint1.y, paramPoint2.y, paramFloat));
	}
}
