package com.learzhu.sqliteapp.sqlite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 21:13
 * @update Learzhu 2017/8/6 21:13
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbFiled {
    String value();
}
