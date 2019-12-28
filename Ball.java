package com.example.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

public class Ball {
    public float x, y;
    private Bitmap color, white;
    private boolean red;

    public Ball(float x, float y, Bitmap color1, Bitmap whit) {
        this.x = x;
        this.y = y;
        this.color = color1;
        this.white = whit;
        this.red = true;
    }

    public boolean contains(float l, float m){
        if(x - 100 + 125 <= l && l <= x + 100 + 125){
            if(y - 100 + 120 <= m && m <= y + 100 + 120)
                return true;
        }
        return false;
    }
    public boolean getRed(){
        return red;
    }

    public void draw(Canvas c, int t){
        if(t < 100){
            c.drawBitmap(color, x, y, null);
        }
        else {
            c.drawBitmap(white, x + 35, y + 15, null);
        }
    }

    public void setRed(boolean red) {
        this.red = red;
    }

}
