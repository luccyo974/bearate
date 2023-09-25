package com.lukasoft.bearatesDemo;

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
import android.widget.TextView;

public class Page extends Activity {

    boolean audio = false ;
    private int pageInt = 1 ;
    private MediaPlayer mediaPlayer ;

    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.page);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle b = getIntent().getExtras();
        pageInt = b.getInt("page");
        Log.v("LUCCYO LOGS", "STARTING PAGE " + pageInt);

        try{
            audio = b.getBoolean("audio");
        }
        catch (NullPointerException e){
            Log.v("LUCCYO LOGS", "Catch get Audio");
        }

        String actualPage = "page"+pageInt;

        int firstText = getResources().getIdentifier(actualPage+"par1","string", getPackageName());
        int secondText = getResources().getIdentifier(actualPage+"par2","string", getPackageName());
        int thirdText = getResources().getIdentifier(actualPage+"par3","string", getPackageName());
        int titleText = getResources().getIdentifier(actualPage,"string", getPackageName());

        final TextView partOneText = (TextView) findViewById(R.id.page1Par1);
        partOneText.setText(firstText);
        partOneText.setTypeface(partOneText.getTypeface(), 2);
        final TextView partTwoText = (TextView) findViewById(R.id.page1Par2);
        partTwoText.setText(secondText);
        partTwoText.setTypeface(partOneText.getTypeface(), 2);
        final TextView partTreeText = (TextView) findViewById(R.id.page1Par3);
        partTreeText.setText(thirdText);
        partTreeText.setTypeface(partOneText.getTypeface(), 2);

        final TextView partOneTitle = (TextView) findViewById(R.id.pageTitle);
        partOneTitle.setText(titleText);
        partOneTitle.setTypeface(partOneText.getTypeface(), 2);

        int animation = getResources().getIdentifier("animationpage"+pageInt,"drawable", getPackageName());
        Log.v("LUCCYO LOGS", "animation ID=" + animation);

        ImageView rocketImage = (ImageView) findViewById(R.id.page1Img);
        try{
            ((AnimationDrawable)(rocketImage.getBackground())).stop() ;
        }
        catch (NullPointerException e){
            Log.v("LUCCYO LOGS", "Catch stop animation");
        }

        rocketImage.setBackgroundDrawable(null);
        rocketImage.setBackgroundResource(animation);

        AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        rocketAnimation.setOneShot(true);

        rocketImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                try{
                    ImageView rocketImage = (ImageView) findViewById(R.id.page1Img);
                    AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
                    rocketAnimation.start();
                    checkIfAnimationDone(rocketAnimation);
                }
                catch (NullPointerException e){
                    Log.v("LUCCYO LOGS", "Catch ");
                }

                return true;
            }
        });
	}

    private Runnable task = new Runnable() {
        public void run() {
            int mediaInt = getResources().getIdentifier("page"+pageInt,"raw", getPackageName());
            mediaPlayer = MediaPlayer.create(Page.this, mediaInt);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();
                    mp = null;
                    goToNextAfterSound();
                }
            });
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        }
    };

    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus && audio){
            Handler handler = new Handler();
            handler.postDelayed(task, 2000);
        }
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

	@Override
	public void onBackPressed() {
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        catch(NullPointerException e){}
        catch(IllegalStateException e){}
        Intent accueilIntent = new Intent(this, MyActivity.class);
        this.startActivity(accueilIntent);
        this.finish();
	}

    public void goToPrev(View view) {

        MediaPlayer pageTurn = MediaPlayer.create(Page.this, R.raw.pageturn);
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

    private void goToPrevAfterSound(){

        int newPage = pageInt-1;

        Log.v("LUCCYO LOGS", "GOING BACKWARD TO PAGE " + newPage);
        AnimationDrawable rocketAnimation = null;
        ImageView rocketImage = (ImageView) findViewById(R.id.page1Img);
        try{
            rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
        }
        catch(NullPointerException n){
            Log.v("LUCCYO LOGS", "Catch ");
        }

        try{
            rocketAnimation.stop();
            if(newPage != 0){
                for (int i = 0; i < rocketAnimation.getNumberOfFrames(); ++i){
                    Drawable frame = rocketAnimation.getFrame(i);
                    if (frame instanceof BitmapDrawable) {
                        if(null != frame && !((BitmapDrawable)frame).getBitmap().isRecycled())
                            ((BitmapDrawable)frame).getBitmap().recycle();
                    }
                    frame.setCallback(null);
                }
            }
            rocketAnimation.setCallback(null);
        }
        catch(NullPointerException n){
            Log.v("LUCCYO LOGS", "Catch ");
        }

        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        catch(NullPointerException e){}
        catch(IllegalStateException e){}

        if(newPage == 4){
            Intent prevIntent = new Intent(this, Page20.class);
            if(audio)
                prevIntent.putExtra("audio", true);
            this.startActivity(prevIntent);

        }
        else if(newPage == 0){
            Intent prevIntent = new Intent(this, MyActivity.class);
            this.startActivity(prevIntent);
        }
        else{
            getIntent().putExtra("page", newPage);
            if(audio)
                getIntent().putExtra("audio", true);

            this.startActivity(getIntent());
        }
        this.finish();
        System.gc();
    }

    public void goToNext(View view) {

        MediaPlayer pageTurn = MediaPlayer.create(Page.this, R.raw.pageturn);
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

    private void goToNextAfterSound(){
        int newPage = pageInt+1;

        Log.v("LUCCYO LOGS", "GOING FORWARD TO PAGE " + newPage);

        AnimationDrawable rocketAnimation = null;
        ImageView rocketImage = (ImageView) findViewById(R.id.page1Img);
        try{
            rocketAnimation = (AnimationDrawable) rocketImage.getBackground() ;
        }
        catch(NullPointerException n){
            Log.v("LUCCYO LOGS", "Catch ");
        }
        try{
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
        catch(NullPointerException e){}

        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        catch(NullPointerException e){}
        catch(IllegalStateException e){}

        if(newPage == 4){
            Intent nextIntent = new Intent(this, Page20.class);
            if(audio)
                nextIntent.putExtra("audio", true);

            this.startActivity(nextIntent);
        }
        else{
            getIntent().putExtra("page", newPage);
            if(audio)
                getIntent().putExtra("audio", true);

            this.startActivity(getIntent());
        }
        this.finish();
        System.gc();
    }

    public void launchAudio(View view){
        int mediaInt = getResources().getIdentifier("page"+pageInt,"raw", getPackageName());
        if(mediaPlayer == null || !mediaPlayer.isPlaying()){
            mediaPlayer = MediaPlayer.create(Page.this, mediaInt);
            mediaPlayer.start();
        }
        else if(audio){
            mediaPlayer.stop();
            audio = false;

            //to remove if auto play should be ON on next page
            getIntent().removeExtra("audio");
        }else{
            mediaPlayer.stop();
            mediaPlayer.start();
        }
    }

}
