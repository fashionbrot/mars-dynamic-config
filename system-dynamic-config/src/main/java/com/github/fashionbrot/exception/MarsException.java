package com.github.fashionbrot.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
@Getter
@Setter
public class MarsException extends RuntimeException {

    private String message ;

    public MarsException(String message) {
        super(message);
        this.message = message;
    }
}
