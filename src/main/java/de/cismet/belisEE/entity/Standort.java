/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.entity;

import de.cismet.belisEE.util.StandortKey;
import de.cismet.belisEE.bean.interfaces.ObjectKey;
import de.cismet.belisEE.mapicons.MapIcons;
import de.cismet.belisEE.util.LeuchteComparator;
import de.cismet.cismap.commons.tools.IconUtils;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;
import java.awt.Color;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "tdta_standort_mast",uniqueConstraints = {@UniqueConstraint(columnNames={"`Straßenschlüssel`", "`Kennziffer`","`lfd_Nummer`"})})
@NamedQueries({
    //ToDo Leuchten problem
    @NamedQuery(name = "Standort.findStandortByGeom", query = "SELECT s FROM Standort s WHERE s.geometrie = :geometrie"),
    @NamedQuery(name = "Standort.findStandortByStrassenschluesselByKennzifferBylfdNummer", query = "FROM Standort s WHERE s.strassenschluessel.pk =:strassenschluessel AND s.kennziffer.kennziffer=:kennziffer AND s.laufendeNummer=:laufendeNummer"),
    @NamedQuery(name = "Standort.findStandortByStrassenschluessel", query = "FROM Standort s WHERE s.strassenschluessel.pk =:strassenschluessel"),
    @NamedQuery(name = "Standort.findStandortByStrassenschluesselByKennziffer", query = "FROM Standort s WHERE s.strassenschluessel.pk =:strassenschluessel AND s.kennziffer.kennziffer=:kennziffer"),
    @NamedQuery(name = "Standort.findStandortByStrassenschluesselBylfdNummer", query = "FROM Standort s WHERE s.strassenschluessel.pk =:strassenschluessel AND s.laufendeNummer = :laufendeNummer"),
    @NamedQuery(name = "Standort.findHighestLaufendenummer", query = "SELECT MAX(s.laufendeNummer) FROM Standort s WHERE s.strassenschluessel.pk like :strassenschluessel AND s.kennziffer.kennziffer = :kennziffer"),
    @NamedQuery(name = "Standort.refresh", query = "FROM Standort s WHERE s.id in (:ids)")
})
public class Standort extends GeoBaseEntity implements Serializable, ObjectKey, PropertyChangeListener, DocumentContainer {


    public static UnterhaltMast DEFAULT_UNTERHALT;

    static {
        DEFAULT_UNTERHALT = new UnterhaltMast();
        DEFAULT_UNTERHALT.setPk((short)0);
        DEFAULT_UNTERHALT.setUnterhaltMast("öffentl. Beleuchtung");
    }

