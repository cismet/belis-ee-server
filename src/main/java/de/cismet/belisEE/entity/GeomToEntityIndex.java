/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.commons.server.interfaces.Geom;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "geom_to_entity_index")
@NamedQuery(name = "GeomToEntityIndex.findGeomToEntityIndexByGeomId", query = "FROM GeomToEntityIndex g WHERE g.geometry.id =:id")
public class GeomToEntityIndex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //ToDo umstellen
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Class entityClass;
    
    private Long entityID;

    @OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="fk_geom")
    private Geom geometry;

    public Long getEntityID() {
        return entityID;
    }

    public void setEntityID(Long enityID) {
        this.entityID = enityID;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityName) {
        this.entityClass = entityName;
    }

    public Geom getGeometry() {
        return geometry;
    }

    public void setGeometry(Geom geometry) {
        this.geometry = geometry;
    }    

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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeomToEntityIndex)) {
            return false;
        }
        GeomToEntityIndex other = (GeomToEntityIndex) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.GeomToEntityIndex[id=" + id + "]";
    }

}
