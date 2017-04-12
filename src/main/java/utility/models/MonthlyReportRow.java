package utility.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import manager.TsrowManager;
import model.Tsrow;
import model.Workpack;
import model.Wplab;
import model.Wpstarep;

@SuppressWarnings("serial")
public class MonthlyReportRow implements Serializable, Comparable<MonthlyReportRow> {

    /**
     * Total Hours Budgeted.
     */
    BigDecimal budgetTotalHours;

    /**
     * Total Costs Budgeted.
     */
    BigDecimal budgetTotalCosts;

    /**
     * Total Hours Currently Completed.
     */
    BigDecimal curTotalHours;

    /**
     * Total Costs Currently Spent.
     */
    BigDecimal curTotalCosts;

    /**
     * Projected Total Hours.
     */
    BigDecimal projTotalHours;

    /**
     * Projected Total Costs.
     */
    BigDecimal projTotalCosts;

    /**
     * Variance between projTotalHours and budgetTotalHours.
     */
    BigDecimal varTime;

    /**
     * Variance between projTotalCosts and budgetTotalCosts.
     */
    BigDecimal varCosts;
    
    /**
     * Overtime worked.
     */
    BigDecimal overtimeHrs;

    /**
     * The {@link Workpack} this report is for.
     */
    Workpack workpack;

    /**
     * Visited flag.
     */
    int visited;

    /**
     * If report is an aggregate report.
     */
    public boolean aggregate;

    public MonthlyReportRow() {
        budgetTotalHours = BigDecimal.ZERO;
        budgetTotalCosts = BigDecimal.ZERO;
        curTotalHours = BigDecimal.ZERO;
        curTotalCosts = BigDecimal.ZERO;
        projTotalHours = BigDecimal.ZERO;
        projTotalCosts = BigDecimal.ZERO;
        varTime = BigDecimal.ZERO;
        varCosts = BigDecimal.ZERO;
        overtimeHrs = BigDecimal.ZERO;
        visited = 0;
    }

    /**
     * Creates a Monthly Report.
     * 
     * @param tsrows
     *            List of Tsrows
     * @param wplabs
     *            {@link Wplab}'s of the {@link Workpack} to generate the report
     *            for.
     * @param report
     *            {@link Wpstarep} of the {@link Workpack} for the week.
     * @param rateMap
     *            Map of Labour Grades and their rates.
     */
    public MonthlyReportRow(Workpack workpack, List<Tsrow> tsrows, Set<Wplab> wplabs, Wpstarep report,
            HashMap<String, BigDecimal> rateMap) {
        this.workpack = workpack;
        budgetTotalHours = BigDecimal.ZERO;
        budgetTotalCosts = BigDecimal.ZERO;
        curTotalHours = BigDecimal.ZERO;
        curTotalCosts = BigDecimal.ZERO;
        projTotalHours = BigDecimal.ZERO;
        projTotalCosts = BigDecimal.ZERO;
        varTime = BigDecimal.ZERO;
        varCosts = BigDecimal.ZERO;
        overtimeHrs = BigDecimal.ZERO;
        visited = 0;
        aggregate = false;
        
        for (Tsrow t : tsrows) {
            BigDecimal rate = t.getTimesheet().getTsPayGrade() == null ? 
                    t.getTimesheet().getEmployee().getEmpLabGrd().getLgRate() :
                    t.getTimesheet().getTsPayGrade().getLgRate();
            curTotalCosts = curTotalCosts.add(t.getTotal().multiply(rate));
            curTotalHours = curTotalHours.add(t.getTotal());
            overtimeHrs = overtimeHrs.add(t.getTsrOt() == null ? BigDecimal.ZERO : t.getTsrOt());
        }

        for (Wplab w : wplabs) {
            BigDecimal op1 = w.getWlPlanHrs();
            BigDecimal op2 = rateMap.get(w.getId().getWlLgId());
            budgetTotalHours = budgetTotalHours.add(w.getWlPlanHrs());
            budgetTotalCosts = budgetTotalCosts.add(op1.multiply(op2));
        }

        if (report != null) {
            String fields = report.getWsrEstDes();
            String[] rows = fields.split(",");

            // The list of "labour grades : hours" is stored as a single String
            // in the database,
            // this loop parses the String.
            for (String s : rows) {
                String[] columns = s.split(":");
                BigDecimal op1 = new BigDecimal(Double.parseDouble(columns[1]) * 8);
                BigDecimal op2 = rateMap.get(columns[0]);
                projTotalCosts = projTotalCosts.add(op1.multiply(op2));
                projTotalHours = projTotalHours.add(op1);
            }

            projTotalCosts = projTotalCosts.add(curTotalCosts);
            projTotalHours = projTotalHours.add(curTotalHours);
        } else {
            projTotalCosts = null;
            projTotalHours = null;
        }
        
        if (!workpack.getCharged()) {
            projTotalCosts = budgetTotalCosts;
            projTotalHours = budgetTotalHours;
        }

        if (projTotalCosts != null && projTotalHours != null) {
            if (budgetTotalCosts.equals(BigDecimal.ZERO)) {
                varCosts = new BigDecimal(1);
            } else {                
                varCosts = ((projTotalCosts.subtract(budgetTotalCosts)).divide(budgetTotalCosts, 2,
                        RoundingMode.HALF_EVEN));
            }
            
            if (budgetTotalHours.equals(BigDecimal.ZERO)) {
                varTime = new BigDecimal(1);
            } else {                
                varTime = ((projTotalHours.subtract(budgetTotalHours)).divide(budgetTotalHours, 2, RoundingMode.HALF_EVEN));
            }
        } else {
            varCosts = null;
            varTime = null;
        }
    }

    public Workpack getWorkpack() {
        return this.workpack;
    }

