package net.glamenvseptzen.studyj8jcajsse.subcommand;

import java.security.Security;

import net.glamenvseptzen.studyj8jcajsse.ISubCommand;

public class DumpMajorJcaJssePolicy implements ISubCommand {

    @Override
    public String getDescription() {
        return "Dump major security policies related to JCA/JSSE";
    }

    @Override
    public void run(String[] args) throws Exception {
        System.out.println("--- major security properties related to JCA/JSSE ---");
        final String[] majorProps = new String[] {
            // @formatter:off
            "securerandom.source",
            "securerandom.strongAlgorithms",
            "jdk.certpath.disabledAlgorithms",
            "jdk.tls.disabledAlgorithms",
            "jdk.tls.legacyAlgorithms",
            "jdk.tls.server.defaultDHEParameters",
            "crypto.policy",
            // @formatter:on
        };
        for (String p : majorProps) {
            final String v = Security.getProperty(p);
            System.out.println(p + "=[" + v + "]");
        }
    }

}
