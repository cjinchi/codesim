package com.chenjinchi.codesim;

public class ErrorCodeTest {
    public static void main(String[] args) {
        for (ErrorCode code:ErrorCode.values()){
            if (code.getValue()>=0){
                throw new RuntimeException();
            }
        }
    }
}
