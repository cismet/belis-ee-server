/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "tkey_kennziffer")
//ToDo should work automatically for the normalised tables so you don't have to write every query
@NamedQueries({    
@NamedQuery(name = "Kennziffer.findAllKennziffer", query = "SELECT k FROM Kennziffer k")
})
public class Kennziffer extends BaseEntity implements Serializable {
    @Id
    @Column(name = "`Kennziffer`", nullable = false)
    private Short kennziffer;
    @Column(name = "`Beschreibung`")
    private String beschreibung;

    public Kennziffer() {
    }

    public Kennziffer(final Short kennziffer) {
        this.kennziffer = kennziffer;
    }

    public Short getKennziffer() {
        return kennziffer;
    }

    public void setKennziffer(final Short kennziffer) {
        this.kennziffer = kennziffer;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public int hashCode() {
        if (this.getKennziffer() == null)
            return super.hashCode();
        return this.getKennziffer().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Kennziffer){
            Kennziffer anEntity = (Kennziffer) other;
            if ( this == other) {
                return true;
            } else if ((other == null) || (  ! this.getClass().isAssignableFrom(other.getClass())  ) ) {
                return false;
            } else if (this.getKennziffer() == null || anEntity.getKennziffer() == null) {
                return false;
            } else {
                return this.getKennziffer().equals(anEntity.getKennziffer());
            }
        } else {
            return false;
        }
    }

    @Override
    public String getKeyString() {
        if(getKennziffer() != null && getBeschreibung() != null){
            return getKennziffer()+" - "+getBeschreibung();
        } else if(getKennziffer() != null){
            return getKennziffer()+" - Keine Beschreibung vorhanden.";
        } else if(getBeschreibung() != null){
            return "Keine ID - "+getBeschreibung();
        } else {
            return "";
        }
    }

    @Override
    public String getCompareCriteriaString() {
        if(getBeschreibung() != null){
            return getBeschreibung();
        } else {
            return "";
        }
    }

}
