package com.example.w22g4_log_em_payslip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtViewTotal;
    TextView txtViewHours;
    PaySlipDatabase paySlipInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewHours = findViewById(R.id.txtViewHours);
        txtViewTotal = findViewById(R.id.txtViewTotal);

        String punchIn, punchOut, empName;

        txtViewHours = paySlipInfo.updateHours(punchIn, punchOut, empName);

        double empPay;
        empPay = paySlipInfo.getEmpPay(empName);

    }


}