package com.chenjinchi.codesim;

enum ErrorCode {
    INVALID_CONFIG,
    FILE_NOT_FOUND,
    INVALID_CODE_LENGTH;

    public int getValue(){
        return - this.ordinal() - 1;
    }
}

