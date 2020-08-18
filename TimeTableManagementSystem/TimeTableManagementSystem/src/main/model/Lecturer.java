package main.model;

public class Lecturer {
    int empId;
    String empName;
    String Faculty;
    String department;
    String center;
    String designation;
    String building;
    int level;
    String rank;


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Lecturer(int empId, String empName, String faculty, String department, String center, String designation, String building, int level, String rank) {
        this.empId = empId;
        this.empName = empName;
        Faculty = faculty;
        this.department = department;
        this.center = center;
        this.designation = designation;
        this.building = building;
        this.level = level;
        this.rank = rank;
    }

    public Lecturer() {
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
