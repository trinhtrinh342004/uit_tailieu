REM ******************************************************************
REM   file: dropStudent.sql
REM  description: used for droping student schema objects. Note that
REM                 this script only drops the objects created for
REM                 the STUDENT related tables not the supplemental
REM                 tables.
REM  created January 30, 2000
REM  modified June 21, 2004 AR
REM ******************************************************************

PROMPT ** ******************************************************** **
PROMPT ** Continuing will cause all of your student schema objects **
PROMPT **   and data to be dropped. This may be desirable if you   **
PROMPT **   are attempting to rebuild the objects.                 **
PROMPT ** Press CTRL-C now to cancel or <RETURN> to continue.      **
PROMPT ** ******************************************************** **
PAUSE

PROMPT
PROMPT Dropping Student schema objects

REM === Drop Tables ===

DROP TABLE COURSE CASCADE CONSTRAINTS;
DROP TABLE ENROLLMENT CASCADE CONSTRAINTS;
DROP TABLE GRADE CASCADE CONSTRAINTS;
DROP TABLE GRADE_CONVERSION CASCADE CONSTRAINTS;
DROP TABLE GRADE_TYPE CASCADE CONSTRAINTS;
DROP TABLE GRADE_TYPE_WEIGHT CASCADE CONSTRAINTS;
DROP TABLE INSTRUCTOR CASCADE CONSTRAINTS;
DROP TABLE SECTION CASCADE CONSTRAINTS;
DROP TABLE STUDENT CASCADE CONSTRAINTS;
DROP TABLE ZIPCODE CASCADE CONSTRAINTS;

REM === Drop Sequences ===
DROP SEQUENCE COURSE_NO_SEQ;
DROP SEQUENCE INSTRUCTOR_ID_SEQ;
DROP SEQUENCE SECTION_ID_SEQ;
DROP SEQUENCE STUDENT_ID_SEQ;
