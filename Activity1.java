package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity1 extends Activity implements View.OnClickListener {
     public GameView gameView;
     FrameLayout frame;
     RelativeLayout relative;
     Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long highscore = Long.valueOf(0);
        getIntent().getLongExtra("Highscore", highscore);
        Member m = new Member(getIntent().getStringExtra("Name"), getIntent().getStringExtra("Password"), highscore);
        gameView = new GameView(this, m);
        b1 = new Button(this);
        b1.setText("QUIT GAME");
        b1.setTextSize(20);
        b1.setId(1000013);


        frame = new FrameLayout(this);
        relative = new RelativeLayout(this);


        RelativeLayout.LayoutParams but1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams but3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        relative.setLayoutParams(but3);


        but1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        but1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        b1.setLayoutParams(but1);
        b1.setOnClickListener(this);
        relative.addView(b1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        frame.addView(gameView);
        frame.addView(relative);

        setContentView(frame);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

}
