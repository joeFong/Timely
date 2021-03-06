package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import manager.EmployeeManager;
import manager.TimesheetManager;
import manager.TsrowManager;
import manager.WorkPackageManager;
import model.Employee;
import model.Project;
import model.Timesheet;
import model.TimesheetId;
import model.Tsrow;
import model.Workpack;
import model.WorkpackId;
import utility.DateTimeUtility;

/**
 * Contains methods used by employees to interact with their timesheets.
 * @author Timely
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
@Named("Employee")
@Stateful
public class EmployeeController implements Serializable {
    /**
     * Used for accessing employee data in database (Employee table).
     */
    @Inject
    private EmployeeManager employeeManager;
    /**
     * Used for accessing workpackage data in database (WorkPack table).
     */
    @Inject
    private WorkPackageManager wpManager;
    /**
     * Used for accessing timesheet data in database (Timesheet table).
     */
    @Inject
    private TimesheetManager tManager;
    /**
     * Used for accessing timesheet row data in database (Tsrow table).
     */
    @Inject
    private TsrowManager trManager;
    /**
     * Represents employee whose information is being altered (currently-logged
     * in employee).
     * 
     * @HasGetter
     * @HasSetter
     */
    @Inject
    private Employee emp;

    /**
     * Id of the timesheet currently being viewed.
     * 
     * @HasGetter
     * @HasSetter
     */
    private TimesheetId tsId;
    /**
     * Collection of timesheet approvers.
     * 
     * @HasGetter
     * @HasSetter
     */
    private List<Employee> timesheetApprovers;
    /**
     * Collection of timesheet rows in the current timesheet.
     * 
     * @HasGetter
     * @HasSetter
     */
    private Set<Tsrow> tsrList;
    /**
     * List of all employees. Non-descriptive name.
     * 
     * @HasGetter
     */
    private List<Employee> list;
    /**
     * List of all timesheets belonging to the employee.
     * 
     * @HasGetter
     */
    private Set<Timesheet> tsList;
    /**
     * Name of Ta Approver employee.
     * 
     * @HasGetter
     */
    private Integer taApprover;

    /**
     * Represents current week's timesheet.
     * 
     * @HasGetter
     * @HasSetter
     */
    private Timesheet curTimesheet;
    /**
     * The current week's number.
     */
    private int weekNumber;

    /**
     * The amount of overtime charged to the timesheet.
     * 
     * @HasGetter
     * @HasSetter
     */
    private BigDecimal overtime;
    /**
     * The amount of flextime charged to the timesheet.
     * 
     * @HasGetter
     * @HasSetter
     */
    private BigDecimal flextime;
    /**
     * Whether or not overtime may be edited in timesheet.
     * 
     * @HasGetter
     * @HasSetter
     */
    private boolean overtimeEditable;

    /**
     * For determining whether or not button for adding a new timesheet row is
     * displayed. Returns true (button is rendered in view) if
     * @return true if button should be displayed
     */
    public boolean showAddButton() {
        try {
            return (tManager.find(getNewTsId()) != null) ? false : true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Return a timesheet id.
     * 
     * @return tsId.
     */
    public TimesheetId getTsId() {
        return tsId;
    }

    /**
     * Creates a new timesheetId based on employee id and week end.
     * 
     * @param empId
     *            employee id.
     * @param wkend
     *            weekend.
     */
    public void setTsId(Integer empId, String wkend) {
        this.tsId = new TimesheetId(empId, wkend);
    }

    /**
     * Sets the employee.
     * 
     * @param emp employee
     */
    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    /**
     * Return employee.
     * 
     * @return employee.
     */
    public Employee getEmp() {
        return emp;
    }

    /**
     * Number of columns for tracking hours worked per package. (one for each
     * day of the week plus one for the sum of hours worked in the week, plus
     * one for overtime).
     */
    private static int DAYS_IN_WEEK_AND_TOTAL = 9;
    /**
     * Total amount of hours charged per day over all work packages.
     * 
     * @HasGetter
     * @HasSetter
     */
    private BigDecimal[] dailyTotals;

    /**
     * Get daily totals.
     * @return daily totals.
     */
    public BigDecimal[] getDailyTotals() {
        dailyTotals = new BigDecimal[DAYS_IN_WEEK_AND_TOTAL];
        for (int i = 0; i < dailyTotals.length; i++) {
            dailyTotals[i] = new BigDecimal(0);
        }
        for (Tsrow row : tsrList) {
            if (row.getTsrMon() != null) {
                dailyTotals[0] = dailyTotals[0].add(new BigDecimal(row.getTsrMon().doubleValue()));
            }
            if (row.getTsrTue() != null) {
                dailyTotals[1] = dailyTotals[1].add(new BigDecimal(row.getTsrTue().doubleValue()));
            }
            if (row.getTsrWed() != null) {
                dailyTotals[2] = dailyTotals[2].add(new BigDecimal(row.getTsrWed().doubleValue()));
            }
            if (row.getTsrThu() != null) {
                dailyTotals[3] = dailyTotals[3].add(new BigDecimal(row.getTsrThu().doubleValue()));
            }
            if (row.getTsrFri() != null) {
                dailyTotals[4] = dailyTotals[4].add(new BigDecimal(row.getTsrFri().doubleValue()));
            }
            if (row.getTsrSat() != null) {
                dailyTotals[5] = dailyTotals[5].add(new BigDecimal(row.getTsrSat().doubleValue()));
            }
            if (row.getTsrSun() != null) {
                dailyTotals[6] = dailyTotals[6].add(new BigDecimal(row.getTsrSun().doubleValue()));
            }
            if (row.getTotal() != null) {
                dailyTotals[7] = dailyTotals[7].add(new BigDecimal(row.getTotal().doubleValue()));
            }
            if (row.getTsrOt() != null) {
                dailyTotals[8] = dailyTotals[8].add(new BigDecimal(row.getTsrOt().doubleValue()));
            }
        }
        return dailyTotals;
    }

    /**
     * Sets daily totals.
     * 
     * @param dailyTotals daily totals
     */
    public void setDailyTotals(BigDecimal[] dailyTotals) {
        this.dailyTotals = dailyTotals;
    }

    /**
     * Return current timesheet.
     * 
     * @return current timesheet.
     */
    public Timesheet getCurTimesheet() {
        curTimesheet = tManager.find(tsId);
        return curTimesheet;
    }

    /**
     * Set current timesheet.
     * 
     * @param curTimesheet current timesheet
     */
    public void setCurTimesheet(Timesheet curTimesheet) {
        this.curTimesheet = curTimesheet;
    }

    /**
     * Return a list of timesheets.
     * 
     * @return timesheet list
     */
    public Set<Timesheet> getTsList() {
        if (tsList == null) {
            refreshTsList();
        }
        return tsList;
    }

    /**
     * Retrieves updated list of all timesheets belonging to the employee.
     */
    public void refreshTsList() {

        try {
            tsList = employeeManager.find(getEmp().getEmpId()).getTimesheet();
        } catch (NullPointerException e) {
            tsrList = new HashSet<Tsrow>();
        }
    }

    /**
     * Rows within a timesheet.
     * 
     * @return rows in a timesheet.
     */
    public Set<Tsrow> getTsrList() {
        tsrList = refreshTsrList(tsrList, curTimesheet.getId());
        overtime = curTimesheet.getTsOverTm() == null ? null : curTimesheet.getTsOverTm();
        flextime = curTimesheet.getTsFlexTm() == null ? null : curTimesheet.getTsFlexTm();
        return tsrList;
    }

    /**
     * Retrieves current list of all timesheet rows belonging to a given
     * timesheet.
     * 
     * @param tsrList
     *            the updated timesheet row list
     * @param id
     *            ID of the timesheet whose rows are being fetched
     * @return timesheet row list
     */
    public Set<Tsrow> refreshTsrList(Set<Tsrow> tsrList, TimesheetId id) {
        int remainder = 0;

        boolean getRowsFromDb = false;

        if (tsrList != null) {
            Timesheet t = tManager.find(id);

            if (t == null) {
                return tsrList;
            }

            for (Tsrow tr : tsrList) {
                // if selected timesheet week does not match up with weeks of
                // rows in tsrList,
                // get new set of rows from the db
                if (tr.getTsrWkEnd() != null && !tr.getTsrWkEnd().equals(t.getId().getTsWkEnd())) {
                    getRowsFromDb = true;
                }
            }

            if (!getRowsFromDb) {
                return tsrList;
            }
        }

        tsrList = tManager.find(id).getTsrow();

        if (tsrList.size() < 5) {
            int size = tsrList.size();
            remainder = 5 - size;
            for (int i = 0; i < remainder; i++) {
                Tsrow row = new Tsrow();
                row.setTsrEmpId(emp.getEmpId());
                row.setTsrWkEnd(id.getTsWkEnd());
                tsrList.add(row);
            }
        }

        return tsrList;
    }

    /**
     * Returns employee with a given ID.
     * 
     * @param id
     *            ID of employee to find.
     * @return Employee employee retrieved with the given ID.
     */
    public Employee find(int id) {
        return employeeManager.find(id);
    }

    /**
     * Adds a new employee to the employee roster.
     */
    public void add() {
        employeeManager.persist(emp);
    }

    /**
     * Return a list of employees.
     * 
     * @return list of employees
     */
    public List<Employee> getList() {
        refreshList();

        return list;
    }

    /**
     * Retrieves updated list of all employees.
     */
    public void refreshList() {
        if (list == null) {            
            list = employeeManager.getAll();
        }
    }

    /**
     * Updates the list with a new one from the database.
     */
    public void resetList() {
        list = employeeManager.getAll();
    }

    /**
     * Generated editable form fields for making changes to a timesheet.
     * 
     * @return String null navigation string for refreshing the current page.
     */
    public String editAction() {
        for (Tsrow row : tsrList) {
            row.setEditable(true);
        }
        setOvertimeEditable(true);
        return null;
    }

    /**
     * Checks for duplicate rows in a timesheet.
     * 
     * @param count
     *            of same rows.
     * @param row
     *            current row in comparison
     * @return true for error, false otherwise.
     */
    public boolean checkDuplicateRows(int count, Tsrow row) {
        for (Tsrow r : tsrList) {
            if (row.equals(r)) {
                count++;
                if (count >= 2) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL, "Duplicate Rows", "Please Try Again!"));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Saves timesheet information inputted into form fields.
     * 
     * @return String navigation string that refreshes current page.
     */
    public String saveAction() {

        for (Tsrow row : tsrList) {
            int count = 0;
            row.setEditable(false);
            row.setTimesheet(getCurTimesheet());
            row.setTimesheet(curTimesheet);

            if (row.getTsrWpNo().isEmpty()) {                
                continue;
            }

            if (row.getTsrProjNo() != 0 && !row.getTsrWpNo().isEmpty()) {

                if (checkDuplicateRows(count, row)) {                    
                    return null;
                }

                if (wpManager.find(new WorkpackId(row.getTsrProjNo(), row.getTsrWpNo())) != null) {                    
                    trManager.merge(row);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Project Number and Work Package Number do not match.", "Please Try Again!"));
                    row.setTsrWpNo("");
                }
            }
        }

        setOvertimeEditable(false);
        curTimesheet.setTsOverTm(overtime);
        curTimesheet.setTsFlexTm(flextime);
        if (curTimesheet.getTsOverTm() != null || curTimesheet.getTsFlexTm() != null) {
            if ((curTimesheet.getTsTotal().doubleValue() - curTimesheet.getTsOverTm().doubleValue()
                    - curTimesheet.getTsFlexTm().doubleValue()) > 40
                    || (curTimesheet.getTsTotal().doubleValue() - curTimesheet.getTsOverTm().doubleValue()
                            - curTimesheet.getTsFlexTm().doubleValue()) < 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Too much flextime and overtime.", "Please Try Again!"));
            }
        } else {
            tManager.merge(curTimesheet);
        }

        return "timesheet";
    }

    /**
     * Adds a row to the current timesheet.
     * @return null string
     */
    public String addTsrow() {
        Tsrow row = new Tsrow();
        row.setTsrEmpId(emp.getEmpId());
        tsrList.add(row);
        return null;
    }

    /**
     * Generate current weeks number.
     * 
     * @return week number
     * @throws ParseException
     */
    public int getWeekNumber() throws ParseException {
        String input = getCurTimesheet().getId().getTsWkEnd();
        String format = "yyyyMMdd";
        DateFormat df = new SimpleDateFormat(format);
        Date date = df.parse(input);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.SATURDAY);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        weekNumber = week;
        System.out.println(weekNumber);
        return weekNumber;
    }

    /**
     * Returns a list containing IDs of all projects belonging to the employee.
     * 
     * @return List<Integer> list of project IDs
     */
    public List<Integer> projectIntegerList() {
        List<Integer> listt = new ArrayList<Integer>();
        for (Project p : emp.getProjects()) {
            listt.add(p.getProjNo());
        }
        return listt;
    }

    /**
     * List of work packages.
     * 
     * @return list of workpackages based on their work package number.
     */
    public List<String> workPackList() {
        List<String> listt = new ArrayList<String>();
        listt.add("");
        for (Workpack w : emp.getWorkpackages()) {
            // only get open WP's
            if (w.getWpStatus() != null && w.getWpStatus() != 1) {
                listt.add(w.getId().getWpNo());
            }
        }
        return listt;
    }

    /**
     * Generates a new timesheet id.
     * 
     * @return TimesheetId newly-generated timesheet id.
     */
    public TimesheetId getNewTsId() {
        DateTimeUtility dtu = new DateTimeUtility();
        return new TimesheetId(emp.getEmpId(), dtu.getEndOfWeek());
    }

    /**
     * Creates a new timesheet.
     * 
     * @return String navigation string for refreshing the current page.
     */
    public String createNewTimesheet() {

        Timesheet ts = new Timesheet();
        ts.setId(getNewTsId());
        ts.setTsDel((short) 0);
        ts.setTsSubmit((short) 0);
        ts.setTsPayGrade(emp.getEmpLabGrd());
        Set<Tsrow> tsrList = new HashSet<Tsrow>();
        tsrList = refreshTsrList(tsrList, ts.getId());
        ts.setTsrow(tsrList);
        tManager.persist(ts);
        tsList.add(ts);
        return null;
    }

    /**
     * Check if current timesheet is submitted.
     * 
     * @return true if submitted
     */
    public boolean isSubmitted() {
        return curTimesheet.getTsSubmit() == 2 || curTimesheet.getTsSubmit() == 1;
    }

    /**
     * Returns the number of approved timesheets.
     * 
     * @return count.
     */
    public int getApprovedTs() {

        int count = 0;

        if (tsList == null) {            
            return count;
        }

        for (Timesheet t : tsList) {
            if (t.getTsSubmit() == 2) {                
                count++;
            }
        }
        return count;
    }

    /**
     * Return number of rejected timesheets.
     * 
     * @return count of rejected timesheets.
     */
    public int getRejectedTs() {

        int count = 0;

        if (tsList == null) {            
            return count;
        }

        for (Timesheet t : tsList) {
            if (t.getTsSubmit() == 3) {                
                count++;
            }
        }
        return count;
    }

    /**
     * Return number of pending timesheets.
     * 
     * @return number of pending timesheets.
     */
    public int getPendingTs() {

        int count = 0;

        if (tsList == null) {            
            return count;
        }

        for (Timesheet t : tsList) {
            if (t.getTsSubmit() == 1) {                
                count++;
            }
        }
        return count;
    }

    /**
     * Current size of all timesheets for the current employee.
     * 
     * @return size of timesheet list
     */
    public int getTsSize() {
        getTsList();
        return (tsList != null) ? tsList.size() : 0;
    }

    /**
     * List of approved timesheets.
     * 
     * @return approved timesheets
     */
    public List<Timesheet> approvedTsList() {
        List<Timesheet> approvedList = new ArrayList<>();

        if (tsList == null) {            
            return approvedList;
        }

        for (Timesheet t : tsList) {
            if (t.getTsSubmit() == 2) {                
                approvedList.add(t);
            }
        }
        return approvedList;
    }

    /**
     * List of rejected timesheets.
     * 
     * @return rejected timesheets
     */
    public List<Timesheet> rejectedTsList() {
        List<Timesheet> rejectedList = new ArrayList<>();

        if (tsList == null) {            
            return rejectedList;
        }

        for (Timesheet t : tsList) {
            if (t.getTsSubmit() == 3)  {                
                rejectedList.add(t);
            }
        }
        return rejectedList;
    }

    /**
     * List of pending timesheets.
     * 
     * @return pending timesheets
     */
    public List<Timesheet> pendingTsList() {
        List<Timesheet> pendingList = new ArrayList<>();

        if (tsList == null) {            
            return pendingList;
        }

        for (Timesheet t : tsList) {
            if (t.getTsSubmit() == 1) {                
                pendingList.add(t);
            }
        }
        return pendingList;
    }

    /**
     * Saves information in a timesheet to database.
     * 
     * @return String navigation string for refreshing the current page.
     */
    public String submitTimesheet() {
        Employee approver = null;

        curTimesheet.setTsSubmit((short) 1);

        approver = employeeManager.find(getTaApprover());
        curTimesheet.setTsApprover(approver);

        tManager.merge(curTimesheet);
        tManager.flush();

        return null;
    }

    /**
     * Map of time sheet approvers.
     * 
     * @return ta approver name map
     */
    public Map<String, Integer> getTaApproverNames() {
        getTimesheetApprovers();
        Map<String, Integer> list = new LinkedHashMap<String, Integer>();
        for (Employee e : timesheetApprovers) {
            String name = e.getEmpFnm() + " " + e.getEmpLnm();
            list.put(name, e.getEmpId());
        }
        return list;
    }

    /**
     * Checks if key exists based on value.
     * 
     * @param map map
     * @param value value
     * @return te
     */
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns the name of timesheet approver.
     * 
     * @param tsApprId approver id
     * @return name
     */
    public String getTaApproverName(Integer tsApprId) {
        String fullName = "TBD";
        if (getTaApproverNames().containsValue(tsApprId)) {            
            fullName = getKeyByValue(getTaApproverNames(), tsApprId);
        }
        return fullName;
    }

    /**
     * Get Timesheet approver number.
     * 
     * @return ta approver number
     */
    public Integer getTaApprover() {
        return taApprover;
    }

    /**
     * Set Timesheet approver number.
     * 
     * @param taApprover ta approver number
     */
    public void setTaApprover(Integer taApprover) {
        this.taApprover = taApprover;
    }

    /**
     * List of timesheet approvers.
     * 
     * @return list of timesheet approvers
     */
    public List<Employee> getTimesheetApprovers() {
        timesheetApprovers = employeeManager.getTaApprovers();
        return timesheetApprovers;
    }

    /**
     * Set timesheet approvers.
     * 
     * @param timesheetApprovers timesheet approvers
     */
    public void setTimesheetApprovers(List<Employee> timesheetApprovers) {
        this.timesheetApprovers = timesheetApprovers;
    }

    /**
     * Get overtime value.
     * 
     * @return overtime
     */
    public BigDecimal getOvertime() {
        return overtime;
    }

    /**
     * Set overtime value.
     * 
     * @param overtime overtime
     */
    public void setOvertime(BigDecimal overtime) {
        this.overtime = overtime;
    }

    /**
     * Get flextime value.
     * 
     * @return flextime
     */
    public BigDecimal getFlextime() {
        return flextime;
    }

    /**
     * Set flextime value.
     * 
     * @param flextime flextime
     */
    public void setFlextime(BigDecimal flextime) {
        this.flextime = flextime;
    }

    /**
     * Edit overtime.
     * 
     * @return is overtime editable
     */
    public boolean isOvertimeEditable() {
        return overtimeEditable;
    }

    /**
     * Set editing overtime boolean.
     * 
     * @param overtimeEditable is overtime editable
     */
    public void setOvertimeEditable(boolean overtimeEditable) {
        this.overtimeEditable = overtimeEditable;
    }

}
