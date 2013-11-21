package com.donothingbox.fireflies_android;

import donothingbox.game.controller.StateController;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.example.fireflies_android.*;

/*
 *  This is the Primary activity of the application
 *  
 *  Currently, it displays the DNB logo, and simple button options, from XML layout
 * 
 */

public class MainActivity extends Activity {

	public TextView m_feedbackText;
	public Button m_playGame;
	public Button m_aboutAuthor;
    protected CoreApp m_CoreApp;//TODO to be used later more
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StateController.init(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        m_feedbackText = (TextView) findViewById(R.id.textView1);
        m_feedbackText.setText("please choose wisely");
        m_playGame = (Button) findViewById(R.id.button1);
        m_aboutAuthor = (Button) findViewById(R.id.button2);
                
        //Play game
        m_playGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				m_feedbackText.setText("you chose wisely");
		        Intent intent = new Intent(MainActivity.this, GameSurfaceActivity.class);
		        startActivity(intent);
			}
		});
        
        //acces author info
        m_aboutAuthor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				m_feedbackText.setText("you chose . . . poorly");
		        Intent intent = new Intent(MainActivity.this, DynamicActivity.class); 
		        startActivity(intent);
			}
		});
    }
    
    /*
     * To be implemented later, for more detailed cleanup and retore
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {        
        //clearReferences();
        super.onDestroy();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    //TODO properly implement this
    private void clearReferences(){
        Activity currActivity = m_CoreApp.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
        	m_CoreApp.setCurrentActivity(null);
    }
}
