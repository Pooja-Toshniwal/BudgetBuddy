package com.who.budgetbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.who.budgetbuddy.Model.Data;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Callable;


public class Dashboard extends Fragment {
private FloatingActionButton fab_main;
private FloatingActionButton fab_income,fab_expense;

private TextView income_text,expense_text;
private Animation Open,Close;
private boolean isOpen=false;
private TextView incomeTotalSum,expenseTotalSum,income_percentage,expense_percentage;
private FirebaseAuth mAuth;
private DatabaseReference mIncomeDataBase,mExpenseDataBase;
    PieChart pieChart;
int a=0,b=0;
    float total_income = 0;
float total;
float total_expense=0;
float exp=0,inc=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser= mAuth.getCurrentUser();
        String uid=mUser.getUid();
        mIncomeDataBase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDataBase= FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        View myview = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fab_main=myview.findViewById(R.id.main_btn);
        fab_income=myview.findViewById(R.id.income_ft_btn);
        incomeTotalSum=myview.findViewById(R.id.income_data);
        expenseTotalSum=myview.findViewById(R.id.expense_data);
        fab_expense=myview.findViewById(R.id.expense_ft_btn);
        income_text=myview.findViewById(R.id.income_ft_text);
        expense_text=myview.findViewById(R.id.expense_ft_text);
        Open= AnimationUtils.loadAnimation(getActivity(),R.anim.float_button);
        Close=AnimationUtils.loadAnimation(getActivity(),R.anim.float_button_2);
        pieChart = myview.findViewById(R.id.piechart);
 expense_percentage=myview.findViewById(R.id.ep);
 income_percentage=myview.findViewById(R.id.ip);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                if (isOpen) {
                    fab_income.startAnimation(Close);
                    fab_expense.startAnimation(Close);
                    fab_income.setClickable(false);
                    fab_expense.setClickable(false);
                    income_text.startAnimation(Close);
                    expense_text.startAnimation(Close);
                    income_text.setClickable(false);
                    expense_text.setClickable(false);
                    isOpen = false;
                } else {
                    fab_income.startAnimation(Open);
                    fab_expense.startAnimation(Open);
                    fab_income.setClickable(true);
                    fab_expense.setClickable(true);
                    income_text.startAnimation(Open);
                    expense_text.startAnimation(Open);
                    income_text.setClickable(true);
                    expense_text.setClickable(true);
                    isOpen = true;
                }

            }

        });


        mIncomeDataBase.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a=1;
           total_income=0;
                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);
                    total_income += data.getAmount();
                    String sTotalvalue = String.valueOf(total_income);
                    incomeTotalSum.setText(sTotalvalue);
                }

                if(a==1 && b==1){
                    total=total_income+total_expense;
                    inc=(total_income/total)*100.0f;
                    exp=(total_expense/total)*100.0f;

                }
                else{
                    total=2;
                    inc=(1/total)*100.0f;
                    exp=(1/total)*100.0f;
                }
                if(total==0){
                    total=2;
                    inc=(1/total)*100.0f;
                    exp=(1/total)*100.0f;
                }

                change_chart();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));





        mExpenseDataBase.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                b=1;
                total_expense=0;

                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);
                    total_expense += data.getAmount();
                    String sTotalvalue = String.valueOf(total_expense);
                    expenseTotalSum.setText(sTotalvalue);
                }
                if(a==1 && b==1){
                    total=total_income+total_expense;
                    inc=(total_income/total)*100.0f;
                    exp=(total_expense/total)*100.0f;
                }
                else{
                    total=2;
                    inc=(1/total)*100.0f;
                    exp=(1/total)*100.0f;
                }
                if(total==0){
                    total=2;
                    inc=(1/total)*100.0f;
                    exp=(1/total)*100.0f;
                }

