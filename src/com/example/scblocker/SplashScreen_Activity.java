package com.example.scblocker;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen_Activity extends Activity {
	
		private int iDELAY = 4000;
		public int currentimageindex=0;
//	    Timer timer;
//	    TimerTask task;
	    ImageView slidingimage;
	    
	    private int[] IMAGE_IDS = {
	            R.drawable.logo, 
	            
	        };
	    
	    
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_slide_show);
	        final Handler mHandler = new Handler();
	        new Handler ( ).postDelayed(new Runnable ( ) {
				public void run ( ) {
				Intent splashIntent = new Intent ( SplashScreen_Activity.this,MainActivity.class);
				
				startActivity ( splashIntent );
				SplashScreen_Activity.this.finish();
				}
			}, iDELAY);
	        // Create runnable for posting
	        final Runnable mUpdateResults = new Runnable() {
	            public void run() {
	                
	                AnimateandSlideShow();
	                
	            }
	        };
	        
	        int delay = 500; // delay for 1 sec.

	        int period = 10000; // repeat every 4 sec.

	        Timer timer = new Timer();

	        timer.scheduleAtFixedRate(new TimerTask() {

	        public void run() {

	             mHandler.post(mUpdateResults);

	        }

	        }, delay, period);
	        
	        
	               
	    }

	    public void onClick(View v) {
	    
	        Intent intent = new Intent(SplashScreen_Activity.this, MainActivity.class);
	        startActivity(intent);
	      }
	    
	    /**
	     * Helper method to start the animation on the splash screen
	     */
	   private void AnimateandSlideShow() {
	        
	        
	        slidingimage = (ImageView)findViewById(R.id.imageView1);
	           slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
	           
	           currentimageindex++;
	      
	          Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.in_from_left);
	          
	        
	          slidingimage.startAnimation(rotateimage);
	          
	              
	}


}
