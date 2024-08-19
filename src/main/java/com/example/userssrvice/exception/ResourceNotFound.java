package com.example.userssrvice.exception;

public class ResourceNotFound  extends  RuntimeException{


    public  ResourceNotFound(){
        super("Resource not found in server ");
    }
    public  ResourceNotFound(String message){
        super(message);
    }
}
