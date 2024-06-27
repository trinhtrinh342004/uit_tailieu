CREATE DATABASE HK1_2018_2019
USE HK1_2018_2019


CREATE TABLE MATHANG (
    MAMH VARCHAR(50) PRIMARY KEY,
    TENMH VARCHAR(100),
    DVT VARCHAR(50),
    NUOCSX VARCHAR(100)
)

CREATE TABLE NHACC (
    MACC VARCHAR(50) PRIMARY KEY,
    TENCC VARCHAR(100),
    DIACHICC VARCHAR(200)
)

CREATE TABLE CUNGCAP (
    MACC VARCHAR(50),
    MAMH VARCHAR(50),
    TUNGAY DATE,
    PRIMARY KEY (MACC, MAMH),
    FOREIGN KEY (MACC) REFERENCES NHACC (MACC),
    FOREIGN KEY (MAMH) REFERENCES MATHANG (MAMH)
)

CREATE TABLE DONDH (
    MADH VARCHAR(50) PRIMARY KEY,
    NGAYDH DATE,
    MACC VARCHAR(50),
    TONGTRIGIA DECIMAL(18, 2) DEFAULT 0,
    SOMH INT DEFAULT 0,
    FOREIGN KEY (MACC) REFERENCES NHACC (MACC)
)

CREATE TABLE CHITIET 
(
    MADH VARCHAR(50),
    MAMH VARCHAR(50),
    SOLUONG INT,
    DONGIA DECIMAL(18, 2),
    TRIGIA DECIMAL(18, 2),
    PRIMARY KEY (MADH, MAMH),
    FOREIGN KEY (MADH) REFERENCES DONDH (MADH),
    FOREIGN KEY (MAMH) REFERENCES MATHANG (MAMH)
)

set dateformat dmy
-- Nhập dữ liệu giả cho bảng MATHANG
INSERT INTO MATHANG (MAMH, TENMH, DVT, NUOCSX) VALUES
    ('MH001', 'Mặt hàng 1', 'Đơn vị 1', 'Việt Nam'),
    ('MH002', 'Mặt hàng 2', 'Đơn vị 2', 'Trung Quốc'),
    ('MH003', 'Mặt hàng 3', 'Đơn vị 3', 'Việt Nam'),
    ('MH004', 'Mặt hàng 4', 'Đơn vị 4', 'Việt Nam');

-- Nhập dữ liệu giả cho bảng NHACC
INSERT INTO NHACC (MACC, TENCC, DIACHICC) VALUES
    ('CC001', 'Nhà cung cấp 1', 'Địa chỉ 1'),
    ('CC002', 'Nhà cung cấp 2', 'Địa chỉ 2'),
    ('CC003', 'Vissan', 'Địa chỉ 3'),
    ('CC004', 'Nhà cung cấp 4', 'Địa chỉ 4');

-- Nhập dữ liệu giả cho bảng CUNGCAP
INSERT INTO CUNGCAP (MACC, MAMH, TUNGAY) VALUES
    ('CC001', 'MH001', '2018-01-01'),
    ('CC002', 'MH002', '2018-01-01'),
    ('CC003', 'MH003', '2018-01-01'),
    ('CC004', 'MH004', '2018-01-01'),
    ('CC003', 'MH001', '2018-01-01'),
    ('CC003', 'MH002', '2018-01-01');

-- Nhập dữ liệu giả cho bảng DONDH
INSERT INTO DONDH (MADH, NGAYDH, MACC, TONGTRIGIA, SOMH) VALUES
    ('DH001', '2018-01-01', 'CC001', 1000000, 5),
    ('DH002', '2018-02-01', 'CC002', 2000000, 10),
    ('DH003', '2018-03-01', 'CC003', 3000000, 15),
    ('DH004', '2019-01-01', 'CC004', 4000000, 20),
    ('DH005', '2019-02-01', 'CC003', 5000000, 25),
    ('DH006', '2019-03-01', 'CC003', 6000000, 30);

