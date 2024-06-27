
drop table skill
--1
create table employee
(
	EmpNo number primary key,
	EmpName varchar2(30),
	BirthDay date,
	DeptNo number not null,
	MgrNo number not null,
	StartDate date,
	Salary number,
	Level_employee number,
	Status number,
	Note varchar2(200),
	constraint ck_level check (Level_employee >=1 and Level_employee <=7),
	constraint ck_status check (Status in (0,1,2))
);

select*from employee
drop table skill
create table SKILL
(
	SkillNo number not null,
	SkillName varchar2(30),
	Note varchar2(200)
)

ALTER TABLE skill ADD (
  CONSTRAINT pk_skillNo PRIMARY KEY (SkillNo));

CREATE SEQUENCE skillNo_seq START WITH 1;


 
create table EMP_SKILL
(
	SkillNo number not null,
	EmpNo number not null,
	SkillLevel number ,
	RegDate date,
	constraint pk_skillNo_EmpNo primary key  (SkillNo,EmpNo),
	constraint ck_skillLevel check (SkillLevel >=1 and SkillLevel <=3),
	constraint fk_EmpNo foreign key (EmpNo) references employee(EmpNo),
	constraint fk_SkillNo foreign key (SkillNo) references skill(SkillNo)
)
drop table emp_skill
create table DEPARTMENT
(
	DeptNo number ,
	DeptName varchar2(30),
	Note varchar2(200)
)
ALTER TABLE DEPARTMENT ADD (
  CONSTRAINT pk_deptNo2 PRIMARY KEY (DeptNo));
CREATE SEQUENCE deptNo_seq2 START WITH 1;

--drop SEQUENCE deptNo_seq2

--drop table department

--2
/*2.1*/
alter table EMPLOYEE 
add 
Email varchar(30) unique


/*2.2*/
--
ALTER TABLE EMPLOYEE
MODIFY MgrNo DEFAULT 0;
ALTER TABLE EMPLOYEE
MODIFY Status DEFAULT 0;

--3
alter table employee 
add
constraint fk_deptNo foreign key (DeptNo) references department(DeptNo);
select * from emp_skill
alter table emp_skill
drop column Description

--4
alter session set nls_date_format = 'dd/MON/yyyy'





