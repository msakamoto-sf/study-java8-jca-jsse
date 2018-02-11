package net.glamenvseptzen.studyj8jcajsse.subcommand;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

import net.glamenvseptzen.studyj8jcajsse.ISubCommand;
import net.glamenvseptzen.studyj8jcajsse.Main;

public class DumpSecurityProviders implements ISubCommand {

    @Override
    public String getDescription() {
        return "Dump security providers";
    }

    @Override
    public void run(String[] args) throws Exception {
        System.out.println("--- security providers ---");
        System.out.println("[name],[version],[info]");
        for (Provider p : Security.getProviders()) {
            System.out.println(p.getName() + "," + p.getVersion() + "," + p.getInfo());
            final Set<Provider.Service> services = p.getServices();
            System.out.println("  [alg],[type],[classname],[toString]");
            for (Provider.Service s : services) {
                System.out.println("  " + s.getAlgorithm() + "," + s.getType() + "," + s.getClassName() + ",");
                System.out.println("    " + Main.stripCRLF(s));
            }
        }
    }
}
