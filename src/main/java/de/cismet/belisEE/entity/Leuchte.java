/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
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
@Table(name = "tdta_leuchten",uniqueConstraints = {@UniqueConstraint(columnNames={"`Straßenschlüssel`", "`Kennziffer`","`lfd_Nummer`"})})
//ToDo if there are leuchten without standort find dem
@NamedQueries({})
public class Leuchte extends BaseEntity implements Serializable,PropertyChangeListener,DocumentContainer {

    public Leuchte() {
        super();
    }

    public static final UnterhaltLeuchte DEFAULT_UNTERHALT;
    public static final Doppelkommando DEFAULT_DOPPELKOMMANDO;

    static {
        DEFAULT_UNTERHALT=new UnterhaltLeuchte();
        DEFAULT_UNTERHALT.setPk((short)0);
        DEFAULT_UNTERHALT.setUnterhaltspflichtigeLeuchte("Öffentl. Beleuchtung");

        DEFAULT_DOPPELKOMMANDO = new Doppelkommando();
        DEFAULT_DOPPELKOMMANDO.setPk("12");
        DEFAULT_DOPPELKOMMANDO.setBeschreibung("Ganznachtbetrieb (früher Nacht)");
    }
    

    private static final long serialVersionUID = 1L;
    @Column(name = "`Stadtbezirk`")
    private Short stadtbezirk;
    @Column(name = "`PLZ`")
    private String plz;
        
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="`Straßenschlüssel`")     
    private Strassenschluessel strassenschluessel;
    public static final String PROP_STRASSENSCHLUESSEL = "Leuchte.strassenschluessel";
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="`Kennziffer`")
    private Kennziffer kennziffer;
    public static final String PROP_KENNZIFFER = "Leuchte.Kennziffer";
    @Column(name ="`lfd_Nummer`")
    private Short laufendeNummer;    
    
    @Column(name = "`Leuchtennummer`")
    private Short leuchtennummer;
    public static final String PROP_LEUCHTENNUMMER = "Leuchte.leuchtennummer";
    @Column(name = "`Schaltstelle`")
    private String schaltstelle;
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="`fk_Energielieferant`")    
    private Energielieferant energielieferant;
    
    @Column(name = "`Rundsteuerempfänger`")
    private String rundsteuerempfaenger;
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="`fk_Leuchttyp`")        
    private Leuchtentyp leuchtentyp;
    public static final String PROP_LEUCHTENTYP = "Leuchte.leuchtentyp";
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="`fk_Unterhaltspflicht_Leuchte`")     
    private UnterhaltLeuchte unterhaltspflichtLeuchte;
    
    @Column(name = "`Zähler`")
    private boolean zaehler;
    
    //change name something like doppelkommando check
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="`1DK`")      
    private Doppelkommando dk1;

    @Column(name = "`Anzahl_1DK`")
    private Short anzahl1DK;
    
    //Table Tkey_Doppelkommando_1 fehlt
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "`2DK`")
    private Doppelkommando dk2;
    @Column(name = "`Anzahl_2DK`")
    private Short anzahl2DK;
    @Column(name = "`Montagefirma_Leuchte`")
    private String montageFirmaLeuchte;
    @Column(name = "`Inbetriebnahme_Leuchte`")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inbetriebnahmeLeuchte;
    @Column(name = "`Bemerkungen`")
    private String bemerkungen;
    
    @SequenceGenerator(name="Leuchte_seq",sequenceName="tdta_leuchten_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Leuchte_seq")
    @Id
    @Column(name = "`id`",nullable=false)
    private Integer id;
        
