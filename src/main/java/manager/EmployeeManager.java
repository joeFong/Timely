package manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Employee;
import model.Project;

@SuppressWarnings("serial")
@Dependent
@Stateless
public class EmployeeManager implements Serializable {
    @PersistenceContext(unitName = "Timely-persistence-unit")
    EntityManager em;

    /* Who knows if this'll work.See ProjectManagerController. */
    public List<Employee> getEmployeesOnProject(int pid) {
        Query query = em.createNativeQuery("SELECT empChNo FROM Empproj WHERE projNo = ?1");
        // TypedQuery<Employee> query = em.createQuery("select s from Empproj s
        // where s.projectNo=:code", Employee.class);
        // query.setParameter("code", pid);

        query.setParameter(1, pid);
        
        @SuppressWarnings("unchecked")
        List<Employee> employees = query.getResultList();

        return (employees != null) ? employees : new ArrayList<Employee>();
    }

    public Employee find(int id) {
        Employee foundEmployee = em.find(Employee.class, id);
        
        return (foundEmployee != null) ? foundEmployee : new Employee();
    }

    public void flush() {
        em.flush();
    }

    public void persist(Employee employee) {
        em.persist(employee);
    }

    public void merge(Employee employee) {
        em.merge(employee);
    }

    public void remove(Employee employee) {
        employee = find(employee.getEmpId());
        em.remove(employee);
    }

    public List<Employee> getAll() {
        TypedQuery<Employee> query = em.createQuery("select s from Employee s", Employee.class);
        List<Employee> employees = query.getResultList();

        return (employees != null) ? employees : new ArrayList<Employee>();
    }

    /**
     * Get all employees that have not been deleted
     * 
     * @return list of employees
     */
    public List<Employee> getActiveEmps() {
        TypedQuery<Employee> query = em.createQuery("select s from Employee s where s.empDel = 0", Employee.class);
        List<Employee> employees = query.getResultList();

        return (employees != null) ? employees : new ArrayList<Employee>();
    }

    public Map<String, Employee> getActiveEmpMap() {
        Map<String, Employee> employeeMap = new TreeMap<String, Employee>();
        TypedQuery<Employee> query = em.createQuery("select s from Employee s where s.empDel = 0", Employee.class);
        List<Employee> employees = query.getResultList();
        for (Employee e : employees) {
            employeeMap.put(e.getEmpLnm(), e);
        }
        return (employeeMap != null) ? employeeMap : new HashMap<String, Employee>();
    }

    /**
     * Deletes the employee given by the parameter, by changing its empdel flag.
     * 
     * @param employee
     *            the employee to be deleted
     */
    public void delete(Employee employee) {
        employee.setEmpDel((short) 1);
    }

    /**
     * Restores the employee given by the parameter, by changing its empdel
     * flag.
     * 
     * @param employee
     *            the employee to be deleted
     */
    public void restore(Employee employee) {
        employee.setEmpDel((short) 0);
    }

    /**
     * Gets all projects the given employee is assigned to.
     * 
     * @param emp
     *            employee
     * @return list of projects
     */
    public Set<Project> getProjects(Employee emp) {
        return emp.getProjects();
    }

    public List<Employee> getEmpNotProj(Project p) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e" + ", Project AS p "
                + "WHERE p = :selectProject AND p " + "NOT MEMBER OF e.projects", Employee.class);
        query.setParameter("selectProject", p);
        List<Employee> employees = query.getResultList();
        return (employees != null) ? employees : new ArrayList<Employee>();
    }

    public List<Employee> getEmpProj(Project p) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e" + ", Project AS p "
                + "WHERE p = :selectProject AND p " + "MEMBER OF e.projects", Employee.class);
        query.setParameter("selectProject", p);
        List<Employee> employees = query.getResultList();

        return (employees != null) ? employees : new ArrayList<Employee>();
    }
}
