package com.example.mygame;

import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends Activity implements View.OnClickListener {

    private Button goGame, highscore;
    Member m;
    DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        goGame = findViewById(R.id.gogame);
        goGame.setOnClickListener(this);
        highscore = findViewById(R.id.highscore);
        highscore.setOnClickListener(this);
        Long highscore = Long.valueOf(0);
        highscore = getIntent().getLongExtra("Highscore", highscore);
        m = new Member(getIntent().getStringExtra("Name"), getIntent().getStringExtra("Password"), highscore);
        reference1 = FirebaseDatabase.getInstance().getReference().child("Highscores");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.highscore:
                final Intent i1 = new Intent(this, Highscore.class);
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("MEMBER").child(m.getName());
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long Hig = (Long) dataSnapshot.child("highscore").getValue();
                        i1.putExtra("Highscores", Hig);
                        startActivity(i1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case R.id.gogame:
                Intent i = new Intent(this, Activity1.class);
                i.putExtra("Name", m.getName());
                i.putExtra("Password", m.getPassword());
                i.putExtra("Highscore", m.getHighscore());
                startActivity(i);
                break;
        }

    }
}
