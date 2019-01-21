package com.file.manager.Event;

import com.file.manager.frame.FileList;
import com.file.manager.function.FileNodeOperation;
import com.file.manager.function.I_Node;
import com.file.manager.normal.NormalConstant;
import info.monitorenter.cpdetector.io.*;
import sun.misc.BASE64Decoder;

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
 * @Date: 2019/1/2 09:34
 * @Description: 解密事件
 */
public class PasswordOperationDecryptEvent implements ActionListener {

    private FileList fileSystemList = null;

    private FileNodeOperation fileNodeOperation = null;

    public PasswordOperationDecryptEvent(FileNodeOperation fileNodeOperation, FileList fileSystemList) {

        this.fileNodeOperation = fileNodeOperation;

        this.fileSystemList = fileSystemList;
    }

    public void execute() {
        I_Node node = (I_Node) fileSystemList.getSelectedValue();

        decrypt(node.getPath(), NormalConstant.DES_KEY);
    }

    private void decrypt(String path, byte[] desKey) {
        String decryptedData = null;
        File file = new File(path);
        int index = path.lastIndexOf("\\");
        String destFile =
                path.split("\\.")[0] +
                        "加密文件" + "." + path.split("\\.")[1];
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            File newFile = new File(destFile);
            OutputStream outputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[5616];
            int r;

            DESKeySpec desKeySpec = new DESKeySpec(desKey);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(desKeySpec);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, NormalConstant.SECURE_RANDOM);

            while ((r = inputStream.read(buffer)) >= 0) {
                System.out.println(new String(buffer));
                decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder()
                        .decodeBuffer(new String(buffer, 0, r))));
                String[] str = decryptedData.split("\\*");
                System.out.println(decryptedData);
                outputStream.write(str[0].getBytes());
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
            file.delete();
            newFile.renameTo(new File(path));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }
}
