package model;
// Generated 15-Feb-2017 2:38:53 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Project generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Project")
public class Project implements java.io.Serializable {

    /**
     * Project number.
     */
    private Integer projNo;

    /**
     * Project name.
     */
    private String projNm;

    /**
     * Project description.
     */
    private String projDesc;

    /**
     * Project manager.
     */
    private Integer projMan;

    /**
     * Project start date.
     */
    private Date projStaDt;

    /**
     * Project end date.
     */
    private Date projEndDt;

    /**
     * Project status.
     */
    private short projStatus;

    /**
     * Project deleted flag.
     */
    private short projDel;

    /**
     * Insert date.
     */
    private Date projInsDt;

    /**
     * Update date.
     */
    private Date projUdDt;

    /**
     * Work packages in this project.
     */
    private SortedSet<Workpack> workPackages;

    /**
     * Employees in this project.
     */
    private Set<Employee> employees;

    /**
     * Default ctor.
     */
    public Project() {
    }

    /**
     * Create a project.
     * 
     * @param projNm
     *            project number
     * @param projMan
     *            project manager
     * @param projStatus
     *            project status
     * @param projDel
     *            delete flag
     * @param projInsDt
     *            insert date
     * @param projUdDt
     *            update date
     */
    public Project(String projNm, int projMan, short projStatus, short projDel, Date projInsDt, Date projUdDt) {
        this.projNm = projNm;
        this.projMan = projMan;
        this.projStatus = projStatus;
        this.projDel = projDel;
        this.projInsDt = projInsDt;
        this.projUdDt = projUdDt;
    }

    /**
     * Create a project.
     * 
     * @param projNm
     *            project number
     * @param projDesc
     *            project description
     * @param projMan
     *            project manager
     * @param projStaDt
     *            start date
     * @param projEndDt
     *            end date
     * @param projStatus
     *            status
     * @param projDel
     *            delete flag
     * @param projInsDt
     *            insert date
     * @param projUdDt
     *            update date
     */
    public Project(String projNm, String projDesc, int projMan, Date projStaDt, Date projEndDt, short projStatus,
            short projDel, Date projInsDt, Date projUdDt) {
        this.projNm = projNm;
        this.projDesc = projDesc;
        this.projMan = projMan;
        this.projStaDt = projStaDt;
        this.projEndDt = projEndDt;
        this.projStatus = projStatus;
        this.projDel = projDel;
        this.projInsDt = projInsDt;
        this.projUdDt = projUdDt;
    }

    /**
     * Get workPackages.
     * 
     * @return workPackages
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "wpProjNo", referencedColumnName = "projNo")
    @OrderBy("id.wpNo ASC")
    public SortedSet<Workpack> getWorkPackages() {
        return this.workPackages;
    }

    /**
     * Set workPackages.
     * 
     * @param workpackages
     *            workPackages
     */
    public void setWorkPackages(SortedSet<Workpack> workpackages) {
        this.workPackages = workpackages;
    }

    /**
     * Get employees.
     * 
     * @return employees
     */
    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    public Set<Employee> getEmployees() {
        return this.employees;
    }

    /**
     * Set employees.
     * 
     * @param employees
     *            employees
     */
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Get projNo.
     * 
     * @return projNo
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "projNo", unique = true, nullable = false)
    public Integer getProjNo() {
        return this.projNo;
    }

    /**
     * Set projNo.
     * 
     * @param projNo
     *            projNo
     */
    public void setProjNo(Integer projNo) {
        this.projNo = projNo;
    }

    /**
     * Get projNm.
     * 
     * @return projNm
     */
    @Column(name = "projNm", nullable = false, length = 64)
    public String getProjNm() {
        return this.projNm;
    }

    /**
     * Set projNm.
     * 
     * @param projNm
     *            projNm
     */
    public void setProjNm(String projNm) {
        this.projNm = projNm;
    }

    /**
     * Get projDesc.
     * 
     * @return projDesc
     */
    @Column(name = "projDesc")
    public String getProjDesc() {
        return this.projDesc;
    }

    /**
     * Set projDesc.
     * 
     * @param projDesc
     *            projDesc
     */
    public void setProjDesc(String projDesc) {
        this.projDesc = projDesc;
    }

    /**
     * Get projMan.
     * 
     * @return projMan
     */
    @Column(name = "projMan")
    public Integer getProjMan() {
        return this.projMan;
    }

    /**
     * Set projMan.
     * 
     * @param projMan
     *            projMan
     */
    public void setProjMan(Integer projMan) {
        this.projMan = projMan;
    }

    /**
     * Get projStaDt.
     * 
     * @return projStaDt
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "projStaDt", length = 10)
    public Date getProjStaDt() {
        return this.projStaDt;
    }

    /**
     * Set projStaDt.
     * 
     * @param projStaDt
     *            projStaDt
     */
    public void setProjStaDt(Date projStaDt) {
        this.projStaDt = projStaDt;
    }

    /**
     * Get projEndDt.
     * 
     * @return projEndDt
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "projEndDt", length = 10)
    public Date getProjEndDt() {
        return this.projEndDt;
    }

    /**
     * Set projEndDt.
     * 
     * @param projEndDt
     *            projEndDt
     */
    public void setProjEndDt(Date projEndDt) {
        this.projEndDt = projEndDt;
    }

    /**
     * Get projStatus.
     * 
     * @return projStatus
     */
    @Column(name = "projStatus", nullable = false)
    public short getProjStatus() {
        return this.projStatus;
    }

    /**
     * Set projStatus.
     * 
     * @param projStatus
     *            projStatus
     */
    public void setProjStatus(short projStatus) {
        this.projStatus = projStatus;
    }

    /**
     * Get projDel.
     * 
     * @return projDel
     */
    @Column(name = "projDel", nullable = false)
    public short getProjDel() {
        return this.projDel;
    }

    /**
     * Set projDel.
     * 
     * @param projDel
     *            projDel
     */
    public void setProjDel(short projDel) {
        this.projDel = projDel;
    }

    /**
     * Get projInsDt.
     * 
     * @return projInsDt
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "projInsDt", nullable = false, length = 19)
    public Date getProjInsDt() {
        return this.projInsDt;
    }

    /**
     * Set projInsDt.
     * 
     * @param projInsDt
     *            projInsDt
     */
    public void setProjInsDt(Date projInsDt) {
        this.projInsDt = projInsDt;
    }

    /**
     * Get projUdDt.
     * 
     * @return projUdDt
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "projUdDt", nullable = false, length = 19)
    public Date getProjUdDt() {
        return this.projUdDt;
    }

    /**
     * Set projUdDt.
     * 
     * @param projUdDt
     *            projUdDt
     */
    public void setProjUdDt(Date projUdDt) {
        this.projUdDt = projUdDt;
    }

    public String toString() {
        return this.projNo.toString();
    }

    /* =========================================== */
    public boolean equals(Object entityObject) {
        Project entity = (Project) entityObject;
        return this.getProjNo().equals(entity.getProjNo());
    }

}
