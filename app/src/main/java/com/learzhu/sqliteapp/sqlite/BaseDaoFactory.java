package com.learzhu.sqliteapp.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 21:23
 * @update Learzhu 2017/8/6 21:23
 * @updateDes
 * @include {@link }
 * @used {@link }
 */

public class BaseDaoFactory {
    private static final BaseDaoFactory ourInstance = new BaseDaoFactory();

    private SQLiteDatabase sqliteDataBase;
    private String sqliteDataBasePath;


    public static BaseDaoFactory getInstance() {
        return ourInstance;
    }

    private BaseDaoFactory() {
        sqliteDataBasePath = Environment.getExternalStorageDirectory().getPath() + "/lz.db";
        sqliteDataBase = SQLiteDatabase.openOrCreateDatabase(sqliteDataBasePath, null);
    }

    public synchronized <T extends BaseDao<M>, M> T
    getDataHelper(Class<T> clazz, Class<M> entityClass) {
        BaseDao baseDao = null;
        try {
//            baseDao = BaseDao.class.newInstance();
            baseDao = clazz.newInstance();
            baseDao.initDatabase(sqliteDataBase, entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.initDatabase(sqliteDataBase, entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
