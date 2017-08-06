package com.learzhu.sqliteapp.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 20:59
 * @update Learzhu 2017/8/6 20:59
 * @updateDes
 * @include {@link }
 * @used {@link }
 */

public class BaseDao<T> implements IBaseDao<T> {

    /**
     * 创建表
     *
     * @param entity
     * @return
     */
    SQLiteDatabase mSQLiteDatabase;

    //插入的对象类型
    private Class<T> mEntityClass;

    private boolean inited = false;

    private String tableName;

    private Map<String, Field> cacheMap;

    protected boolean initDatabase(SQLiteDatabase sqliteDatabase, Class<T> entityClass) {
        this.mSQLiteDatabase = sqliteDatabase;
        this.mEntityClass = entityClass;

        if (!inited) {
            //下一步  创建表名
            tableName = entityClass.getAnnotation(DbTable.class).value();
            if (!mSQLiteDatabase.isOpen()) {
                return false;
            }
            String createTableSql = createTable();
            mSQLiteDatabase.execSQL(createTableSql);
        }
        initHashMap();
        return true;
    }

    //自动创建表 拼接数据库语句
    private String createTable() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName + "(");

        Field[] fields = mEntityClass.getDeclaredFields();
        for (Field field :
                fields) {
            Class type = field.getType();
            //反射赋值
            if (type == String.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " TEXT,");
            } else if (type == Double.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " DOUBLE,");
            } else if (type == Integer.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " INTEGER,");
            } else if (type == Long.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BIGINT,");
            } else if (type == byte[].class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BLOB,");
            } else {
                //不支持的类型
                continue;
            }
        }
        //去除末尾的逗号
        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override
    public long insert(T entity) {
        //拆解 对象
        Map<String, String> map = getValues(entity);
        //map转换成ContentValues
        ContentValues values = getContentValues(map);
        long result = mSQLiteDatabase.insert(tableName, null, values);
        return result;
    }

    @Override
    public long update(T entity, T where) {
        Map<String, String> values = getValues(entity);
        ContentValues contentValues = getContentValues(values);

        Map<String, String> whereMap = getValues(where);
        Condition condition = new Condition(whereMap);
//        mSQLiteDatabase.update(tableName, contentValues, "tb_name=?", new String[]{"lz"});
        int update = mSQLiteDatabase.update(tableName, contentValues, condition.whereClause, condition.whereArgs);
        return update;
    }

    private Map<String, String> getValues(T entity) {
//        entity.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next();
            field.setAccessible(true);

            //反射拿到值
            try {
                Object object = field.get(entity);
                if (object == null) {
                    continue;
                }

                String value = object.toString();
                String key = field.getAnnotation(DbFiled.class).value();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;

    }

    private void initHashMap() {
        cacheMap = new HashMap<>();
        //要知道列名
        String sql = "select * from " + this.tableName + " limit 1,0";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        //获取表中的列名
        String[] columnNames = cursor.getColumnNames();

        Field[] colmounFields = mEntityClass.getDeclaredFields();

        //遍历映射
        for (String colmunName :
                columnNames) {
            Field resultField = null;
            for (Field field
                    : colmounFields) {
                String fieldAannotionName = field.getAnnotation(DbFiled.class).value();
                //找到对应的映射
                if (colmunName.equals(fieldAannotionName)) {
                    resultField = field;
                    break;
                }
            }

            if (resultField != null) {
                cacheMap.put(colmunName, resultField);
            }
        }
    }

    public ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    class Condition {
        String whereClause;
        String[] whereArgs;

        public Condition(Map<String, String> whereMap) {
            StringBuffer stringBuffer = new StringBuffer();
            List list = new ArrayList();
            Set keys = whereMap.keySet();
            Iterator iterator = keys.iterator();
            stringBuffer.append(" 1=1 ");
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = whereMap.get(key);
                if (value != null) {
                    stringBuffer.append("and " + key + "=?");
                    list.add(value);
                }
            }
        }
    }
}
