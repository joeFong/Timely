package model;
// Generated 15-Feb-2017 2:38:53 PM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Tsrow generated by hbm2java
 */
@Entity
@Table(name = "Tsrow", uniqueConstraints = @UniqueConstraint(columnNames = { "tsrEmpID", "tsrWkEnd",
        "tsrProjNo", "tsrWpNo" }))
public class Tsrow implements java.io.Serializable {

    private Long tsr_id;
    private int tsrEmpId;
    private String tsrWkEnd;
    private int tsrProjNo;
    private String tsrWpNo;
    private BigDecimal tsrSat;
    private BigDecimal tsrSun;
    private BigDecimal tsrMon;
    private BigDecimal tsrTue;
    private BigDecimal tsrWed;
    private BigDecimal tsrThu;
    private BigDecimal tsrFri;
    private String tsrNote;
    private short tsrDel;
    private Date tsrInsDt;
    private Date tsrUpDt;

    public Tsrow() {
    }

    public Tsrow(int tsrEmpId, String tsrWkEnd, int tsrProjNo, String tsrWpNo, short tsrDel, Date tsrInsDt,
            Date tsrUpDt) {
        this.tsrEmpId = tsrEmpId;
        this.tsrWkEnd = tsrWkEnd;
        this.tsrProjNo = tsrProjNo;
        this.tsrWpNo = tsrWpNo;
        this.tsrDel = tsrDel;
        this.tsrInsDt = tsrInsDt;
        this.tsrUpDt = tsrUpDt;
    }

    public Tsrow(int tsrEmpId, String tsrWkEnd, int tsrProjNo, String tsrWpNo, BigDecimal tsrSat, BigDecimal tsrSun,
            BigDecimal tsrMon, BigDecimal tsrTue, BigDecimal tsrWed, BigDecimal tsrThu, BigDecimal tsrFri,
            String tsrNote, short tsrDel, Date tsrInsDt, Date tsrUpDt) {
        this.tsrEmpId = tsrEmpId;
        this.tsrWkEnd = tsrWkEnd;
        this.tsrProjNo = tsrProjNo;
        this.tsrWpNo = tsrWpNo;
        this.tsrSat = tsrSat;
        this.tsrSun = tsrSun;
        this.tsrMon = tsrMon;
        this.tsrTue = tsrTue;
        this.tsrWed = tsrWed;
        this.tsrThu = tsrThu;
        this.tsrFri = tsrFri;
        this.tsrNote = tsrNote;
        this.tsrDel = tsrDel;
        this.tsrInsDt = tsrInsDt;
        this.tsrUpDt = tsrUpDt;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tsr_id", unique = true, nullable = false)
    public Long getId() {
        return this.tsr_id;
    }

    public void setId(Long id) {
        this.tsr_id = id;
    }

    @Column(name = "tsrEmpID", nullable = false)
    public int getTsrEmpId() {
        return this.tsrEmpId;
    }

    public void setTsrEmpId(int tsrEmpId) {
        this.tsrEmpId = tsrEmpId;
    }

    @Column(name = "tsrWkEnd", nullable = false, length = 8)
    public String getTsrWkEnd() {
        return this.tsrWkEnd;
    }

    public void setTsrWkEnd(String tsrWkEnd) {
        this.tsrWkEnd = tsrWkEnd;
    }

    @Column(name = "tsrProjNo", nullable = false)
    public int getTsrProjNo() {
        return this.tsrProjNo;
    }

    public void setTsrProjNo(int tsrProjNo) {
        this.tsrProjNo = tsrProjNo;
    }

    @Column(name = "tsrWpNo", nullable = false, length = 8)
    public String getTsrWpNo() {
        return this.tsrWpNo;
    }

    public void setTsrWpNo(String tsrWpNo) {
        this.tsrWpNo = tsrWpNo;
    }

    @Column(name = "tsrSat", precision = 4)
    public BigDecimal getTsrSat() {
        return this.tsrSat;
    }

    public void setTsrSat(BigDecimal tsrSat) {
        this.tsrSat = tsrSat;
    }

    @Column(name = "tsrSun", precision = 4)
    public BigDecimal getTsrSun() {
        return this.tsrSun;
    }

    public void setTsrSun(BigDecimal tsrSun) {
        this.tsrSun = tsrSun;
    }

    @Column(name = "tsrMon", precision = 4)
    public BigDecimal getTsrMon() {
        return this.tsrMon;
    }

    public void setTsrMon(BigDecimal tsrMon) {
        this.tsrMon = tsrMon;
    }

    @Column(name = "tsrTue", precision = 4)
    public BigDecimal getTsrTue() {
        return this.tsrTue;
    }

    public void setTsrTue(BigDecimal tsrTue) {
        this.tsrTue = tsrTue;
    }

    @Column(name = "tsrWed", precision = 4)
    public BigDecimal getTsrWed() {
        return this.tsrWed;
    }

    public void setTsrWed(BigDecimal tsrWed) {
        this.tsrWed = tsrWed;
    }

    @Column(name = "tsrThu", precision = 4)
    public BigDecimal getTsrThu() {
        return this.tsrThu;
    }

    public void setTsrThu(BigDecimal tsrThu) {
        this.tsrThu = tsrThu;
    }

    @Column(name = "tsrFri", precision = 4)
    public BigDecimal getTsrFri() {
        return this.tsrFri;
    }

    public void setTsrFri(BigDecimal tsrFri) {
        this.tsrFri = tsrFri;
    }

    @Column(name = "tsrNote")
    public String getTsrNote() {
        return this.tsrNote;
    }

    public void setTsrNote(String tsrNote) {
        this.tsrNote = tsrNote;
    }

    @Column(name = "tsrDel", nullable = false)
    public short getTsrDel() {
        return this.tsrDel;
    }

    public void setTsrDel(short tsrDel) {
        this.tsrDel = tsrDel;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tsrInsDt", nullable = false, length = 19)
    public Date getTsrInsDt() {
        return this.tsrInsDt;
    }

    public void setTsrInsDt(Date tsrInsDt) {
        this.tsrInsDt = tsrInsDt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tsrUpDt", nullable = false, length = 19)
    public Date getTsrUpDt() {
        return this.tsrUpDt;
    }

    public void setTsrUpDt(Date tsrUpDt) {
        this.tsrUpDt = tsrUpDt;
    }

}