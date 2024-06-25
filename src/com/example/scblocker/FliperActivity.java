package com.example.scblocker;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class FliperActivity extends Activity 
{
            private ViewFlipper viewFlipper;
            private float lastX;

            @Override
            protected void onCreate(Bundle savedInstanceState) 
            {
                         super.onCreate(savedInstanceState);
                         setContentView(R.layout.activity_flipper);
                         viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
                         
            
            
            }
            
                        
            // Method to handle touch event like left to right swap and right to left swap
            public boolean onTouchEvent(MotionEvent touchevent) 
            {
                         switch (touchevent.getAction())
                         {
                                // when user first touches the screen to swap
                                 case MotionEvent.ACTION_DOWN: 
                                 {
                                     lastX = touchevent.getX();
                                     break;
                                }
                                 case MotionEvent.ACTION_UP: 
                                 {
                                     float currentX = touchevent.getX();
                                     
                                     // if left to right swipe on screen
                                     if (lastX < currentX) 
                                     {
                                          // If no more View/Child to flip
                                         if (viewFlipper.getDisplayedChild() == 0)
                                             break;
                                         
                                         // set the required Animation type to ViewFlipper
                                         // The Next screen will come in form Left and current Screen will go OUT from Right 
                                         viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                         viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                         // Show the next Screen
                                         viewFlipper.showNext();
                                     }
                                     
                                     // if right to left swipe on screen
                                     if (lastX > currentX)
                                     {
                                         if (viewFlipper.getDisplayedChild() == 1)
                                             break;
                                         // set the required Animation type to ViewFlipper
                                         // The Next screen will come in form Right and current Screen will go OUT from Left 
                                         viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                         viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                         // Show The Previous Screen
                                         viewFlipper.showPrevious();
                                     }
                                     if (lastX < currentX) 
                                     {
                                          // If no more View/Child to flip
                                         if (viewFlipper.getDisplayedChild() == 1)
                                             break;
                                         
                                         // set the required Animation type to ViewFlipper
                                         // The Next screen will come in form Left and current Screen will go OUT from Right 
                                         viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                         viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                         // Show the next Screen
                                         viewFlipper.showNext();
                                     }
                                     
                                     // if right to left swipe on screen
                                     if (lastX > currentX)
                                     {
                                         if (viewFlipper.getDisplayedChild() == 2)
                                             break;
                                         // set the required Animation type to ViewFlipper
                                         // The Next screen will come in form Right and current Screen will go OUT from Left 
                                         viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                         viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                         // Show The Previous Screen
                                         viewFlipper.showPrevious();
                                     }
                                     ///
                                     if (lastX < currentX) 
                                     {
                                          // If no more View/Child to flip
                                         if (viewFlipper.getDisplayedChild() == 2)
                                             break;
                                         
                                         // set the required Animation type to ViewFlipper
                                         // The Next screen will come in form Left and current Screen will go OUT from Right 
                                         viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                         viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                         // Show the next Screen
                                         viewFlipper.showNext();
                                     }
                                     
                                     // if right to left swipe on screen
                                     if (lastX > currentX)
                                     {
                                         if (viewFlipper.getDisplayedChild() == 3)
                                             break;
                                         // set the required Animation type to ViewFlipper
                                         // The Next screen will come in form Right and current Screen will go OUT from Left 
                                         viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                         viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                         // Show The Previous Screen
                                         viewFlipper.showPrevious();
                                     }
                                     break;
                                 }
                         }
                         return false;
            }

 }
