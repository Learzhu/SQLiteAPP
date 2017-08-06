package com.learzhu.sqliteapp.sqlite;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 20:58
 * @update Learzhu 2017/8/6 20:58
 * @updateDes
 * @include {@link }
 * @used {@link }
 */

public interface IBaseDao<T> {
    long insert(T entity);

    long update(T entity, T where);
}
