/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.commons.server.entity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 *
 * @author spuhl
 */
@MappedSuperclass
public class BaseEntity implements Serializable,PropertyChangeListener{


    @Transient
    private boolean wasModified = false;

    public boolean isWasModified() {
        return wasModified;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

    @Transient
    protected transient PropertyChangeSupport propertyChangeSupport=new PropertyChangeSupport(this);

    public PropertyChangeSupport getPropertyChangeSupport() {
        if(propertyChangeSupport == null){
            propertyChangeSupport=new PropertyChangeSupport(this);
            propertyChangeSupport.addPropertyChangeListener(this);
        }
        return propertyChangeSupport;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
        this.propertyChangeSupport = propertyChangeSupport;
    }    

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }
    
    public  String getKeyString(){
        return "";
    }

    public String getCompareCriteriaString(){
        return getKeyString();
    }
    
    public String getHumanReadablePosition(){
        return "";
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
    }    

}
