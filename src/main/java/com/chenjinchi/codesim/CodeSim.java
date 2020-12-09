package com.chenjinchi.codesim;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CodeSim {
    private static boolean verbose = false;


    public static void verbosePrintln(String content){
        if (verbose){
            System.out.println(content);
        }
    }

    public static void verbosePrint(String content){
        if (verbose){
            System.out.print(content);
        }
    }


    public static void main(String[] args) {
        Config config = UserInterface.processArgs(args);
        if (config.isError()) {
            System.err.println(config.getErrorInfo());
            System.exit(ErrorCode.INVALID_CONFIG.getValue());
        }
        if (config.isHelp()) {
            UserInterface.printUsage();
            return;
        }
        if(config.isVerbose()){
            verbose = true;
        }

        verbosePrintln("Args analysed successfully.");
        verbosePrintln("Start reading code from files.");


        String codeA = null, codeB = null;
        try {
            codeA = new String(Files.readAllBytes(Paths.get(config.getFiles().get(0))), StandardCharsets.UTF_8);
            codeB = new String(Files.readAllBytes(Paths.get(config.getFiles().get(1))), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage()+": file not found");
            System.exit(ErrorCode.FILE_NOT_FOUND.getValue());
        }

        verbosePrintln("Reading files successfully.");
        verbosePrintln("Start comparing.");

        System.out.println(CppCompare.compare(codeA, codeB));


    }
}
