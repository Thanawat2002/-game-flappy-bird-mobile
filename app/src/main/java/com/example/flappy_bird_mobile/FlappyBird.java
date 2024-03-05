package com.example.flappy_bird_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class FlappyBird extends View {

    private Bitmap background, birdDown, birdUp;
    private int birdX, birdY;
    private boolean isBirdUp = true;

    public FlappyBird(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        birdDown = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirddown);
        birdUp = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirdup);

        birdX = 300; // Set the desired X-coordinate
        birdY = 850; // Set the desired Y-coordinate
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, 0, 0, null);

//        birdY += isBirdUp ? birdVelocityUp : birdVelocityDown;
        Bitmap currentBird = isBirdUp ? birdUp : birdDown;
        canvas.drawBitmap(currentBird, birdX, birdY, null);

        // Swap the flag for the next iteration
        isBirdUp = !isBirdUp;

        // Trigger the onDraw method continuously for the animation effect
        invalidate();
    }

}
