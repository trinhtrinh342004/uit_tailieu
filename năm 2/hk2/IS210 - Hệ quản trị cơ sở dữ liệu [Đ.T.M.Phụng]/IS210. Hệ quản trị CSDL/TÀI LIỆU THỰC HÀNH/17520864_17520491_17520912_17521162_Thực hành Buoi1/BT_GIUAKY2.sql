CREATE TABLE EMPLOYEE (
    EmployeeID number primary key,
    EmployeeFirstName varchar2(15),
    EmployeeLastName varchar2(20),
    EmployeeHireDate date,
    EmployeeStatus varchar2(1),
    SupervisorID number,
    SocialSecurityNumber varchar(9)
);
alter table Employee
add constraint fk_SupervisorID_Employee FOREIGN Key (SupervisorID)
    references Employee(EmployeeID);

CREATE TABLE PROJECTS (
    ProjectID number primary key,
    ProjectName varchar2(30),
    ProjectStartDate date,
    ProjectDescription varchar2(100),
    ProjectDetail varchar2(500),
    ProjectCompletedOn date,
    ProjectManager number
);
alter table Projects
add constraint fk_projectManager_Projects FOREIGN KEY (ProjectManager)
    references EMPLOYEE (EmployeeID);
    
create table Project_Modules (
    ModuleID number primary key,
    ProjectID number,
    EmployeeID number,  
    ProjectModuleDate date,
    ProjectModuleCompledOn date,
    ProjectModuleDescription varchar2(100)
);
alter table Project_Modules 
add (constraint fk_projectID_Project_Modules FOREIGN KEY (ProjectID)
    references Projects (ProjectID),
    constraint fk_EMPID_Project_Modules FOREIGN KEY (EmployeeID)
    references EMPLOYEE (EmployeeID));

create table Work_Done(
    WorkDoneID number primary key,
    ModuleID number,
    WorkDoneDate date,
    WorkDoneDescription varchar2(100),
    WorkDoneStatus varchar2(1)
);
--Mot nhan vien co the bao cao cong viec cua nguoi khac
alter table Work_Done 
add (constraint fk_moduleID_Work_Done FOREIGN KEY (ModuleID)
    references Project_Modules (ModuleID));
    
alter table Work_Done
add EmployeeID number;

alter table Work_Done
add constraint fk_EMPID_Work_Done FOREIGN Key (EmployeeID)
    references Employee(EmployeeID);
    
alter SESSION set NLS_DATE_FORMAT = 'DD-MM-YYYY';
    
insert into Employee values(1,'EMPF name 1','EMPL name 1', '01/01/2000', '0', NULL, 'SSN 1');
insert into Employee values(2,'EMPF name 2','EMPL name 2', '01/01/2000', '0', NULL, 'SSN 2');
insert into Employee values(3,'EMPF name 3','EMPL name 3', '01/01/2000', '0', NULL, 'SSN 3');
insert into Employee values(4,'EMPF name 4','EMPL name 4', '01/01/2000', '0', NULL, 'SSN 4');
insert into Employee values(5,'EMPF name 5','EMPL name 5', '01/01/2000', '0', NULL, 'SSN 5');

insert into SUPERVISOR values(1);
insert into SUPERVISOR values(3);
insert into SUPERVISOR values(2);

insert into Projects values (1, 'project name 1', '25/12/2018', NULL, NULL, '05/03/2019', 2 );
insert into Projects values (2, 'project name 2', '11/11/2017', NULL, NULL, '05/03/2018', 1 );
insert into Projects values (3, 'project name 3', '14/03/2019', NULL, NULL, '15/06/2019', 3 );


insert into Project_Modules values (1,1,2,'10/01/2019','09/01/2019',NULL);
insert into Project_Modules values (2,1,3,'10/01/2019','09/01/2019',NULL);
insert into Project_Modules values (3,1,4,'10/01/2019','09/01/2019',NULL);
insert into Project_Modules values (4,1,5,'10/01/2019','09/01/2019',NULL);

insert into Project_Modules values (5,2,1,'12/12/2017','15/12/2017',NULL);
insert into Project_Modules values (6,2,4,'12/12/2017','15/12/2017',NULL);
insert into Project_Modules values (7,2,5,'12/12/2017','15/12/2017',NULL);

insert into Project_Modules values (8,3,4,'22/04/2019','20/04/2019',NULL);

