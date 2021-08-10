package me.zhengjie.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author bron
 * todo 设置ssl password
 */
public class EncryptUtil {

    /**
     * @param str
     * @return
     */
    public static String decryption(String str) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//        textEncryptor.setPassword(UKDataContext.getSystemSecrityPassword());
        return textEncryptor.decrypt(str);
    }
}
