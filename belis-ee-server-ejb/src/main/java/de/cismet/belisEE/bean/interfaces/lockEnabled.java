/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.bean.interfaces;

import de.cismet.belisEE.entity.Lock;
import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author spuhl
 */
public interface lockEnabled extends Serializable{
    
    public Lock lockEntity(Object objectToLock,String userString) throws ActionNotSuccessfulException,LockAlreadyExistsException;    
    public Set<Lock> lockEntity(Set<Object> objectsToLock,String userString) throws ActionNotSuccessfulException,LockAlreadyExistsException;    
    public Lock isEntityLocked(Object lockedObject) throws ActionNotSuccessfulException;
    public Lock tryToLockEntity(Object lockedObject,String userString) throws ActionNotSuccessfulException,LockAlreadyExistsException;
    public void unlockEntity(Object objectToUnlock) throws ActionNotSuccessfulException;     
    public Set<Object> unlockEntity(Set<Object> objectsToUnlock) throws ActionNotSuccessfulException;     
    public void unlockEntity(Lock holdedLock) throws ActionNotSuccessfulException;                

}
