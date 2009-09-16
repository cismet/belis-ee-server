package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import java.net.MalformedURLException;
import java.net.URL;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "url")
public class Url extends BaseEntity {

    @SequenceGenerator(name = "url_seq", sequenceName = "url_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq")
    @Id()
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "url_base_id", nullable = false)
    private UrlBase urlBase;
    @Column(name = "object_name", nullable = false)
    private String objektname;

    public Url() {
    }

    public URL getURL() {
        try {
            return new URL(urlBase.getCompleteURLBase() + objektname);
        } catch (MalformedURLException ex) {
            //TODO log
        }
        return null;
    }

    public UrlBase getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(UrlBase val) {
        this.urlBase = val;
    }

    public String getObjektname() {
        return objektname;
    }

    public void setObjektname(String val) {
        this.objektname = val;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (other instanceof Url) {
            Url anEntity = (Url) other;
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
        return "de.cismet.belis.entity.Url[id=" + getId() + "]";
    }
}