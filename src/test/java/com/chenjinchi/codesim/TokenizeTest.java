package com.chenjinchi.codesim;

import org.antlr.v4.runtime.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TokenizeTest {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> tokenTypes  = Files.readAllLines(Paths.get(TokenizeTest.class.getResource("/CPP14Lexer.tokens").toURI()));
        Map<Integer,String> type2literal = new HashMap<>();
        for (String line:tokenTypes){
            int equalIndex = line.lastIndexOf('=');
            type2literal.put(Integer.valueOf(line.substring(equalIndex+1)),line.substring(0,equalIndex));
        }


        String code = new String(Files.readAllBytes(Paths.get(TokenizeTest.class.getResource("/CppSourceFiles/1.cpp").toURI())), StandardCharsets.UTF_8);
        List<Token> tokens = CppCompare.tokenize(code);
        for (Token token : tokens) {
            System.out.println(token.toString()+"    "+token.getText()+"     "+type2literal.get(token.getType()));
        }
    }
}
