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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"fk_strassenschluessel","laufende_nummer"})})
@NamedQueries({
    //ToDo Leuchten problem
    @NamedQuery(name = "Schaltstelle.findSchaltstelleByGeom", query = "SELECT s FROM Schaltstelle s WHERE s.geometrie = :geometrie"),
    @NamedQuery(name = "Schaltstelle.findSchaltstelleByStrassenschluessel", query = "FROM Schaltstelle s WHERE s.strassenschluessel.pk =:strassenschluessel"),
    @NamedQuery(name = "Schaltstelle.findSchaltstelleByStrassenschluesselBylfdNummer", query = "FROM Schaltstelle s WHERE s.strassenschluessel.pk =:strassenschluessel AND s.laufendeNummer = :laufendeNummer"),
    @NamedQuery(name = "Schaltstelle.refresh", query = "FROM Schaltstelle s WHERE s.id in (:ids)")
})
public class Schaltstelle extends GeoBaseEntity implements Serializable,DocumentContainer {

    @Id
    @SequenceGenerator(name = "Schaltstelle_seq", sequenceName = "schaltstelle_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Schaltstelle_seq")
    private Long id;
    public static final String PROP_ID = "Schaltstelle.id";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_strassenschluessel")
    private Strassenschluessel strassenschluessel;
    public static final String PROP_STRASSENSCHLUESSEL = "Schaltstelle.strassenschluessel";
    @Column(name = "laufende_nummer")
    private Short laufendeNummer;
    public static final String PROP_LAUFENDE_NUMMER = "Schaltstelle.laufendeNummer";
    @Column(name = "haus_nummer")
    private String hausnummer;
    public static final String PROP_HAUSNUMMER = "Schaltstelle.hausnummer";
    @Column(name = "schaltstellen_nummer")
    private String schaltstellenNummer;
    public static final String PROP_SCHALTSTELLENNUMMER = "Schaltstelle.schaltstellenNummer";
    @Column(name = "zusaetzliche_standortbezeichnung")
    private String zusaetzlicheStandortbezeichnung;
    public static final String PROP_ZUSAETZLICHE_STANDORTBEZEICHNUNG = "Schaltstelle.zusaetzlicheStandortbezeichnung";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_bauart")
    private Bauart bauart;
    public static final String PROP_BAUART= "Schaltstelle.bauart";
    private String bemerkung;
    public static final String PROP_BEMERKUNG= "Schaltstelle.bemerkung";
    @Temporal(TemporalType.TIMESTAMP)
    private Date erstellungsjahr;
    public static final String PROP_ERSTELLUNGSJAHR= "Schaltstelle.erstellungsjahr";

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "jt_schaltstelle_dokument", joinColumns = {@JoinColumn(name = "fk_schaltstelle")}, inverseJoinColumns = {@JoinColumn(name = "fk_dokument")})
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

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
        getPropertyChangeSupport().firePropertyChange(PROP_HAUSNUMMER, null, getHausnummer());
    }

    public String getSchaltstellenNummer() {
        return schaltstellenNummer;
    }

    public void setSchaltstellenNummer(String schaltstellenNummer) {
        final String oldSchaltstellenNummer = getSchaltstellenNummer();
        this.schaltstellenNummer = schaltstellenNummer;
        getPropertyChangeSupport().firePropertyChange(PROP_SCHALTSTELLENNUMMER, oldSchaltstellenNummer, getSchaltstellenNummer());
    }

    public Date getErstellungsjahr() {
        return erstellungsjahr;
    }

    public void setErstellungsjahr(Date erstellungsjahr) {
        this.erstellungsjahr = erstellungsjahr;
        getPropertyChangeSupport().firePropertyChange(PROP_ERSTELLUNGSJAHR, null, getErstellungsjahr());
    }

    public Bauart getBauart() {
        return bauart;
    }

    public void setBauart(Bauart bauart) {
        this.bauart = bauart;
        getPropertyChangeSupport().firePropertyChange(PROP_BAUART, null, getBauart());
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
        getPropertyChangeSupport().firePropertyChange(PROP_BEMERKUNG, null, getBemerkung());
    }

    public String getZusaetzlicheStandortbezeichnung() {
        return zusaetzlicheStandortbezeichnung;
    }

    public void setZusaetzlicheStandortbezeichnung(String zusaetzlicheStandortbeschreibung) {
        this.zusaetzlicheStandortbezeichnung = zusaetzlicheStandortbeschreibung;
        getPropertyChangeSupport().firePropertyChange(PROP_ZUSAETZLICHE_STANDORTBEZEICHNUNG, null, getZusaetzlicheStandortbezeichnung());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Schaltstelle) {
            Schaltstelle anEntity = (Schaltstelle) other;
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
    public String toString() {
        return "de.cismet.belisEE.entity.Schaltstelle[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        String schaltellennummer = "";
        if (getSchaltstellenNummer() != null) {
            schaltellennummer = getSchaltstellenNummer();
        }
        return schaltellennummer;
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
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoSchaltstelle, null);
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
