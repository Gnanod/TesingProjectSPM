drop database timetableManagementSystem;

create database timetableManagementSystem;
use timetableManagementSystem;

create table academicYearAndSemester(
	id int not null auto_increment,
	yearName varchar(100)not null ,
	semesterName varchar (100) not null,
	fullName varchar(100) not null,
	constraint primary key(id)
);

create table programme(
	programmeid int not null auto_increment,
	programmeName varchar(100)not null ,
	constraint primary key(programmeid)
);

create table tag(
	tagid int not null auto_increment,
	tagName varchar(100)not null ,
	constraint primary key(tagid)
);

create table maingroup(
  id int not null auto_increment,
  mgroupName varchar (100) not null,
  groupNumber int,
  groupid varchar(100) not null,
  programmeid int,
  semid int,
  constraint primary key(id),
  FOREIGN KEY (programmeid) REFERENCES programme(programmeid) ON DELETE CASCADE,
  FOREIGN KEY (semid) REFERENCES academicYearAndSemester(id) ON DELETE CASCADE
);

create table subgroup(
  id int not null auto_increment,
  subgroupid varchar(100) not null,
  subgroupnumber int ,
  maingroupid int,
  constraint primary key(id),
  FOREIGN KEY (maingroupid) REFERENCES maingroup(id)ON DELETE CASCADE
);

create table building(
	bid int not null auto_increment,
	center varchar(100) not null,
	building varchar(100) not null,
	constraint primary key(bid)
);

create table room(
	rid int not null auto_increment,
	buildingid int not null,
	room varchar(100) not null,
	capacity int not null,
	constraint primary key(rid),
	FOREIGN KEY (buildingid) REFERENCES building(bid) ON DELETE CASCADE
);



create table department(
  dId int not null auto_increment,
  departmentName  varchar (30) not null,
  constraint primary key(dId)
);

CREATE TABLE Lecturer (
  employeeId INT(6) UNSIGNED PRIMARY KEY,
  employeeName VARCHAR(30) NOT NULL,
  faculty VARCHAR(30),
  departmentId int,
  designation VARCHAR(100) NOT NULL,
  center VARCHAR(50),
  buildingId int ,
  level INT(1) NOT NULL ,
  ranks VARCHAR(8) NOT NULL,
  FOREIGN KEY (buildingId) REFERENCES building(bid) ON DELETE CASCADE,
  FOREIGN KEY (departmentId) REFERENCES department(dId) ON DELETE CASCADE
);
CREATE TABLE Subject (
  subId VARCHAR(10)  PRIMARY KEY,
  subName VARCHAR(30) NOT NULL,
  offeredYearSemId INT NOT NULL,
  noLecHrs INT NOT NULL,
  noTutHrs INT NOT NULL,
  noEvalHrs INT NOT NULL,
  FOREIGN KEY (offeredYearSemId) REFERENCES academicYearAndSemester(id) ON DELETE CASCADE
);

create table WorkingDaysMain(
  workingId  int not null auto_increment,
  type varchar (30) NOT NULL,
  noOfDays int NOT null,
  constraint primary key(workingId)
);

create table WorkingDaysSub(
  subId int not null auto_increment,
  workingId int not null,
  workingday varchar (30) NOT NULL ,
  FOREIGN KEY (workingId) REFERENCES WorkingDaysMain(workingId) ON DELETE CASCADE,
  constraint primary key(subId)
);

create table workingHoursPerDay(
     whpId int not null auto_increment,
     workingTime varchar(20),
     timeSlot varchar (20),
     constraint primary key (whpId)
);

create table notAvailableLectures(
    id int not null auto_increment,
    toTime varchar(20),
    fromTime varchar(20),
    subgroupId int,
    mainGroupId int,
    FOREIGN KEY (subgroupId) REFERENCES subgroup(id) ON DELETE CASCADE,
    FOREIGN KEY (mainGroupId) REFERENCES maingroup(id) ON DELETE CASCADE,
    constraint primary key (id)
);