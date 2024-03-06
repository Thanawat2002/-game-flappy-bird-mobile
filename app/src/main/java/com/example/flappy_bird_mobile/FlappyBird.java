package com.example.flappy_bird_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.os.Handler;
import android.view.View;

public class FlappyBird extends View {

    private Bitmap background, birdDown, birdUp , birdMid;
    private int birdX, birdY, score = 0,birdIndex = 0;
    private boolean isBirdUp = true;
    private double time = 0;
    private final double BOUNCE_AMPLITUDE = 50, BOUNCE_FREQUENCY = 0.5;
    private  int POS_BIRD_X = 300, POS_BIRD_Y = 850;
    private Paint scorePaint;
    private Handler handler;
    private Runnable birdUpdateRunnable;

    public FlappyBird(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        birdDown = BitmapFactory.decodeResource(getResources(), R.drawable.redbird_downflap);
        birdMid = BitmapFactory.decodeResource(getResources(), R.drawable.redbird_midflap);
        birdUp = BitmapFactory.decodeResource(getResources(), R.drawable.redbird_upflap);

        POS_BIRD_X = 600 / 2;
        POS_BIRD_Y = 1700 / 2;

        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "font/flappy_bird_font.ttf");

        scorePaint = new Paint();
        scorePaint.setARGB(200, 0, 0, 0); // Set color (white)
        scorePaint.setTextSize(100); // Set text size
        scorePaint.setTypeface(customFont);

        handler = new Handler();
        birdUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                //setIncrement X and Y
                updateBirdPosition(POS_BIRD_X,POS_BIRD_Y);
                handler.postDelayed(this, 80);
            }
        };
        handler.postDelayed(birdUpdateRunnable, 80);

    }

    private void updateBirdPosition(double x , double y) {
        double interpolation = 0.1; // Adjust the interpolation factor
        int targetY = (int) (y + (int) (Math.sin(time) * BOUNCE_AMPLITUDE));
        int targetX = (int) (x + (int) (Math.sin(time) * BOUNCE_AMPLITUDE));
        birdY = (int) (y + interpolation * (targetY - y));
        birdX = (int) (x + interpolation * (targetX - x));
        time += BOUNCE_FREQUENCY;
        if (birdIndex == 3) {
           birdIndex=-1;
        }
        birdIndex++;

    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, 0, 0, null);
        Bitmap currentBird;
        if (birdIndex==0) {
             currentBird =  birdUp;
        } else if (birdIndex==1) {
             currentBird =  birdMid;
        }else{
             currentBird =  birdDown;
        }
        canvas.drawBitmap(currentBird, birdX, birdY, null);

        canvas.drawText( "" + score, 500, 300, scorePaint);
    }

    public void incrementScore() {
        score++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("TAG", "onTouchEvent: "+event.getX());
            setPOS_BIRD_X(event.getX());
            setPOS_BIRD_Y(event.getY());
            Log.d("TAG", "onTouchEvent: "+event.getY());

            updateBirdPosition(event.getX(),event.getY());
            invalidate();
            incrementScore();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void setPOS_BIRD_X(double x){
        this.POS_BIRD_X = (int) x;
    }

    public int getPOS_BIRD_X(){
        return this.POS_BIRD_X;
    }

    public void setPOS_BIRD_Y(double y){
        this.POS_BIRD_Y = (int) y;
    }

    public int getPOS_BIRD_Y(){
        return this.POS_BIRD_Y;
    }
}
