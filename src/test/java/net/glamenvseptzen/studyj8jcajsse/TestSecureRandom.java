package net.glamenvseptzen.studyj8jcajsse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSecureRandom {
    @Test
    public void defaultProvider() {
        final SecureRandom sr0 = new SecureRandom();
        System.out.println("SecureRandom.getAlgorithm()=" + sr0.getAlgorithm());
        System.out.println("SecureRandom.getProvider().getName()=" + sr0.getProvider().getName());
        final byte[] r0 = new byte[10];
        sr0.nextBytes(r0);
        final byte[] seed = sr0.generateSeed(10);
        assertEquals(seed.length, 10);
    }

    @Test
    public void testSetSeed() {
        final SecureRandom sr0 = new SecureRandom();
        final byte[] seed = sr0.generateSeed(10);

        final SecureRandom sr1 = new SecureRandom();
        sr1.setSeed(seed);

        final SecureRandom sr2 = new SecureRandom();
        sr2.setSeed(seed);

        for (int i = 0; i < 10; i++) {
            byte[] r1 = new byte[10];
            byte[] r2 = new byte[10];
            sr1.nextBytes(r1);
            sr2.nextBytes(r2);
            assertEquals(r1, r2);
        }
    }

    @Test
    public void testStrongProvider() throws NoSuchAlgorithmException {
        final SecureRandom sr0 = SecureRandom.getInstanceStrong();
        System.out.println("SecureRandom.getInstanceStrong().getAlgorithm()=" + sr0.getAlgorithm());
        System.out.println("SecureRandom.getInstanceStrong().getProvider().getName()=" + sr0.getProvider().getName());
        final byte[] r0 = new byte[10];
        sr0.nextBytes(r0);
        final byte[] seed = sr0.generateSeed(10);
        assertEquals(seed.length, 10);

        final SecureRandom sr1 = SecureRandom.getInstanceStrong();
        assertNotEquals(sr0, sr1);
    }

    @Test
    public void testRandomClassMethod() {
        final SecureRandom sr0 = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            final int r = sr0.nextInt(10);
            assertTrue((0 <= r) && (r < 10));
        }
    }

    @Test(expectedExceptions = { NoSuchAlgorithmException.class })
    public void testNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        SecureRandom.getInstance("abcd1234");
        Assert.fail("not reach here");
    }
}
