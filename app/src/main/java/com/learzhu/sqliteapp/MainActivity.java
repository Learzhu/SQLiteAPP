package com.learzhu.sqliteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.learzhu.sqliteapp.bean.User;
import com.learzhu.sqliteapp.sqlite.BaseDao;
import com.learzhu.sqliteapp.sqlite.BaseDaoFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insert(View view) {
//        BaseDao<User> userBaseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        BaseDao<User> userBaseDao = BaseDaoFactory.getInstance().getDataHelper(BaseDao.class, User.class);
        userBaseDao.insert(new User(11, "B", "12345"));
    }

    public void update(View view) {
//        BaseDao<User> userBaseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        BaseDao<User> userBaseDao = BaseDaoFactory.getInstance().getDataHelper(BaseDao.class, User.class);

        //条件对象 tb_name----lz
        User where = new User();
//        where.setName("X");
        where.setName("B");
        User user = new User(2000, "NEW", "11111111111111");
        userBaseDao.update(user, where);

//        userBaseDao.update(new User(11, "B", "12345"));
    }

    public void onTestSQL(View view) {
//        insert(view);
        update(view);
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