//    //ToDo test remove
//    //ToDo document limitations
    //@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    //@Fetch(FetchMode.SELECT)
    //@JoinColumn(name = "standort_id")
    private Standort standort;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "jt_leuchte_dokument", joinColumns = {@JoinColumn(name = "fk_leuchte")}, inverseJoinColumns = {@JoinColumn(name = "fk_dokument")})
    protected Set<DmsUrl> dokumente;

    public Set<DmsUrl> getDokumente() {
        return dokumente;
    }

    public void setDokumente(Set<DmsUrl> dokumente) {
        this.dokumente = dokumente;
    }

    public Standort getStandort() {
        return standort;
    }

    public void setStandort(Standort standort) {
        this.standort = standort;
    }

    public Leuchte(Integer id) {
        this.id = id;
    }

    public Short getStadtbezirk() {
        return stadtbezirk;
    }

    public void setStadtbezirk(final Short stadtbezirk) {
        this.stadtbezirk = stadtbezirk;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(final String plz) {
        this.plz = plz;
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

    public void setLaufendeNummer(final Short laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
    }

    public Short getLeuchtennummer() {
        return leuchtennummer;
    }

    public void setLeuchtennummer(final Short leuchtennummer) {
        final Short oldLeuchtenummer = getLeuchtennummer();
        this.leuchtennummer = leuchtennummer;
        getPropertyChangeSupport().firePropertyChange(PROP_LEUCHTENNUMMER, oldLeuchtenummer, leuchtennummer);
    }

    public String getSchaltstelle() {
        return schaltstelle;
    }

    public void setSchaltstelle(final String schaltstelle) {
        this.schaltstelle = schaltstelle;
    }

    public Energielieferant getEnergielieferant() {
        return energielieferant;
    }

    public void setEnergielieferant(final Energielieferant energielieferant) {
        this.energielieferant = energielieferant;
    }

    public String getRundsteuerempfaenger() {
        return rundsteuerempfaenger;
    }

    public void setRundsteuerempfaenger(final String rundsteuerempfaenger) {
        this.rundsteuerempfaenger = rundsteuerempfaenger;
    }

    public Leuchtentyp getLeuchtentyp() {
        return leuchtentyp;
    }

    public void setLeuchtentyp(final Leuchtentyp leuchtentyp) {
        final Leuchtentyp oldLeuchtenTyp = getLeuchtentyp();
        this.leuchtentyp = leuchtentyp;
        getPropertyChangeSupport().firePropertyChange(PROP_LEUCHTENTYP, oldLeuchtenTyp, leuchtentyp);
    }

    public UnterhaltLeuchte getUnterhaltspflichtLeuchte() {
        return unterhaltspflichtLeuchte;
    }

    public void setUnterhaltspflichtLeuchte(final UnterhaltLeuchte unterhaltspflichtLeuchte) {
        this.unterhaltspflichtLeuchte = unterhaltspflichtLeuchte;
    }

    public boolean getZaehler() {
        return zaehler;
    }

    public void setZaehler(final boolean zaehler) {
        this.zaehler = zaehler;
    }

    public Doppelkommando getDk1() {
        return dk1;
    }

    public void setDk1(Doppelkommando dk1) {
        this.dk1 = dk1;
    }

    public Short getAnzahl1DK() {
        return anzahl1DK;
    }

    public void setAnzahl1DK(final Short anzahl1DK) {
        this.anzahl1DK = anzahl1DK;
    }

    public Doppelkommando getDk2() {
        return dk2;
    }

    public void setDk2(final Doppelkommando dk2) {
        this.dk2 = dk2;
    }

    public Short getAnzahl2DK() {
        return anzahl2DK;
    }

    public void setAnzahl2DK(final Short anzahl2DK) {
        this.anzahl2DK = anzahl2DK;
    }

    public String getMontageFirmaLeuchte() {
        return montageFirmaLeuchte;
    }

    public void setMontageFirmaLeuchte(final String montageFirmaLeuchte) {
        this.montageFirmaLeuchte = montageFirmaLeuchte;
    }

    public Date getInbetriebnahmeLeuchte() {
        return inbetriebnahmeLeuchte;
    }

    public void setInbetriebnahmeLeuchte(final Date inbetriebnahmeLeuchte) {
        this.inbetriebnahmeLeuchte = inbetriebnahmeLeuchte;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(final String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
   public boolean equals(Object other) {
        if(other instanceof Leuchte){
            Leuchte anEntity = (Leuchte) other;
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

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Leuchte[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        String leuchtennummer = "";
        String leuchtentyp = "";        
        if(getLaufendeNummer() != null){
            leuchtennummer = getLaufendeNummer().toString();
        }
        if(getLeuchtentyp() != null && getLeuchtentyp().getLeuchtentyp() != null){
            leuchtentyp = getLeuchtentyp().getLeuchtentyp();
        }
        if(leuchtennummer.length() > 0 && leuchtentyp.length() > 0){
            return leuchtennummer +", "+leuchtentyp;
        } else if (leuchtennummer.length() > 0){
            return leuchtennummer;
        } else if (leuchtentyp.length() > 0){
            return leuchtentyp;
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
    
//    @Override
//    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
//        if (mapIcon != null) {
//            return mapIcon;
//        } else {
//            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
//                    MapIcons.icoLeuchte, MapIcons.icoLeuchteSelected);
//            return mapIcon;
//        }
//    }

     @Override
     public void propertyChange(PropertyChangeEvent evt) {
       if(evt.getSource() != null && evt.getSource() instanceof Standort && isParentMast((Standort)evt.getSource())){
            System.out.println("Property of parent Mast has changed. Changing this leuchte property");
            if(evt.getPropertyName() != null && evt.getPropertyName().equals(Standort.PROP_STRASSENSCHLUESSEL)){
                System.out.println("Strassenschluessel changed");
                setStrassenschluessel((Strassenschluessel)evt.getNewValue());
            } else if(evt.getPropertyName() != null && evt.getPropertyName().equals(Standort.PROP_KENNZIFFER)){
                System.out.println("Kennziffer changed");
                setKennziffer((Kennziffer)evt.getNewValue());
            } else {
                System.out.println("Unkown property. Nothing to change");
            }
       } else {
          System.out.println("Property not from parent mast");
       }
    }

    private boolean isParentMast(Standort standort){
        return standort != null && standort.getLeuchten() != null && standort.getLeuchten().contains(this);
    }
}
