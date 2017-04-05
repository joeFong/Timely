package model;
// Generated 15-Feb-2017 2:38:53 PM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EmpwpId generated by hbm2java
 */
@SuppressWarnings("serial")
@Embeddable
public class EmpwpId implements java.io.Serializable {

    private int ewEmpId;
    private int ewProjNo;
    private String ewWpNo;

    public EmpwpId() {
    }

    public EmpwpId(int ewEmpId, int ewProjNo, String ewWpNo) {
        this.ewEmpId = ewEmpId;
        this.ewProjNo = ewProjNo;
        this.ewWpNo = ewWpNo;
    }

    @Column(name = "ewEmpID", nullable = false)
    public int getEwEmpId() {
        return this.ewEmpId;
    }

    public void setEwEmpId(int ewEmpId) {
        this.ewEmpId = ewEmpId;
    }

    @Column(name = "ewProjNo", nullable = false)
    public int getEwProjNo() {
        return this.ewProjNo;
    }

    public void setEwProjNo(int ewProjNo) {
        this.ewProjNo = ewProjNo;
    }

    @Column(name = "ewWpNo", nullable = false, length = 8)
    public String getEwWpNo() {
        return this.ewWpNo;
    }

    public void setEwWpNo(String ewWpNo) {
        this.ewWpNo = ewWpNo;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof EmpwpId))
            return false;
        EmpwpId castOther = (EmpwpId) other;

        return (this.getEwEmpId() == castOther.getEwEmpId()) && (this.getEwProjNo() == castOther.getEwProjNo())
                && ((this.getEwWpNo() == castOther.getEwWpNo()) || (this.getEwWpNo() != null
                        && castOther.getEwWpNo() != null && this.getEwWpNo().equals(castOther.getEwWpNo())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getEwEmpId();
        result = 37 * result + this.getEwProjNo();
        result = 37 * result + (getEwWpNo() == null ? 0 : this.getEwWpNo().hashCode());
        return result;
    }

}
