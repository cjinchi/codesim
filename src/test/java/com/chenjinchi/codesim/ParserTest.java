package com.chenjinchi.codesim;

import antlr.CPP14Lexer;
import antlr.CPP14Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserTest {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String code = new String(Files.readAllBytes(Paths.get(ParserTest.class.getResource("/backup/2.cpp").toURI())), StandardCharsets.UTF_8);
        CPP14Lexer lexer = new CPP14Lexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);

//        CPP14Parser.StatementContext context = parser.statement();
////        System.out.println(context.toStringTree(parser));
//        new CPP14ParserVisitor<>().vis
    }
}
