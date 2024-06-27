INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName1','Note1');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName2','Note2');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName3','Note3');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName4','Note4');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName5','Note5');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName6','Note6');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,'DeptName7','Note7');
INSERT INTO DEPARTMENT VALUES (deptNo_seq2.nextval,    'DeptName8','Note8');


INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill1','Note1');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill2','Note2');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill3','Note3');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill4','Note4');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill5','Note5');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill6','Note6');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill7','Note7');
INSERT INTO skill VALUES (skillNo_seq.nextval,'Skill8','Note8');


INSERT INTO employee VALUES (1,'Name2',to_date('1/1/1998','dd-mm-yyyy)',1, 3,'1/1/2000',2000000
           ,1,'Note1',1,'mail@com.vn');


INSERT INTO employee VALUES (2,'Name2',to_date('1/1/1998','dd-mm-yyyy'),2, 1,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail2@com.vn');


INSERT INTO employee VALUES (3,'Name3',to_date('1/1/1998','dd-mm-yyyy'),3, 3,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail3@com.vn');

INSERT INTO employee VALUES (4,'Name4',to_date('1/1/1998','dd-mm-yyyy'),4, 1,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail4@com.vn');

INSERT INTO employee VALUES (5,'Name5',to_date('1/1/1998','dd-mm-yyyy'),5, 1,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail5@com.vn');

INSERT INTO employee VALUES (6,'Name6',to_date('1/1/1998','dd-mm-yyyy'),6, 1,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail6@com.vn');

INSERT INTO employee VALUES (7,'Name7',to_date('1/1/1998','dd-mm-yyyy'),7, 3,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail7@com.vn');

INSERT INTO employee VALUES (8,'Name8',to_date('1/1/1998','dd-mm-yyyy'),8, 3,to_date('1/1/2000','dd-mm-yyyy'),2000000
           ,1,'Note1',1,'mail8@com.vn');

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