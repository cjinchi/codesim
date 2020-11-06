package com.chenjinchi.codesim;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private boolean verbose = false;
    private boolean help = false;
    private List<String> files = new ArrayList<>();
    private boolean error = false;
    private String errorInfo = null;

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isHelp() {
        return help;
    }

    public List<String> getFiles() {
        return files;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
