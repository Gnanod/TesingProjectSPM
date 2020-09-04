package main.constants;

public class Constants {

    public final  static String QUERY_ACADEMIC_YEAR=" create table academicYearAndSemester(" +
                                                    "id int not null auto_increment," +
                                                    "yearName varchar(100)not null ," +
                                                    "semesterName varchar (100) not null," +
                                                    "fullName varchar(100) not null," +
                                                    "constraint primary key(id)" +
                                                    ");";

    public final static String PROFESSOR ="Professor";
}
