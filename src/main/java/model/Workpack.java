package model;
// Generated 15-Feb-2017 2:38:53 PM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Workpack generated by hbm2java
 */
@Entity
@Table(name = "Workpack")
public class Workpack implements java.io.Serializable {

    private WorkpackId id;
    private String wpNm;
    private String wpDesc;
    private Integer wpResEng;
    private Short wpStatus;
    private Date wpStaDt;
    private Date wpEndDt;
    private short wpDel;
    private Date wpInsDt;
    private Date wpUpDt;

    public Workpack() {
    }

    public Workpack(WorkpackId id, String wpNm, short wpDel, Date wpInsDt, Date wpUpDt) {
        this.id = id;
        this.wpNm = wpNm;
        this.wpDel = wpDel;
        this.wpInsDt = wpInsDt;
        this.wpUpDt = wpUpDt;
    }

    public Workpack(WorkpackId id, String wpNm, String wpDesc, Integer wpResEng,
            Short wpStatus, Date wpStaDt, Date wpEndDt, 
            short wpDel, Date wpInsDt, Date wpUpDt) {
        this.id = id;
        this.wpNm = wpNm;
        this.wpDesc = wpDesc;
        this.wpResEng = wpResEng;
        this.wpStatus = wpStatus;
        this.wpStaDt = wpStaDt;
        this.wpEndDt = wpEndDt;
        this.wpDel = wpDel;
        this.wpInsDt = wpInsDt;
        this.wpUpDt = wpUpDt;
    }
    
    private Set<Wplab> wplabs;
    
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "wlProjNo", referencedColumnName = "wpProjNo"),
    	@JoinColumn(name = "wlWpNo", referencedColumnName = "wpNo")})
    public Set<Wplab> getWplabs() {
    	return wplabs;
    }
    
    public void setWplabs(Set<Wplab> wplabs) {
    	this.wplabs = wplabs;
    }

    @EmbeddedId

    @AttributeOverrides({ @AttributeOverride(name = "wpProjNo", column = @Column(name = "wpProjNo", nullable = false)),
            @AttributeOverride(name = "wpNo", column = @Column(name = "wpNo", nullable = false, length = 8)) })
    public WorkpackId getId() {
        return this.id;
    }

    public void setId(WorkpackId id) {
        this.id = id;
    }

    @Column(name = "wpNm", nullable = false, length = 64)
    public String getWpNm() {
        return this.wpNm;
    }

    public void setWpNm(String wpNm) {
        this.wpNm = wpNm;
    }

    @Column(name = "wpDesc")
    public String getWpDesc() {
        return this.wpDesc;
    }

    public void setWpDesc(String wpDesc) {
        this.wpDesc = wpDesc;
    }

    @Column(name = "wpResEng")
    public Integer getWpResEng() {
        return this.wpResEng;
    }

    public void setWpResEng(Integer wpResEng) {
        this.wpResEng = wpResEng;
    }

    @Column(name = "wpStatus")
    public Short getWpStatus() {
        return this.wpStatus;
    }

    public void setWpStatus(Short wpStatus) {
        this.wpStatus = wpStatus;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "wpStaDt", length = 10)
    public Date getWpStaDt() {
        return this.wpStaDt;
    }

    public void setWpStaDt(Date wpStaDt) {
        this.wpStaDt = wpStaDt;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "wpEndDt", length = 10)
    public Date getWpEndDt() {
        return this.wpEndDt;
    }

    public void setWpEndDt(Date wpEndDt) {
        this.wpEndDt = wpEndDt;
    }

    @Column(name = "wpDel", nullable = false)
    public short getWpDel() {
        return this.wpDel;
    }

    public void setWpDel(short wpDel) {
        this.wpDel = wpDel;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "wpInsDt", nullable = false, length = 19)
    public Date getWpInsDt() {
        return this.wpInsDt;
    }

    public void setWpInsDt(Date wpInsDt) {
        this.wpInsDt = wpInsDt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "wpUpDt", nullable = false, length = 19)
    public Date getWpUpDt() {
        return this.wpUpDt;
    }

    public void setWpUpDt(Date wpUpDt) {
        this.wpUpDt = wpUpDt;
    }

}