    public void setWorkpack(Workpack workpack) {
        this.workpack = workpack;
    }

    public BigDecimal getBudgetTotalHours() {
        return budgetTotalHours;
    }

    public void setBudgetTotalHours(BigDecimal budgetTotalHours) {
        this.budgetTotalHours = budgetTotalHours;
    }

    public BigDecimal getBudgetTotalDays() {
        return this.getBudgetTotalHours().divide(new BigDecimal(8));
    }

    public void setBudgetTotalDays(BigDecimal budgetTotalDays) {
        this.setBudgetTotalHours(budgetTotalDays.multiply(new BigDecimal(8)));
    }

    public BigDecimal getBudgetTotalCosts() {
        return budgetTotalCosts;
    }

    public void setBudgetTotalCosts(BigDecimal budgetTotalCosts) {
        this.budgetTotalCosts = budgetTotalCosts;
    }

    public BigDecimal getCurTotalHours() {
        return curTotalHours;
    }

    public void setCurTotalHours(BigDecimal curTotalHours) {
        this.curTotalHours = curTotalHours;
    }

    public BigDecimal getCurTotalDays() {
        return this.getCurTotalHours().divide(new BigDecimal(8));
    }

    public void setCurTotalDays(BigDecimal curTotalDays) {
        this.setCurTotalHours(curTotalDays.multiply(new BigDecimal(8)));
    }

    public BigDecimal getCurTotalCosts() {
        return curTotalCosts;
    }

    public void setCurTotalCosts(BigDecimal curTotalCosts) {
        this.curTotalCosts = curTotalCosts;
    }

    public BigDecimal getProjTotalHours() {
        return projTotalHours;
    }

    public void setProjTotalHours(BigDecimal projTotalHours) {
        this.projTotalHours = projTotalHours;
    }

    public BigDecimal getProjTotalDays() {
        return this.getProjTotalHours() == null ? null : this.getProjTotalHours().divide(new BigDecimal(8));
    }

    public void setProjTotalDays(BigDecimal projTotalDays) {
        this.setProjTotalHours(projTotalDays.multiply(new BigDecimal(8)));
    }

    public BigDecimal getProjTotalCosts() {
        return projTotalCosts;
    }

    public void setProjTotalCosts(BigDecimal projTotalCosts) {
        this.projTotalCosts = projTotalCosts;
    }

    public BigDecimal getVarTime() {
        return varTime;
    }

    public void setVarTime(BigDecimal varTime) {
        this.varTime = varTime;
    }

    public BigDecimal getVarCosts() {
        return varCosts;
    }

    public void setVarCosts(BigDecimal varCosts) {
        this.varCosts = varCosts;
    }

    public BigDecimal getOvertimeHrs() {
        return overtimeHrs;
    }

    public void setOvertimeHrs(BigDecimal overtimeHrs) {
        this.overtimeHrs = overtimeHrs;
    }
    
    public BigDecimal getOvertimeDays() {
        return this.overtimeHrs.divide(new BigDecimal(8));
    }

    public int getVisited() {
        return this.visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public boolean getAggregate() {
        return this.aggregate;
    }

    public void setAggregate(boolean aggregate) {
        this.aggregate = aggregate;
    }

    public static MonthlyReportRow generateAggregate(Workpack workpack, List<MonthlyReportRow> reports) {
        MonthlyReportRow report = new MonthlyReportRow();

        for (MonthlyReportRow m : reports) {
            report.setBudgetTotalCosts(report.getBudgetTotalCosts().add(m.getBudgetTotalCosts()));
            report.setBudgetTotalHours(report.getBudgetTotalHours().add(m.getBudgetTotalHours()));
            report.setCurTotalCosts(report.getCurTotalCosts().add(m.getCurTotalCosts()));
            report.setCurTotalHours(report.getCurTotalHours().add(m.getCurTotalHours()));
            report.setOvertimeHrs(report.getOvertimeHrs().add(m.getOvertimeHrs()));

            if (m.getProjTotalCosts() == null || m.getProjTotalHours() == null) {
                report.setProjTotalCosts(null);
                report.setProjTotalHours(null);
            } else {
                if (report.getProjTotalCosts() == null) {
                    report.setProjTotalCosts(BigDecimal.ZERO);
                }
                if (report.getProjTotalHours() == null) {
                    report.setProjTotalHours(BigDecimal.ZERO);
                }
                report.setProjTotalCosts(report.getProjTotalCosts().add(m.getProjTotalCosts()));
                report.setProjTotalHours(report.getProjTotalHours().add(m.getProjTotalHours()));
            }
        }

        if (report.getProjTotalCosts() != null && report.getProjTotalHours() != null) {
            if (report.getProjTotalCosts().equals(BigDecimal.ZERO)) {
                report.setVarCosts(new BigDecimal(1));
            } else {
                report.setVarCosts(((report.getProjTotalCosts().subtract(report.getBudgetTotalCosts()))
                        .divide(report.getBudgetTotalCosts(), 2, RoundingMode.HALF_EVEN)));                
            }
            if (report.getProjTotalHours().equals(BigDecimal.ZERO)) {
                report.setVarTime(new BigDecimal(1));
            } else {                
                report.setVarTime(((report.getProjTotalHours().subtract(report.getBudgetTotalHours()))
                        .divide(report.getBudgetTotalHours(), 2, RoundingMode.HALF_EVEN)));
            }
        } else {
            report.setVarCosts(null);
            report.setVarTime(null);
        }

        report.setWorkpack(workpack);
        report.setAggregate(true);

        return report;
    }

    @Override
    public int compareTo(MonthlyReportRow o) {
        return this.getWorkpack().getId().getWpNo().compareTo(o.getWorkpack().getId().getWpNo());
    }

}
