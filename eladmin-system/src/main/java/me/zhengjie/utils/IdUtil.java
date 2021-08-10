package me.zhengjie.utils;

/**
 * @author bron
 */
public class IdUtil {

    public static String getContextId(String session) {
        return session.replaceAll("-", "");
    }
}
