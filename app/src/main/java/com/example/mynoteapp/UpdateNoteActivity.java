package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {
    EditText title,discription;
    Button   update_btn;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        title=findViewById(R.id.title_in);
        discription=findViewById(R.id.discription_in);
        update_btn=findViewById(R.id.update_btn);
        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        discription.setText(intent.getStringExtra("discription"));
        id=intent.getStringExtra("id");
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(title.getText().toString())&& !TextUtils.isEmpty(discription.getText().toString())){
                    Databaseclass db=new Databaseclass(UpdateNoteActivity.this);
                    db.updateNote(title.getText().toString(),discription.getText().toString(),id);
                    Intent intent1=new Intent(UpdateNoteActivity.this,MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1)
                    ;
                    finish();

                }
                else {
                    Toast.makeText(UpdateNoteActivity.this,"Both feilds requirs",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}