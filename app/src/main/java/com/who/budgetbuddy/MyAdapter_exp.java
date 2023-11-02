package com.who.budgetbuddy;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import com.who.budgetbuddy.Model.Data;

public class MyAdapter_exp extends RecyclerView.Adapter<MyAdapter_exp.MyViewHolder>{

    Expense context;
    ArrayList<Data> list;
    public MyAdapter_exp(Expense context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data user=list.get(position);
        holder.Type.setText(user.getType());
        holder.Note.setText(user.getNote());
        String amount=String.valueOf(user.getAmount());
        holder.Amount.setText(amount);
        holder.Date.setText(user.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Type,Note,Amount,Date;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            Type=itemView.findViewById(R.id.type_txt_expense);
            Note=itemView.findViewById(R.id.note_txt_expense);
            Amount=itemView.findViewById(R.id.amount_txt_expense);
            Date=itemView.findViewById(R.id.date_txt_expense);


        }



    }
}
