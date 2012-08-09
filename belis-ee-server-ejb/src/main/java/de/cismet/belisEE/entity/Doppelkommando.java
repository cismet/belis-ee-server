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
@Table(name = "tkey_doppelkommando")
@NamedQueries({
@NamedQuery(name = "Doppelkommando.findAllDoppelkommando", query = "SELECT d FROM Doppelkommando d")
})
public class Doppelkommando extends BaseEntity implements Serializable {
    @Id
    @Column(name = "`pk`", nullable = false)
    private String pk;
    @Column(name = "`Beschreibung`")
    private String beschreibung;

    public Doppelkommando() {
    }

    public Doppelkommando(final String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(final String pk) {
        this.pk = pk;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public int hashCode() {
        if (this.getPk() == null)
            return super.hashCode();
        return this.getPk().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Doppelkommando){
            Doppelkommando anEntity = (Doppelkommando) other;
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
        return "de.cismet.belis.entity.Doppelkommando[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if(getBeschreibung() != null){
            return getBeschreibung();
        } else {
            return "";
        }
    }
}
