package com.example.mynoteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.MyviewHolder> implements Filterable {
    Context context;
    Activity activity;
    List<model>note_list;
    List<model>newList;


    public Adapter(Context context, Activity activity, List<model> note_list) {
        this.context = context;
        this.activity = activity;
        this.note_list = note_list;
        newList=new ArrayList<>(note_list);
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout,
                parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.title.setText(note_list.get(position).getTitle());
        holder.discription.setText(note_list.get(position).getDiscription());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,UpdateNoteActivity.class);
                intent.putExtra("title",note_list.get(position).getTitle());
                intent.putExtra("discription",note_list.get(position).getDiscription());
                intent.putExtra("id",note_list.get(position).getid());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return note_list.size();
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }
  private  Filter examplefilter=new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
          List<model>filteredlist=new ArrayList<>();
          if(constraint==null |constraint.length()==0){
              filteredlist.addAll(newList);
          }
          else {
              String filterpartner=constraint.toString().toLowerCase().trim();
              for(model item: newList){
                  if(item.getTitle().toLowerCase().contains(filterpartner)){
                      filteredlist.add(item);
                  }
              }

          }
          FilterResults filterResults=new FilterResults();
          filterResults.values=filteredlist;
          return filterResults;
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        note_list.clear();
        note_list.addAll((List)results.values);
        notifyDataSetChanged();
      }
  };
    public class MyviewHolder extends RecyclerView.ViewHolder {
         TextView title, discription;
         RelativeLayout layout;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            discription=itemView.findViewById(R.id.discription);
            layout=itemView.findViewById(R.id.not_layout);

        }
    }
    public   List<model>getList(){
        return note_list;
    }
    public  void  removeItem(int positioin){
         note_list.remove(positioin);
         notifyItemChanged(positioin);
    }
    public  void  restore(model item,int position){
        note_list.add(position,item);
        notifyItemInserted(position);
    }
}