    public Standort() {
        super();
        setVirtuellerStandort(false);
    }

    
    private static final long serialVersionUID = 1L;
//    @Column(name = "`Stadtbezirk`")
//    private Short stadtbezirk;
    @Column(name = "`PLZ`")
    private String plz;
    public static final String PROP_PLZ = "Standort.plz";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`Straßenschlüssel`")
    private Strassenschluessel strassenschluessel;
    public static final String PROP_STRASSENSCHLUESSEL = "Standort.strassenschluessel";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`Kennziffer`")
    private Kennziffer kennziffer;
    public static final String PROP_KENNZIFFER = "Standort.Kennziffer";
    @Column(name = "`lfd_Nummer`")
    private Short laufendeNummer;
    public static final String PROP_LAUFENDENUMMER = "Standort.laufendeNummer";
    @Column(name = "`Standortangabe`")
    private String standortangabe;
    public static final String PROP_STANDORTANGABE = "Standort.standortangabe";
    @Column(name = "`Haus_Nr`")
    private String hausnummer;
    public static final String PROP_HAUSNUMMER = "Standort.hausnummer";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`fk_Stadtbezirk`")
    private Stadtbezirk stadtbezirk;
    public static final String PROP_STADTBEZIRK = "Standort.stadtbezirk";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`fk_Mastart`")
    private Mastart mastart;
    public static final String PROP_MASTART = "Standort.mastart";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`fk_Klassifizierung`")
    private Klassifizierung klassifizierung;
    public static final String PROP_KLASSIFIZIERUNG = "Standort.klassifizierung";
    @Column(name = "`Mastanstrich`")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mastanstrich;
    public static final String PROP_MASTANSTRICH = "Standort.mastanstrich";
    @Column(name = "`Mastschutz`")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mastschutz;
    public static final String PROP_MASTSCHUTZ = "Standort.mastschutz";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`fk_Unterhaltspflicht_Mast`")
    private UnterhaltMast unterhaltspflichtMast;
    public static final String PROP_UNTERHALTSPFLICHT = "Standort.unterhaltspflichtMast";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`fk_Masttyp`")
    private Masttyp masttyp;
    public static final String PROP_MASTTYP = "Standort.masttyp";
    @Column(name = "`Montagefirma`")
    private String montagefirma;
    public static final String PROP_MONTAGEFIRMA = "Standort.montagefirma";
    @Column(name = "`Inbetriebnahme_Mast`")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inbetriebnahmeMast;
    public static final String PROP_INBETRIEBNAHME_MAST = "Standort.inbetriebnahmeMast";
    @Column(name = "`Verrechnungseinheit`")
    private boolean verrechnungseinheit;
    public static final String PROP_VERRECHNUNGSEINHEIT = "Standort.verrechnungseinheit";
    @Column(name = "`Bemerkungen`")
    private String bemerkungen;
    public static final String PROP_BEMERKUNG = "Standort.bemerkungen";
    @Column(name = "`Letzte_Änderung`")
    @Temporal(TemporalType.TIMESTAMP)
    private Date letzteAenderung;
    public static final String PROP_LETZTE_AENDERUNG = "Standort.letzteAenderung";

    @SequenceGenerator(name = "Standort_seq", sequenceName = "tdta_standort_mast_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Standort_seq")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public static final String PROP_ID = "Standort.id";    //ToDo document limitations

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="standort")
    @JoinColumns({@JoinColumn(name = "`Straßenschlüssel`", referencedColumnName = "`Straßenschlüssel`"),
        @JoinColumn(name = "`Kennziffer`", referencedColumnName = "`Kennziffer`"),
        @JoinColumn(name = "`lfd_Nummer`", referencedColumnName = "`lfd_Nummer`")
    })
    //@Fetch(FetchMode.SUBSELECT)
    @Sort(type = SortType.COMPARATOR, comparator = LeuchteComparator.class)
    private Set<Leuchte> leuchten;
    public static final String PROP_LEUCHTEN = "Standort.leuchten";
    
    public static final String PROP_VIRTUELLER_STANDORT = "Standort.virtuellerStandort";
    @Column(name = "`ist_virtueller_standort`")
    private Boolean virtuellerStandort;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "jt_standort_dokument", joinColumns = {@JoinColumn(name = "fk_standort")}, inverseJoinColumns = {@JoinColumn(name = "fk_dokument")})
    protected Set<DmsUrl> dokumente;

    public Set<DmsUrl> getDokumente() {
        return dokumente;
    }

    public void setDokumente(Set<DmsUrl> dokumente) {
        this.dokumente = dokumente;
    }

    public Boolean isVirtuellerStandort() {
        return virtuellerStandort;
    }

    public void setVirtuellerStandort(Boolean virtualStandort) {
        this.virtuellerStandort = virtualStandort;
        getPropertyChangeSupport().firePropertyChange(PROP_VIRTUELLER_STANDORT, null, isVirtuellerStandort());
    }

    public boolean isStandortMast() {
        if (getMastart() != null || getMasttyp() != null || getMastanstrich() != null || getMastschutz() != null || (isVirtuellerStandort() == null || (isVirtuellerStandort() != null && !isVirtuellerStandort()))) {
            return true;
        } else {
            return false;
        }
    }

    public Set<Leuchte> getLeuchten() {
        return leuchten;
    }

    public void setLeuchten(Set<Leuchte> leuchten) {
        this.leuchten = leuchten;
        getPropertyChangeSupport().firePropertyChange(PROP_LEUCHTEN, null, getLeuchten());
    }

    public Standort(final Long id) {
        setId(id);
    }

