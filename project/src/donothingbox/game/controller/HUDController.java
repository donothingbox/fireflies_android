package donothingbox.game.controller;

import donothingbox.game.view.GameLayout;

/*
 * Static functions let anyone call hudcontroller methods. I tend to prefer this way over event listening, but longer term testing may prove otherwise . . . 
 * 
 * Upside seems to be cleaner dispatch code, and central mgmt. Downside is more code required to insure cleanup and rebuild without causing mem leaks
 * 
 */

public class HUDController {
	
	public static GameLayout sGameLayout;
	
    private HUDController(){}

	public static void init(GameLayout gameLayout)
	{
		sGameLayout = gameLayout;
	}
	
	public static void setPointCounter(int points){
		System.out.println("updating points: " + sGameLayout.mScoreText);
		sGameLayout.mScoreText.setText(Integer.toString(points));
	}
}




