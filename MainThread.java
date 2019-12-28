package com.example.mygame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.app.Activity.*;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;

public class MainThread extends Thread implements View.OnTouchListener {
    private GameView gameview;
    private SurfaceHolder surfaceholder;
    public Canvas canvas;
    private int time;
    private boolean running;
    public int numberofgames;
    Member member;
    private boolean firstshot;
    private Resources res;

    public MainThread(SurfaceHolder s, GameView g, Member m, Resources re){
        super();
        this.res = re;
        this.gameview = g;
        this.member = m;
        this.surfaceholder = s;
        this.running = true;
        this.time = 0;
        numberofgames = 0;
        this.firstshot = true;
    }
    public int gettime(){
        return time;
    }

    public boolean getrun(){
        return this.running;
    }

    @Override
    public void run(){
        while(true) {
            if(!surfaceholder.getSurface().isValid())
                continue;
            try{
                time ++;
                canvas = this.surfaceholder.lockCanvas();
                this.gameview.draw(canvas, time);
                this.surfaceholder.unlockCanvasAndPost(canvas);
                if(!this.running){
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        float x1 = e.getX();
        float y1 = e.getY();
        boolean touchcorrectball = false;
        boolean contains = false;
        for(int i = 0; i < 9; i++)
            if(gameview.balls[i].contains(x1,y1))
            {
                touchcorrectball = !gameview.balls[i].getRed();
                contains = true;
                break;
            }
        if(!contains){
            return true;
        }
        if(touchcorrectball && firstshot && contains){
            numberofgames += 1;
        }
        if(!touchcorrectball && contains)
        {
            if(numberofgames >= member.getHighscore())
                member.setHighscore(numberofgames);
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference ref1, ref2;
            ref1 = reff.child("Highscores");
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int[] high = new int[3];
                    String[] names = new String[3];
                    int i = 0;
                    for(DataSnapshot c : dataSnapshot.getChildren()){
                        high[i] = Integer.valueOf(c.getValue().toString());
                        names[i] = c.getKey().toString();
                        i++;
                    }
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    dataSnapshot.getRef().removeValue();
                    long t = member.getHighscore();
                    for(int j = 2; j >= 0; j--){
                        if(t > high[j]){
                            high[j] = (int) t;
                            names[j] = member.getName();
                            break;
                        }
                    }
                    for(int j = 2; j >= 0; j--) {
                        ref1.child(String.valueOf(names[j])).setValue(high[j]);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            ref2 = reff.child("MEMBER");
            ref2.child(member.getName()).setValue(member);
            setrun(false);
            canvas = this.surfaceholder.lockCanvas();
            this.gameview.draw(canvas, time);
            this.surfaceholder.unlockCanvasAndPost(canvas);
            return true;
        }
        time = 0;
        Bitmap colors1[] = new Bitmap[9];
        Bitmap white = BitmapFactory.decodeResource(this.res, R.drawable.white);
        colors1[0] = BitmapFactory.decodeResource(this.res, R.drawable.need1);
        colors1[1] = BitmapFactory.decodeResource(this.res, R.drawable.need2);
        colors1[2] = BitmapFactory.decodeResource(this.res, R.drawable.need3);
        colors1[3] = BitmapFactory.decodeResource(this.res, R.drawable.need4);
        colors1[4] = BitmapFactory.decodeResource(this.res, R.drawable.need5);
        colors1[5] = BitmapFactory.decodeResource(this.res, R.drawable.need6);
        colors1[6] = BitmapFactory.decodeResource(this.res, R.drawable.need7);
        colors1[7] = BitmapFactory.decodeResource(this.res, R.drawable.need8);
        colors1[8] = BitmapFactory.decodeResource(this.res, R.drawable.need9);
        List<Integer> a = new ArrayList<Integer>();
        for(int i = 0 ; i < 9; i++){
            a.add(i);
        }
        Collections.shuffle(a);
        for(int i = 0; i < 9 ; i+=3) {
            gameview.balls[i] = new Ball(60, 180 + 400*((int)i/3), colors1[a.get(i)], white);
            gameview.balls[i+1] = new Ball(410, 180 + 400*((int)i/3), colors1[a.get(i+1)], white);
            gameview.balls[i+2] = new Ball(760, 180 + 400*((int)i/3), colors1[a.get(i+2)], white);
        }
        int r = (int)(Math.random() * 8);
        gameview.last_ball = new Ball(400, 1380, colors1[a.get(r)], colors1[a.get(r)]);
        gameview.balls[r].setRed(false);
        return true;
    }

    public void setrun(boolean b) {
        this.running = b;
    }

}
