/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.entity;

import de.cismet.commons.server.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author spuhl
 */
@Entity
@Table(name = "sperre")
@NamedQueries({@NamedQuery(name = "Lock.findLockForObject", query = "SELECT l FROM Lock l WHERE l.classId = :classId AND l.objectId = :objectId")})
public class Lock extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "class_id")
    private String classId;
    @Column(name = "object_id")
    private String objectId;
    @Column(name = "user_string")
    private String userString;
    @Column(name = "additional_info")
    private String additionalInfo;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "lock_timestamp")
    private Date lockTimestamp;
    
    //ToDo There will be a overflow
    @SequenceGenerator(name="lock_seq",sequenceName="lock_seq",allocationSize=1,initialValue=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="lock_seq")
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    public Lock() {
    }

    public Lock(Integer id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserString() {
        return userString;
    }

    public void setUserString(String userString) {
        this.userString = userString;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return lockTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.lockTimestamp = timestamp;
    }
    
    

    @Override
    public int hashCode() {
       if (this.getId() == null)
            return super.hashCode();
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lock)) {
            return false;
        }
        Lock other = (Lock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Lock[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        return "de.cismet.belisEE.entity.Lock[id=" + id + "]";
    }

}
