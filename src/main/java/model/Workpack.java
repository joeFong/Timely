package model;
// Generated 15-Feb-2017 2:38:53 PM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Workpack generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Workpack")
public class Workpack implements java.io.Serializable, Comparable<Workpack> {
	
	private boolean removeWplabs = false;
	private BigDecimal totalCost;
	private BigDecimal totalDays;
	private String childName;
	private HashMap<String, BigDecimal> initialEst;
	/* =============================================== */

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
    private Set<Employee> employees;

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
    
    @OneToMany(fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
    @JoinColumns({@JoinColumn(name = "wlProjNo", referencedColumnName = "wpProjNo"),
    	@JoinColumn(name = "wlWpNo", referencedColumnName = "wpNo")})
    @OrderBy("id.wlLgId")
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

    @Column(name = "wpResEng", nullable = true)
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
    @Column(name = "wpInsDt", insertable = false, nullable = false, length = 19)
    public Date getWpInsDt() {
        return this.wpInsDt;
    }

    public void setWpInsDt(Date wpInsDt) {
        this.wpInsDt = wpInsDt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "wpUpDt", insertable = false, nullable = false, length = 19)
    public Date getWpUpDt() {
        return this.wpUpDt;
    }

    public void setWpUpDt(Date wpUpDt) {
        this.wpUpDt = wpUpDt;
    }
    
    @ManyToMany(mappedBy="workpackages", fetch=FetchType.EAGER)
    public Set<Employee> getEmployees() {
        return this.employees;
    }
    
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

	@Override
	public int compareTo(Workpack arg0) {
		return this.getId().getWpNo().compareTo(arg0.getId().getWpNo());
	}
	
    @Override
    public int hashCode() {
    	int result = 17;

        result = 37 * result + this.getId().hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object other) {
    	if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof Workpack))
            return false;
        Workpack castOther = (Workpack) other;

        return (this.getId().equals(castOther.getId()));
    }
	
	/* =============================================== */
	
	@Transient
    public boolean getRemoveWplabs() {
        return removeWplabs;
    }
	
	public void setRemoveWplabs(boolean removeWplabs) {
		this.removeWplabs = removeWplabs;
	}
	
	@Transient
	public BigDecimal getTotalCost() {
		return this.totalCost;
	}
	
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
	@Transient
	public BigDecimal getTotalDays() {
		return this.totalDays;
	}
	
	public void setTotalDays(BigDecimal totalDays) {
		this.totalDays = totalDays;
	}
	
	@Transient
	public String getChildName() {
	    return this.childName;
	}
	
	public void setChildName(String childName) {
	    this.childName = childName;
	}
	
	@Transient
	public String getNamePrefix() {
	    return this.getId().getWpNo().replaceAll("0", "");
	}
	
	public void setNamePrefix(String namePrefix) {
	}
	
	@Transient
	public HashMap<String, BigDecimal> getInitialEst() {
	    return this.initialEst;
	}
	
	public void setInitialEst(HashMap<String, BigDecimal> initialEst) {
	    this.initialEst = initialEst;
	}
    
    public String toString(){
    	return this.id.getWpNo();
    }


}