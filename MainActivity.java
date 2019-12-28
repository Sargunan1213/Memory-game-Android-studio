package com.example.mygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button login, register;
    private EditText name, password;
    private DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        reference1 = FirebaseDatabase.getInstance().getReference().child("MEMBER");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                final String tempname1 = name.getText().toString();
                final String temppassword1 = password.getText().toString();
                if(tempname1.equals("") || temppassword1.equals("")){
                    Toast.makeText(MainActivity.this, "Incomplete info:))", Toast.LENGTH_SHORT).show();
                    return;
                }
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(tempname1)){
                            Toast.makeText(MainActivity.this, "Username Taken :((", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Registration Successful :))", Toast.LENGTH_SHORT).show();
                            Member m = new Member(tempname1, temppassword1);
                            reference1.child(tempname1).setValue(m);
                            Intent i = new Intent(MainActivity.this, Main2Activity.class);
                            i.putExtra("Name", m.getName());
                            i.putExtra("Password", m.getPassword());
                            i.putExtra("Highscore", m.getHighscore());
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;

            case R.id.login:
                    final String tempname = name.getText().toString();
                    final String temppassword = password.getText().toString();
                if(tempname.equals("") || temppassword.equals("")){
                    Toast.makeText(MainActivity.this, "Incomplete info:))", Toast.LENGTH_SHORT).show();
                    return;
                }
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(tempname)){
                                String pass = dataSnapshot.child(tempname).child("password").getValue().toString();
                                Long highscore =  (Long) dataSnapshot.child(tempname).child("highscore").getValue();
                                if(pass.equals(temppassword)){
                                    Toast.makeText(MainActivity.this, "Login Successful :))", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                                    i.putExtra("Name", tempname);
                                    i.putExtra("Password", temppassword);
                                    i.putExtra("Highscore", highscore);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Incorrect Password :((", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Please Register :))", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;
        }

    }
}
