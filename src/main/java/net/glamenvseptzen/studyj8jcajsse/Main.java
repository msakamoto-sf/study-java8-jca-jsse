package net.glamenvseptzen.studyj8jcajsse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpMajorJcaJssePolicy;
import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpSecureRandomProviders;
import net.glamenvseptzen.studyj8jcajsse.subcommand.DumpSecurityProviders;

public class Main {
    static final Map<String, ISubCommand> COMMANDS;
    static {
        COMMANDS = new LinkedHashMap<>();
        COMMANDS.put("dump-providers", new DumpSecurityProviders());
        COMMANDS.put("dump-secure-random-providers", new DumpSecureRandomProviders());
        COMMANDS.put("dump-major-policy", new DumpMajorJcaJssePolicy());
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
