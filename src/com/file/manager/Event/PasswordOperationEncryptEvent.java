package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;
import com.file.manager.normal.NormalConstant;
import info.monitorenter.cpdetector.io.*;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @Auther: CQ02
 * @Date: 2019/1/2 09:32
 * @Description: 加密事件
 */
public class PasswordOperationEncryptEvent implements ActionListener {

    private FileList fileSystemList = null;

    private FileNodeOperation fileNodeOperation = null;

    public PasswordOperationEncryptEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {
        this.fileNodeOperation = fileNodeOperation;
        this.fileSystemList = fileSystemList;
    }

    public void execute() throws Exception {
        I_Node node = (I_Node) fileSystemList.getSelectedValue();

        encrypt(node.getPath(), NormalConstant.DES_KEY);
    }

    /**
     * DES加密
     *
     * @param
     * @return
     */
    private void encrypt(String path, byte[] desKey) {
        String charsetName = null;
        //处理文件的字符集，得到文件的字符集
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;

        String encryptedData = null;
        File file = new File(path);
        int index = path.lastIndexOf("\\");
        String destFile =
                path.split("\\.")[0] +
                        "加密文件" + "." + path.split("\\.")[1];
        try {
            File newFile = new File(destFile);
            DESKeySpec deskey = new DESKeySpec(NormalConstant.DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, NormalConstant.SECURE_RANDOM);
            // 加密，并把字节数组编码成字符串
            char[] buffer = new char[1024];
            byte[] bytes = new byte[4096];
            int r;
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            OutputStream outputStream = new FileOutputStream(newFile);
            charset = detector.detectCodepage(inputStream, 8);
            if (charset.name().equalsIgnoreCase("US-ASCII") || charset.name().equals("windows-1252")) {
                charsetName = "gbk";
            } else {
                charsetName = charset.name();
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charsetName);

            while ((r = inputStreamReader.read(buffer)) >= 0) {
                String str = new String(buffer);
                while (str.getBytes().length < bytes.length) {
                    str = str + "*";
                }
                encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(str.getBytes()));
                outputStream.write(encryptedData.getBytes());
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            inputStreamReader.close();
            file.delete();
            newFile.renameTo(new File(path));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            execute();
            RefreshEvent refreshEvent = new RefreshEvent(fileNodeOperation, fileSystemList);
            refreshEvent.execute();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
