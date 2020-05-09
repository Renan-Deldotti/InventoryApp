package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductsRepository {

    private ProductsDao productsDao;
    private LiveData<List<Products>> products;
    private static final int ACTION_INSERT = 1;
    private static final int ACTION_UPDATE = 2;
    private static final int ACTION_DELETE = 3;
    private static final int ACTION_DELETE_ALL = 4;
    private static final int ACTION_DELETE_SINGLE = 5;

    public ProductsRepository(Application application){
        InventoryDatabase database = InventoryDatabase.getInstance(application);
        productsDao = database.productsDao();
        products = productsDao.getAllProducts();
    }

    public LiveData<List<Products>> getProductsSortedByQuantity(){
        return productsDao.getAllProductsSortedByQuantity();
    }

    public void insert(final Products products){
        InventoryDatabase.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                productsDao.insert(products);
            }
        });
    }
    public void update(Products products){new productsDbAsyncTask(productsDao,ACTION_UPDATE).execute(products);}
    public void delete(Products products){new productsDbAsyncTask(productsDao,ACTION_DELETE).execute(products);}
    public void deleteAllData(){new productsDbAsyncTask(productsDao,ACTION_DELETE_ALL).execute();}
    public void deleteSingleItem(int singleItemId){new productsDbAsyncTask(productsDao,ACTION_DELETE_SINGLE,singleItemId).execute();}
    public LiveData<List<Products>> getAllProducts(){return products;}


    private static class productsDbAsyncTask extends AsyncTask<Products,Void,Void>{
        private ProductsDao productsDao;
        private int action;
        private int idToDelete;
        private productsDbAsyncTask(ProductsDao productsDao, int action){
            this.productsDao = productsDao;
            this.action = action;
        }
        private productsDbAsyncTask(ProductsDao productsDao, int action, int idToDelete){
            this.productsDao = productsDao;
            this.action = action;
            this.idToDelete = idToDelete;
        }
        @Override
        protected Void doInBackground(Products... products) {
            switch (action){
                case ACTION_INSERT:
                    productsDao.insert(products[0]);
                    break;
                case ACTION_UPDATE:
                    productsDao.update(products[0]);
                    break;
                case ACTION_DELETE:
                    productsDao.delete(products[0]);
                    break;
                case ACTION_DELETE_ALL:
                    productsDao.deleteAllProducts();
                    break;
                case ACTION_DELETE_SINGLE:
                    productsDao.deleteSingleItem(idToDelete);
                default:
                    return null;
            }
            return null;
        }
    }
}