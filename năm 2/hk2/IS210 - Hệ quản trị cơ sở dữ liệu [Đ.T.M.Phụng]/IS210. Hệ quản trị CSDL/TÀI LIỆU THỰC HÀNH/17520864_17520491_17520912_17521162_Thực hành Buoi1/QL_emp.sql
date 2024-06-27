
drop table EMPLOYEE
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
DROP SEQUENCE skillNo_seq
ALTER TABLE skill ADD (
  CONSTRAINT pk_skillNo PRIMARY KEY (SkillNo));

CREATE SEQUENCE skillNo_seq START WITH 1;
DROP SEQUENCE skillNo_seq

 
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

drop SEQUENCE deptNo_seq2

--drop table department

--2
/*2.1*/
alter table EMPLOYEE 
add 
Email varchar(30) unique

INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName1','Note1');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName2','Note2');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName3','Note3');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName4','Note4');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName5','Note5');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName6','Note6');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName7','Note7');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName8','Note8');

INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill1','Note1');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill2','Note2');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill3','Note3');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill4','Note4');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill5','Note5');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill6','Note6');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill7','Note7');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill8','Note8');

INSERT INTO Employee VALUES (1,'Name1',to_date('1/1/1998','dd/mm/yyyy'),1, 3,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail1@com.vn');
INSERT INTO Employee VALUES (2,'Name2',to_date('1/1/1998','dd/mm/yyyy'),2, 1,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail2@com.vn');
INSERT INTO Employee VALUES (3,'Name3',to_date('1/1/1998','dd/mm/yyyy'),3, 3,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail3@com.vn');
INSERT INTO Employee VALUES (4,'Name4',to_date('1/1/1998','dd/mm/yyyy'),4, 1,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail4@com.vn');
INSERT INTO Employee VALUES (5,'Name5',to_date('1/1/1998','dd/mm/yyyy'),5, 1,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail5@com.vn');
INSERT INTO Employee VALUES (6,'Name6',to_date('1/1/1998','dd/mm/yyyy'),6, 1,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail6@com.vn');
INSERT INTO Employee VALUES (7,'Name7',to_date('1/1/1998','dd/mm/yyyy'),7, 3,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail7@com.vn');
INSERT INTO Employee VALUES (8,'Name8',to_date('1/1/1998','dd/mm/yyyy'),8, 3,to_date('1/1/2000','dd/mm/yyyy'),2000000,1,1,'Note1','mail8@com.vn');
alter SESSION set NLS_DATE_FORMAT = 'MM-DD-YYYY';
INSERT INTO EMP_SKILL
     VALUES  (1, 1, 1 ,'1/1/2001');
INSERT INTO EMP_SKILL
     VALUES  (1, 2, 1 ,'1/1/2002');
INSERT INTO EMP_SKILL
     VALUES  (1, 3, 1 ,'1/1/2003');
INSERT INTO EMP_SKILL
     VALUES  (2, 1, 2 ,'1/1/2004');
INSERT INTO EMP_SKILL
     VALUES  (2, 2, 1 ,'1/1/2005');
INSERT INTO EMP_SKILL
     VALUES  (3, 3, 1 ,'1/1/2005');
INSERT INTO EMP_SKILL
     VALUES  (2, 3, 1 ,'1/1/2005');
INSERT INTO EMP_SKILL
     VALUES  (4, 3, 1 ,'1/1/2005');


--cau b
CREATE OR REPLACE PROCEDURE UPDATE_LEVEL
AS
    COUNT_ROW NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO COUNT_ROW
    FROM EMPLOYEE
    WHERE EMPLOYEE.Level_employee = 1 AND (EXTRACT(YEAR FROM (SYSDATE)) - EXTRACT(YEAR FROM (STARTDATE))) >= 3;
    
    UPDATE EMPLOYEE
    SET EMPLOYEE.Level_employee = 2
    WHERE EMPLOYEE.Level_employee = 1 AND (EXTRACT(YEAR FROM (SYSDATE)) - EXTRACT(YEAR FROM (STARTDATE))) >= 3;
    DBMS_OUTPUT.PUT_LINE('SO DONG DA THAY DOI LA: '||COUNT_ROW);
END;

SET SERVEROUTPUT ON 

DECLARE
BEGIN
  UPDATE_LEVEL;
END;


-- cau c
CREATE OR REPLACE PROCEDURE PRINT_EMP_OUT (in_empNo EMPLOYEE.EmpNo%TYPE)
AS
    out_EmpName EMPLOYEE.EmpName%TYPE;
    out_EMAIL EMPLOYEE.EMAIL%TYPE;
    out_DEPTNAME DEPARTMENT.DEPTNAME%TYPE;
    
BEGIN
  SELECT EMPLOYEE.EmpName, EMPLOYEE.EMAIL, DEPARTMENT.DEPTNAME
  INTO out_EmpName,out_EMAIL,out_DEPTNAME
  FROM EMPLOYEE,DEPARTMENT
  WHERE EMPLOYEE.EMPNO = in_empNo AND EMPLOYEE.DEPTNO = DEPARTMENT.DEPTNO AND 
  EMPLOYEE.STATUS = 2;
  DBMS_OUTPUT.PUT_LINE(out_EmpName);
  DBMS_OUTPUT.PUT_LINE(out_EMAIL);
  DBMS_OUTPUT.PUT_LINE(out_DEPTNAME);
END;

DECLARE
BEGIN
  PRINT_EMP_OUT(7);
END;

--cau d 
CREATE OR REPLACE FUNCTION Emp_Tracking(in_empNo EMPLOYEE.EmpNo%TYPE)
RETURN NUMBER
AS
    out_Salary number;
BEGIN
    SELECT SALARY
    INTO out_Salary
    FROM EMPLOYEE
    WHERE EMPNO = in_empNo AND STATUS = 0;
    
    RETURN out_Salary;
END;
DECLARE
BEGIN
  DBMS_OUTPUT.PUT_LINE(Emp_Tracking(1));
END;

--cau e
set define off; --Chay cung Trigger
CREATE OR REPLACE TRIGGER check_insert_employee AFTER INSERT OR UPDATE OF Level_employee,SALARY ON EMPLOYEE
FOR EACH ROW
BEGIN

    IF(:NEW.Level_employee = 1 AND :NEW.SALARY > 10000000)
    THEN
        RAISE_APPLICATION_ERROR(-2000, 'LOI: KHONG DUOC NHAP LEVEL = 1 VA SALARY > 10.000.000');
    END IF;
END;

INSERT INTO Employee VALUES (10,'Name10',to_date('1/1/1998','dd/mm/yyyy'),8, 3,to_date('1/1/2000','dd/mm/yyyy'),15000000,1,1,'Note1','mail10@com.vn');      


--cau f CACH 1 : KHONG DUNG FUNCTION Emp_Tracking
DECLARE
    out_empNo EMPLOYEE.EmpNo%TYPE;
    out_level EMPLOYEE.LEVEL_EMPLOYEE%TYPE;
    out_startdate EMPLOYEE.STARTDATE%TYPE;
    out_salary EMPLOYEE.SALARY%TYPE;
    count_row NUMBER;
    CURSOR cur is SELECT EmpNo FROM EMPLOYEE;
BEGIN
    OPEN cur;
    LOOP
        FETCH cur into out_empNo;
        EXIT WHEN cur%NOTFOUND;
        
        SELECT LEVEL_EMPLOYEE,STARTDATE,SALARY
        INTO out_level,out_startdate,out_salary
        FROM EMPLOYEE
        WHERE EMPLOYEE.EMPNO = out_empNo;
        
        SELECT COUNT(*)
        INTO count_row
        FROM EMPLOYEE, EMP_SKILL
        WHERE EMPLOYEE.EMPNO = out_empNo AND EMPLOYEE.EMPNO = EMP_SKILL.EMPNO;
        
        IF(out_level =2 AND count_row > 1 AND (EXTRACT(YEAR FROM (SYSDATE)) - EXTRACT(YEAR FROM (out_startdate))) >= 2 )
        THEN
            UPDATE EMPLOYEE
            SET Salary = out_salary + 300000
            WHERE EMPLOYEE.EMPNO = out_empNo;
        ELSIF(out_level =3 AND count_row > 1 AND (EXTRACT(YEAR FROM (SYSDATE)) - EXTRACT(YEAR FROM (out_startdate))) >= 3 )
        THEN
            UPDATE EMPLOYEE
            SET Salary = out_salary + 500000
            WHERE EMPLOYEE.EMPNO = out_empNo;
        END IF;
    END LOOP;
END;

DECLARE
BEGIN
  UPDATE_SALARY;
END;

--cau f CACH 2 : DUNG FUNCTION Emp_Tracking
DECLARE
    out_empNo EMPLOYEE.EmpNo%TYPE;
    out_level EMPLOYEE.LEVEL_EMPLOYEE%TYPE;
    out_startdate EMPLOYEE.STARTDATE%TYPE;
    out_salary EMPLOYEE.SALARY%TYPE;
    out_status EMPLOYEE.STATUS%TYPE;
    count_row NUMBER;
    CURSOR cur is SELECT EmpNo FROM EMPLOYEE;
BEGIN
    OPEN cur;
    LOOP
        FETCH cur into out_empNo;
        EXIT WHEN cur%NOTFOUND;
        
        SELECT LEVEL_EMPLOYEE,STARTDATE,STATUS
        INTO out_level,out_startdate,out_status
        FROM EMPLOYEE
        WHERE EMPLOYEE.EMPNO = out_empNo;
        
        SELECT COUNT(*)
        INTO count_row
        FROM EMPLOYEE, EMP_SKILL
        WHERE EMPLOYEE.EMPNO = out_empNo AND EMPLOYEE.EMPNO = EMP_SKILL.EMPNO;
        
        --DBMS_OUTPUT.PUT_LINE('lUONG = '||out_salary);
        IF(out_level =2 AND count_row > 1 AND (EXTRACT(YEAR FROM (SYSDATE)) - EXTRACT(YEAR FROM (out_startdate))) >= 2 AND
        out_status = 0)
        THEN
            out_salary:= Emp_Tracking(out_empNo);
            UPDATE EMPLOYEE
            SET Salary = out_salary + 300000
            WHERE EMPLOYEE.EMPNO = out_empNo;
        ELSIF(out_level =3 AND count_row > 1 AND (EXTRACT(YEAR FROM (SYSDATE)) - EXTRACT(YEAR FROM (out_startdate))) >= 3 AND
        out_status = 0)
        THEN
            out_salary:= Emp_Tracking(out_empNo);
            UPDATE EMPLOYEE
            SET Salary = out_salary + 500000
            WHERE EMPLOYEE.EMPNO = out_empNo;
        END IF;
    END LOOP;
END;
        
    
    



