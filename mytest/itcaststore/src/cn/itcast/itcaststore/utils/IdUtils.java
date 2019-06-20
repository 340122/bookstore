package cn.itcast.itcaststore.utils;

import java.util.UUID;

/*
利用UUID，随机生成Id编号
 */
public class IdUtils {

    public static String getUUID() {
        String code = UUID.randomUUID().toString();
        return code;
    }
}
