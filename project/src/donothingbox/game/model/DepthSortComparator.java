package donothingbox.game.model;

import java.util.Comparator;
import donothingbox.game.view.*;

/*
 *  used to sort the Arraylist holding objects on the gameSurfaceView. 
 * 
 */

public class DepthSortComparator implements Comparator<Sprite>
{
	public int compare(Sprite a, Sprite b)
	{
	    if (a.depth < b.depth)
	        return -1;
	
	    if (a.depth == b.depth)
	    {
	        if (a.depth < b.depth)
	            return -1;
	        if (a.depth > b.depth)
	            return 1;
	        return 0;
	    }
	
	    return 1;
	}
}