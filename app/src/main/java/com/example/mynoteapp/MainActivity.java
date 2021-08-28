package com.example.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter adapter;
    List<model>note_list;
    Databaseclass databaseclass;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        coordinatorLayout=findViewById(R.id.layout_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);
            }
        });
        note_list=new ArrayList<>();
        databaseclass=new Databaseclass(this);
        fetchAllNoteFromDatabase();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new Adapter(this,MainActivity.this,note_list);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

     void fetchAllNoteFromDatabase() {
         Cursor cursor =databaseclass.readAllData();
         if(cursor.getCount()==0){
             Toast.makeText(this,"No Data Show",Toast.LENGTH_LONG).show();
         }
         else {
             while (cursor.moveToNext()){
                 note_list.add(new model(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
             }
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setQueryHint("search here");
        SearchView.OnQueryTextListener listener=new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        };
        searchView.setOnQueryTextListener(listener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.delete_all_note){
                deleteAllNote();
            }
            return  super.onOptionsItemSelected(item);
    }

    private void deleteAllNote() {
        Databaseclass databaseclass=new Databaseclass(MainActivity.this);
        databaseclass.deleteAllNote();
        recreate();
    }
    ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            model item=adapter.getList().get(position);
            adapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Item Deleted",Snackbar.LENGTH_SHORT).setAction("UNDO",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restore(item,position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    if(!(event==DISMISS_EVENT_ACTION)){
                        Databaseclass databaseclass=new Databaseclass(MainActivity.this);

                        databaseclass.deleteSingleItem(item.getid());
                    }
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();



        }
    };

}