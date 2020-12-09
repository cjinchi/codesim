package com.chenjinchi.codesim;

import antlr.CPP14Lexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CppCompare {
    // ID, int, float, double, bool, char, signed, unsigned, short, long, wchar_t, char16_t, char32_t, ','
    private final static List<Integer> parameterTypes = Arrays.asList(128, 39, 33, 24, 8, 12, 55, 72, 54, 40, 77, 13, 14, 118);

    // private, protected, public
    private final static List<Integer> accessibilityTypes = Arrays.asList(48, 49, 50);

    public static List<Token> tokenize(String code) {
        CPP14Lexer lexer = new CPP14Lexer(CharStreams.fromString(code));
        CommonTokenStream stream = new CommonTokenStream(lexer);
        stream.fill();
        return stream.getTokens();
    }

    public static List<Token> filter(List<Token> tokens) {
        try {
            List<Token> ret = new ArrayList<>();
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).getType() == 123) {
                    // '::', check RC1
                    if (!ret.isEmpty()) {
                        ret.remove(ret.size() - 1);
                    }
                } else if (tokens.get(i).getType() == 96) {
                    // '<', check RC2
                    int temp = i + 1;
                    while (temp < tokens.size() && parameterTypes.contains(tokens.get(temp).getType())) {
                        temp++;
                    }
                    if (tokens.get(temp).getType() == 97) {
                        // '>'
                        // throw all
                        i = temp;
                    } else {
                        ret.add(tokens.get(i));
                    }
                } else if (accessibilityTypes.contains(tokens.get(i).getType())) {
                    // check RC5
                    if (i + 1 < tokens.size() && tokens.get(i + 1).getType() == 122) {
                        // ':'
                        //throw accessibility and ':'
                        i = i + 1;
                    }
                }else{
                    ret.add(tokens.get(i));
                }
            }
            return ret;
        } catch (Exception e) {
            return tokens;
        }

    }

    public static double compare(String codeA, String codeB) {
        List<Token> tokensA = filter(tokenize(codeA));
        List<Token> tokensB = filter(tokenize(codeB));

        // verbose log
        CodeSim.verbosePrintln("---------------------------------");
        CodeSim.verbosePrintln("Tokens of file 1 after filtering:");
        for (Token token:tokensA){
            CodeSim.verbosePrint(token.getText()+" ");
        }
        CodeSim.verbosePrintln("");

        CodeSim.verbosePrintln("---------------------------------");
        CodeSim.verbosePrintln("Tokens of file 2 after filtering:");
        for(Token token:tokensB){
            CodeSim.verbosePrint(token.getText()+" ");
        }
        CodeSim.verbosePrintln("");
        CodeSim.verbosePrintln("---------------------------------");

        if (tokensA.size()<1 || tokensB.size()<1){
            System.err.println("Invalid code length.");
            System.exit(ErrorCode.INVALID_CODE_LENGTH.getValue());
        }

        boolean[][] isEqual = new boolean[tokensA.size()][tokensB.size()];
        for (int i = 0; i < tokensA.size(); i++) {
            for (int j = 0; j < tokensB.size(); j++) {
                isEqual[i][j] = (tokensA.get(i).getType() == tokensB.get(j).getType());
            }
        }

        final int MIN_SEQUENCE_LENGTH = 10;
        CodeSim.verbosePrintln("MIN_SEQUENCE_LENGTH is 10.");

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
                    if (offset >= MIN_SEQUENCE_LENGTH) {
                        // found sim
                        for (int index = 0; index < offset; index++) {
                            coveredA[i + index] = true;
                            coveredB[j + index] = true;
                        }
                    }
                }
            }
        }

        int coveredLengthA = 0, coveredLengthB = 0;
        for (boolean covered : coveredA) {
            if (covered) {
                coveredLengthA++;
            }
        }
        for (boolean covered : coveredB) {
            if (covered) {
                coveredLengthB++;
            }
        }
        CodeSim.verbosePrintln("Cover length of file 1 is "+coveredLengthA+"/"+tokensA.size());
        CodeSim.verbosePrintln("Cover length of file 2 is "+coveredLengthB+"/"+tokensB.size());

        return 100 * Math.max((double) coveredLengthA / tokensA.size(), (double) coveredLengthB / tokensB.size());
    }

}
