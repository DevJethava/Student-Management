package com.android.dev.studentmanagement.DataBase.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.dev.studentmanagement.DataBase.DAO.StudentDAO;
import com.android.dev.studentmanagement.DataBase.Model.StudentModel;

/**
 * Created by Dev Jethava on 10/6/2020.
 */
@Database(entities = {StudentModel.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB instance;

    private static String DATABASE_NAME = RoomDB.class.getSimpleName();

    public synchronized static RoomDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract StudentDAO dataDAO();

}
