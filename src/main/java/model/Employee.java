package model;
// Generated 15-Feb-2017 2:38:53 PM by Hibernate Tools 3.5.0.Final


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name="Employee")
public class Employee  implements java.io.Serializable {


     private Integer empId;
     private String empPw;
     private String empFnm;
     private String empLnm;
     private String empDept;
     private String empSig;
     private BigDecimal empFlxTm;
     private Labgrd empLabGrd;
     //private String empLabGrd;
     private Integer empSupId;
     private short empDel;
     private Date empInsDt;
     private Date empUpDt;
     
     private Set<Project> projects;
     
     @ManyToMany(fetch=FetchType.EAGER)
     @JoinTable(name="Empproj",
    		 joinColumns={@JoinColumn(name="epEmpId", referencedColumnName="empID")},
    		 inverseJoinColumns={@JoinColumn(name="epProjNo", referencedColumnName="projNo")})
     public Set<Project> getProjects() {
    	 return projects;
     }
     
     public void setProjects(Set<Project> projects) {
    	 this.projects = projects;
     }
     
     private Set<Title> titles;
     
     @ManyToMany(fetch=FetchType.EAGER)
     @JoinTable(name="Emptitle",
    		 joinColumns={@JoinColumn(name="etEmpID", referencedColumnName="empID")},
    		 inverseJoinColumns={@JoinColumn(name="etTitID", referencedColumnName="titID")})
     public Set<Title> getTitles() {
    	 return titles;
     }
     
     public void setTitles(Set<Title> titles) {
    	 this.titles = titles;
     }
     
     private Set<Timesheet> timesheet;

     @OneToMany(mappedBy="employee", fetch = FetchType.EAGER)
     public Set<Timesheet> getTimesheet() {
         return timesheet;
     }

     public void setTimesheet(Set<Timesheet> timesheet) {
        this.timesheet = timesheet;
     }


    public Employee() {
    }

	
    public Employee(String empPw, String empFnm, String empLnm, short empDel, Date empInsDt, Date empUpDt) {
        this.empPw = empPw;
        this.empFnm = empFnm;
        this.empLnm = empLnm;
        this.empDel = empDel;
        this.empInsDt = empInsDt;
        this.empUpDt = empUpDt;
    }
    public Employee(String empPw, String empFnm, String empLnm, String empDept, String empSig, BigDecimal empFlxTm, Labgrd empLabGrd, Integer empSupId, short empDel, Date empInsDt, Date empUpDt) {
       this.empPw = empPw;
       this.empFnm = empFnm;
       this.empLnm = empLnm;
       this.empDept = empDept;
       this.empSig = empSig;
       this.empFlxTm = empFlxTm;
       this.empLabGrd = empLabGrd;
       this.empSupId = empSupId;
       this.empDel = empDel;
       this.empInsDt = empInsDt;
       this.empUpDt = empUpDt;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="empID", unique=true, nullable=false)
    public Integer getEmpId() {
        return this.empId;
    }
    
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    
    @Column(name="empPw", nullable=false, length=16)
    public String getEmpPw() {
        return this.empPw;
    }
    
    public void setEmpPw(String empPw) {
        this.empPw = empPw;
    }

    
    @Column(name="empFnm", nullable=false, length=32)
    public String getEmpFnm() {
        return this.empFnm;
    }
    
    public void setEmpFnm(String empFnm) {
        this.empFnm = empFnm;
    }

    
    @Column(name="empLnm", nullable=false, length=32)
    public String getEmpLnm() {
        return this.empLnm;
    }
    
    public void setEmpLnm(String empLnm) {
        this.empLnm = empLnm;
    }

    
    @Column(name="empDept", length=16)
    public String getEmpDept() {
        return this.empDept;
    }
    
    public void setEmpDept(String empDept) {
        this.empDept = empDept;
    }

    
    @Column(name="empSig", length=128)
    public String getEmpSig() {
        return this.empSig;
    }
    
    public void setEmpSig(String empSig) {
        this.empSig = empSig;
    }

    
    @Column(name="empFlxTm", precision=4)
    public BigDecimal getEmpFlxTm() {
        return this.empFlxTm;
    }
    
    public void setEmpFlxTm(BigDecimal empFlxTm) {
        this.empFlxTm = empFlxTm;
    }

    @ManyToOne
    @JoinColumn(name="empLabGrd")
    public Labgrd getEmpLabGrd() {
        return this.empLabGrd;
    }
    
    public void setEmpLabGrd(Labgrd empLabGrd) {
        this.empLabGrd = empLabGrd;
    }

    
    @Column(name="empSupID")
    public Integer getEmpSupId() {
        return this.empSupId;
    }
    
    public void setEmpSupId(Integer empSupId) {
        this.empSupId = empSupId;
    }

    
    @Column(name="empDel", nullable=false)
    public short getEmpDel() {
        return this.empDel;
    }
    
    public void setEmpDel(short empDel) {
        this.empDel = empDel;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="empInsDt", nullable=false, length=19)
    public Date getEmpInsDt() {
        return this.empInsDt;
    }
    
    public void setEmpInsDt(Date empInsDt) {
        this.empInsDt = empInsDt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="empUpDt", nullable=false, length=19)
    public Date getEmpUpDt() {
        return this.empUpDt;
    }
    
    public void setEmpUpDt(Date empUpDt) {
        this.empUpDt = empUpDt;
    }




}


