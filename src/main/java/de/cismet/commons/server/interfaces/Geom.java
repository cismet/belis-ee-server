package de.cismet.commons.server.interfaces;

import de.cismet.commons.server.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Type;
import org.postgis.Geometry;

//@NamedNativeQueries({
//    @NamedNativeQuery(name="Geom.getAllGeometriesByBoundingBox",query="SELECT id FROM geom WHERE id = ?")
//})
@Entity
public class Geom extends BaseEntity {
    @SequenceGenerator(name="geom_seq",sequenceName="geom_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="geom_seq")
    @Id() 
    private Integer id;
    
    @Type(type="de.cismet.hibernate.GeometryType")
    @Column(name="geo_field",columnDefinition="Geometry")    
    private Geometry geomField;
    
    private transient Boolean isEditable = false;
    private transient Boolean isHidden = false;
    

    public Geom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer val) {
        this.id = val;
    }

    public Geometry getGeomField() {
        return geomField;
    }

    public void setGeomField(Geometry val) {
        this.geomField = val;
    }

    @Override
    public String getKeyString() {
        return "Geom ["+getId()+"]";
    }
    
    @Override
    public int hashCode() {
        if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
   public boolean equals(Object other) {
        if(other instanceof Geom){
            Geom anEntity = (Geom) other;
            if ( this == other) {
                return true;
            } else if ((other == null) || (  ! this.getClass().isAssignableFrom(other.getClass())  ) ) {
                return false;
            } else if (this.getId() == null || anEntity.getId() == null) {
                return false;
            } else {
                return this.getId().equals(anEntity.getId());
            }
        } else {
            return false;
        }
    }
    
    
}
