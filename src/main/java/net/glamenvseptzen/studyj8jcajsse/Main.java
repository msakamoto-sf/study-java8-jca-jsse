package net.glamenvseptzen.studyj8jcajsse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpMajorJcaJssePolicy;
import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpMessageDigestProviders;
import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpSecureRandomProviders;
import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpSecurityProviders;

public class Main {
    static final Map<String, ISubCommand> COMMANDS;
    static {
        COMMANDS = new LinkedHashMap<>();
        COMMANDS.put("dump-providers", new DumpSecurityProviders());
        COMMANDS.put("dump-secure-random-providers", new DumpSecureRandomProviders());
        COMMANDS.put("dump-message-digest-providers", new DumpMessageDigestProviders());
        COMMANDS.put("dump-major-policy", new DumpMajorJcaJssePolicy());
    }

    public static String stripCRLF(final Object o) {
        return stripCRLF(o.toString());
    }

    public static String stripCRLF(final String s) {
        return s.replace("\r", "").replace("\n", "");
    }

    public static String dumpHex(final byte[] src) {
        final HexDumper hd = new HexDumper();
        hd.setPrefix("0x");
        hd.setSeparator(",");
        hd.setToUpperCase(false);
        return hd.dump(src);
    }

    public static byte[] toba(int... src) {
        if (src.length == 0) {
            return new byte[] {};
        }
        final byte[] r = new byte[src.length];
        for (int i = 0; i < src.length; i++) {
            r[i] = (byte) src[i];
        }
        return r;
    }

    public static void main(String[] args) throws Exception {
        String[] subargs = new String[0];
        ISubCommand subcommand = null;
        if (args.length > 0 && COMMANDS.containsKey(args[0])) {
            subargs = (args.length > 1) ? Arrays.copyOfRange(args, 1, args.length - 1) : new String[0];
            subcommand = COMMANDS.get(args[0]);
            subcommand.run(subargs);
        } else {
            System.out.println("Command Line Arguments : <command-name> (args...)");
            System.out.println("\n<command-name> are:");
            for (Entry<String, ISubCommand> e : COMMANDS.entrySet()) {
                System.out.println(e.getKey() + " - " + e.getValue().getDescription());
            }
            System.out.print("\nEnter <command-name>:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            final String commandname = br.readLine().trim();
            if (COMMANDS.containsKey(commandname)) {
                COMMANDS.get(commandname).run(new String[0]);
            } else {
                System.err.println(commandname + " is not available.");
            }
        }
    }
}
