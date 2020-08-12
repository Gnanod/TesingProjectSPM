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
  FOREIGN KEY (programmeid) REFERENCES programme(programmeid),
  FOREIGN KEY (semid) REFERENCES academicYearAndSemester(id)
);

create table subgroup(
  id int not null auto_increment,
  subgroupid varchar(100) not null,
  subgroupnumber int ,
  maingroupid int,
  constraint primary key(id),
  FOREIGN KEY (maingroupid) REFERENCES maingroup(id)
);

create table building(
	bid int not null auto_increment,
	building varchar(100) not null,
	center varchar(100) not null,
	constraint primary key(bid)
);

create table room(
	rid int not null auto_increment,
	buildingid int not null,
	room varchar(100) not null,
	capacity int not null,
	constraint primary key(rid),
	FOREIGN KEY (buildingid) REFERENCES building(bid)
);



create table building(
	bid int not null auto_increment,
	building varchar(100) not null,
	center varchar(100) not null,
	constraint primary key(bid)
);

create table room(
	rid int not null auto_increment,
	buildingid int not null,
	room varchar(100) not null,
	capacity int not null,
	constraint primary key(rid),
	FOREIGN KEY (buildingid) REFERENCES building(bid)
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
center VARCHAR(50),
buildingId int ,
level INT(1) NOT NULL ,
ranks VARCHAR(8) NOT NULL,
FOREIGN KEY (buildingId) REFERENCES building(bid),
FOREIGN KEY (departmentId) REFERENCES department(dId)
);


