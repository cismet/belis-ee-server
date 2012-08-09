/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.exception;

import de.cismet.belisEE.entity.Lock;
import java.util.ArrayList;

/**
 *
 * @author spuhl
 */
public class LockAlreadyExistsException extends Exception {

    private final ArrayList<Lock> alreadyExisingLocks = new ArrayList<Lock>();
    
    public LockAlreadyExistsException(String message,Lock alreadyExistingLock) {
        super(message);
        alreadyExisingLocks.add(alreadyExistingLock);
    }
    
    public LockAlreadyExistsException(String message,ArrayList<Lock> alreadyExistingLocks) {
        super(message);
        if(alreadyExistingLocks != null){
            alreadyExisingLocks.addAll(alreadyExistingLocks);
        }
        
    }

    public ArrayList<Lock> getAlreadyExisingLocks() {
        return alreadyExisingLocks;
    }        
        
}
