package com.cudo.mediabusiness.mediasite.exception;

public class validateException extends IllegalStateException{
    public validateException(){
        super();
    }
    public validateException(String message){
        super(message);
    }
}
