package com.chat.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;

import info.monitorenter.cpdetector.io.*;
import org.apache.commons.io.FileUtils;


/**
 * @Auther: CQ02
 * @Date: 2019/1/8 17:31
 * @Description:
 */
public class DesUtil {

    /**
     * DES算法密钥
     */
    private static final byte[] DES_KEY = {21, 1, -110, 82, -32, -85, -128, -65};

    /**
     * 数据加密，算法（DES）
     *
     * @param data 要进行加密的数据
     * @return 加密后的数据
     */
    public static String encryptBasedDes(String data) {
        String charsetName = null;
        String encryptedData = null;
        //处理文件的字符集，得到文件的字符集
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串
            char[] buffer = new char[1024];
            byte[] bytes = new byte[4096];
            int r;
            OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\CQ02\\Desktop\\新建 Microsoft Word 文档 (3).docx"));
            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File
                    ("C:\\Users\\CQ02\\Desktop\\新建 Microsoft Word 文档 (2).docx")));
            charset = detector.detectCodepage(inputStream, 8);
            System.out.println(charset.name());
            if (charset.name().equalsIgnoreCase("US-ASCII") || charset.name().equals("windows-1252")) {
                charsetName = "gbk";
            } else {
                charsetName = charset.name();
            }
            System.out.println(charsetName);
            System.out.println(charset.name());
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charsetName);
            BufferedReader br = new BufferedReader(inputStreamReader);
            while ((r = inputStreamReader.read(buffer)) >= 0) {
                String str = new String(buffer);
                while (str.getBytes().length < bytes.length) {
                    str = str + "*";
                }
                encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(str.getBytes()));
                System.out.println(str);
                System.out.println(str.getBytes().length);
                System.out.println(encryptedData.length());
                System.out.println(encryptedData);
                outputStream.write(encryptedData.getBytes());
                outputStream.flush();
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            inputStreamReader.close();
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    /**
     * 数据解密，算法（DES）
     *
     * @param cryptData 加密数据
     * @return 解密后的数据
     */
    public static String decryptBasedDes(String cryptData) {
        String decryptedData = null;

        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            byte[] buffer = new byte[5616];
            int a;
            OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\CQ02\\Desktop\\新建 Microsoft Word 文档 (2).docx"));
            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File
                    ("C:\\Users\\CQ02\\Desktop\\新建 Microsoft Word 文档 (3).docx")));
            while ((a = inputStream.read(buffer)) >= 0) {
                System.out.println(new String(buffer, 0, a));
                decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder()
                        .decodeBuffer(new String(buffer, 0, a))));
                String[] str = decryptedData.split("\\*");

                System.out.println("\n");
                outputStream.write(str[0].getBytes());
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String str = "0123456789abcdefg";

        // DES数据加密
        String s1 = encryptBasedDes(str);


        // DES数据解密
        String s2 = decryptBasedDes(s1);

//        String str = "aaa";
//
//        String[] strings = str.split("/");
//        System.out.println(strings.length);
//        System.out.println(Arrays.toString(strings));


    }
}
