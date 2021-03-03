package com.vcanus.vjvmqttmariadb;

import com.vcanus.vjvmqttmariadb.Jasypt.JasyptConfig;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JasyptEncryption {


    @Test
    public void encryption(){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);
        encryptor.setPassword(JasyptConfig.KEY);
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");

        String plainText = "user";
        String encryptedText = encryptor.encrypt(plainText);
        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("Enc:" + encryptedText + ", Dec:" + decryptedText);

        plainText = "password";
        encryptedText = encryptor.encrypt(plainText);
        decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("Enc:" + encryptedText + ", Dec:" + decryptedText);

    }

}