insert into Project_Modules values (9,3,2,'22/04/2019','20/04/2019',NULL);
insert into Project_Modules values (10,3,2,'22/04/2019','20/04/2019',NULL);
insert into Project_Modules values (11,3,2,'22/04/2019','20/04/2019',NULL);
insert into Project_Modules values (12,3,2,'22/04/2019','20/04/2019',NULL);
select * from Project_Modules;
select * from Work_done;
delete from Work_done;
insert into Work_done values(1,1,'30/12/2019',NULL,0,2);
insert into Work_done values(2,1,'30/12/2019',NULL,0,2);
insert into Work_done values(3,2,'30/12/2019',NULL,0,3);
insert into Work_done values(4,3,'30/12/2019',NULL,0,4);
insert into Work_done values(5,4,'30/12/2019',NULL,0,5);

insert into Work_done values(6,5,'15/11/2017',NULL,0,1);
insert into Work_done values(7,6,'15/11/2017',NULL,0,4);
insert into Work_done values(8,7,'15/11/2017',NULL,0,5);

insert into Work_done values(9,8,'18/03/2019',NULL,0,4);
insert into Work_done values(10,8,'18/03/2019',NULL,0,2);
insert into Work_done values(11,9,'18/03/2019',NULL,0,2);
insert into Work_done values(12,9,'18/03/2019',NULL,0,3);
insert into Work_done values(13,10,'18/03/2019',NULL,0,2);
insert into Work_done values(14,11,NULL,NULL,0,2);
insert into Work_done values(15,12,NULL,NULL,0,2);
insert into Work_done values(16,12,NULL,NULL,0,2);

insert into Work_done values(17,2,'30/12/2019',NULL,0,2);
insert into Work_done values(18,5,'15/11/2017',NULL,0,3);
insert into Work_done values(19,11,NULL,NULL,0,2);
select * from Work_done;
delete from Work_done where WORKDONEID = 18;
/*
select * from Work_done where MODULEID in (select moduleID
                                            from PROJECT_MODULES PM, Projects P
                                            where P.PROJECTID = PM.PROJECTID and P.PROJECTID = 1);
                                            
select * from Work_done where MODULEID in (select moduleID
                      from PROJECT_MODULES PM, Projects P
                      where P.PROJECTID = PM.PROJECTID and 
                      (sysdate - P.PROJECTSTARTDATE) >= 90);
delete from Work_done where MODULEID in (select moduleID
                                            from PROJECT_MODULES, Projects
                                            where Projects.PROJECTID = PROJECT_MODULES.PROJECTID and Projects.PROJECTID = 1);
select * from Project_Modules where PROJECTID = 1;
delete from Project_Modules where PROJECTID = 1;
select * from Projects where ProjectID = 1;
delete from Projects where ProjectID = 1;
*/
--cau b
create or replace PROCEDURE removeProjects
as
  countRow_Projects number;
  countRow_Project_Modules number;
  countRow_Work_done number;
begin
    select count(*)
    into countRow_Work_done
    from Work_done
    where MODULEID in (select moduleID
                      from PROJECT_MODULES PM, Projects P
                      where P.PROJECTID = PM.PROJECTID and 
                      (sysdate - P.PROJECTCOMPLETEDON) >= 90 );
    DBMS_OUTPUT.PUT_LINE(countRow_Work_done);
    select count(*)
    into countRow_Project_Modules
    from Project_Modules
    where ProjectID in (select ProjectID
                        from Projects
                        where (sysdate - PROJECTCOMPLETEDON) >= 90);
    DBMS_OUTPUT.PUT_LINE(countRow_Project_Modules);
    select count(*)
    into countRow_Projects
    from Projects
    where (sysdate - PROJECTCOMPLETEDON) >= 90;
    DBMS_OUTPUT.PUT_LINE(countRow_Projects);
    --delete
    delete
    from Work_done
    where MODULEID in (select moduleID
                      from PROJECT_MODULES PM, Projects P
                      where P.PROJECTID = PM.PROJECTID and 
                      (sysdate - P.PROJECTCOMPLETEDON) >= 90 );
    delete
    from Project_Modules
    where ProjectID in (select ProjectID
                        from Projects
                        where (sysdate - PROJECTCOMPLETEDON) >= 90);
                        
    delete
    from Projects
    where (sysdate - PROJECTCOMPLETEDON) >= 90;
    
end;
select *
from Work_done
where MODULEID in (select moduleID
                      from PROJECT_MODULES PM, Projects P
                      where P.PROJECTID = PM.PROJECTID and 
                      (sysdate - P.PROJECTCOMPLETEDON) >= 90 );

DECLARE
begin
    removeProjects;
end;


