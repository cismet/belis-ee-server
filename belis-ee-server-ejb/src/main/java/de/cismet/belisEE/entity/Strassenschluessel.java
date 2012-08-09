/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;
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
@Table(name = "`tkey_straßenschlüssel`")
@NamedQueries({
    @NamedQuery(name = "Strassenschluessel.findAllStrassenschluessel", query = "SELECT s FROM Strassenschluessel s")
})
public class Strassenschluessel extends BaseEntity implements Serializable {

    @Id
    @Column(name = "`pk`", nullable = false)
    private String pk;
    @Column(name = "`Strasse`")
    private String strasse;

    public Strassenschluessel() {
    }

    public Strassenschluessel(final String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(final String pk) {
        this.pk = pk;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(final String strasse) {
        this.strasse = strasse;
    }

    @Override
    public int hashCode() {
        if (this.getPk() == null) {
            return super.hashCode();
        }
        return this.getPk().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Strassenschluessel) {
            Strassenschluessel anEntity = (Strassenschluessel) other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if (this.getPk() == null || anEntity.getPk() == null) {
                return false;
            } else {
                return this.getPk().equals(anEntity.getPk());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Strassenschlüssel[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
//        if(getPk() != null && getStrasse() != null){
//            return getPk()+" - "+getStrasse();
//        } else if(getPk() != null){
//            return getPk()+" - Kein Straßennamen vorhanden.";
//        } else if(getStrasse() != null){
//            return "Keine ID - "+getStrasse();
//        } else {
//            return "";
//        }
        if (getStrasse() != null) {
            return getStrasse();
        } else {
            return "";
        }
    }

    @Override
    public String getCompareCriteriaString() {
        if (getStrasse() != null) {
            return getStrasse();
        } else {
            return "";
        }
    }
}