change_chart();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));



        return myview;
    }

    private void fbtn(){
        if (isOpen) {
            fab_income.startAnimation(Close);
            fab_expense.startAnimation(Close);
            fab_income.setClickable(false);
            fab_expense.setClickable(false);
            income_text.startAnimation(Close);
            expense_text.startAnimation(Close);
            income_text.setClickable(false);
            expense_text.setClickable(false);
            isOpen = false;
        } else {
            fab_income.startAnimation(Open);
            fab_expense.startAnimation(Open);
            fab_income.setClickable(true);
            fab_expense.setClickable(true);
            income_text.startAnimation(Open);
            expense_text.startAnimation(Open);
            income_text.setClickable(true);
            expense_text.setClickable(true);
            isOpen = true;
        }

    }
    private void addData(){
        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              income_data_insert();
            }
        });

        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 expense_data_insert();
            }
        });


    }

    public void income_data_insert(){
        AlertDialog.Builder mydialog =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview =inflater.inflate(R.layout.custom_layout_for_insert_data,null);
        mydialog.setView(myview);
        AlertDialog dialog= mydialog.create();
        dialog.setCancelable(false);
        EditText Amount=myview.findViewById(R.id.amount);
        EditText Note=myview.findViewById(R.id.Note);
        EditText Type=myview.findViewById(R.id.type);
        Button cancel=myview.findViewById(R.id.cancel);
        Button save=myview.findViewById(R.id.save );
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type=Type.getText().toString().trim();
                String note=Note.getText().toString().trim();
                String amount=Amount.getText().toString().trim();
                if(TextUtils.isEmpty(amount)){
                    Amount.setError("Required field");
                    return;
                }
                if(TextUtils.isEmpty(type)){
                      Type.setError("Required field");
                      return;
                }
                if(TextUtils.isEmpty(note)){
                    Note.setError("Required field");
                    return;
                }
                int amount_value=Integer.parseInt(amount);
                String id= mIncomeDataBase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(amount_value,type,note,id,mDate);
                mIncomeDataBase.child(id).setValue(data);
                Toast.makeText(getActivity(), "Data added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                fbtn();

            }
        });
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog.dismiss();
        fbtn();
    }
});

dialog.show();


    }


    public void expense_data_insert(){
        AlertDialog.Builder mydialog =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview =inflater.inflate(R.layout.custom_layout_for_insert_data,null);
        mydialog.setView(myview);
        AlertDialog dialog= mydialog.create();
        dialog.setCancelable(false);
        EditText Amount=myview.findViewById(R.id.amount);
        EditText Note=myview.findViewById(R.id.Note);
        EditText Type=myview.findViewById(R.id.type);
        Button cancel=myview.findViewById(R.id.cancel);
        Button save=myview.findViewById(R.id.save );


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type=Type.getText().toString().trim();
                String note=Note.getText().toString().trim();
                String amount=Amount.getText().toString().trim();
                if(TextUtils.isEmpty(amount)){
                    Amount.setError("Required field");
                    return;
                }
                if(TextUtils.isEmpty(type)){
                    Type.setError("Required field");
                    return;
                }
                if(TextUtils.isEmpty(note)){
                    Note.setError("Required field");
                    return;
                }
                int amount_value=Integer.parseInt(amount);
                String id= mExpenseDataBase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(amount_value,type,note,id,mDate);
                mExpenseDataBase.child(id).setValue(data);

                Toast.makeText(getActivity(), "Data added", Toast.LENGTH_SHORT).show();
                fbtn();

                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbtn();

                dialog.dismiss();

            }
        });

        dialog.show();


    }


    public void change_chart(){

        inc=(total_income/total)*100.0f;
       Double inc_p = BigDecimal.valueOf(inc)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
       exp=100.00f-inc;
        Double exp_p = BigDecimal.valueOf(exp)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
      expense_percentage.setText(Double.toString(exp_p));
      income_percentage.setText(Double.toString(inc_p));


        pieChart.clearChart();
        pieChart.addPieSlice(
                new PieModel(
                        "E",exp
                        ,
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "I",inc
                        ,
                        Color.parseColor("#29B6F6")));


    }



}