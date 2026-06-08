package com.project.MockInterviewScheduler.exceptions;


public class ResourceAlreadyException extends RuntimeException{
    public ResourceAlreadyException(String msg){
        super(msg);
    }
}
