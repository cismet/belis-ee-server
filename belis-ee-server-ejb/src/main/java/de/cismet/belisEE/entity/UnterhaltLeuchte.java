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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "tkey_unterh_leuchte")
@NamedQueries({@NamedQuery(name = "UnterhaltLeuchte.findAllUnterhaltLeuchte", query = "SELECT u FROM UnterhaltLeuchte u")})
public class UnterhaltLeuchte extends BaseEntity implements Serializable {
    @Id
    @Column(name = "`pk`", nullable = false)
    private Short pk;
    @Column(name = "`Unterhaltspflichtiger_Leuchte`")
    private String unterhaltspflichtigeLeuchte;

    public UnterhaltLeuchte() {
    }

    public UnterhaltLeuchte(final Short pk) {
        this.pk = pk;
    }

    public Short getPk() {
        return pk;
    }

    public void setPk(final Short pk) {
        this.pk = pk;
    }

    public String getUnterhaltspflichtigeLeuchte() {
        return unterhaltspflichtigeLeuchte;
    }

    public void setUnterhaltspflichtigeLeuchte(final String unterhaltspflichtigeLeuchte) {
        this.unterhaltspflichtigeLeuchte = unterhaltspflichtigeLeuchte;
    }

//    public Object getGeom() {
//        return geom;
//    }
//
//    public void setGeom(Object geom) {
//        this.geom = geom;
//    }

    @Override
    public int hashCode() {
        if (this.getPk() == null)
            return super.hashCode();
        return this.getPk().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof UnterhaltLeuchte){
            UnterhaltLeuchte anEntity = (UnterhaltLeuchte) other;
            if ( this == other) {
                return true;
            } else if ((other == null) || (  ! this.getClass().isAssignableFrom(other.getClass())  ) ) {
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
        return "de.cismet.belis.entity.UnterhaltLeuchte[pk=" + pk + "]";        
    }

     @Override
    public String getKeyString() {
        if(getUnterhaltspflichtigeLeuchte() != null){
            return getUnterhaltspflichtigeLeuchte();
        } else {
            return "";
        }
    }

    
    
}
