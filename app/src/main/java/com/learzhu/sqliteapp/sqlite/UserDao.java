package com.learzhu.sqliteapp.sqlite;

import java.util.List;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 21:16
 * @update Learzhu 2017/8/6 21:16
 * @updateDes
 * @include {@link }
 * @used {@link }
 */

public class UserDao extends BaseDao {

    String createTable() {
        return "create table if not exists tb_user (name varchar(20),password varchar(10))";
    }

    public List query(String sql) {
        return null;
    }
}
