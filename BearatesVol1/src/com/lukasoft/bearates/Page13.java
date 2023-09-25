package com.lukasoft.bearates;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class Page13 extends Activity {

    boolean audio = false ;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.page13);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle b = getIntent().getExtras();
        Log.v("LUCCYO LOGS", "STARTING PAGE 13");

        if(b != null)
            audio = b.getBoolean("audio");


        ImageView rocketImage = (ImageView) findViewById(R.id.page13Img1);
        try{
            ((AnimationDrawable)(rocketImage.getBackground())).stop() ;
        }
        catch (NullPointerException e){
            Log.v("LUCCYO LOGS", "Catch stop animation");
        }

        rocketImage.setBackgroundDrawable(null);
        rocketImage.setBackgroundResource(R.drawable.animationpage13);
        rocketImage.setScaleType(ImageView.ScaleType.FIT_XY);

        AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        rocketAnimation.setOneShot(true);

        rocketImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                ImageView rocketImage = (ImageView) findViewById(R.id.page13Img1);
                AnimationDrawable rocketAnimation = null ;
                if(rocketImage.getBackground() != null){
                    rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
                }
                if(rocketAnimation != null){
                    rocketAnimation.start();
                    checkIfAnimationDone(rocketAnimation);
                }

                return true;
            }
        });
    }

    private void checkIfAnimationDone(AnimationDrawable rocketAnimation){
        final AnimationDrawable a = rocketAnimation;
        int timeBetweenChecks = 600;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(a);
                } else{
                    a.stop();
                    a.setVisible(true,false);
                }
            }
        }, timeBetweenChecks);
    };


    private Runnable task = new Runnable() {
        public void run() {
            ImageView rocketImage = (ImageView) findViewById(R.id.page13Img1);
            AnimationDrawable rocketAnimation = null ;
            if(rocketImage.getBackground() != null){
                rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
            }
            if(rocketAnimation != null){
                rocketAnimation.start();
                checkIfAnimationDone(rocketAnimation);
            }
            goToNextAfterSound();
        }
    };

    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus && audio){
            Handler handler = new Handler();
            handler.postDelayed(task, 3000);
        }
    }

	@Override
	public void onBackPressed() {
        final ImageView img = (ImageView) findViewById(R.id.page13Img1);
        Drawable imgFrame = img.getDrawable();
        if(imgFrame != null){
            if (imgFrame instanceof BitmapDrawable) {
                ((BitmapDrawable)imgFrame).getBitmap().recycle();
            }
            imgFrame.setCallback(null);
        }
		Intent accueilIntent = new Intent(this, MyActivity.class);
		this.startActivity(accueilIntent);
		this.finish();
	}

	public void goToPrev(View view) {
        MediaPlayer pageTurn = MediaPlayer.create(Page13.this, R.raw.pageturn);
        pageTurn.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
                mp = null;
                goToPrevAfterSound();
            }
        });
        pageTurn.start(); // no need to call prepare(); create() does that for you
	}

    private void goToPrevAfterSound() {

        AnimationDrawable rocketAnimation = null;
        final ImageView rocketImage = (ImageView) findViewById(R.id.page13Img1);
        if(rocketImage.getBackground() != null){
            rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
        }
        if(rocketAnimation != null){
            rocketAnimation.stop();
            for (int i = 0; i < rocketAnimation.getNumberOfFrames(); ++i){
                Drawable frame = rocketAnimation.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    if(null != frame && !((BitmapDrawable)frame).getBitmap().isRecycled())
                        ((BitmapDrawable)frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            rocketAnimation.setCallback(null);
        }

        Intent prevIntent = new Intent(this, Page.class);
        prevIntent.putExtra("page", 12);
        if(audio)
            prevIntent.putExtra("audio", true);
        this.startActivity(prevIntent);
        this.finish();
        System.gc();
    }

    public void goToNext(View view) {
        MediaPlayer pageTurn = MediaPlayer.create(Page13.this, R.raw.pageturn);
        pageTurn.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
                mp = null;
                goToNextAfterSound();
            }
        });
        pageTurn.start(); // no need to call prepare(); create() does that for you
    }

    private void goToNextAfterSound() {
        AnimationDrawable rocketAnimation = null;
        final ImageView rocketImage = (ImageView) findViewById(R.id.page13Img1);
        if(rocketImage.getBackground() != null){
            rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
        }
        if(rocketAnimation != null){
            rocketAnimation.stop();
            for (int i = 0; i < rocketAnimation.getNumberOfFrames(); ++i){
                Drawable frame = rocketAnimation.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    if(null != frame && !((BitmapDrawable)frame).getBitmap().isRecycled())
                        ((BitmapDrawable)frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            rocketAnimation.setCallback(null);
        }

        Intent nextIntent = new Intent(this, Page.class);
        nextIntent.putExtra("page", 14);
        if(audio)
            nextIntent.putExtra("audio", true);
        this.startActivity(nextIntent);
        this.finish();
        System.gc();
    }

}
