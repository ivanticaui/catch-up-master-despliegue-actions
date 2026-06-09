package com.acme.catchup.platform.news.domain.model.exceptions;

public abstract class DomainInvalidExceptions extends RuntimeException{
     public DomainInvalidExceptions(String message) {
         super(message);
     }
}
