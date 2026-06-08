package com.project.MockInterviewScheduler.exceptions;

public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException(String msg){
        super(msg);
    }
}
