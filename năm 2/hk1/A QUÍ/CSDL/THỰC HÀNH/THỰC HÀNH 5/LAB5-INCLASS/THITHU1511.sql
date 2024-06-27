--MSSV: 21520417. Tên: Huỳnh Ngọc Quí
CREATE DATABASE QUAN_LI_GIAO_THUC_AN2
GO

USE QUAN_LI_GIAO_THUC_AN2

--1. Tao va nhap du lieu cho cac quan he tren
--2. Khai bao khoa chinh va khoa ngoai cua quan he
create table khachhang
(
	makh char(5),
	ho nvarchar(10),
	ten nvarchar(20),
	ngaysinh smalldatetime,
	diachi nvarchar(30),
	sdt char(12)
	constraint pk_khachhang primary key (makh)
)

create table monan
(
	mama char(5),
	tenma nvarchar(20),
	loaima nvarchar(20),
	constraint pk_monan primary key (mama)
)

create table hoadon
(
	mahd char(5),
	makh char(5),
	ngayhd smalldatetime,
	constraint pk_hoadon primary key (mahd),
	constraint fk_hoadon_khachhang foreign key (makh) references khachhang(makh)
)

create table cthd
(
	mahd char(5),
	mama char(5),
	sl int,
	dongia money
	constraint pk_cthd primary key (mahd,mama)
	constraint fk_cthd_hoadon foreign key (mahd) references hoadon(mahd),
	constraint fk_cthd_monan foreign key (mama) references monan(mama)
)

set dateformat dmy

insert into khachhang values ('KH01','Nguyen','Van A','15/02/2003','Quang Ngai','0868508744')
insert into khachhang values ('KH02','Huynh','Van B','16/02/2002','Quang Nam','0868218744')
insert into khachhang values ('KH03','Tran','Van D','15/02/2002','Da Nang','0268208744')
insert into khachhang values ('KH04','Le','Van C','18/02/2002','Sai Gon','0968208744')

insert into monan values ('MA01','Mon 1',N'Canh')
insert into monan values ('MA02','Mon 2',N'Mặn')
insert into monan values ('MA03','Mon 3',N'Xào')
insert into monan values ('MA04','Mon 4',N'Tráng miệng')

insert into hoadon values ('HD01','KH01','12/11/2022')
insert into hoadon values ('HD02','KH02','10/11/2022')
insert into hoadon values ('HD03','KH03','15/11/2022')
insert into hoadon values ('HD04','KH04','26/11/2021')

insert into cthd values ('HD01','MA01',100,20000)
insert into cthd values ('HD01','MA02',100,500000)
insert into cthd values ('HD02','MA01',100,20000)
insert into cthd values ('HD03','MA03',100,30000)
insert into cthd values ('HD04','MA04',100,500000)
insert into cthd values ('HD02','MA02',100,500000)




GO
--3.
CREATE TRIGGER trg_INSERT_UPDATE_MONAN
ON MONAN
FOR INSERT,UPDATE
AS
	BEGIN
		DECLARE @loaima nvarchar(20)
		select @loaima = loaima from inserted

		if @loaima not in ('Canh','Mặn','Xào','Tráng miệng')
		begin 
			print(N'Loại món ăn phải là 1 trong các loại sau: "Canh", "Mặn", "Xào", "Tráng miệng"')
			rollback transaction
		end
		else print('Successful')
	END
GO

--4.
select MAKH, HO+' '+TEN 'HOTEN', DIACHI
from khachhang
WHERE YEAR(ngaysinh)=2002
order by diachi desc, ho asc

--5.
select LOAIMA, sum(sl) 'SL_MONAN'
from monan,cthd
where monan.mama = cthd.mama
group by loaima 

--6.
select distinct KHACHHANG.MAKH,HO+' '+TEN 'HOTEN'
from khachhang,hoadon,cthd
where khachhang.makh = hoadon.makh and hoadon.mahd=cthd.mahd 
		and dongia = (select top 1 dongia from cthd order by dongia desc)