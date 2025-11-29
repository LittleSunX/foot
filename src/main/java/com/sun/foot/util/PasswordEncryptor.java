package com.sun.foot.util;

import com.sun.crypto.AesUtil;

public class PasswordEncryptor {

    public static void main(String[] args) throws Exception {
        // 从 secret.key 文件复制 Base64 字符串
        String base64Key = "ykhllt4Tha0PN5UHTDak9g==";
        String plain = "root"; // 数据库明文密码
        String encrypted = AesUtil.encrypt(plain, base64Key);
        System.out.println("ENC(" + encrypted + ")");

        System.out.println(AesUtil.decrypt("KHS5zGygMlA3HE/63R5cZg==", base64Key));
    }
}
