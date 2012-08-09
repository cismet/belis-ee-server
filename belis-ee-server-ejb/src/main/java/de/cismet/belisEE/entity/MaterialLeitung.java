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
@Table(name = "material_leitung")
@NamedQueries({
@NamedQuery(name = "MaterialLeitung.findAllMaterialLeitung", query = "SELECT m FROM MaterialLeitung m")
})
public class MaterialLeitung extends BaseEntity implements Serializable {
    @Id
    @SequenceGenerator(name="MaterialLeitung_seq",sequenceName="material_leitung_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="MaterialLeitung_seq")
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
        if (!(object instanceof MaterialLeitung)) {
            return false;
        }
        MaterialLeitung other = (MaterialLeitung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.MaterialLeitung[id=" + id + "]";
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
