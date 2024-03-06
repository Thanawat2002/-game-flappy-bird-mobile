package com.example.flappy_bird_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class FlappyBird extends View {

    private Bitmap background, birdDown, birdUp;
    private int birdX, birdY;
    private boolean isBirdUp = true;
    private double time = 0;
    private final double BOUNCE_AMPLITUDE = 20; // Adjust the desired amplitude
    private final double BOUNCE_FREQUENCY = 0.15; // Adjust the desired frequency
    private final int CENTER_X; // Adjust the desired X-coordinate
    private final int CENTER_Y; // Adjust the desired Y-coordinate
    private int score = 0;
    private Paint scorePaint;

    public FlappyBird(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        birdDown = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirddown);
        birdUp = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirdup);

        CENTER_X = 600 / 2;
        CENTER_Y = 1700 / 2;

        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "font/flappy_bird_font.ttf");
        // Set the initial X-coordinate to the center
        birdX = CENTER_X;
        birdY = CENTER_Y;
        scorePaint = new Paint();
        scorePaint.setARGB(200, 0, 0, 0); // Set color (white)
        scorePaint.setTextSize(100); // Set text size
        scorePaint.setTypeface(customFont);
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, 0, 0, null);

        birdY = CENTER_Y + (int) (Math.sin(time) * BOUNCE_AMPLITUDE);

        Bitmap currentBird = isBirdUp ? birdUp : birdDown;
        canvas.drawBitmap(currentBird, birdX, birdY, null);

        birdY = CENTER_Y + (int) (Math.sin(time) * BOUNCE_AMPLITUDE);

        canvas.drawText( "" + score, 500, 300, scorePaint);
        time += BOUNCE_FREQUENCY;

        isBirdUp = !isBirdUp;

        invalidate();
    }

    public void incrementScore() {
        score++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Increment the score when the screen is touched
            incrementScore();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
