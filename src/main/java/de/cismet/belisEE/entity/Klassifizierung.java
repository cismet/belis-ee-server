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
@Table(name = "Tkey_Klassifizierung")
@NamedQueries({
@NamedQuery(name = "Klassifizierung.findAllKlassifizierungen", query = "SELECT k FROM Klassifizierung k")
})
public class Klassifizierung extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "`pk`", nullable = false)
    private Short pk;
    @Column(name = "`Klassifizierung`")
    private String klassifizierung;

    public Klassifizierung() {
    }

    public Klassifizierung(final Short pk) {
        this.pk = pk;
    }

    public Short getPk() {
        return pk;
    }

    public void setPk(final Short pk) {
        this.pk = pk;
    }

    public String getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(String klassifizierung) {
        this.klassifizierung = klassifizierung;
    }

   @Override
    public int hashCode() {
        if (this.getPk() == null)
            return super.hashCode();
        return this.getPk().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Klassifizierung){
            Klassifizierung anEntity = (Klassifizierung) other;
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
        return "de.cismet.belis.entity.Klassifizierung[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
         if(getKlassifizierung() != null){
            return getKlassifizierung();
        } else {
            return "";
        }
    }

    
}
