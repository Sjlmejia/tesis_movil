package com.tesis.capacitysoft;

import java.util.Timer;
import java.util.TimerTask;
 
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
 
public class SplashScreenActivity extends Activity {
 
    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     

        
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
 
        setContentView(R.layout.splash_screen);
        ImageView im=(ImageView)findViewById(R.id.imageini);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        new Thread(new Runnable() {
            public void run() {
               while (progressStatus < 100) {
                  progressStatus += 1;
           // Update the progress bar and display the 
                                //current value in the text view
           handler.post(new Runnable() {
           public void run() {
              progressBar.setProgress(progressStatus);
            
           }
               });
               try {
                  // Sleep for 200 milliseconds. 
                                //Just to display the progress slowly
                  Thread.sleep(200);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
         }).start();
        //ObjectAnimator.ofFloat(im, "rotationY", 0, 360).setDuration(3800).start();
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
 
                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
               
                startActivity(mainIntent);
 
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };
 
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
       // final ProgressDialog progressDialog=ProgressDialog.show(SplashScreenActivity.this, "Espere por favor", "Cargando aplicación");
    }
 
}