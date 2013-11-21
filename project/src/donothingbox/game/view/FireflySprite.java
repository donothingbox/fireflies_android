package donothingbox.game.view;

import java.util.Random;

import android.graphics.Bitmap;

public class FireflySprite extends Sprite{
	
	public float alphaChange = 1;
	private Boolean isDimming = true;
	private Boolean isHidden = false;
	public Boolean isActive = true;

	public FireflySprite(Bitmap bitmap) {
		super(bitmap);
		// TODO Auto-generated constructor stub
	}
	
	public void updateGraphic(){
		if(!isHidden)
		{
			if(isDimming)
			{
				if(this.getAlpha()<=0)
				{
					isDimming = false;
					isHidden = true;
					//visible = false;
				}
				this.setAlpha(getAlpha()-alphaChange);
			}
			else
			{
				if(this.getAlpha()>=100)
					isDimming = true;
				this.setAlpha(getAlpha()+alphaChange);
			}
		}
		else
		{
			Random rand = new Random();
		   if( (rand.nextFloat() * (2000 - 0) + 0) >1950)
		   {
			   isHidden = false;
			   visible = true;

		   }
		    
		}
	}
}
