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
@Table(name = "tkey_leuchtentyp")
@NamedQueries({
@NamedQuery(name = "Leuchtentyp.findAllLeuchtentyp", query = "SELECT l FROM Leuchtentyp l")
})
public class Leuchtentyp extends BaseEntity implements Serializable {
    @Id
    @Column(name = "`Leuchtentyp`", nullable = false)
    private String leuchtentyp;
    @Column(name = "`Fabrikat`")
    private String fabrikat;
    @Column(name = "`Bestueckung`")
    private Double bestueckung;
    @Column(name = "`Leistung`")
    private Double leistung;
    @Column(name = "`Leistung_brutto`")
    private Double leistungBrutto;
    @Column(name = "`Lampe`")
    private String lampe;

    public Leuchtentyp() {
    }

    public Leuchtentyp(final String leuchtentyp) {
        this.leuchtentyp = leuchtentyp;
    }

    public String getLeuchtentyp() {
        return leuchtentyp;
    }

    public void setLeuchtentyp(final String leuchtentyp) {
        this.leuchtentyp = leuchtentyp;
    }

    public String getFabrikat() {
        return fabrikat;
    }

    public void setFabrikat(final String fabrikat) {
        this.fabrikat = fabrikat;
    }

    public Double getBestueckung() {
        return bestueckung;
    }

    public void setBestueckung(final Double bestueckung) {
        this.bestueckung = bestueckung;
    }

    public Double getLeistung() {
        return leistung;
    }

    public void setLeistung(final Double leistung) {
        this.leistung = leistung;
    }

    public Double getLeistungBrutto() {
        return leistungBrutto;
    }

    public void setLeistungBrutto(final Double leistungBrutto) {
        this.leistungBrutto = leistungBrutto;
    }

    public String getLampe() {
        return lampe;
    }

    public void setLampe(final String lampe) {
        this.lampe = lampe;
    }

    @Override
    public int hashCode() {
        if (this.getLeuchtentyp() == null)
            return super.hashCode();
        return this.getLeuchtentyp().hashCode();
    }

     @Override
    public boolean equals(Object other) {
        if(other instanceof Leuchtentyp){
            Leuchtentyp anEntity = (Leuchtentyp) other;
            if ( this == other) {
                return true;
            } else if ((other == null) || (  ! this.getClass().isAssignableFrom(other.getClass())  ) ) {
                return false;
            } else if (this.getLeuchtentyp() == null || anEntity.getLeuchtentyp() == null) {
                return false;
            } else {
                return this.getLeuchtentyp().equals(anEntity.getLeuchtentyp());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Leuchtentyp[leuchtentyp=" + leuchtentyp + "]";
    }

    @Override
    public String getKeyString() {
        if(getLeuchtentyp() != null){
            return getLeuchtentyp();
        } else {
            return "";
        }
    }

}
