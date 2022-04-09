package com.example.dashboardlogem;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "employee")
public class Employee {

    @NonNull
    @PrimaryKey
    private String empNum;
    private String empName;
    private String messages;
    private String clockInTime;
    private String clockOutTime;

    public Employee(){

    }

    public Employee(@NonNull String empNum, String empName, String messages, String clockInTime, String clockOutTime) {
        this.empNum = empNum;
        this.empName = empName;
        this.messages = messages;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
    }

    @NonNull
    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(@NonNull String empNum) {
        this.empNum = empNum;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
}
