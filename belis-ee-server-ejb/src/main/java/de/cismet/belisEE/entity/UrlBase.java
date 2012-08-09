package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "url_base")
public class UrlBase extends BaseEntity {

    @SequenceGenerator(name = "url_base_seq", sequenceName = "url_base_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_base_seq")
    @Id()
    private Integer id;
    @Column(name = "prot_prefix")
    private String protPrefix;
    @Column(name = "server", nullable = false)
    private String server;
    @Column(name = "path", nullable = false)
    private String pfad;

    public UrlBase() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer val) {
        this.id = val;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String val) {
        this.server = val;
    }

    public String getProtPrefix() {
        return protPrefix;
    }

    public void setProtPrefix(String val) {
        this.protPrefix = val;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String val) {
        this.pfad = val;
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
        if (other instanceof UrlBase) {
            UrlBase anEntity = (UrlBase) other;
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

    public String getCompleteURLBase() {
        return protPrefix + server + pfad;
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.UrlBase[id=" + getId() + "]";
    }
}
