package com.learzhu.sqliteapp.bean;

import com.learzhu.sqliteapp.sqlite.DbFiled;
import com.learzhu.sqliteapp.sqlite.DbTable;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 21:03
 * @update Learzhu 2017/8/6 21:03
 * @updateDes
 * @include {@link }
 * @used {@link }
 * user ----table_user
 * user-----user  映射不好
 */
@DbTable("tb_user")
public class User {

    @DbFiled("id")
    private Integer id;

    @DbFiled("tb_name")
    private String name;

    @DbFiled("tb_password")
    private String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
