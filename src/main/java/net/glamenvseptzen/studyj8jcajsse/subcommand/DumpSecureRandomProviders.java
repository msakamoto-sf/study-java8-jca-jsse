package net.glamenvseptzen.studyj8jcajsse.subcommand;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

import net.glamenvseptzen.studyj8jcajsse.ISubCommand;
import net.glamenvseptzen.studyj8jcajsse.Main;

public class DumpSecureRandomProviders implements ISubCommand {

    @Override
    public String getDescription() {
        return "Dump SecureRandom providers";
    }

    @Override
    public void run(String[] args) throws Exception {
        System.out.println("--- SecureRandom providers ---");
        System.out.println("[alg],[provider],[toString(includes aliases)]");
        for (Provider p : Security.getProviders()) {
            final Set<Provider.Service> services = p.getServices();
            for (Provider.Service s : services) {
                if (!"SecureRandom".equals(s.getType())) {
                    continue;
                }
                System.out.println(s.getAlgorithm() + "," + p.getName() + "," + Main.stripCRLF(s));
            }
        }
    }
}
