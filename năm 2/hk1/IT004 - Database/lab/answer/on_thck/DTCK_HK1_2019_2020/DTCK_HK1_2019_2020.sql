--ĐỀ 1
CREATE DATABASE HK1_2019_2020
USE HK1_2019_2020
DROP DATABASE HK1_2019_2020
-- Tạo bảng Quocgia
CREATE TABLE Quocgia (
  MaQG CHAR(2) PRIMARY KEY,
  TenQG VARCHAR(255),
  ChauLuc VARCHAR(255),
  DienTich INT
)

-- Nhập dữ liệu cho bảng Quocgia
INSERT INTO Quocgia (MaQG, TenQG, ChauLuc, DienTich)
VALUES
  ('DE', 'Đức', 'Châu Âu', NULL),
  ('UK', 'Anh', 'Châu Âu', NULL),
  ('JA', 'Nhật Bản', 'Châu Á', NULL),
  ('BR', 'Brazil', 'Châu Mỹ', NULL),
  ('CH', 'Trung Quốc', 'Châu Á', NULL);

-- Tạo bảng Thevanhoi
CREATE TABLE Thevanhoi (
  MaTVH CHAR(6) PRIMARY KEY,
  TenTVH VARCHAR(255),
  MaQG CHAR(2),
  Nam INT,
  FOREIGN KEY (MaQG) REFERENCES Quocgia(MaQG)
)

-- Nhập dữ liệu cho bảng Thevanhoi
INSERT INTO Thevanhoi (MaTVH, TenTVH, MaQG, Nam)
VALUES
  ('TVH01', 'Olympic Beijing 2008', 'CH', 2008),
  ('TVH02', 'Olympic London 2012', 'UK', 2012),
  ('TVH03', 'Olympic Rio 2016', 'BR', 2016),
  ('TVH04', 'Olympic Tokyo 2020', 'JA', 2020);

-- Tạo bảng Vandongvien
CREATE TABLE Vandongvien (
  MaVDV CHAR(6) PRIMARY KEY,
  HoTen VARCHAR(255),
  NgSinh DATE,
  GioiTinh VARCHAR(10),
  QuocTich CHAR(2),
  FOREIGN KEY (QuocTich) REFERENCES Quocgia(MaQG)
);
SET DATEFORMAT DMY
-- Nhập dữ liệu cho bảng Vandongvien
INSERT INTO Vandongvien (MaVDV, HoTen, NgSinh, GioiTinh, QuocTich)
VALUES
  ('VDV001', 'John', '1988-01-10', 'Nam', 'UK'),
  ('VDV002', 'Helen', '1989-04-20', 'Nu', 'UK'),
  ('VDV003', 'Osaka', '1990-03-17', 'Nu', 'JA'),
  ('VDV004', 'Ronaldo', '1990-03-01', 'Nam', 'BR');

-- Tạo bảng Noidungthi
CREATE TABLE Noidungthi (
  MaNDT INT PRIMARY KEY,
  TenNDT VARCHAR(255),
  GhiChu VARCHAR(255)
);

-- Nhập dữ liệu cho bảng Noidungthi
INSERT INTO Noidungthi (MaNDT, TenNDT, GhiChu)
VALUES
  (1, 'Điền kinh', ''),
  (2, 'Bắn cung', ''),
  (3, 'Nhảy cầu', ''),
  (4, 'Bắn súng', '');

-- Tạo bảng Thamgia
CREATE TABLE Thamgia (
  MaVDV CHAR(6),
  MaNDT INT,
  MaTVH CHAR(6),
  HuyChuong INT,
  PRIMARY KEY (MaVDV, MaNDT, MaTVH),
  FOREIGN KEY (MaVDV) REFERENCES Vandongvien(MaVDV),
  FOREIGN KEY (MaNDT) REFERENCES Noidungthi(MaNDT),
  FOREIGN KEY (MaTVH) REFERENCES Thevanhoi(MaTVH)
);

