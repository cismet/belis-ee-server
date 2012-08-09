/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.exception;

/**
 *
 * @author spuhl
 */
public class ActionNotSuccessfulException extends Exception {
    private Object exceptionObject;

    public ActionNotSuccessfulException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotSuccessfulException(String message) {
        super(message);
    }    
    public ActionNotSuccessfulException(String message,Object exceptionObject) {
        super(message);
        this.exceptionObject = exceptionObject;
    }

    public Object getExceptionObject() {
        return exceptionObject;
    }

    public void setExceptionObject(Object exceptionObject) {
        this.exceptionObject = exceptionObject;
    }    
}