//    public Short getStadtbezirk() {
//        return stadtbezirk;
//    }
//
//    public void setStadtbezirk(final Short stadtbezirk) {
//        this.stadtbezirk = stadtbezirk;
//    }
    public String getPlz() {
        return plz;
    }

    public void setPlz(final String plz) {
        this.plz = plz;
        getPropertyChangeSupport().firePropertyChange(PROP_PLZ, null, getPlz());
    }

    public Strassenschluessel getStrassenschluessel() {
        return strassenschluessel;
    }

    public void setStrassenschluessel(final Strassenschluessel strassenschluessel) {
        final Strassenschluessel oldStrassenschluessel = getStrassenschluessel();
        this.strassenschluessel = strassenschluessel;
        getPropertyChangeSupport().firePropertyChange(PROP_STRASSENSCHLUESSEL, oldStrassenschluessel, strassenschluessel);
    }

    public Kennziffer getKennziffer() {
        return kennziffer;
    }

    public void setKennziffer(final Kennziffer kennziffer) {
        final Kennziffer oldKennziffer = getKennziffer();
        this.kennziffer = kennziffer;
        getPropertyChangeSupport().firePropertyChange(PROP_KENNZIFFER, oldKennziffer, kennziffer);
    }

    public Short getLaufendeNummer() {
        return laufendeNummer;
    }

    public void setLaufendeNummer(final Short lfdNummer) {
        this.laufendeNummer = lfdNummer;
        getPropertyChangeSupport().firePropertyChange(PROP_LAUFENDENUMMER, null, getLaufendeNummer());
    }

    public String getStandortangabe() {
        return standortangabe;
    }

    public void setStandortangabe(final String standortangabe) {
        this.standortangabe = standortangabe;
        getPropertyChangeSupport().firePropertyChange(PROP_STANDORTANGABE, null, getStandortangabe());
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(final String hausnummer) {
        this.hausnummer = hausnummer;
        getPropertyChangeSupport().firePropertyChange(PROP_HAUSNUMMER, null, getHausnummer());
    }

    public Stadtbezirk getStadtbezirk() {
        return stadtbezirk;
    }

    public void setStadtbezirk(final Stadtbezirk stadtbezirk) {
        this.stadtbezirk = stadtbezirk;
        getPropertyChangeSupport().firePropertyChange(PROP_STADTBEZIRK, null, getStadtbezirk());
    }

    public Mastart getMastart() {
        return mastart;
    }

    public void setMastart(final Mastart mastart) {
        final Mastart oldMastart = getMastart();
        this.mastart = mastart;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTART, oldMastart, mastart);
    }

    public Klassifizierung getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final Klassifizierung klassifizierung) {
        this.klassifizierung = klassifizierung;
        getPropertyChangeSupport().firePropertyChange(PROP_KLASSIFIZIERUNG, null, getKlassifizierung());
    }

    public Date getMastanstrich() {
        return mastanstrich;
    }

    public void setMastanstrich(final Date mastanstrich) {
        this.mastanstrich = mastanstrich;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTANSTRICH, null, getMastanstrich());
    }

    public Date getMastschutz() {
        return mastschutz;
    }

    public void setMastschutz(final Date mastschutz) {
        this.mastschutz = mastschutz;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTSCHUTZ, null, getMastschutz());
    }

    public UnterhaltMast getUnterhaltspflichtMast() {
        return unterhaltspflichtMast;
    }

    public void setUnterhaltspflichtMast(final UnterhaltMast unterhaltspflichtMast) {
        this.unterhaltspflichtMast = unterhaltspflichtMast;
        getPropertyChangeSupport().firePropertyChange(PROP_UNTERHALTSPFLICHT, null, getUnterhaltspflichtMast());
    }

    public Masttyp getMasttyp() {
        return masttyp;
    }

    public void setMasttyp(final Masttyp masttyp) {
        final Masttyp oldMasttyp = getMasttyp();
        this.masttyp = masttyp;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTTYP, oldMasttyp, getMasttyp());
    }

    public String getMontagefirma() {
        return montagefirma;
    }

    public void setMontagefirma(final String montagefirma) {
        this.montagefirma = montagefirma;
        getPropertyChangeSupport().firePropertyChange(PROP_MONTAGEFIRMA, null, getMontagefirma());
    }

    public Date getInbetriebnahmeMast() {
        return inbetriebnahmeMast;
    }

    public void setInbetriebnahmeMast(final Date inbetriebnahmeMast) {
        this.inbetriebnahmeMast = inbetriebnahmeMast;
        getPropertyChangeSupport().firePropertyChange(PROP_INBETRIEBNAHME_MAST, null, getInbetriebnahmeMast());
    }

    public boolean isVerrechnungseinheit() {
        return verrechnungseinheit;
    }

    public void setVerrechnungseinheit(final boolean verrechnungseinheit) {
        this.verrechnungseinheit = verrechnungseinheit;
        getPropertyChangeSupport().firePropertyChange(PROP_VERRECHNUNGSEINHEIT, null, isVerrechnungseinheit());
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(final String bemerkungen) {
        this.bemerkungen = bemerkungen;
        getPropertyChangeSupport().firePropertyChange(PROP_BEMERKUNG, null, getBemerkungen());
    }

    public Date getLetzteAenderung() {
        return letzteAenderung;
    }

    public void setLetzteAenderung(final Date letzteAenderung) {
        this.letzteAenderung = letzteAenderung;
        getPropertyChangeSupport().firePropertyChange(PROP_LETZTE_AENDERUNG, null, getLetzteAenderung());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long oldId = getId();
        this.id = id;
        getPropertyChangeSupport().firePropertyChange(PROP_ID, oldId, id);
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
        if (other instanceof Standort) {
            Standort anEntity = (Standort) other;
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
        return "de.cismet.belis.entity.Standort[id=" + getId() + "]";
    }

    public StandortKey getStandortKey() {
        return new StandortKey(getStrassenschluessel(), getKennziffer(), getLaufendeNummer());
    }

    // new version
//     @Override
//    public String getKeyString() {
//        String masttyp = "";
//        String mastart = "";
//        String lfd="";
//        if (getMasttyp() != null && getMasttyp().getMasttyp() != null) {
//            masttyp = getMasttyp().getMasttyp();
//        }
//        if (getMastart() != null && getMastart().getMastart() != null) {
//            mastart = getMastart().getMastart();
//        }
//        if (getLaufendeNummer() != null) {
//            lfd = getLaufendeNummer().toString();
//        }
//        if (mastart.length() > 0 && masttyp.length() > 0 && lfd.length() > 0) {
//            return lfd+", "+mastart + ", " + masttyp;
//        } else if (mastart.length() > 0 && lfd.length() > 0) {
//            return lfd+", "+mastart;
//        } else if (masttyp.length() > 0 && lfd.length() > 0) {
//            return lfd+", "+masttyp;
//         } else if (masttyp.length() > 0 && mastart.length() > 0) {
//            return mastart+", "+masttyp;
//        } else if (masttyp.length() > 0) {
//            return masttyp;
//        } else if (mastart.length() > 0) {
//            return mastart;
//        } else if (lfd.length() > 0) {
//            return lfd;
//        } else {
//            return "";
//        }
//    }

    @Override
    public String getKeyString() {
        String masttyp = "";
        String mastart = "";
        if (getMasttyp() != null && getMasttyp().getMasttyp() != null) {
            masttyp = getMasttyp().getMasttyp();
        }
        if (getMastart() != null && getMastart().getMastart() != null) {
            mastart = getMastart().getMastart();
        }
        if (mastart.length() > 0 && masttyp.length() > 0) {
            return mastart + ", " + masttyp;
        } else if (mastart.length() > 0) {
            return mastart;
        } else if (masttyp.length() > 0) {
            return masttyp;
        } else {
            return "";
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
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        final List<Integer> nums = new ArrayList<Integer>(6);
//        if (mapIcon != null) {
//            return mapIcon;
//        } else {
//            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
//                    MapIcons.icoStandort, MapIcons.icoStandortSelected);
//            return mapIcon;
//        }
        if (isStandortMast()) {
            System.out.println("Standort is Mast");
            if (getLaufendeNummer() != null) {
                nums.add(getLaufendeNummer().intValue());
            }
            Image iconToUse = null;
            if (getLeuchten() != null && getLeuchten().size() > 0) {
                System.out.println("There are leuchten at this mast.");
                iconToUse = MapIcons.icoMastWithLeuchte;
            } else {
                System.out.println("There are no leuchten at this mast.");
                iconToUse = MapIcons.icoMast;
            }

            Image i1 = IconUtils.mergeNumbersToIcon(iconToUse, nums, Color.RED);
//                Image i2 = IconUtils.mergeNumbersToIcon(MapIcons.icoMastWithLeuchteSelected, nums, Color.BLUE);

            return FeatureAnnotationSymbol.newCustomSweetSpotFeatureAnnotationSymbol(
                    i1,
                    null,
                    IconUtils.calcNewSweepSpotX(0.5, iconToUse, i1),
                    IconUtils.calcNewSweepSpotY(0.5, iconToUse, i1));
//            } else {
//
////                return FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
////                        MapIcons.icoMast, null);
//            }
        } else {
            System.out.println("Standort is no Mast. There fore must be Leuchte");
            if (getLeuchten() != null) {

                if (getLeuchten().size() != 1) {
                    System.out.println("Standort is Leuchte, but getLeuchten().size() is != 1! (It is " + getLeuchten().size() + ")");
                }
                for (final Leuchte l : getLeuchten()) {
                    if (l.getLaufendeNummer() != null) {
                        nums.add(l.getLaufendeNummer().intValue());
                        break;
                    }
                }
            }
            Image i1 = IconUtils.mergeNumbersToIcon(MapIcons.icoLeuchte, nums, Color.RED);
//            Image i2 = IconUtils.mergeNumbersToIcon(MapIcons.icoLeuchteSelected, nums, Color.BLUE);
            return FeatureAnnotationSymbol.newCustomSweetSpotFeatureAnnotationSymbol(
                    i1,
                    null,
                    IconUtils.calcNewSweepSpotX(0.5, MapIcons.icoLeuchte, i1),
                    IconUtils.calcNewSweepSpotY(0.5, MapIcons.icoLeuchte, i1));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropertyChange Standort");
        if (!isStandortMast() && evt.getSource() instanceof Leuchte) {
            System.out.println("Property of Leuchte has changed. Changing Standort property");
            if (evt.getPropertyName() != null && evt.getPropertyName().equals(Leuchte.PROP_STRASSENSCHLUESSEL)) {
                System.out.println("Strassenschluessel changed");
                setStrassenschluessel((Strassenschluessel) evt.getNewValue());
            } else if (evt.getPropertyName() != null && evt.getPropertyName().equals(Leuchte.PROP_KENNZIFFER)) {
                System.out.println("Kennziffer changed");
                setKennziffer((Kennziffer) evt.getNewValue());
            } else {
                System.out.println("Unkown property. Nothing to change");
            }
        } else if (evt.getSource().equals(this) && !evt.getPropertyName().equals(PROP_ID)) {
            System.out.println("this entity has changed and the property was not the id");
            setWasModified(true);
        }
    }
}