-- Nhập dữ liệu cho bảng Thamgia
INSERT INTO Thamgia (MaVDV, MaNDT, MaTVH, HuyChuong)
VALUES
  ('VDV001', 1, 'TVH01', 0),
  ('VDV001', 2, 'TVH01', 1),
  ('VDV001', 4, 'TVH02', 2),
  ('VDV002', 1, 'TVH01', 2),
  ('VDV002', 3, 'TVH01', 2);
--Tại một kỳ thế vận hội, mỗi nội dung thi chỉ có duy nhất một huy chương vàng. 
--a. Liệt kê danh sách vận động viên (HoTen, NgSinh, GioiTinh) có Quốc tịch là ‘UK’ và sắp 
--xếp danh sách theo (HoTen) tăng dần. (1 điểm) 
SELECT HoTen, NgSinh, GioiTinh
FROM Vandongvien
WHERE QuocTich = 'UK'
ORDER BY HoTen ASC

--b. In ra danh sách những vận động viên tham gia nội dung thi ‘Bắn Cung’ ở thế vận hội 
--‘Olympic Tokyo 2020’. (1 điểm)
SELECT MaVDV
FROM Thamgia TG, Noidungthi NDT, Thevanhoi TVH
WHERE TG.MaNDT = NDT.MaNDT AND TG.MaTVH = TVH.MaTVH
AND TenNDT = 'Bắn Cung'
AND TenTVH = 'Olympic Tokyo 2020'

--c. Cho biết số lượng huy chương vàng mà các vận động viên ‘Nhật Bản’ đạt được ở thế vận 
--hội diễn ra vào năm 2020. (1 điểm) 
SELECT COUNT(HuyChuong) AS HUYCHUONG
FROM Thamgia TG, Vandongvien VDV, Thevanhoi TVH, Quocgia Q
WHERE TG.MaVDV = VDV.MaVDV AND TG.MaTVH = TVH.MaTVH
AND Q.MaQG = TVH.MaQG
AND HuyChuong = 1
AND TenQG = 'Nhật Bản'
AND Nam = 2020

--D. Liệt kê họ tên và quốc tịch của những vận động viên tham gia cả 2 nội dung thi ‘100m bơi 
--ngửa’ và ‘200m tự do’. (1 điểm) 
SELECT HoTen, QuocTich
FROM Vandongvien V, Thamgia T, Noidungthi N
WHERE V.MaVDV = T.MaVDV AND N.MaNDT = T.MaNDT
AND TenNDT = '100m bơi ngửa'
INTERSECT
SELECT HoTen, QuocTich
FROM Vandongvien V, Thamgia T, Noidungthi N
WHERE V.MaVDV = T.MaVDV AND N.MaNDT = T.MaNDT
AND TenNDT = '200m tự do'

--e. In ra thông tin (MaVDV, HoTen) của những vận động viên Nữ người Anh (QuocTich=UK) 
--tham gia tất cả các kỳ thế vận hội từ năm 2008 tới nay. (1 điểm)
SELECT V.MaVDV, V.HoTen
FROM Vandongvien V
WHERE QuocTich = 'UK' AND GioiTinh = 'Nu'
AND NOT EXISTS (
					SELECT *
					FROM Thevanhoi T
					WHERE NAM >= 2008
					AND NOT EXISTS (
										SELECT *
										FROM Thamgia TG
										WHERE TG.MaVDV = V.MaVDV
										AND TG.MaTVH = T.MaTVH
									)
				)
--F. Tìm vận đông viên (MaVDV, HoTen) đã đạt từ 2 huy chương vàng trở lên tại thế vận hội 
--‘Olympic Rio 2016’. (1 điểm) 
SELECT V.MaVDV, V.HoTen
FROM Thamgia TG, Vandongvien V, Thevanhoi T
WHERE TG.MaVDV = V.MaVDV AND TG.MaTVH = T.MaTVH
AND TenTVH = 'Olympic Rio 2016'
AND HuyChuong = 1
GROUP BY V.MaVDV, V.HoTen
HAVING COUNT(*) >= 2
