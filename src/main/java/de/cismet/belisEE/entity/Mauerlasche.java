/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.belisEE.mapicons.MapIcons;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "mauerlasche",uniqueConstraints = {@UniqueConstraint(columnNames={"fk_strassenschluessel","laufende_nummer"})})
@NamedQueries({
    //ToDo Leuchten problem
    @NamedQuery(name = "Mauerlasche.findStandortByGeom", query = "SELECT s FROM Mauerlasche s WHERE s.geometrie = :geometrie"),
    @NamedQuery(name = "Mauerlasche.findMauerlascheByStrassenschluessel", query = "FROM Mauerlasche m WHERE m.strassenschluessel.pk =:strassenschluessel"),    
    @NamedQuery(name = "Mauerlasche.findMauerlascheByStrassenschluesselBylfdNummer", query = "FROM Mauerlasche m WHERE m.strassenschluessel.pk =:strassenschluessel AND m.laufendeNummer = :laufendeNummer"),
    @NamedQuery(name = "Mauerlasche.refresh", query = "FROM Mauerlasche m WHERE m.id in (:ids)")
})
public class Mauerlasche extends GeoBaseEntity implements Serializable,DocumentContainer {
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Mauerlasche_seq",sequenceName="mauerlasche_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Mauerlasche_seq")
    private Long id;
    public static final String PROP_ID = "Mauerlasche.id";

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fk_strassenschluessel") 
    private Strassenschluessel strassenschluessel;
    public static final String PROP_STRASSENSCHLUESSEL = "Mauerlasche.strassenschluessel";
    
    @Column(name="laufende_nummer")
    private Short laufendeNummer;
    public static final String PROP_LAUFENDE_NUMMER = "Mauerlasche.laufendeNummer";
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date erstellungsjahr;
    public static final String PROP_ERSTELLUNGSJAHR = "Mauerlasche.erstellungsjahr";
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name ="fk_material")
    private MaterialMauerlasche material;
    public static final String PROP_MATERIAL = "Mauerlasche.material";

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "jt_mauerlasche_dokument", joinColumns = {@JoinColumn(name = "fk_mauerlasche")}, inverseJoinColumns = {@JoinColumn(name = "fk_dokument")})
    protected Set<DmsUrl> dokumente;

    public Set<DmsUrl> getDokumente() {
        return dokumente;
    }

    public void setDokumente(Set<DmsUrl> dokumente) {
        this.dokumente = dokumente;
    }
    
    //ToDo photo
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        getPropertyChangeSupport().firePropertyChange(PROP_ID, null, getId());
    }

    public Date getErstellungsjahr() {
        return erstellungsjahr;
    }

    public void setErstellungsjahr(Date erstellungsjahr) {
        this.erstellungsjahr = erstellungsjahr;
        getPropertyChangeSupport().firePropertyChange(PROP_ERSTELLUNGSJAHR, null, getErstellungsjahr());
    }

    public Short getLaufendeNummer() {
        return laufendeNummer;
    }

    public void setLaufendeNummer(Short laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
        getPropertyChangeSupport().firePropertyChange(PROP_LAUFENDE_NUMMER, null, getLaufendeNummer());
    }

    public Strassenschluessel getStrassenschluessel() {
        return strassenschluessel;
    }

    public void setStrassenschluessel(Strassenschluessel strassenschluessel) {
        final Strassenschluessel oldStrassenschluessel = getStrassenschluessel();
        this.strassenschluessel = strassenschluessel;
        getPropertyChangeSupport().firePropertyChange(PROP_STRASSENSCHLUESSEL, oldStrassenschluessel, strassenschluessel);
    }

    public MaterialMauerlasche getMaterial() {
        return material;
    }

    public void setMaterial(MaterialMauerlasche material) {
        this.material = material;
        getPropertyChangeSupport().firePropertyChange(PROP_MATERIAL, null, getMaterial());
    }        

    @Override
    public int hashCode() {
       if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Mauerlasche) {
            Mauerlasche anEntity = (Mauerlasche) other;
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
    public String getHumanReadablePosition() {
        if (getStrassenschluessel() != null && getStrassenschluessel().getStrasse() != null) {
            return getStrassenschluessel().getStrasse();
        } else {
            return super.getHumanReadablePosition();
        }
    }
    
    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Mauerlasche[id=" + id + "]";
    }

    @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoMauerlasche, null);
            return mapIcon;
        }
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
