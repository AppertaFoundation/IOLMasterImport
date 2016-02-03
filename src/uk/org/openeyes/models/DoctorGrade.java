/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "doctor_grade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DoctorGrade.findAll", query = "SELECT d FROM DoctorGrade d"),
    @NamedQuery(name = "DoctorGrade.findById", query = "SELECT d FROM DoctorGrade d WHERE d.id = :id"),
    @NamedQuery(name = "DoctorGrade.findByGrade", query = "SELECT d FROM DoctorGrade d WHERE d.grade = :grade"),
    @NamedQuery(name = "DoctorGrade.findByDisplayOrder", query = "SELECT d FROM DoctorGrade d WHERE d.displayOrder = :displayOrder")})
public class DoctorGrade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "grade")
    private String grade;
    @Basic(optional = false)
    @Column(name = "display_order")
    private int displayOrder;
    @OneToMany(mappedBy = "doctorGradeId")
    private Collection<User> userCollection;

    public DoctorGrade() {
    }

    public DoctorGrade(Integer id) {
        this.id = id;
    }

    public DoctorGrade(Integer id, String grade, int displayOrder) {
        this.id = id;
        this.grade = grade;
        this.displayOrder = displayOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoctorGrade)) {
            return false;
        }
        DoctorGrade other = (DoctorGrade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.DoctorGrade[ id=" + id + " ]";
    }
    
}
