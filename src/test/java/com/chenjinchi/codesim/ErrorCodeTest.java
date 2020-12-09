package com.chenjinchi.codesim;

public class ErrorCodeTest {
    public static void main(String[] args) {
        for (ErrorCode code:ErrorCode.values()){
            System.out.println(code.getValue());
        }
    }
}
