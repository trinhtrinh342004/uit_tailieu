
--Câu 1
CREATE DATABASE BAITHI
USE BAITHI

CREATE TABLE KHACHHANG 
(
  MAKH VARCHAR(5),
  TENKH VARCHAR(59),
  DIACHI VARCHAR(50),
  LOAIKH VARCHAR(6),
  CONSTRAINT PK_KH PRIMARY KEY (MAKH)
)

CREATE TABLE THUOC 
(
  MATHUOC VARCHAR(5),
  TENTHUOC VARCHAR(20),
  NGUONGOC VARCHAR(20),
  GIA DECIMAL(10, 3),
  CONSTRAINT PK_THUOC PRIMARY KEY (MATHUOC)
)


CREATE TABLE DONTHUOC 
(
  MADT INT,
  NGDT DATE,
  MAKH VARCHAR(5),
  CONSTRAINT PK_DONTHUOC PRIMARY KEY (MADT)
)


CREATE TABLE CTDT 
(
  MADT INT,
  MATHUOC VARCHAR(5),
  SOLUONG INT,
  CONSTRAINT PK_CTDT PRIMARY KEY (MADT, MATHUOC)
)

--Thêm khóa ngoại 
ALTER TABLE DONTHUOC
ADD CONSTRAINT FK_DONTHUOC FOREIGN KEY (MAKH) REFERENCES KHACHHANG(MAKH)

ALTER TABLE CTDT
ADD CONSTRAINT FK1_CTDT FOREIGN KEY (MADT) REFERENCES DONTHUOC(MADT)

ALTER TABLE CTDT
ADD CONSTRAINT FK2_CTDT FOREIGN KEY (MATHUOC) REFERENCES THUOC(MATHUOC)

-----------------------------------------------------------------------------
--Câu 2: Nhập dữ liệu
-- Nhập dữ liệu vào bảng KHACHHANG
set dateformat dmy
INSERT INTO KHACHHANG (MAKH, TENKH, DIACHI, LOAIKH)
VALUES ('KH001', 'Nguyen Trong Tan', 'Tp.HCM', 'VIP'),
       ('KH002', 'Tran Tien Trien', 'Dong Nai', 'Thuong'),
       ('KH003', 'Hoang My Uyen', 'Kien Giang', 'Thuong');

-- Nhập dữ liệu vào bảng THUOC
INSERT INTO THUOC (MATHUOC, TENTHUOC, NGUONGOC, GIA)
VALUES ('MT001', 'Aspirin', 'Uc', 480000),
       ('MT002', 'Panadol', 'Phap', 200000),
       ('MT003', 'Vaccine', 'My', 140000);

-- Nhập dữ liệu vào bảng DONTHUOC
INSERT INTO DONTHUOC (MADT, NGDT, MAKH)
VALUES ('00001', '2018-11-12', 'KH001'),
       ('00002', '2018-10-10', 'KH003'),
       ('00003', '2018-10-12', 'KH002');

-- Nhập dữ liệu vào bảng CTDT
INSERT INTO CTDT (MADT, MATHUOC, SOLUONG)
VALUES ('00001', 'MT001', 3),
       ('00001', 'MT002', 8),
       ('00003', 'MT003', 12);
-----------------------------------------------------------------------------
--Câu 3 Hiện thực ràng buộc toàn vẹn sau: Số lượng thuốc trong từng chi tiết hóa đơn phải lớn hơn 0
ALTER TABLE CTDT
ADD CONSTRAINT CK_SoLuongLonHonKhong CHECK (SOLUONG > 0)

-----------------------------------------------------------------------------
--Câu 4 4. Hiện thực ràng buộc toàn vẹn sau: (2đ)
--. Nếu nhân viên nhập giá của thuốc nhỏ hơn 1000 hệ thống sẽ báo lỗi sau 'Loi: Gia thuoc
--khong hop le và transaction sẽ bị hủy.
--• Ngược lại, nếu giả thuốc từ 1000 trở lên hệ thống sẽ báo thành công Thong tin thuoc moi da duoc nhap thanh cong' và transaction sẽ được thực hiện.
-- Tạo bảng THUOC
go
-- Tạo trigger
CREATE TRIGGER TRG_CheckGiaThuoc
ON THUOC
FOR INSERT, UPDATE
AS
BEGIN
    DECLARE @GiaThuoc INT

    -- Lấy giá thuốc từ bảng inserted
    SELECT @GiaThuoc = GIA FROM inserted

    -- Kiểm tra giá thuốc mới
    IF @GiaThuoc < 1000
    BEGIN
        -- Nếu giá thuốc nhỏ hơn 1000, báo lỗi và hủy transaction
        RAISERROR ('Loi: Gia thuoc khong hop le', 16, 1)
        ROLLBACK TRANSACTION
    END
    ELSE
    BEGIN
        -- Nếu giá thuốc từ 1000 trở lên, báo thành công và thực hiện transaction
        PRINT 'Thong tin thuoc moi da duoc nhap thanh cong'
        COMMIT TRANSACTION
    END
END;
-----------------------------------------------------------------------------
--Câu 5: Tìm Mã khách hàng và tên khách hàng của những khách hàng ở Tp.HCM (14)
SELECT MAKH, TENKH
FROM KHACHHANG
WHERE DIACHI = 'Tp.HCM'
-----------------------------------------------------------------------------
--Câu 6. Tìm tất cả các đơn thuốc có ngày lập đơn thuốc TRƯỚC tháng 12 năm 2018 (1đ).
SELECT *
FROM DONTHUOC
WHERE MONTH(NGDT) < 12
AND YEAR(NGDT) <=2018

-----------------------------------------------------------------------------
--Câu 7. Tim MaKH, TenKH, Tong_gia_tri của khách hàng có tổng giá trị hoá đơn lớn nhất năm 2018
SELECT KH.MAKH, TENKH, SUM(T.GIA * C.SOLUONG) AS [TONG_GIA_TRI]
FROM KHACHHANG KH, DONTHUOC DT, CTDT C, THUOC T
WHERE KH.MAKH = DT.MAKH AND C.MADT = DT.MADT AND C.MATHUOC = T.MATHUOC
AND YEAR(DT.NGDT) = 2018
GROUP BY KH.MAKH, TENKH
HAVING SUM(T.GIA * C.SOLUONG) = (
							SELECT TOP 1 SUM(T2.GIA * C2.SOLUONG)
							FROM CTDT C2, THUOC T2, DONTHUOC DT2
							WHERE C2.MATHUOC = T2.MATHUOC AND DT2.MADT = C2.MADT
							AND YEAR(DT2.NGDT) = 2018
							GROUP BY MAKH
							ORDER BY SUM(T2.GIA * C2.SOLUONG) DESC
						)

-----------------------------------------------------------------------------
--Câu 8: Tính tổng giá trị tất cả các hóa đơn của tất cả các khách hàng loại vịp đã mua. (1đ).
SELECT KH.LOAIKH, SUM(T.GIA * C.SOLUONG) AS [Tong_gia_tri]
FROM KHACHHANG KH, DONTHUOC DT, CTDT C, THUOC T
WHERE KH.MAKH = DT.MAKH AND C.MADT = DT.MADT AND C.MATHUOC = T.MATHUOC
AND KH.LOAIKH = 'Vip'
GROUP BY KH.LOAIKH

