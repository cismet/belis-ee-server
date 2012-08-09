package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.tools.URLSplitter;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "dms_url")
public class DmsUrl extends BaseEntity {

    @SequenceGenerator(name = "dms_url_seq", sequenceName = "dms_url_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dms_url_seq")
    @Id()
    private Integer id;
    @Column(name = "typ")
    private Integer typ;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    //@Basic(fetch=FetchType.EAGER)
    private String beschreibung;
   //@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.MERGE})

    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "url_id")
    private Url url;

    public DmsUrl() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer val) {
        this.id = val;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer val) {
        this.typ = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String val) {
        this.name = val;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String val) {
        this.beschreibung = val;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url val) {
        this.url = val;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    public File toFile() {
        File candidate = null;
        if (getUrl() != null) {
            final URL u = getUrl().getURL();
            if (u != null) {
                try {
                    candidate = new File(u.toURI());
                } catch (URISyntaxException ex) {
                    candidate = new File(u.getPath());
                }
                if (candidate.isFile()) {
                    return candidate;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof DmsUrl) {
            DmsUrl anEntity = (DmsUrl) other;
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
        return "de.cismet.belis.entity.DmsUrl[id=" + getId() + "]";
    }

    public static final DmsUrl createDmsURLFromLink(final String link, final String description) {
        if (link == null || description == null) {
            throw new NullPointerException();
        }
        final DmsUrl dmsUrlEntity = new DmsUrl();
        final Url url = new Url();
        final UrlBase base = new UrlBase();
        final URLSplitter splitter = new URLSplitter(link);
        dmsUrlEntity.setBeschreibung(description);
        url.setUrlBase(base);
        dmsUrlEntity.setUrl(url);
        base.setPfad(splitter.getPath());
        base.setProtPrefix(splitter.getProt_prefix());
        base.setServer(splitter.getServer());
        url.setObjektname(splitter.getObject_name());
        return dmsUrlEntity;
    }
}
