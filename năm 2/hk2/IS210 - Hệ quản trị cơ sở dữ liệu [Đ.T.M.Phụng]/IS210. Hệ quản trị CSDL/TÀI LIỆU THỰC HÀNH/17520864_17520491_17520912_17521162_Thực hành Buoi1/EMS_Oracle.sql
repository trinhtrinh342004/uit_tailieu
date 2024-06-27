DROP TABLE EMP_SKILL
DROP TABLE SKILL
DROP TABLE EMPLOYEE
DROP TABLE DEPARTMENT

CREATE TABLE EMPLOYEE
(
	EmpNo varchar(5) not null,
	EmpName varchar2(20) not null,
	Birthday date not null,
	DeptNo number not null,
	MgrNo varchar(5) not null,
	StartDate date not null,
	salary number not null,
	Level_fixed number not null,
	Status number not null,
	Note varchar2(50),
	constraint pk_EE primary key (EmpNo)
);

CREATE TABLE DEPARTMENT
(
	DeptNo number not null,
	DeptName varchar2(20) not null,
	Note varchar2(50),
	constraint pk_DE primary key(DeptNo)
);
CREATE SEQUENCE DeptNo_seq2 START WITH 1;
CREATE TABLE SKILL
(
	SkillNo number CONSTRAINT pk_SKILL PRIMARY KEY not null,
	SkillName varchar2(20) not null,
	Note varchar2(50)
);
CREATE SEQUENCE SkillNo_seq START WITH 1;
CREATE TABLE EMP_SKILL
(
	SkillNo number constraint fk_EMP_SKILL_SKILL  REFERENCES SKILL(SkillNo) not null,
	EmpNo varchar2(5) constraint fk_EMP_SKILL_EMPLOYEE REFERENCES EMPLOYEE(EmpNo) not null,
	SkillLevel number not null,
	RegDate date not null,
	Description varchar2(50),
	CONSTRAINT pk_EMP_SKILL PRIMARY KEY (SkillNo,EmpNo)
);


----- Câu 1
ALTER TABLE EMPLOYEE
ADD CHECK (Level_fixed between 1 and 7)

ALTER TABLE EMPLOYEE
ADD CHECK (Status between 0 and 2)

ALTER TABLE EMP_SKILL
ADD CHECK (SkillLevel between 1 and 3)
----- Câu 2
/*a Thêm email*/
ALTER TABLE EMPLOYEE
ADD EMAIL VARCHAR2(50) UNIQUE
/*b ??t giá tr? m?c ??nh*/
ALTER TABLE EMPLOYEE
MODIFY MgrNo DEFAULT '0';
ALTER TABLE EMPLOYEE
MODIFY Status DEFAULT 0;
---- Câu 3
/*a*/
alter table EMPLOYEE
add constraint fk_EMPLOYEE_DEPARTMENT FOREIGN KEY (DeptNo) REFERENCES  DEPARTMENT(DeptNo)
/*b*/
ALTER TABLE EMP_SKILL
DROP COLUMN Description
---- Câu 4
/*Câu a*/
INSERT INTO DEPARTMENT VALUES(DeptNo_seq2.nextval,'Linh Trung',Null);
INSERT INTO DEPARTMENT VALUES(DeptNo_seq2.nextval,'Co Bac', Null);
INSERT INTO DEPARTMENT VALUES(DeptNo_seq2.nextval,'Linh Trung 2','Co So 2');
INSERT INTO DEPARTMENT VALUES(DeptNo_seq2.nextval,'Linh Tay',Null);
INSERT INTO DEPARTMENT VALUES(DeptNo_seq2.nextval,'Kha Van Can',Null);
select * from department;

select*from employee
INSERT INTO EMPLOYEE VALUES('NV001','Nguyen Van A',to_date('1-1-1978','dd-mm-yyyy'),1,'NV006',To_Date('3-2-2019','dd-mm-yyyy'),30000000,1,2,Null,'a@gmail.com');
INSERT INTO EMPLOYEE VALUES('NV002','Nguyen Van B',to_date('1/1/1979','dd/mm/yyyy'),3,'NV006',To_Date('3/2/2019','dd/mm/yyyy'),30000000,3,0,Null,'b@gmail.com');
INSERT INTO EMPLOYEE VALUES('NV003','Nguyen Van C',to_date('3/1/1982','dd/mm/yyyy'),2,'NV006',To_Date('3/2/2019','dd/mm/yyyy'),30000000,2,2,Null,'c@gmail.com');
INSERT INTO EMPLOYEE VALUES('NV004','Nguyen Van D',to_date('2/5/1979','dd/mm/yyyy'),3,'NV006',To_Date('3/2/2019','dd/mm/yyyy'),30000000,4,2,Null,'d@gmail.com');
INSERT INTO EMPLOYEE VALUES('NV005','Nguyen Van E',to_date('3/8/1982','dd/mm/yyyy'),4,'NV006',To_Date('3/2/2019','dd/mm/yyyy'),30000000,1,2,Null,'e@gmail.com');
select * from employee;
INSERT INTO SKILL VALUES(SkillNo_seq.nextval,'thuyet trinh',null);
INSERT INTO SKILL VALUES(SkillNo_seq.nextval,'maketing',null);
INSERT INTO SKILL VALUES(SkillNo_seq.nextval,'seller',null);
INSERT INTO SKILL VALUES(SkillNo_seq.nextval,'designer',null);
INSERT INTO SKILL VALUES(SkillNo_seq.nextval,'tester',null);
select * from skill;
INSERT INTO EMP_SKILL VALUES(1,'NV004',2,To_Date('01/03/2018','dd/mm/yyyy'));
INSERT INTO EMP_SKILL VALUES(2,'NV005',3,To_Date('01/02/2018','dd/mm/yyyy'));
INSERT INTO EMP_SKILL VALUES(3,'NV001',1,To_Date('01/05/2018','dd/mm/yyyy'));
INSERT INTO EMP_SKILL VALUES(4,'NV003',2,To_Date('01/06/2018','dd/mm/yyyy'));
INSERT INTO EMP_SKILL VALUES(1,'NV002',2,To_Date('01/09/2018','dd/mm/yyyy'));
SELECT * FROM EMP_SKILL;
/*Câu b*/
DROP VIEW EMPLOYEE_TRACKING
CREATE VIEW EMPLOYEE_TRACKING AS
SELECT EmpNo,EmpName, Level_fixed
FROM EMPLOYEE
WHERE Level_fixed >=3 and Level_fixed <=5
select * from employee_tracking