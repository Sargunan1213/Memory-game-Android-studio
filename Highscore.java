package com.example.mygame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Highscore extends Activity implements ValueEventListener, View.OnClickListener {

    TextView t, one, two, three;
    Button back1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        long high = Long.valueOf(0);
        back1 = findViewById(R.id.back);
        back1.setOnClickListener(this);
        t = findViewById(R.id.high);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        high = getIntent().getLongExtra("Highscores", high);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Highscores");
        reff.addListenerForSingleValueEvent(this);
        t.setText(Long.toString(high));
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        long[] high = new long[3];
        int i = 0;
        String[] names = new String[3];
        for(DataSnapshot c : dataSnapshot.getChildren()){
            high[i] = Long.valueOf(c.getValue().toString());
            names[i] = c.getKey().toString();
            i++;
        }
        one.setText(names[0] + "       " + String.valueOf(high[0]));
        two.setText(names[1] + "       " + String.valueOf(high[1]));
        three.setText(names[2] + "       " + String.valueOf(high[2]));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }
}
