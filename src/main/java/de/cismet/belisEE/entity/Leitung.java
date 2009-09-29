/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;
import java.awt.Color;
import java.awt.Paint;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "leitung")
@NamedQueries({
    //ToDo Leuchten problem
    @NamedQuery(name = "Leitung.findStandortByGeom", query = "SELECT s FROM Leitung s WHERE s.geometrie = :geometrie"),
    @NamedQuery(name = "Leitung.refresh", query = "FROM Leitung l WHERE l.id in (:ids)")

})
public class Leitung extends GeoBaseEntity implements Serializable,DocumentContainer {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "Leitung_seq", sequenceName = "leitung_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Leitung_seq")
    private Long id;
    public static final String PROP_ID = "Leitung.id";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_material")
    private MaterialLeitung material;
    public static final String PROP_MATERIAL = "Leitung.material";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_leitungstyp")
    private Leitungstyp leitungstyp;
    public static final String PROP_LEITUNGSTYP = "Leitung.Leitungstyp";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_querschnitt")
    private Querschnitt querschnitt;
    public static final String PROP_QUERSCHNITT = "Leitung.querschnitt";

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "jt_leitung_dokument", joinColumns = {@JoinColumn(name = "fk_leitung")}, inverseJoinColumns = {@JoinColumn(name = "fk_dokument")})
    protected Set<DmsUrl> dokumente;

    public Set<DmsUrl> getDokumente() {
        return dokumente;
    }

    public void setDokumente(Set<DmsUrl> dokumente) {
        this.dokumente = dokumente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        getPropertyChangeSupport().firePropertyChange(PROP_ID, null, getId());
    }

    public MaterialLeitung getMaterial() {
        return material;
    }

    public void setMaterial(MaterialLeitung material) {
        this.material = material;
        getPropertyChangeSupport().firePropertyChange(PROP_MATERIAL, null, getMaterial());
    }

    public Leitungstyp getLeitungstyp() {
        return leitungstyp;
    }

    public void setLeitungstyp(Leitungstyp leitungstyp) {
        final Leitungstyp oldLeitungstyp = getLeitungstyp();
        this.leitungstyp = leitungstyp;
        getPropertyChangeSupport().firePropertyChange(PROP_LEITUNGSTYP, oldLeitungstyp, getLeitungstyp());
    }

    public Querschnitt getQuerschnitt() {
        return querschnitt;        
    }

    public void setQuerschnitt(Querschnitt querschnitt) {
        this.querschnitt = querschnitt;
        getPropertyChangeSupport().firePropertyChange(PROP_QUERSCHNITT, null, getQuerschnitt());
    }
    //ToDo change the style of the leitung depending to the attributes    
    @Override
    public int hashCode() {
       if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
    public int getLineWidth() {
        return 4;
    }
    
    @Override
    public Paint getLinePaint() {
        if (getLeitungstyp() != null && getLeitungstyp().getBezeichnung() != null) {
            if (getLeitungstyp().getBezeichnung().equals("Erdkabel")) {
                return new Color(126,46 , 0 , 255);
            } else if (getLeitungstyp().getBezeichnung().equals("Tragseil mit Freileitung")) {
                return new Color(102, 0, 102, 255);
            } else if (getLeitungstyp().getBezeichnung().equals("Tragseil")) {
                return new Color(0, 0, 255, 255);
            } else if (getLeitungstyp().getBezeichnung().equals("Freileitung")) {
                return new Color(255, 0, 0, 255);
            }
        }
        return Color.black;
    }

    @Override
    public boolean equals(Object other) {
       if (other instanceof Leitung) {
            Leitung anEntity = (Leitung) other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
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

    @Override
    public String getKeyString() {
        String leitungstyp = "";
        if (getLeitungstyp() != null && getLeitungstyp().getBezeichnung() != null) {
            leitungstyp = getLeitungstyp().getBezeichnung();
        }
        return leitungstyp;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Leitung[id=" + id + "]";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        if(evt.getSource().equals(this) && !evt.getPropertyName().equals(PROP_ID)){
           System.out.println("this entity has changed and the property was not the id");
           setWasModified(true);
       }
    }
}
