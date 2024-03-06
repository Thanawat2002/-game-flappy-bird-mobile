package com.example.flappy_bird_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.os.Handler;
import android.view.View;

public class FlappyBird extends View {

    private Bitmap background, birdDown, birdUp;
    private int birdX, birdY, score = 0;
    private boolean isBirdUp = true;
    private double time = 0;
    private final double BOUNCE_AMPLITUDE = 50, BOUNCE_FREQUENCY = 0.5;
    private final int POS_BIRD_X, POS_BIRD_Y;
    private Paint scorePaint;
    private Handler handler;
    private Runnable birdUpdateRunnable;

    public FlappyBird(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        birdDown = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirddown);
        birdUp = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirdup);

        POS_BIRD_X = 600 / 2;
        POS_BIRD_Y = 1700 / 2;

        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "font/flappy_bird_font.ttf");
        birdX = POS_BIRD_X;
        birdY = POS_BIRD_Y;
        scorePaint = new Paint();
        scorePaint.setARGB(200, 0, 0, 0); // Set color (white)
        scorePaint.setTextSize(100); // Set text size
        scorePaint.setTypeface(customFont);

        handler = new Handler();
        birdUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                updateBirdPosition();
                invalidate();
                handler.postDelayed(this, 80);
            }
        };
        handler.postDelayed(birdUpdateRunnable, 80);

    }

    private void updateBirdPosition() {
        double interpolation = 0.1; // Adjust the interpolation factor
        int targetY = POS_BIRD_Y + (int) (Math.sin(time) * BOUNCE_AMPLITUDE);
        birdY = (int) (birdY + interpolation * (targetY - birdY));
        time += BOUNCE_FREQUENCY;
        isBirdUp = !isBirdUp;
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, 0, 0, null);

        Bitmap currentBird = isBirdUp ? birdUp : birdDown;
        canvas.drawBitmap(currentBird, birdX, birdY, null);

        canvas.drawText( "" + score, 500, 300, scorePaint);
    }

    public void incrementScore() {
        score++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            incrementScore();
            return true;
        }
        return super.onTouchEvent(event);
    }

}
