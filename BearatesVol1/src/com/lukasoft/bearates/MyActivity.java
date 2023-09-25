package com.lukasoft.bearates;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    boolean sound = true ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.main);
    }

    public void goToPage1(View view) {

        ImageView rocketImage = (ImageView) findViewById(R.id.imageView);

        Intent page1Intent = new Intent(this, Page.class);
        page1Intent.putExtra("page", 1);
        this.startActivity(page1Intent);
        this.finish();
        System.gc();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        System.gc();
    }

    public void goHome() {
        Intent accueilIntent = new Intent(this, MyActivity.class);
        this.startActivity(accueilIntent);
        this.finish();
        System.gc();
    }

    public void toogleSound(View view) {
        if(sound){
            sound=false;
            Toast.makeText(getApplicationContext(), "Sons OFF", Toast.LENGTH_SHORT).show();
        }
        else{
            sound = true;
            Toast.makeText(getApplicationContext(), "Sons ON", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToPage20(View view) {
        Intent page20Intent = new Intent(this,Page20.class);
        page20Intent.putExtra("page", 0);
        this.startActivity(page20Intent);
        this.finish();
        System.gc();
    }

    public void launchAudioAuto(View view) {
        Intent page1Intent = new Intent(this,Page.class);
        page1Intent.putExtra("audio", true);
        page1Intent.putExtra("page", 1);
        page1Intent.putExtra("sound", sound);
        this.startActivity(page1Intent);
        this.finish();
        System.gc();
    }
}
