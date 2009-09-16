/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.belisEE.mapicons.MapIcons;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "abzweigdose")
@NamedQueries({
    @NamedQuery(name = "Abzweigdose.refresh", query = "FROM Abzweigdose a WHERE a.id in (:ids)")
})
public class Abzweigdose extends GeoBaseEntity implements DocumentContainer {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "abzweigdose_seq", sequenceName = "tdta_abzweigdose_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abzweigdose_seq")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "jt_abzweigdose_dokument", joinColumns = {@JoinColumn(name = "fk_abzweigdose")}, inverseJoinColumns = {@JoinColumn(name = "fk_dokument")})
    protected Set<DmsUrl> dokumente;

    public Set<DmsUrl> getDokumente() {
        return dokumente;
    }

    public void setDokumente(Set<DmsUrl> dokumente) {
        this.dokumente = dokumente;
    }

    @Override
    public int hashCode() {
       if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object other) {
       if (other instanceof Abzweigdose) {
            Abzweigdose anEntity = (Abzweigdose) other;
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
        return "de.cismet.belisEE.entity.Abzweigdose[id=" + id + "]";
    }

     @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoAbzweigdose, null);
            return mapIcon;
        }
    }

}
