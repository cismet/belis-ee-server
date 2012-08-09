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
import javax.persistence.Table;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "material_mauerlasche")
@NamedQueries({
@NamedQuery(name = "MaterialMauerlasche.findAllMaterialMauerlasche", query = "SELECT m FROM MaterialMauerlasche m")
})
public class MaterialMauerlasche extends BaseEntity implements Serializable {
    @Id
    @SequenceGenerator(name="MaterialMauerlasche_seq",sequenceName="material_mauerlasche_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="MaterialMauerlasche_seq")
    private Long id;

    private String bezeichnung;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }
    
    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialMauerlasche)) {
            return false;
        }
        MaterialMauerlasche other = (MaterialMauerlasche) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.MaterialMauerlasche[id=" + id + "]";
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
