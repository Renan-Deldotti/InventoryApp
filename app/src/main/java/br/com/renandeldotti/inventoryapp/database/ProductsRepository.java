package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductsRepository {

    private ProductsDao productsDao;
    private LiveData<List<Products>> products;
    private LiveData<List<Sold>> soldProducts;
    private static final int ACTION_INSERT = 1;
    private static final int ACTION_UPDATE = 2;
    private static final int ACTION_DELETE = 3;
    private static final int ACTION_DELETE_ALL = 4;

    public ProductsRepository(Application application){
        InventoryDatabase database = InventoryDatabase.getInstance(application);
        productsDao = database.productsDao();
        products = productsDao.getAllProducts();
        soldProducts = productsDao.getAllSales();
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
    public LiveData<List<Products>> getAllProducts(){return products;}
    public LiveData<List<Sold>> getAllSales(){return soldProducts;}


    private static class productsDbAsyncTask extends AsyncTask<Products,Void,Void>{
        private ProductsDao productsDao;
        private int action;
        private productsDbAsyncTask(ProductsDao productsDao, int action){
            this.productsDao = productsDao;
            this.action = action;
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
                default:
                    return null;
            }
            return null;
        }
    }
}