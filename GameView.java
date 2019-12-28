package com.example.mygame;

import android.app.Activity;
import com.example.mygame.Activity1;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import androidx.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    public MainThread t;
    Bitmap background, minion, white;
    Bitmap colors1[] = new Bitmap[9];
    public Ball[] balls = new Ball[9];
    public Ball last_ball;
    Canvas c;
    public GameView(Context c, Member m){
        super(c);
        getHolder().addCallback(this);
        this.t = new MainThread(getHolder(), this, m, getResources());
        colors1[0] = BitmapFactory.decodeResource(getResources(), R.drawable.need1);
        colors1[1] = BitmapFactory.decodeResource(getResources(), R.drawable.need2);
        colors1[2] = BitmapFactory.decodeResource(getResources(), R.drawable.need3);
        colors1[3] = BitmapFactory.decodeResource(getResources(), R.drawable.need4);
        colors1[4] = BitmapFactory.decodeResource(getResources(), R.drawable.need5);
        colors1[5] = BitmapFactory.decodeResource(getResources(), R.drawable.need6);
        colors1[6] = BitmapFactory.decodeResource(getResources(), R.drawable.need7);
        colors1[7] = BitmapFactory.decodeResource(getResources(), R.drawable.need8);
        colors1[8] = BitmapFactory.decodeResource(getResources(), R.drawable.need9);
        List<Integer> a = new ArrayList<Integer>();
        white = BitmapFactory.decodeResource(getResources(), R.drawable.white);
        for(int i = 0 ; i < 9; i++){
            a.add(i);
        }
        Collections.shuffle(a);
        setFocusable(true);
        for(int i = 0; i < 9 ; i+=3) {
            balls[i] = new Ball(60, 180 + 400*((int)i/3), colors1[a.get(i)], white);
            balls[i+1] = new Ball(410, 180 + 400*((int)i/3), colors1[a.get(i+1)], white);
            balls[i+2] = new Ball(760, 180 + 400*((int)i/3), colors1[a.get(i+2)], white);
        }
        int r = (int)(Math.random() * 8);
        last_ball = new Ball(400, 1380, colors1[a.get(r)], colors1[a.get(r)]);
        balls[r].setRed(false);
        minion = BitmapFactory.decodeResource(getResources(), R.drawable.minion);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        t.setrun(true);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(t.gettime() >= 100){
            this.t.onTouch(this, e);
        }
        return true;
    }

    public void draw(Canvas c, int t){
        super.draw(c);
        if(this.t.getrun()){
            c.drawBitmap(background, 0, 0, null);
            for(int i = 0; i < 9 ; i++)
                balls[i].draw(c, t);
            if (t >= 100) {
                last_ball.draw(c, t);
            }
           // c.drawBitmap(minion, 75, 180, null);
        }
        else{
            Paint p = new Paint();
            p.setTextSize(100);
            p.setColor(Color.BLACK);
            p.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawColor(Color.YELLOW);
            c.drawBitmap(minion, 0, 100, null);
            String text = "Games " + "Won:  " + this.t.numberofgames;
            c.drawText(text, 150,1500, p);
        }
        this.c = c;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean r = true;
        while(r){
            try{
                t.setrun(false);
                t.join();
                r = false;
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
