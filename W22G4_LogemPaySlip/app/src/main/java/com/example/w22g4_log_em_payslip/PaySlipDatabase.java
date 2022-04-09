package com.example.w22g4_log_em_payslip;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PaySlipDatabase extends SQLiteOpenHelper {

    static final private String DB_NAME = "PaySlip";
    static final private String DB_Table = "payslips";
    static final private int DB_VERSION = 1;

    Context ctx;
    SQLiteDatabase myDB;


    public PaySlipDatabase(Context ct) {
        super(ct, DB_NAME, null, DB_VERSION);
        ctx = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DB_Table + " (_id integer primary key autoincrement, emp_name text, " +
                "emp_punchin datetime, emp_punchout datetime, emp_pay double, total_pay double);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData() {
        myDB = getWritableDatabase();
        myDB.execSQL("insert into " + DB_Table + " (emp_name, emp_pay) values('Jeff', '15.65');");
    }

    public void updateHours(String punchIn, String punchOut, String empName) {
        myDB = getWritableDatabase();
        myDB.execSQL("update " + DB_Table + " set emp_punchin = '" + punchIn + "', emp_punchout = '" + punchOut + "' " +
                "where emp_name = '" + empName + "'");

        myDB.execSQL("select hour(timediff(emp_punchin, emp_punchout)) from " + DB_Table + " where emp_name = '" + empName + "';");

    }

    public double getEmpPay(String empName) {
        double pay;
        myDB = getReadableDatabase();
        pay = myDB.execSQL("select emp_pay from " + DB_Table + " where emp_name = '" + empName + "';");

        return pay;
    }
}