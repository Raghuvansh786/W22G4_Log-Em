package com.example.dashboardlogem;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Entity.class}, version = 1)
public abstract class EntityDatabase extends RoomDatabase {

    private static EntityDatabase instance;

    public abstract EntityDao entityDao();

    public static synchronized EntityDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), EntityDatabase.class, "entity_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private EntityDao entityDao;

        private PopulateDbAsyncTask(EntityDatabase db){
            entityDao = db.entityDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            entityDao.insert(new Entity("Title 1", "Description 1"));
            entityDao.insert(new Entity("Title 2", "Description 2"));
            entityDao.insert(new Entity("Title 3", "Description 3"));
            entityDao.insert(new Entity("Title 4", "Description 4"));

            return null;
        }
    }
}
