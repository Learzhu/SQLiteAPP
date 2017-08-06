package com.learzhu.sqliteapp.sqlite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * ${className}.java是极搜浏览器的$DES$类。
 *
 * @author Learzhu
 * @version 3.0.0 2017/8/6 21:06
 * @update Learzhu 2017/8/6 21:06
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
@Target(ElementType.TYPE)
public @interface DbTable {
    String value();
}
