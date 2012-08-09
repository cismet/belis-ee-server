/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author spuhl
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Leitungstyp.findAllLeitungstyp", query = "SELECT l FROM Leitungstyp l")
})
public class Leitungstyp extends BaseEntity implements Serializable {
    
    @Id
    @SequenceGenerator(name="Leitungstyp_seq",sequenceName="leitungstyp_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Leitungstyp_seq")
    private Long id;

    private String bezeichnung;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    
    
    @Override
    public int hashCode() {
        if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leitungstyp)) {
            return false;
        }
        Leitungstyp other = (Leitungstyp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Leitungstyp[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        if(getBezeichnung() != null){
            return getBezeichnung();
        } else {
            return "";
        }
    }

}
