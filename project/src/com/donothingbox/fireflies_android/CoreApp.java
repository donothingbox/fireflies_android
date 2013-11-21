package com.donothingbox.fireflies_android;

import android.app.Activity;
import android.app.Application;

public class CoreApp extends Application {
    public void onCreate() {
          super.onCreate();
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
          return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
          this.mCurrentActivity = mCurrentActivity;
    }
}