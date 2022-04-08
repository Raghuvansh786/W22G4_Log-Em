package com.example.dashboardlogem;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EntityRepository {

    private EntityDao entityDao;
    private LiveData<List<Entity>> allEntity;

    public EntityRepository(Application application){
        EntityDatabase database = EntityDatabase.getInstance(application);
        entityDao = database.entityDao();
        allEntity = entityDao.getAllNotes();
    }

    public void insert(Entity entity){
        new InsertEntityAsyncTask(entityDao).execute(entity);
    }

    public void update(Entity entity){
        new UpdateEntityAsyncTask(entityDao).execute(entity);

    }

    public void delete(Entity entity){
        new DeleteEntityAsyncTask(entityDao).execute(entity);

    }

    public void deleteAllNotes(){
        new DeleteAllEntityAsyncTask(entityDao).execute();

    }

    public LiveData<List<Entity>> getAllEntity(){
        return allEntity;
    }

    private static class InsertEntityAsyncTask extends AsyncTask<Entity, Void, Void>{
        private EntityDao entityDao;

        private InsertEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Entity... entities) {
            entityDao.insert(entities[0]);
            return null;
        }

    }

    private static class UpdateEntityAsyncTask extends AsyncTask<Entity, Void, Void>{
        private EntityDao entityDao;

        private UpdateEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Entity... entities) {
            entityDao.update(entities[0]);
            return null;
        }

    }

    private static class DeleteEntityAsyncTask extends AsyncTask<Entity, Void, Void>{
        private EntityDao entityDao;

        private DeleteEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Entity... entities) {
            entityDao.update(entities[0]);
            return null;
        }

    }

    private static class DeleteAllEntityAsyncTask extends AsyncTask<Void, Void, Void>{
        private EntityDao entityDao;

        private DeleteAllEntityAsyncTask(EntityDao entityDao){
            this.entityDao = entityDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            entityDao.deleteAllNotes();
            return null;
        }
    }

}
