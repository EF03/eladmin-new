package me.zhengjie.utils;


import cn.hutool.core.codec.Base62;

import java.util.UUID;

/**
 * @author bron
 */
public class HtechUtil {

    /**
     * 当前时间+已过随机生成的 长整形数字
     *
     * @return
     */
    public static String genId() {
        return Base62.encode(getUUID()).toLowerCase();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
