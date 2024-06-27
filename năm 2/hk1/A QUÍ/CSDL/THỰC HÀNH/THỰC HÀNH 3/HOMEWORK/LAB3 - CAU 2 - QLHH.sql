Create database QUANLIHANGHOA
go
use QUANLIHANGHOA
go
Create table Nhacungcap 
(
maNCC		varchar(5),
tenNCC	varchar(20), 
trangthai 	numeric(2),
thanhpho	varchar(30),
Constraint PKNcc primary key (maNCC)
)


create table Phutung 
(
 maPT		varchar(5),
 tenPT	varchar(10),
 mausac	varchar(10),
 khoiluong	float,
 thanhpho	 varchar(30),
 Constraint PKPt Primary Key (maPT) 
)


Create table Vanchuyen 
(
maNCC		varchar(5) ,
maPT		varchar(5),
soluong	numeric(5), 
Constraint PKVc primary key (maNCC,maPT),
Constraint FKShip1 foreign key (maNCC) references Nhacungcap (maNCC),
Constraint FKShip2 foreign key (maPT) references Phutung (maPT)
)


insert into Nhacungcap values ('S1','Smith','20','London')
insert into Nhacungcap values ('S2','Jones','10','Paris')
insert into Nhacungcap values ('S3','Blake','30','Paris')
insert into Nhacungcap values ('S4','Clark','20','London')
insert into Nhacungcap values ('S5','Adams','30','Athens')


Insert  into Phutung values  ( 'P1' , 'Nut' , 'Red' , 12.0 , 'London')
Insert  into Phutung values  ( 'P2' , 'Bolt' , 'Green', 17.0 , 'Paris')
Insert  into Phutung values  ( 'P3' , 'Screw' , 'Blue', 17.0 , 'Oslo')
Insert  into Phutung values  ( 'P4' , 'Screw' , 'Red' , 14.0 , 'London')
Insert  into Phutung values  ( 'P5' , 'Cam' , 'Blue' , 12.0 , 'Paris')
Insert  into Phutung values  ( 'P6' , 'Cog' , 'Red' , 19.0 , 'London')



Insert into Vanchuyen values ('S1','P1',300)
Insert into Vanchuyen values ('S1','P2',200)
Insert into Vanchuyen values ('S1','P3',400)
Insert into Vanchuyen values ('S1','P4',200)
Insert into Vanchuyen values ('S1','P5',100)
Insert into Vanchuyen values ('S1','P6',100)
Insert into Vanchuyen values ('S2','P1',300)
Insert into Vanchuyen values ('S2','P2',400)
Insert into Vanchuyen values ('S3','P2',200)
Insert into Vanchuyen values ('S4','P2',200)
Insert into Vanchuyen values ('S4','P4',300)
Insert into Vanchuyen values ('S4','P5',400)

select * from Vanchuyen 


-- Cau 1:
SELECT maNCC, tenNCC, thanhpho
FROM Nhacungcap

-- Cau 2:
SELECT * FROM Phutung

-- Cau 3:
SELECT * 
FROM Nhacungcap
Where thanhpho = 'London' 


-- Cau 4:
SELECT maPT, tenPT, mausac
FROM Phutung
Where thanhpho = 'Paris' 

-- Cau 5:
SELECT maPT, tenPT, khoiluong
FROM Phutung
Where khoiluong>15

-- Cau 6:
SELECT maPT, tenPT, mausac
FROM Phutung
Where khoiluong>15 and mausac<>'red'

-- Cau 7:
SELECT maPT, tenPT, mausac
FROM Phutung
Where khoiluong>15 and mausac not in ('red','green')

-- Cau 8:
SELECT maPT, tenPT, khoiluong
FROM Phutung
Where khoiluong>15 and khoiluong<20 
Order by tenPT

-- Cau 9:
SELECT DISTINCT PhuTung.maPT, tenPT
FROM Phutung, Vanchuyen
Where Phutung.maPT = Vanchuyen.maPT and Vanchuyen.maNCC='S1'

-- Cau 10:
SELECT Nhacungcap.maNCC, tenNCC
FROM Nhacungcap, Vanchuyen
WHERE Nhacungcap.maNCC = Vanchuyen.maNCC and Vanchuyen.maPT='P1'

-- Cau 11:
SELECT DISTINCT Nhacungcap.maNCC, tenNCC
FROM Nhacungcap, Vanchuyen, Phutung
Where Nhacungcap.maNCC=Vanchuyen.maNCC and Vanchuyen.maPT=Phutung.maPT and Phutung.thanhpho ='London' and Nhacungcap.thanhpho='London'

-- Cau 12:
SELECT DISTINCT maPT, tenPT
FROM Phutung
Where maPT in (SELECT maPT FROM Vanchuyen WHERE maNCC='S1')

-- Cau 13:
SELECT maNCC, tenNCC
FROM Nhacungcap
WHERE maNCC in (SELECT maNCC FROM Vanchuyen WHERE maPT ='P1') 

-- Cau 14:
SELECT DISTINCT maPT, tenPT
FROM Phutung
Where exists (SELECT * FROM Vanchuyen WHERE Phutung.maPT = Vanchuyen.maPT and Vanchuyen.maNCC = 'S1')

-- Cau 15:
SELECT maNCC, tenNCC
FROM Nhacungcap
WHERE exists (SELECT * FROM Vanchuyen WHERE Vanchuyen.maNCC = Nhacungcap.maNCC and Vanchuyen.maPT='P1') 

-- Cau 16:
SELECT DISTINCT maNCC, tenNCC
FROM Nhacungcap
Where maNCC in (SELECT Nhacungcap.maNCC 
				FROM Vanchuyen, Nhacungcap, Phutung 
				WHERE Nhacungcap.maNCC=Vanchuyen.maNCC and Vanchuyen.maPT=Phutung.maPT and Nhacungcap.thanhpho='London' and Phutung.thanhpho='London')


-- Cau 17:
SELECT DISTINCT maNCC, tenNCC
FROM Nhacungcap ncc1
Where exists (SELECT * 
				FROM Vanchuyen, Nhacungcap, Phutung 
				WHERE Nhacungcap.maNCC=Vanchuyen.maNCC and Vanchuyen.maPT=Phutung.maPT and Nhacungcap.thanhpho='London' and Phutung.thanhpho='London' and Vanchuyen.maNCC =ncc1.maNCC)

-- Cau 18:
SELECT maNCC, tenNCC
FROM Nhacungcap
Where maNCC not in (SELECT DISTINCT maNCC from Vanchuyen)

-- Cau 19:
SELECT maNCC, tenNCC
FROM Nhacungcap
Where not exists (SELECT * from Vanchuyen Where Nhacungcap.maNCC = Vanchuyen.maNCC)

-- Cau 20:
SELECT Nhacungcap.maNCC, tenNCC
FROM Nhacungcap left join Vanchuyen on Nhacungcap.maNCC=Vanchuyen.maNCC
Where soluong is null


