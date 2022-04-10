package com.example.w22g4_log_em_payslip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView txtViewTotal;
    TextView txtViewHours;
    PaySlipDatabase paySlipInfo = new PaySlipDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewHours = findViewById(R.id.txtViewHours);
        txtViewTotal = findViewById(R.id.txtViewTotal);

        String punchIn = "2021-04-05 12:02:10", punchOut = "2021-04-05 17:02:10", empName="Jeff";
        try {
            paySlipInfo.insertData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date emphours = new Date();

        try {
            emphours = paySlipInfo.updateHours(punchIn, punchOut, empName);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtViewHours.setText(emphours + "");

//        double totayPay = 0;
//        totayPay = paySlipInfo.GetEmpPayTotal(empName, emphours);
//        txtViewTotal.setText(totayPay + "");

    }


}