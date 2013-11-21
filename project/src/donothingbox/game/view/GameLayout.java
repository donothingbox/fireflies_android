package donothingbox.game.view;

import java.util.HashMap;
import java.util.Map;
import com.example.fireflies_android.*;
import donothingbox.game.controller.HUDController;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/*
 * This class simply preps some graphics and holds the GameSurfaceView
 * 
 * 
 */

public class GameLayout extends FrameLayout {
	
	public TextView mScoreText;
	public GameSurfaceView mGameSurface;

	public GameLayout(Context context) {
		super(context);		
		
        HUDController.init(this);

		//Build hash here for easy lookup of resources in gameSurface TODO, improve this later
		Map<String, Integer> graphicHash = new HashMap<String, Integer>();
		graphicHash.put("firefly", Integer.valueOf(R.drawable.firefly));
		graphicHash.put("background", Integer.valueOf(R.drawable.background_img));
		graphicHash.put("bug_jar", Integer.valueOf(R.drawable.bug_jar));

        mGameSurface = new GameSurfaceView(context, graphicHash);

        this.addView(mGameSurface);

        mScoreText=new TextView(context);
        LayoutParams lp = new LayoutParams(new ViewGroup.MarginLayoutParams(100,100));
        mScoreText.setLayoutParams(lp);
        mScoreText.setTextSize(30);
        mScoreText.setTextColor(Color.WHITE);
        mScoreText.setText("0");
        //Reguires api 11
       // mScoreText.setX(getScreenData().width() - 150);
       // mScoreText.setY(getScreenData().height() - 50);
        this.addView(mScoreText);
	}
	
	public Rect getScreenData(){
		Rect returnRect = new Rect();
		this.getWindowVisibleDisplayFrame(returnRect);
		return returnRect;
	}
}
