package net.glamenvseptzen.studyj8jcajsse;

public interface ISubCommand {
    public String getDescription();
    public void run(String[] args) throws Exception;
}
