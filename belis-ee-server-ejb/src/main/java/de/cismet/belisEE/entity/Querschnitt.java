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
@NamedQuery(name = "Querschnitt.findAllQuerschnitt", query = "SELECT q FROM Querschnitt q")
})
public class Querschnitt extends BaseEntity implements Serializable {
    
    @Id
    @SequenceGenerator(name="Querschnitt_seq",sequenceName="querschnitt_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Querschnitt_seq")
    private Long id;
        
    private Double groesse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGroesse() {
        return groesse;
    }

    public void setGroesse(Double groesse) {
        this.groesse = groesse;
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
        if (!(object instanceof Querschnitt)) {
            return false;
        }
        Querschnitt other = (Querschnitt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Querschnitt[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        if(getGroesse() != null){
            return getGroesse().toString();
        } else {
            return "";
        }
    }

}
