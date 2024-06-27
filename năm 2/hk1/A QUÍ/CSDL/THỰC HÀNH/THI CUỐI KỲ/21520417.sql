--MSSV: 21520417. Tên: Huỳnh Ngọc Quí
CREATE DATABASE THICUOIKY
GO

USE THICUOIKY

-- CÂU 1: Viết câu lệnh SQL tạo ra các quan hệ dựa trên kiểu dữ liệu được mô tả như bảng sau, khai báo các khóa chính và khóa ngoại của quan hệ
CREATE TABLE TACGIA
(
	MaTG char (5) not null,
	HoTen varchar(20),
	DiaChi varchar(50),
	NgaySinh smalldatetime,
	DienThoai varchar(15),
	constraint pk_tacgia primary key (MaTG) 
)

CREATE TABLE GIAOTRINH
(
	MaGT char (5) not null,
	TenGT varchar(25),
	TheLoai varchar(50),
	ChuyenNganh varchar(50),
	constraint pk_giaotrinh primary key (MaGT) 
)

CREATE TABLE TACGIA_GIAOTRINH
(
	MaTG char (5),
	MaGT char(5),
	constraint pk_tggt primary key (MaTG,MaGT),
	constraint fk_tggt_tacgia foreign key (MaTG) references TacGia(MaTG),
	constraint fk_tggt_giaotrinh foreign key (MaGT) references GiaoTrinh(MaGT)
)

CREATE TABLE PHATHANH
(
	MaPH char (5) not null,
	MaGT char(5),
	NgayPH smalldatetime,
	SoLuong int,
	NXB varchar(25)
	constraint pk_phathanh primary key (MaPH),
	constraint fk_phathanh_giaotrinh foreign key (MaGT) references GiaoTrinh(MaGT),
)

set dateformat dmy
GO
-- CÂU 2: Thực hiện ràng buộc toàn vẹn sau:
-- Ngày phát hành giáo trình phải lớn hơn ngày sinh của tác giả
CREATE TRIGGER CAU2_PHATHANH
ON PHATHANH
FOR INSERT, UPDATE
AS 
	BEGIN
		DECLARE @NgayPH smalldatetime, @NgaySinh smalldatetime
		SELECT @NgayPH = NgayPH from inserted
		SELECT @NgaySinh = (SELECT NgaySinh from inserted i, TACGIA_GIAOTRINH, TACGIA WHERE i.MaGT=TACGIA_GIAOTRINH.MaGT and TACGIA_GIAOTRINH.MaTG=TACGIA.MaTG)
		IF (@NgayPH <= @NgaySinh) 
			BEGIN
				PRINT(N'NGÀY PHÁT HÀNH GIÁO TRÌNH PHẢI LỚN HƠN NGÀY SINH CỦA TÁC GIẢ')
				ROLLBACK TRANSACTION
			END
		ELSE PRINT(N'THÀNH CÔNG')
	END
GO

CREATE TRIGGER CAU2_TACGIA
ON TACGIA
FOR UPDATE
AS 
	BEGIN
		DECLARE @NgayPH smalldatetime, @NgaySinh smalldatetime
		SELECT @NgaySinh = NgaySinh from inserted
		SELECT @NgayPH = (SELECT NgayPH from inserted i, TACGIA_GIAOTRINH, PHATHANH WHERE i.MaTG=TACGIA_GIAOTRINH.MaTG and TACGIA_GIAOTRINH.MaGT=PHATHANH.MaGT)
		IF (@NgayPH <= @NgaySinh) 
			BEGIN
				PRINT(N'NGÀY PHÁT HÀNH GIÁO TRÌNH PHẢI LỚN HƠN NGÀY SINH CỦA TÁC GIẢ')
				ROLLBACK TRANSACTION
			END
		ELSE PRINT(N'THÀNH CÔNG')
	END
GO

-- Mỗi lần phát hành thì số lượng phải từ 500 quyển trở lên
ALTER TABLE PHATHANH ADD CONSTRAINT CHECK_SOLUONG CHECK (SoLuong>=500)

-- Loại giáo trình của giáo trình phải là “Giáo trình chính”, “Tham khảo”, “Nâng cao”, “Nghiên cứu”
ALTER TABLE GIAOTRINH ADD CONSTRAINT CHECK_LOAI CHECK (TheLoai in (N'Giáo trình chính',N'Tham khảo',N'Nâng cao',N'Nghiên cứu'))

-- CÂU 3: Viết các câu lệnh SQL thực hiện các câu truy vấn sau:
-- a. Cho biết thông tin Giáo trình (MaGT, TenGT, TheLoai) được phát hành trong năm 2021 và 2022
SELECT GIAOTRINH.MaGT,TenGT,TheLoai
FROM GIAOTRINH, PHATHANH
WHERE GIAOTRINH.MaGT=PHATHANH.MaGT AND (YEAR(NGAYPH) = 2021 OR YEAR(NGAYPH) = 2022)

-- b. Cho biết thông tin tác giả (MaTG, HoTen) viết giáo trình có tên là “Cơ sở dữ liệu” do Nhà xuất bản ĐHQG-HCM phát hành
SELECT TACGIA.MaTG, TACGIA.HoTen
FROM TACGIA, TACGIA_GIAOTRINH, GIAOTRINH, PHATHANH
WHERE TACGIA.MaTG=TACGIA_GIAOTRINH.MaTG AND TACGIA_GIAOTRINH.MaGT=GIAOTRINH.MaGT AND GIAOTRINH.MaGT = PHATHANH.MaGT
		AND GIAOTRINH.TenGT = N'Cơ sở dữ liệu' AND PHATHANH.NXB = N'ĐHQG-HCM'

-- c. Cho biết số lượng giáo trình của từng tác giả phát hành trong năm 2022? Thông tin hiển thị bao gồm: Mã tác giả, tên tác giả và số giáo trình
SELECT TACGIA.MaTG, TACGIA.HoTen, COUNT(*) N'SỐ GIÁO TRÌNH'
FROM TACGIA, TACGIA_GIAOTRINH, PHATHANH
WHERE TACGIA.MaTG=TACGIA_GIAOTRINH.MaTG  AND TACGIA_GIAOTRINH.MaGT=PHATHANH.MaGT AND YEAR(NgayPH)=2022
GROUP BY TACGIA.MaTG, TACGIA.HoTen

-- d. Cho biết thông tin tác giả (MaTG, HoTen) không phát hành giáo trình nào trong năm 2022
SELECT TACGIA.MaTG, TACGIA.HoTen
FROM TACGIA
WHERE TACGIA.MaTG NOT IN (SELECT MaTG FROM TACGIA_GIAOTRINH, PHATHANH WHERE TACGIA_GIAOTRINH.MaGT=PHATHANH.MaGT AND YEAR(NgayPH)=2022)

-- e. Tìm nhà xuất bản phát hành nhiều chuyên ngành nhất
SELECT TOP 1 WITH TIES PHATHANH.NXB, COUNT(DISTINCT GIAOTRINH.ChuyenNganh) N'SỐ CHUYÊN NGÀNH PHÁT HÀNH (KHÁC NHAU)'
FROM PHATHANH, GIAOTRINH
WHERE PHATHANH.MaGT=GIAOTRINH.MaGT
GROUP BY PHATHANH.NXB
ORDER BY COUNT(DISTINCT GIAOTRINH.ChuyenNganh) DESC
