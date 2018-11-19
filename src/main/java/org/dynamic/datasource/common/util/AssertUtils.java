package org.dynamic.datasource.common.util;

import java.util.Collection;

/**
 *
 * @date 2018-11-17 21:59:08
 */
public class AssertUtils {

    public static void isNull(Object obj,RuntimeException runtimeException){
        if(null == obj){
            throw runtimeException;
        }
    }

    public static void isEmpty(Collection cols,RuntimeException runtimeException){
        if(null == cols || cols.size() == 0){
            throw runtimeException;
        }
    }

    public static void isBlank(String str,RuntimeException runtimeException){
        if(null == str || str.trim().length() == 0){
            throw runtimeException;
        }
    }
}
