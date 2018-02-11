package net.glamenvseptzen.studyj8jcajsse;

import static org.testng.Assert.assertEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMessageDigest {

    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        assertEquals(md.getAlgorithm(), "MD5");
        assertEquals(md.getDigestLength(), 16);

        final byte[] src0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05 };
        md.update(src0);
        final byte[] r0 = md.digest();
        assertEquals(r0.length, 16);
        System.out.println("md5 : {" + Main.dumpHex(r0) + "}");
        // @formatter:off
        final byte[] expected0 = Main.toba(0xd1,0x5a,0xe5,0x39,0x31,0x88,0x0f,0xd7,0xb7,0x24,0xdd,0x78,0x88,0xb4,0xb4,0xed);
        // @formatter:on
        assertEquals(r0, expected0);

        assertEquals(md.digest(src0), expected0);

        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.update(new byte[] { 0x03, 0x04, 0x05 });
        assertEquals(md.digest(), expected0);

        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.reset();
        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.update(new byte[] { 0x03, 0x04, 0x05 });
        assertEquals(md.digest(), expected0);
    }

    @Test
    public void testSHA1() throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        assertEquals(md.getAlgorithm(), "SHA-1");
        assertEquals(md.getDigestLength(), 20);

        final byte[] src0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05 };
        md.update(src0);
        final byte[] r0 = md.digest();
        assertEquals(r0.length, 20);
        System.out.println("sha1 : {" + Main.dumpHex(r0) + "}");
        // @formatter:off
        final byte[] expected0 = Main.toba(0x86,0x84,0x60,0xd9,0x8d,0x09,0xd8,0xbb,0xb9,0x3d,0x7b,0x6c,0xdd,0x15,0xcc,0x7f,0xbe,0xc6,0x76,0xb9);
        // @formatter:on
        assertEquals(r0, expected0);

        assertEquals(md.digest(src0), expected0);

        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.update(new byte[] { 0x03, 0x04, 0x05 });
        assertEquals(md.digest(), expected0);

        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.reset();
        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.update(new byte[] { 0x03, 0x04, 0x05 });
        assertEquals(md.digest(), expected0);
    }

    @Test
    public void testSHA256() throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        assertEquals(md.getAlgorithm(), "SHA-256");
        assertEquals(md.getDigestLength(), 32);

        final byte[] src0 = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05 };
        md.update(src0);
        final byte[] r0 = md.digest();
        assertEquals(r0.length, 32);
        System.out.println("sha256 : {" + Main.dumpHex(r0) + "}");
        // @formatter:off
        final byte[] expected0 = Main.toba(0x17,0xe8,0x8d,0xb1,0x87,0xaf,0xd6,0x2c,0x16,0xe5,0xde,0xbf,0x3e,0x65,0x27,0xcd,0x00,0x6b,0xc0,0x12,0xbc,0x90,0xb5,0x1a,0x81,0x0c,0xd8,0x0c,0x2d,0x51,0x1f,0x43);
        // @formatter:on
        assertEquals(r0, expected0);

        assertEquals(md.digest(src0), expected0);

        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.update(new byte[] { 0x03, 0x04, 0x05 });
        assertEquals(md.digest(), expected0);

        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.reset();
        md.update(new byte[] { 0x00, 0x01, 0x02 });
        md.update(new byte[] { 0x03, 0x04, 0x05 });
        assertEquals(md.digest(), expected0);
    }

    @Test(expectedExceptions = { NoSuchAlgorithmException.class })
    public void testNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        MessageDigest.getInstance("abcd1234");
        Assert.fail("not reach here");
    }
}
