package com.markjmind.propose.actor;

import android.animation.Animator;
import android.util.Log;

import com.markjmind.propose.Motion;
import com.markjmind.propose.animation.TimeAnimation;
import com.markjmind.propose.animation.TimeValue;

import java.util.HashMap;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-04-12
 */
public class Taper {
    public boolean tap(Motion motion, long startDuration, long endDuration, long playDuration){
        Log.e("ds","start:"+startDuration+" end:"+endDuration);
        if(startDuration>motion.getTotalDuration()){
            startDuration = motion.getTotalDuration();
        }else if(startDuration < 0){
            startDuration = 0;
        }
        if(endDuration>motion.getTotalDuration()){
            endDuration = motion.getTotalDuration();
        }else if(endDuration < 0){
            endDuration = 0;
        }
        if(startDuration==endDuration){
            return false;
        }

        AnimationTimeValue timeValue = new AnimationTimeValue(motion);
        if(endDuration >= startDuration){
            timeValue.setValues(startDuration,endDuration);
        }else{
            timeValue.setValues(endDuration, startDuration);
        }


        TimeAnimation timeAnimation = new TimeAnimation();

        timeAnimation.setDuration(playDuration);
        timeAnimation.addTimerValue(timeValue);
        timeAnimation.setAnimatorListener(new TimeAnimationEvent());
        timeAnimation.start();
        return true;
    }

    private class AnimationTimeValue extends TimeValue {
        private Motion motion;
        protected AnimationTimeValue(Motion motion){
            this.motion = motion;
        }
        @Override
        public void onAnimationUpdate(long timeValue, HashMap<String, Object> params) {
            Log.e("ds","timeValue:"+timeValue);
            motion.move(timeValue);
        }
    }

    private class TimeAnimationEvent implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }
        @Override
        public void onAnimationEnd(Animator animation) {
//            if(proposePg.isMotionStart){
//                proposePg.endMotionEvent();
//            }
        }
        @Override
        public void onAnimationCancel(Animator animation) {
        }
        @Override
        public void onAnimationRepeat(Animator animation) {
        }

    }
}
