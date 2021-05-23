package com.phoenixwings7.customexceptions;

public class NullDateException extends Exception {
    @Override
    public String getMessage() {
        return "Date is null!";
    }
}
