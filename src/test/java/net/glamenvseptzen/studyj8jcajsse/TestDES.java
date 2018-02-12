package net.glamenvseptzen.studyj8jcajsse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDES {

    @Test
    public void testWithRandomKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        final KeyGenerator kgen = KeyGenerator.getInstance("DES");
        assertEquals(kgen.getAlgorithm(), "DES");
        final SecretKey skey = kgen.generateKey();
        assertEquals(skey.getAlgorithm(), "DES");
        assertEquals(skey.getFormat(), "RAW");
        assertEquals(skey.getEncoded().length, 8);

        final Cipher c0 = Cipher.getInstance("DES");
        c0.init(Cipher.ENCRYPT_MODE, skey);
        assertEquals(c0.getAlgorithm(), "DES");
        assertEquals(c0.getBlockSize(), 8);
        assertNull(c0.getIV());
        final byte[] plain0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05 };
        final byte[] crypted = c0.doFinal(plain0);
        System.out.println("crypted(DES with random key) : {" + Main.dumpHex(crypted) + "}");
        c0.init(Cipher.DECRYPT_MODE, skey);
        final byte[] plain1 = c0.doFinal(crypted);
        assertEquals(plain0, plain1);
    }

    @Test
    public void testWithFixedKey() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        final byte[] rawKey = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        DESKeySpec desKey = new DESKeySpec(rawKey);
        assertEquals(desKey.getKey(), rawKey);
        SecretKeyFactory kf0 = SecretKeyFactory.getInstance("DES");
        SecretKey skey = kf0.generateSecret(desKey);
        assertEquals(skey.getAlgorithm(), "DES");
        assertEquals(skey.getFormat(), "RAW");
        System.out.println("DES fixed SecretKey.getEncoded() : {" + Main.dumpHex(skey.getEncoded()) + "}");
        assertEquals(skey.getEncoded(), new byte[] { 0x01, 0x01, 0x02, 0x02, 0x04, 0x04, 0x07, 0x07 });

        final Cipher c0 = Cipher.getInstance("DES");
        c0.init(Cipher.ENCRYPT_MODE, skey);
        assertEquals(c0.getAlgorithm(), "DES");
        assertEquals(c0.getBlockSize(), 8);
        assertNull(c0.getIV());
        final byte[] plain0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05 };
        c0.update(new byte[] { 0x00, 0x01, 0x02 });
        final byte[] crypted = c0.doFinal(new byte[] { 0x03, 0x04, 0x05 });
        System.out.println("crypted(DES with fixed key) : {" + Main.dumpHex(crypted) + "}");
        assertEquals(crypted, Main.toba(0x81, 0xe1, 0x9e, 0x65, 0x68, 0xad, 0x83, 0xc9));
        c0.init(Cipher.DECRYPT_MODE, skey);
        final byte[] plain1 = c0.doFinal(crypted);
        assertEquals(plain0, plain1);
    }

    @Test
    public void testInvalidParameterException() throws NoSuchAlgorithmException {
        final KeyGenerator kgen = KeyGenerator.getInstance("DES");
        try {
            kgen.init(57);
            Assert.fail("not reach here");
        } catch (InvalidParameterException e) {
            assertEquals(e.getMessage(), "Wrong keysize: must be equal to 56");
        }
    }

    @Test
    public void testIllegalBlockSizeException() throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final KeyGenerator kgen = KeyGenerator.getInstance("DES");
        final SecretKey skey = kgen.generateKey();
        // JDK8 support requirements (NoPadding)
        final String[] algos = new String[] { "DES/CBC/NoPadding", "DES/ECB/NoPadding" };
        final byte[] plain0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        for (String algo : algos) {
            final Cipher c0 = Cipher.getInstance(algo);
            c0.init(Cipher.ENCRYPT_MODE, skey);
            assertEquals(c0.getAlgorithm(), algo);
            assertEquals(c0.getBlockSize(), 8);
            try {
                c0.doFinal(plain0);
                Assert.fail("not reach here");
            } catch (IllegalBlockSizeException e) {
                assertEquals(e.getMessage(), "Input length not multiple of 8 bytes");
            }
        }
    }

    @Test
    public void testDESPatterns()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        final byte[] rawKey = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        DESKeySpec desKey = new DESKeySpec(rawKey);
        SecretKeyFactory kf0 = SecretKeyFactory.getInstance("DES");
        SecretKey skey = kf0.generateSecret(desKey);

        final byte[] rawIV = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };
        final IvParameterSpec iv0 = new IvParameterSpec(rawIV);
        assertEquals(iv0.getIV(), rawIV);

        // JDK8 support requirements
        final String[] algos =
            new String[] { "DES/CBC/NoPadding", "DES/CBC/PKCS5Padding", "DES/ECB/NoPadding", "DES/ECB/PKCS5Padding" };
        final byte[] plain0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        for (String algo : algos) {
            final Cipher c0 = Cipher.getInstance(algo);
            if (algo.indexOf("CBC") > 0) {
                c0.init(Cipher.ENCRYPT_MODE, skey, iv0);
            } else {
                c0.init(Cipher.ENCRYPT_MODE, skey);
            }
            assertEquals(c0.getAlgorithm(), algo);
            assertEquals(c0.getBlockSize(), 8);
            System.out.println("IV(" + algo + ") : {" + Main.dumpHex(c0.getIV()) + "}");
            final byte[] crypted = c0.doFinal(plain0);
            System.out.println("crypted(" + algo + ") : {" + Main.dumpHex(crypted) + "}");
            if (algo.indexOf("CBC") > 0) {
                c0.init(Cipher.DECRYPT_MODE, skey, iv0);
            } else {
                c0.init(Cipher.DECRYPT_MODE, skey);
            }
            final byte[] plain1 = c0.doFinal(crypted);
            assertEquals(plain0, plain1);
        }
    }
}
