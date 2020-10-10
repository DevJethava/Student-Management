package com.android.dev.studentmanagement.DataBase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.dev.studentmanagement.DataBase.Model.StudentModel;

import java.util.List;

/**
 * Created by Dev Jethava on 10/10/2020.
 */
@Dao
public interface StudentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StudentModel model);

    @Delete
    void delete(StudentModel model);

    @Query("Update tbl_student set name= :name, email= :email, password= :pass, city= :city WHERE id= :id")
    void update(int id, String name, String email, String pass, String city);

    @Query("Select * FROM tbl_student")
    List<StudentModel> getAll();

    @Query("Select * FROM tbl_student WHERE email= :email AND password= :password")
    List<StudentModel> getLogin(String email, String password);
}
