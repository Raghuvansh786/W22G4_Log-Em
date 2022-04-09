package com.example.dashboardlogem.interfaces;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dashboardlogem.Employee;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert(onConflict = IGNORE)
    void insertEmployee(Employee... employees);

    @Insert(onConflict = REPLACE)
    Long[] insertEmployeeFromList(List<Employee> employees);

    @Insert(onConflict = REPLACE)
    void insertOneEmployee(Employee employee);

    @Query("SELECT * FROM employee")
    List<Employee> getAllEmployees();


}
