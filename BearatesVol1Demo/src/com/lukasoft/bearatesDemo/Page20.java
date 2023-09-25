package com.lukasoft.bearatesDemo;

import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class Page20 extends Activity {

    boolean audio = false ;
    private int pageInt = 1 ;

	@Override
	public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.page20);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle b = getIntent().getExtras();
        pageInt = b.getInt("page");
        Log.v("LUCCYO LOGS", "STARTING PAGE 20");

        try{
            audio = b.getBoolean("audio");
        }
        catch (NullPointerException e){}

        final ListView listPage = (ListView)findViewById(R.id.listViewPages);
        String[] pages = getResources().getStringArray(R.array.list_pages);
        listPage.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, pages));

        listPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Intent intent;
                int newPage = i+1;

                intent = new Intent(context, Page.class);

                intent.putExtra("page", newPage);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Page20.this.finish();
                System.gc();
            }
        });

        int animation = getResources().getIdentifier("animationpage20","drawable", getPackageName());
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

                try {
                    ImageView rocketImage = (ImageView) findViewById(R.id.page1Img);
                    AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
                    rocketAnimation.start();
                    checkIfAnimationDone(rocketAnimation);
                } catch (NullPointerException e) {
                    Log.v("LUCCYO LOGS", "Catch ");
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

	@Override
	public void onBackPressed() {
		Intent accueilIntent = new Intent(this, MyActivity.class);
		this.startActivity(accueilIntent);
		this.finish();
	}

    public void goToPrev(View view) {
        MediaPlayer pageTurn = MediaPlayer.create(Page20.this, R.raw.pageturn);
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
        final ImageView img = (ImageView) findViewById(R.id.page1Img);
        Drawable imgFrame = img.getDrawable();
        if(imgFrame != null){
            if (imgFrame instanceof BitmapDrawable) {
                ((BitmapDrawable)imgFrame).getBitmap().recycle();
            }
            imgFrame.setCallback(null);
        }

        if(pageInt == 0){
            Intent accueilIntent = new Intent(this, MyActivity.class);
            this.startActivity(accueilIntent);
        }
        else{
            Intent prevIntent = new Intent(this, Page.class);
            prevIntent.putExtra("page", 19);
            if(audio)
                prevIntent.putExtra("audio", true);
            this.startActivity(prevIntent);
        }
        this.finish();
        System.gc();
    }

    public void goToNext(View view) {
    }

}
