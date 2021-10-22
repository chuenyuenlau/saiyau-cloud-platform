package com.saiyau.auth;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.PublicKey;

/**
 * @author liuzhongyuan
 * @ClassName GeneratePublicKey.java
 * @Description TODO
 * @createTime 2021/10/22
 */
public class GeneratePublicKey {
    public static void main(String[] args) {
        //密钥库名称
        String keystore = "jwt.jks";
        //密钥库密码
        String keystorePassword = "123456";
        //别名
        String alias = "jwt";
        //密钥密码
        String keyPassword = "123456";
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keystore), keystorePassword.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keyPassword.toCharArray());
        PublicKey publicKey = keyPair.getPublic();
        System.out.println(new BASE64Encoder().encode(publicKey.getEncoded()));
    }
}
