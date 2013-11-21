package com.donothingbox.fireflies_android;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.fireflies_android.*;


/*
 *  Simple coded (non XML) layout, eventually will display 'real" author info
 * 
 */

public class DynamicActivity extends Activity {

	public TextView choiceText;
	public Button accessHeaven;
	public Button accessHell;
    protected CoreApp mCoreApp;
	
    @Override
    //This activity is built dynamic
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLACK);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        setContentView(linearLayout);
        
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        //ImageView Setup
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.profile);
        linearLayout.addView(imageView);
        imageView.setX(0);
        scaleImage(imageView, 150); // in dp
        
        TextView body = new TextView(this);
        String bodyText = "Selfies ugh lomo, small batch bicycle rights distillery Helvetica cliche meggings sartorial Wes Anderson chillwave mustache. Before they sold out post-ironic ennui, cray meh gentrify DIY pork belly cred. Kogi jean shorts brunch High Life irony Schlitz. Truffaut asymmetrical Williamsburg, you probably haven't heard of them fanny pack bicycle rights Pitchfork Tonx Tumblr PBR&B selvage. Cardigan keffiyeh leggings readymade sartorial chambray. Neutra chillwave leggings Pitchfork. XOXO salvia quinoa, selfies hella asymmetrical ennui letterpress Helvetica lomo. Helvetica pickled twee, messenger bag Vice jean shorts pork belly. <p> Gluten-free four loko synth, XOXO retro Pitchfork food truck fixie. Direct trade sriracha chia church-key Etsy Tumblr. Retro literally PBR&B fixie, paleo hashtag whatever messenger bag craft beer scenester. Godard cray plaid actually. Flexitarian Godard Vice ennui gluten-free artisan. Twee cred +1 stumptown XOXO. Sriracha hella squid, PBR&B cardigan fashion axe aesthetic narwhal twee you probably haven't heard of them polaroid ethical. Mlkshk fingerstache lomo High Life ethical meggings.";
        body.setText(Html.fromHtml(bodyText));
        body.setTextColor(Color.WHITE);
        linearLayout.addView(body);

        TextView link = new TextView(this);
        String linkText = "Visit <a href='http://donothingbox.com'>DoNothingBox</a> web page. which may not even be live yet . . . works in progress . . . ";
        link.setTextColor(Color.WHITE);
        link.setText(Html.fromHtml(linkText));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        linearLayout.addView(link);
    }
    
    
    //TODO code is outdated. migrate to better
    private void scaleImage(ImageView view, int boundBoxInDp)
    {
        // Get the ImageView and its bitmap
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

        // Get current dimensions
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
}
