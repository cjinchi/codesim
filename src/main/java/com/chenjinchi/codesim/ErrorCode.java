package com.chenjinchi.codesim;

enum ErrorCode {
    INVALID_CONFIG,
    FILE_NOT_FOUND,
    CODE_LENGTH_TOO_SMALL;

    public int getValue(){
        return - this.ordinal() - 1;
    }
}

