package model;

import java.sql.Date;

public class Payroll {
    private int payrollId;
    private int empId;
    private double baseSalary;
    private double bonus;
    private double deductions;
    private Date payDate;

    public Payroll() {}
    public Payroll(int payrollId,int empId,double baseSalary,double bonus,double deductions,Date payDate){
        this.payrollId=payrollId; this.empId=empId; this.baseSalary=baseSalary;
        this.bonus=bonus; this.deductions=deductions; this.payDate=payDate;
    }

    public int getPayrollId() { return payrollId; }
    public void setPayrollId(int payrollId) { this.payrollId = payrollId; }
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { this.bonus = bonus; }
    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }
    public Date getPayDate() { return payDate; }
    public void setPayDate(Date payDate) { this.payDate = payDate; }
}
