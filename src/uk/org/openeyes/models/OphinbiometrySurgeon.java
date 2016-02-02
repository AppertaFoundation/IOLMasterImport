/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VEDELEKT
 */
@Entity
@Table(name = "ophinbiometry_surgeon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OphinbiometrySurgeon.findAll", query = "SELECT o FROM OphinbiometrySurgeon o"),
    @NamedQuery(name = "OphinbiometrySurgeon.findById", query = "SELECT o FROM OphinbiometrySurgeon o WHERE o.id = :id"),
    @NamedQuery(name = "OphinbiometrySurgeon.findByName", query = "SELECT o FROM OphinbiometrySurgeon o WHERE o.name = :name")})
public class OphinbiometrySurgeon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public OphinbiometrySurgeon() {
    }

    public OphinbiometrySurgeon(Integer id) {
        this.id = id;
    }

    public OphinbiometrySurgeon(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof OphinbiometrySurgeon)) {
            return false;
        }
        OphinbiometrySurgeon other = (OphinbiometrySurgeon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.org.openeyes.models.OphinbiometrySurgeon[ id=" + id + " ]";
    }
    
}
