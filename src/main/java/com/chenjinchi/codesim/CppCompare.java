package com.chenjinchi.codesim;

import antlr.CPP14Lexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class CppCompare {
    public static List<Token> tokenize(String code) {
        CPP14Lexer lexer = new CPP14Lexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        return tokens.getTokens();
    }

    public static double compare(String codeA, String codeB) {
        List<Token> tokensA = tokenize(codeA);
        List<Token> tokensB = tokenize(codeB);

        boolean[][] isEqual = new boolean[tokensA.size()][tokensB.size()];
        for (int i = 0; i < tokensA.size(); i++) {
            for (int j = 0; j < tokensB.size(); j++) {
                isEqual[i][j] = (tokensA.get(i).getType() == tokensB.get(j).getType());
            }
        }

        final int MIN_SEQUENCE_LENGTH = 8;
//        final int MAX_GAP_LENGTH = 3;

        boolean[][] visited = new boolean[tokensA.size()][tokensB.size()];
        boolean[] coveredA = new boolean[tokensA.size()];
        boolean[] coveredB = new boolean[tokensB.size()];
        for (int i = 0; i < tokensA.size(); i++) {
            for (int j = 0; j < tokensB.size(); j++) {
                if (isEqual[i][j] && !visited[i][j]) {
                    int offset = 0;
                    while (i + offset < tokensA.size() && j + offset < tokensB.size() && isEqual[i + offset][j + offset]) {
                        visited[i + offset][j + offset] = true;
                        offset++;
                    }
                    if (offset < MIN_SEQUENCE_LENGTH) {
                        // remove sparse sequence
                        for (int index = 0; index < offset; index++) {
                            isEqual[i + index][j + index] = false;
                        }
                    } else {
                        // found sim
                        for (int index = 0; index < offset; index++) {
                            coveredA[i + index] = true;
                            coveredB[j + index] = true;
                        }
                    }
                }
            }
        }

        int coveredLengthA = 0,coveredLengthB = 0;
        for(boolean covered:coveredA){
            if(covered){
                coveredLengthA++;
            }
        }
        for(boolean covered:coveredB){
            if (covered){
                coveredLengthB++;
            }
        }

        return 100*Math.max((double)coveredLengthA/ tokensA.size(),(double)coveredLengthB/ tokensB.size());
    }

    public static void main(String[] args) {

    }
}
