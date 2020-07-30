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




