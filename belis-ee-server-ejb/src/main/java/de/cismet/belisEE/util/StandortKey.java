/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.util;

import de.cismet.belisEE.entity.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author spuhl
 */
public class StandortKey implements Serializable{        
              
    private Strassenschluessel strassenschluessel;
        
    private Kennziffer kennziffer;
        
    private Short laufendeNummer;

    public StandortKey() {
    }

    public StandortKey(Strassenschluessel strassenschluessel, Kennziffer kennziffer, Short laufendeNummer) {
        this.strassenschluessel = strassenschluessel;
        this.kennziffer = kennziffer;
        this.laufendeNummer = laufendeNummer;
    }
    
    public StandortKey(String pk,Short kennziffer,Short laufendeNummer){
        this.strassenschluessel = new Strassenschluessel();
        this.strassenschluessel.setPk(pk);
        this.kennziffer = new Kennziffer(kennziffer);
        this.laufendeNummer = laufendeNummer;
    }

    public Kennziffer getKennziffer() {
        return kennziffer;
    }

    public void setKennziffer(Kennziffer kennziffer) {
        this.kennziffer = kennziffer;
    }

    public Short getLaufendeNummer() {
        return laufendeNummer;
    }

    public void setLaufendeNummer(Short laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
    }

    public Strassenschluessel getStrassenschluessel() {
        return strassenschluessel;
    }

    public void setStrassenschluessel(Strassenschluessel strassenschluessel) {
        this.strassenschluessel = strassenschluessel;
    }
    
    @Override
    public int hashCode() {
        int hashCode=0;
        if (getStrassenschluessel() != null && getStrassenschluessel().getPk() != null){
            hashCode = hashCode ^ getStrassenschluessel().getPk().hashCode();
        }
         if (getKennziffer() != null && getKennziffer().getKennziffer() != null){
            hashCode = hashCode ^ getKennziffer().getKennziffer().hashCode();
        }
        if (getLaufendeNummer() != null ){
            hashCode = hashCode ^ getLaufendeNummer().hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Standort){
            StandortKey anEntity = (StandortKey) other;
            if ( this == other) {
                return true;
            } else if ((other == null) || (  ! this.getClass().isAssignableFrom(other.getClass())  ) ) {
                return false;
            } else if (this.getStrassenschluessel() == null ||  this.getStrassenschluessel().getPk() == null ||
                        this.getKennziffer() == null && this.getKennziffer().getKennziffer() == null|| 
                        this.getLaufendeNummer() == null ||
                        anEntity.getStrassenschluessel() == null ||  anEntity.getStrassenschluessel().getPk() == null ||
                        anEntity.getKennziffer() == null && anEntity.getKennziffer().getKennziffer() == null|| 
                        anEntity.getLaufendeNummer() == null
                        ) {
                return false;
            } else {
                return this.getStrassenschluessel().getPk().equals(anEntity.getStrassenschluessel().getPk()) &&
                        this.getKennziffer().getKennziffer().equals(anEntity.getKennziffer().getKennziffer()) &&
                        this.getLaufendeNummer().equals(this.getLaufendeNummer());
            }
        } else {
            return false;
        }
    }
}