-- Nhập dữ liệu giả cho bảng CHITIET
INSERT INTO CHITIET (MADH, MAMH, SOLUONG, DONGIA) VALUES
    ('DH001', 'MH001', 2, 500000),
    ('DH001', 'MH002', 3, 400000),
    ('DH002', 'MH003', 4, 300000),
    ('DH003', 'MH004', 5, 200000),
    ('DH004', 'MH001', 6, 100000),
    ('DH005', 'MH002', 7, 90000),
    ('DH006', 'MH003', 8, 80000);

--------------------------------------------------------------------
--2a. Liệt kê danh sách các đơn hàng (MADH, NGAYDH, TONGTRIGIA) của 
--tên nhà cung cấp ‘Vinamilk’ có tổng trị giá lớn hơn 1.000.000 đồng. (1 điểm)
SELECT MADH, NGAYDH, TONGTRIGIA
FROM NHACC N, DONDH D
WHERE N.MACC = D.MACC
AND TENCC = 'Vinamilk'
AND TONGTRIGIA > 1000000

--b. Liệt kê tổng số lượng sản phẩm có mã mặt hàng (MAMH) là ‘MH001’ đã đặt hàng trong 
--năm 2018. (1 điểm) 
SELECT SUM(CT.SOLUONG) AS [TONG SAN PHAM MH001]
FROM DONDH DH, CHITIET CT
WHERE DH.MADH = CT.MADH
AND MAMH = 'MH001' 
AND YEAR(NGAYDH) = 2018

--c. Liệt kê những nhà cung cấp (MACC, TENCC) có thể cung cấp những mặt hàng do ‘Việt 
--Nam’ sản xuất mà không cung cấp những mặt hàng do ‘Trung Quốc’ sản xuất. (1 điểm) 
SELECT NCC.MACC, TENCC
FROM CUNGCAP CC, MATHANG MH, NHACC NCC
WHERE CC.MACC = NCC.MACC AND CC.MAMH = MH.MAMH
AND NUOCSX = 'Việt Nam'
EXCEPT 
SELECT NCC.MACC, TENCC
FROM CUNGCAP CC, MATHANG MH, NHACC NCC
WHERE CC.MACC = NCC.MACC AND CC.MAMH = MH.MAMH
AND NUOCSX = 'Trung Quốc'

--d. Tính tổng số mặt hàng (SOMH) của tất cả các đơn đặt hàng theo từng năm. Thông tin hiển 
--thị: Năm đặt hàng, Tổng số mặt hàng. (1 điểm) 
SELECT YEAR(NGAYDH) AS [Năm đặt hàng], SUM(SOMH) AS [Tổng số mặt hàng]
FROM DONDH
GROUP BY YEAR(NGAYDH)

--e. Tìm những mã đơn đặt hàng (MADH) đã đặt tất cả các mặt hàng của nhà cung cấp có tên 
--là ‘Vissan’ (TENCC). (1 điểm) 

SELECT MADH
FROM DONDH DH
WHERE NOT EXISTS (
					SELECT * 
					FROM NHACC NCC, CUNGCAP CC
					WHERE NCC.MACC = CC.MACC
					AND TENCC = 'Vissan'
					AND NOT EXISTS (
										SELECT *
										FROM CHITIET CT
										WHERE CT.MAMH = CC.MAMH
										AND CT.MADH = DH.MADH
									)
				)

--F. Tìm những mặt hàng (MAMH, TENMH) có số lượng đặt hàng nhiều nhất trong năm 
--2018. (1 điểm) 
SELECT TOP 1 WITH TIES MH.MAMH, TENMH
FROM DONDH DH, CHITIET CT, MATHANG MH
WHERE DH.MADH = CT.MADH AND CT.MAMH = MH.MAMH
AND YEAR(NGAYDH) = 2018
GROUP BY MH.MAMH, TENMH
ORDER BY SUM(SOLUONG) DESC

