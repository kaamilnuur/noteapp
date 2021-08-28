package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
       EditText title, discriptioin;
       Button  btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title=findViewById(R.id.title_in);
        discriptioin=findViewById(R.id.discription_in);
        btnsave=findViewById(R.id.addNote);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(
                        discriptioin.getText().toString()
                )){
                     Databaseclass databaseclass=new Databaseclass(AddNoteActivity.this);
                     databaseclass.AddNote(title.getText().toString(),discriptioin.getText().toString());
                    Intent intent=new Intent(AddNoteActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddNoteActivity.this,"Both feilds Required",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}