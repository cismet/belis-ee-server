/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.bean;

import de.cismet.belisEE.bean.interfaces.lockEnabled;
import de.cismet.belisEE.entity.Lock;
import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;
import de.cismet.belisEE.util.BelisEEUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author spuhl
 */
//ToDo eraese all the locks every night
public class AbstractServiceBean implements Serializable, lockEnabled {

    @PersistenceContext
    protected EntityManager em;

    public synchronized Lock lockEntity(Object objectToLock, String userString) throws ActionNotSuccessfulException, LockAlreadyExistsException {

        Object id = BelisEEUtils.getEntityId(objectToLock);
        if (id == null) {
            System.out.println("Entity is not yet persisted. Locking not possible");
            throw new ActionNotSuccessfulException("Entity is not yet persisted. Locking not possible");
        }

        try {
            if (objectToLock != null) {
                //datamodell refactoring 22.10.07


                Lock lock = (Lock) em.createNamedQuery(
                        "Lock.findLockForObject").setParameter("classId", objectToLock.getClass().getName()).setParameter("objectId", id.toString()).getSingleResult();
                System.out.println("A lock for the desired object is already existing and is hold by: " + lock.getUserString());
                //ToDo internationalise
                throw new LockAlreadyExistsException("A lock for the desired object is already existing", lock);
            } else {
                System.out.println("The object to lock is null");
                throw new ActionNotSuccessfulException("The object to lock is null");
            }

        } catch (LockAlreadyExistsException ex) {
            throw ex;
        } catch (NoResultException ex) {
            System.out.println("There is no Lock for the object");
            //em.persist(newSperre);
            //       tx.commit();
            Lock newLock = new Lock();
            newLock.setClassId(objectToLock.getClass().getName());
            newLock.setTimestamp(new Date());
            newLock.setUserString(userString);
            newLock.setObjectId(id.toString());
            em.persist(newLock);
            return newLock;

        } catch (NonUniqueResultException ex) {
            System.out.println("There are multiple lock for the objects");
            throw new ActionNotSuccessfulException("There are multiple lock for the objects");
        } catch (Exception ex) {
            System.out.println("Exception while creating lock");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Exception while creating lock", ex);
        }
    }

    public synchronized Lock isEntityLocked(Object lockedObject) throws ActionNotSuccessfulException {
        try {
            //datamodell refactoring 22.10.07
            if (lockedObject != null) {
                //Flurstueck flurstueck = retrieveFlurstueck(key);
                if (lockedObject != null) {
                    Object id = BelisEEUtils.getEntityId(lockedObject);
                    if (id == null) {
                        System.out.println("Entity is not yet persisted. Therefore it is surely not locked");
                        return null;                        
                    }
                    Lock lock = (Lock) em.createNamedQuery(
                            "Lock.findLockForObject").setParameter("classId", lockedObject.getClass().getName()).setParameter("objectId", id.toString()).getSingleResult();
                    System.out.println("A lock for the desired object is already existing and is hold by: " + lock.getUserString());
                    return lock;
                }

            } else {
                System.out.println("The object to check is null");
                return null;

            }
        } catch (NoResultException ex) {
        }
        System.out.println("There is no lock for the given Object");
        return null;
    }

    public synchronized Lock tryToLockEntity(Object objectToLock, String userString) throws ActionNotSuccessfulException, LockAlreadyExistsException {
        Lock exisitingLock = null;
        if ((exisitingLock = isEntityLocked(objectToLock)) == null) {
            return lockEntity(objectToLock, userString);
        } else {
            System.out.println("Entity is locked");
            throw new LockAlreadyExistsException("A lock for the desired object is already existing", exisitingLock);
        }
    }

    public synchronized void unlockEntity(Lock holdedLock) throws ActionNotSuccessfulException {
        try {
            if (holdedLock != null) {
                em.remove(em.merge(holdedLock));

            }
        } catch (Exception ex) {
            System.out.println("Failure while releasing lock");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure while releasing lock", ex);
        }
    }

    public synchronized void unlockEntity(Object objectToUnlock) throws ActionNotSuccessfulException {
        Lock entityLock = isEntityLocked(objectToUnlock);
        if (entityLock != null) {
            System.out.println("There is an Lock for the given Entity");
            unlockEntity(entityLock);
        } else {
            System.out.println("There is no Lock for the given Entity");
        }

    }

    public synchronized Set<Lock> lockEntity(Set<Object> objectsToLock, String userString) throws ActionNotSuccessfulException, LockAlreadyExistsException {
        ArrayList<Lock> lockedObjects = new ArrayList<Lock>();
        ArrayList<Lock> exisitingLock = new ArrayList<Lock>();
        ;
        if (objectsToLock != null) {
            System.out.println("Objects to lock count: " + objectsToLock.size());
            for (Object curObject : objectsToLock) {
                try {
                    lockedObjects.add(lockEntity(curObject, userString));
                } catch (ActionNotSuccessfulException ex) {
                    System.out.println("Error while locking one of the objects");
                    ex.printStackTrace();
                    if (lockedObjects.size() != 0) {
                        System.out.println("Unlocking already locked objects");
                        try {
                            unlockEntity(lockedObjects);
                        } catch (ActionNotSuccessfulException ex2) {
                            System.out.println("Error while unlocking already locked objects");
                            ex2.printStackTrace();
                            throw new ActionNotSuccessfulException("Tried to lock Objects --> faild. Tried to unlock the successful locked objects --> faild", ex);
                        }
                    }
                    throw new ActionNotSuccessfulException("Error while locking one of the objects.", ex);
                } catch (LockAlreadyExistsException ex) {
                    System.out.println("One of the Objects is already locked --> trying the rest of the objects");
                    exisitingLock.addAll(ex.getAlreadyExisingLocks());
                    continue;
                }
            }
            if (exisitingLock.size() == 0) {
                System.out.println("locking of Objects was successful");
                return new HashSet<Lock>(lockedObjects);
            } else {
                if (lockedObjects.size() != 0) {
                    try {
                        System.out.println("unlocking of already locked objects (Cleanup)");
                        final Set failedUnlocks = unlockEntity(new HashSet(lockedObjects));
                    } catch (ActionNotSuccessfulException ex2) {
                        System.out.println("Error while unlocking already locked objects");
                        ex2.printStackTrace();
                        throw new ActionNotSuccessfulException("Tried to lock Objects --> faild. Tried to unlock the successful locked objects --> faild", ex2);
                    }
                }
                throw new LockAlreadyExistsException("Error some of the objects are already locked", exisitingLock);
            }
        } else {
            System.out.println("The set of objects to lock is null");
            throw new ActionNotSuccessfulException("The set of objects to lock is null");
        }
    }

    public synchronized Set<Object> unlockEntity(Set<Object> objectsToUnlock) throws ActionNotSuccessfulException {
        ArrayList unsuccessfulUnlocking = new ArrayList();
        if (objectsToUnlock != null) {
            for (Object curObject : objectsToUnlock) {
                try {
                    unlockEntity(curObject);
                } catch (ActionNotSuccessfulException ex) {
                    unsuccessfulUnlocking.add(curObject);
                }
            }
            return new HashSet<Object>(unsuccessfulUnlocking);
        } else {
            System.out.println("The set of objects to unlock is null");
            throw new ActionNotSuccessfulException("The set of objects to unlock is null");
        }
    }
}
