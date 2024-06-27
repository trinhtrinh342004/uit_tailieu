--ĐỀ ÔN SỐ 1

---------------------------------------------------------------
--Câu 1. DDL
CREATE DATABASE DE1_SACH
USE DE1_SACH

CREATE TABLE TACGIA
(
	MaTG CHAR(5) ,
	HoTen VARCHAR(20),
	DiaChi VARCHAR(50),
	NgSinh SMALLDATETIME,
	SoDT VARCHAR(15),
	CONSTRAINT PK_TACGIA PRIMARY KEY (MaTG)
)

CREATE TABLE SACH
(
	MaSach CHAR(5),
	TenSach VARCHAR(25),
	TheLoai VARCHAR(25),
	CONSTRAINT PK_SACH PRIMARY KEY (MaSach)
)

CREATE TABLE TACGIA_SACH
(
	MaTG CHAR(5),
	MaSach CHAR(5),
	CONSTRAINT PK_TG_SACH PRIMARY KEY (MaTG, MaSach)
)

CREATE TABLE PHATHANH
(
	MaPH CHAR(5),
	MaSach CHAR(5),
	NgayPH SMALLDATETIME,
	SoLuong INT,
	NhaXuatBan VARCHAR(20),
	CONSTRAINT PK_PHATTHANH PRIMARY KEY (MaPH)
)

--Thêm khóa ngoại 
ALTER TABLE TACGIA_SACH
ADD CONSTRAINT FK1_TG_SACH FOREIGN KEY (MaTG) REFERENCES TACGIA (MaTG)

ALTER TABLE TACGIA_SACH
ADD CONSTRAINT FK2_TG_SACH FOREIGN KEY (MaSach) REFERENCES SACH (MaSach)

ALTER TABLE PHATHANH
ADD CONSTRAINT FK_PHATHANH FOREIGN KEY (MaSach) REFERENCES SACH (MaSach)

---------------------------------------------------------------
--Câu 2: TRIGGER
--2.1. Ngày phát hành sách phải lớn hơn ngày sinh của tác giả. (1.5 đ)
GO
CREATE TRIGGER TRG_PHATHANH_NGAYSINH
ON PHATHANH
FOR INSERT, UPDATE
AS
BEGIN
	DECLARE @NGAYSINH SMALLDATETIME, @NGAYPH SMALLDATETIME, @MASACH CHAR(5), @MATG CHAR(5)
	SELECT @NGAYPH = NgayPH,  @MASACH = MaSach FROM INSERTED
	SELECT @NGAYSINH = TACGIA.NgSinh, @MATG = TACGIA_SACH.MaTG
	FROM TACGIA, TACGIA_SACH
	WHERE TACGIA_SACH.MaTG = TACGIA.MaTG AND TACGIA_SACH.MaSach = @MASACH
	IF @NGAYPH < @NGAYSINH
	BEGIN 
		RAISERROR('Ngày phát hành (NgayPH) sách phải lớn hơn ngày sinh (NgSinh) của tác giả.', 16, 1)
        ROLLBACK TRANSACTION
	END
END
GO
DROP TRIGGER TRG_PHATHANH_NGAYSINH
GO

--2.2. Sách thuộc thể loại “Giáo khoa” chỉ do nhà xuất bản “Giáo dục” phát hành. (1.5 đ) 
CREATE TRIGGER TR_SACH_THELOAI_GIAOKHOA
ON SACH
FOR INSERT, UPDATE
AS
BEGIN
    DECLARE @THELOAI VARCHAR(25), @NXB VARCHAR(20), @MASACH CHAR(5)
    SELECT @THELOAI = TheLoai, @MASACH = MaSach FROM INSERTED
    SELECT @NXB = NhaXuatBan FROM PHATHANH WHERE MaSach = @MASACH
    IF @THELOAI = 'Giáo khoa' AND @NXB <> 'Giáo dục'
    BEGIN
        RAISERROR('Sách thuộc thể loại (TheLoai) “Giáo khoa” chỉ do nhà xuất bản (NhaXuatBan) “Giáo dục” phát hành.', 16, 1)
        ROLLBACK TRANSACTION
    END
END

DROP TRIGGER TR_SACH_THELOAI_GIAOKHOA

---------------------------------------------------------------
--Câu 3: TRUY VẤN
--3.1. Tìm tác giả (MaTG,HoTen,SoDT) của những quyển sách thuộc thể loại “Văn học” 
--do nhà xuất bản Trẻ phát hành. (1.5đ)
SELECT TG.MaTG, HoTen, SoDT
FROM TACGIA TG, SACH S, TACGIA_SACH TG_S, PHATHANH PH
WHERE TG_S.MaSach = S.MaSach AND TG_S.MaTG = TG.MaTG AND PH.MaSach = S.MaSach
AND TheLoai = 'Văn học'
AND NhaXuatBan = 'Trẻ'

--3.2. Tìm nhà xuất bản phát hành nhiều thể loại sách nhất.(1.5 đ)  
SELECT TOP 1 WITH TIES NhaXuatBan
FROM PHATHANH PH, SACH S
WHERE PH.MaSach = S.MaSach
GROUP BY NhaXuatBan
ORDER BY COUNT(DISTINCT TheLoai) DESC  --Thiếu DISINCT (*)

--3.3. Trong mỗi nhà xuất bản, tìm tác giả (MaTG,HoTen) có số lần phát hành nhiều sách 
--nhất. (1 đ) 
SELECT NhaXuatBan, TG.MaTG, HoTen, COUNT(MaPH)
FROM TACGIA TG, PHATHANH A, , TACGIA_SACH B
WHERE TG.MATG = B.MATG AND B.MASACH = A.MASACH
GROUP BY NhaXuatBan, TG.MaTG, HoTen
HAVING COUNT(MAPH) >= ALL
		(
			SELECT TOP 1 COUNT(MAPH) AS [SOLUONGPH]
			FROM PHATHANHA1, TACGIA_SACH B1
			WHERE B1.MASACH = A1.MASACH
			AND A1.NHAXUATBAN = A.NHAXUATBAN
			GROUP BY B1.MATG
			ORDER BY COUNT(MAPH) DESC
		)


---------------------------------------------------------------
--Nhập dữ liệu
SET DATEFORMAT DMY

