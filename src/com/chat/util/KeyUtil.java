package com.chat.util;

import java.security.KeyPair;

/**
 * @Auther: CQ02
 * @Date: 2019/1/4 15:20
 * @Description:
 */
public class KeyUtil {
    private KeyPair keyPair = null;

    private static KeyUtil single = null;

    private KeyUtil() {
        try {
            this.keyPair = RSAUtil.getKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static KeyUtil getInstance() {
        if (single == null) {
            synchronized (KeyUtil.class) {
                if (single == null) {
                    single = new KeyUtil();
                }
            }
        }
        return single;
    }
}
