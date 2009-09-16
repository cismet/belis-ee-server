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
@Table(name = "tkey_masttyp")
@NamedQueries({
@NamedQuery(name = "Masttyp.findAllMasttypen", query = "SELECT m FROM Masttyp m")
})
public class Masttyp extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "`Masttyp`", nullable = false)
    private String masttyp;

    public Masttyp() {
    }

    public Masttyp(final String masttyp) {
        this.masttyp = masttyp;
    }

    public String getMasttyp() {
        return masttyp;
    }

    public void setMasttyp(final String masttyp) {
        this.masttyp = masttyp;
    }

   @Override
    public int hashCode() {
        if (this.getMasttyp() == null)
            return super.hashCode();
        return this.getMasttyp().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Masttyp){
            Masttyp anEntity = (Masttyp) other;
            if ( this == other) {
                return true;
            } else if ((other == null) || (  ! this.getClass().isAssignableFrom(other.getClass())  ) ) {
                return false;
            } else if (this.getMasttyp() == null || anEntity.getMasttyp() == null) {
                return false;
            } else {
                return this.getMasttyp().equals(anEntity.getMasttyp());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Masttyp[masttyp=" + masttyp + "]";
    }

   @Override
    public String getKeyString() {
        if(getMasttyp() != null){
            return getMasttyp();
        } else {
            return "";
        }
    }
    
}
