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
@Table(name = "tkey_mastart")
@NamedQueries({
@NamedQuery(name = "Mastart.findAllMastarten", query = "SELECT m FROM Mastart m")
})
public class Mastart extends BaseEntity implements Serializable {
    @Id
    @Column(name = "`pk`", nullable = false)
    private String pk;
    @Column(name = "`Mastart`")
    private String mastart;

    public Mastart() {
    }

    public Mastart(final String pk) {
        this.pk = pk;
    }

    public Mastart(final String pk, final String mastart) {
        this.pk = pk;
        this.mastart = mastart;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(final String pk) {
        this.pk = pk;
    }

    public String getMastart() {
        return mastart;
    }

    public void setMastart(final String mastart) {
        this.mastart = mastart;
    }

    @Override
    public int hashCode() {
        if (this.getPk() == null)
            return super.hashCode();
        return this.getPk().hashCode();
    }

   @Override
    public boolean equals(Object other) {
        if(other instanceof Mastart){
            Mastart anEntity = (Mastart) other;
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
        return "de.cismet.belis.entity.Mastart[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if(getMastart() != null){
            return getMastart();
        } else {
            return "";
        }
    }

}