select * from project_modules;
SET SERVEROUTPUT ON;
--cau c
create or replace procedure printModules (EMP_ID IN Employee.employeeID%TYPE)
as
  cur_ID Project_Modules.ModuleID%TYPE;
  module Project_Modules%ROWTYPE;
  CURSOR cur is select ModuleID
                from PROJECT_MODULES
                where employeeID = EMP_ID;
begin
    OPEN cur;
    LOOP
      fetch cur into cur_ID;
      EXIT WHEN cur%NOTFOUND;
      select *
      into module
      from PROJECT_MODULES PM
      where PM.ModuleID = cur_ID;
      DBMS_OUTPUT.PUT_LINE('moduleID: ' || module.ModuleID || ', ProjectID: ' || 
      module.ProjectID || ', EmployeeID: ' || module.EmployeeID || ', ProjectModuleDate: ' || module.ProjectModuleDate ||
      ', ProjectModuleCompledOn: ' || module.ProjectModuleCompledOn || ', ProjectModuleDescription: ' || module.ProjectModuleDescription );
    END LOOP;
end;
set serveroutput on;
declare
begin
    printModules(2);
end;
select moduleID
from PROJECT_MODULES
where employeeID = 2;
select * from Project_Modules;

--cau e
set define off;
create or replace trigger check_Project_Modules AFTER insert or update 
of ProjectModuleDate, ProjectModuleCompledOn on Project_Modules
For each row
declare
      startDate PROJECTS.PROJECTSTARTDATE%TYPE;
      completedOn Projects.Projectcompletedon%TYPE;
begin
    select PROJECTSTARTDATE,Projectcompletedon
    into startDate, completedOn
    from Projects P
    where P.ProjectID = :NEW.ProjectID;
    
    if(:NEW.ProjectModuleDate < startDate OR :NEW.ProjectModuleCompledOn > completedOn)
    then
        DBMS_OUTPUT.PUT_LINE('LOI !!!');
        RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
    end if;
end;
--test
insert into Project_Modules values(13,3,2,'15/03/2019','20/03/2019',NULL);
select * from Projects
select * from PROJECT_MODULES;
update PROJECT_MODULES
set PROJECTMODULECOMPLEDON = '21/04/2019'
WHERE moduleID = 8;

set define off;
create or replace trigger check_Projects AFTER update of PROJECTSTARTDATE, Projectcompletedon on Projects
For each row
declare
    PMDate Project_Modules.ProjectModuleDate%TYPE;
    PMCompledOn Project_Modules.ProjectModuleCompledOn%TYPE;
    cur_PMID Project_Modules.ModuleID%TYPE;
    Cursor cur is select ModuleID
                  from Project_Modules
                  where ProjectID = :New.ProjectID;
begin
    OPEN cur;
    LOOP
        fetch cur into cur_PMID;
        EXIT WHEN cur%NOTFOUND;
        select ProjectModuleDate, ProjectModuleCompledOn
        into PMDate, PMCompledOn
        from Project_Modules
        where MODULEID = cur_PMID;
        
        if(PMDate < :NEW.PROJECTSTARTDATE OR PMCompledOn  > :NEW.Projectcompletedon)
        then
          DBMS_OUTPUT.PUT_LINE('LOI: Projects !!!');
          RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
        end if;
        
    END LOOP;
end;

update PROJECTS
set PROJECTCOMPLETEDON = '15/5/2019'
where ProjectID = 3;
select * from PROJECT_MODULES

update PROJECT_MODULES
set PROJECTMODULECOMPLEDON = '30/5/2019'
where MODULEID = 10;
select * from Projects;
select * from Project_Modules;
select ModuleID
from Project_Modules 
where ProjectID = 3;

--cau d
create or replace type row_Work_Done as object (
    WorkDoneID number,
    ModuleID number,
    WorkDoneDate date,
    WorkDoneDescription varchar2(100),
    WorkDoneStatus varchar2(1),
    ReporterID number
);
create or replace type w_table as table of row_Work_Done;
create or replace function workInfo(EMP_ID IN Employee.employeeID%TYPE)
RETURN w_table
AS
    work_info w_table;
BEGIN
    select row_Work_Done(WorkdoneID,w.ModuleID,w.WorkDoneDate,w.WorkDoneDescription,w.WorkDoneStatus,w.EmployeeID)
    BULK COLLECT INTO work_info
    FROM Work_done W, project_modules PM
    where W.moduleID = PM.moduleID and W.employeeID = EMP_ID and PM.employeeID <> W.employeeID;
    
    RETURN work_info;
END workInfo;

select * from table(workInfo(2));


select WorkdoneID
from Work_done W, project_modules PM
where W.moduleID = PM.moduleID and W.employeeID = 2 and PM.employeeID <> W.employeeID;

select * from Work_done;