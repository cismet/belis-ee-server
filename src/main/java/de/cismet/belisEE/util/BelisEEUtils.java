/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.util;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import java.lang.reflect.Field;
import javax.persistence.Id;

/**
 *
 * @author spuhl
 */
public class BelisEEUtils {
    
    public static Object getEntityId(Object entity) throws ActionNotSuccessfulException {
        if (entity != null) {
            for (Field curField : entity.getClass().getDeclaredFields()) {
                if (curField.isAnnotationPresent(Id.class)) {
                    try {
                        curField.setAccessible(true);
                        return curField.get(entity);
                    } catch (IllegalAccessException ex) {
                        System.out.println("Id field of entity not accessible");
                        throw new ActionNotSuccessfulException("Id field of entity not accessible", ex);
                    }
                }
            }
            throw new ActionNotSuccessfulException("The object to lock dosen't have an @Id field");
        } else {
            System.out.println("Entity is null, id lookup not possible");
            return null;
        }
    }

}
