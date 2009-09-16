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
@Table(name = "tkey_energielieferant")
@NamedQueries({
@NamedQuery(name = "Energielieferant.findAllEnergielieferanten", query = "SELECT e FROM Energielieferant e")
})
public class Energielieferant extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "`pk`", nullable = false)
    private Short pk;
    @Column(name = "`Energielieferant`")
    private String energielieferant;

    public Energielieferant() {
    }

    public Energielieferant(final Short pk) {
        this.pk = pk;
    }

    public Short getPk() {
        return pk;
    }

    public void setPk(final Short pk) {
        this.pk = pk;
    }

    public String getEnergielieferant() {
        return energielieferant;
    }

    public void setEnergielieferant(final String energielieferant) {
        this.energielieferant = energielieferant;
    }

    @Override
    public int hashCode() {
        if (this.getPk() == null)
            return super.hashCode();
        return this.getPk().hashCode();
    }

   @Override
    public boolean equals(Object other) {
        if(other instanceof Energielieferant){
            Energielieferant anEntity = (Energielieferant) other;
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
        return "de.cismet.belis.entity.Energielieferant[pk=" + pk + "]";        
    }

    @Override
    public String getKeyString() {
        if(getEnergielieferant() != null){
            return getEnergielieferant();
        } else {
            return "";
        }
    }
}
