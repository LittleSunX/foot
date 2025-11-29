package com.sun.foot.util;

import com.sun.crypto.AesUtil;

public class AESKeyGenerator {
    public static void main(String[] args) throws Exception {
        String base64Key = AesUtil.generateKey();
        System.out.println("请将以下内容写入 secret.key 文件：");
        System.out.println(base64Key);
    }
}
