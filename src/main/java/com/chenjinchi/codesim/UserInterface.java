package com.chenjinchi.codesim;

public class UserInterface {
    public static void printUsage(){
        System.out.println("usage: codesim [-v|--verbose] [-h|--help] code1 code2");
    }

    public static Config processArgs(String[] args){
        Config config = new Config();

        if (args.length==0){
            config.setHelp(true);
            return config;
        }

        for (String arg : args) {
            if (arg.startsWith("-")) {
                if ("-v".equals(arg) || "--verbose".equals(arg)) {
                    config.setVerbose(true);
                } else if ("-h".equals(arg) || "--help".equals(arg)) {
                    config.setHelp(true);
                } else {
                    config.setError(true);
                    config.setErrorInfo("Unknown option argument: " + arg);
                    return config;
                }
            } else {
                config.getFiles().add(arg);
            }
        }
        if(!config.isHelp() && config.getFiles().size()!=2){
            config.setError(true);
            config.setErrorInfo("Please specify two files each time.");
        }
        return config;
    }
}
