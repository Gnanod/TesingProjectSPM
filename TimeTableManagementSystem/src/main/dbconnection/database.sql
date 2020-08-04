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




