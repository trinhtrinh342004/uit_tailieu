-- LAB5: BÀI TẬP VỀ NHÀ
-- CÂU 2: QuanLyHangHoa từ câu 21 đến câu 36

-- CÂU 21:
SELECT COUNT(*) N'Số nhà cung cấp'
FROM Nhacungcap

-- CÂU 22:
SELECT COUNT(*) N'Số nhà cung cấp ở London'
FROM Nhacungcap
WHERE thanhpho='London'

-- CÂU 23:
SELECT MAX(TRANGTHAI) N'GIÁ TRỊ CAO NHẤT', MIN(TRANGTHAI) N'GIÁ TRỊ THẤP NHẤT'
FROM Nhacungcap

-- CÂU 24:
SELECT MAX(TRANGTHAI) N'GIÁ TRỊ CAO NHẤT', MIN(TRANGTHAI) N'GIÁ TRỊ THẤP NHẤT'
FROM Nhacungcap
WHERE thanhpho='London'

-- CÂU 25:
SELECT Nhacungcap.maNCC, SUM(soluong) N'SỐ PHỤ TÙNG VẬN CHUYỂN'
FROM Nhacungcap,Vanchuyen
WHERE Nhacungcap.maNCC=Vanchuyen.maNCC
GROUP BY Nhacungcap.maNCC

-- CÂU 26:
SELECT Nhacungcap.maNCC, Nhacungcap.tenNCC, Nhacungcap.thanhpho, SUM(soluong) N'SỐ PHỤ TÙNG VẬN CHUYỂN'
FROM Nhacungcap,Vanchuyen
WHERE Nhacungcap.maNCC=Vanchuyen.maNCC
GROUP BY Nhacungcap.maNCC, Nhacungcap.tenNCC, Nhacungcap.thanhpho

-- CÂU 27:
SELECT Nhacungcap.maNCC
FROM Nhacungcap,Vanchuyen
WHERE Nhacungcap.maNCC=Vanchuyen.maNCC
GROUP BY Nhacungcap.maNCC
HAVING SUM(Vanchuyen.soluong)>500

-- CÂU 28:
SELECT Nhacungcap.maNCC
FROM Nhacungcap,Vanchuyen,Phutung
WHERE Nhacungcap.maNCC=Vanchuyen.maNCC and Vanchuyen.maPT=Phutung.maPT and mausac='red'
GROUP BY Nhacungcap.maNCC
HAVING SUM(Vanchuyen.soluong)>300

-- CÂU 29:
SELECT Nhacungcap.maNCC, Nhacungcap.tenNCC, Nhacungcap.thanhpho, SUM(soluong) N'SỐ LƯỢNG PHỤ TÙNG VẬN CHUYỂN'
FROM Nhacungcap,Vanchuyen,Phutung
WHERE Nhacungcap.maNCC=Vanchuyen.maNCC and Vanchuyen.maPT=Phutung.maPT and mausac='red'
GROUP BY Nhacungcap.maNCC,Nhacungcap.tenNCC, Nhacungcap.thanhpho
HAVING SUM(Vanchuyen.soluong)>300

-- CÂU 30:
SELECT thanhpho, COUNT(*) N'SỐ NHÀ CUNG CẤP'
FROM Nhacungcap
GROUP BY thanhpho

-- CÂU 31:
SELECT TOP 1 WITH TIES Nhacungcap.tenNCC, SUM(SOLUONG) N'SỐ LƯỢNG PHỤ TÙNG'
FROM Nhacungcap,Vanchuyen
WHERE Nhacungcap.maNCC=Vanchuyen.maNCC
GROUP BY Nhacungcap.maNCC,Nhacungcap.tenNCC
ORDER BY SUM(SOLUONG) DESC

-- CÂU 32:
SELECT DISTINCT Nhacungcap.thanhpho
FROM Phutung,Nhacungcap
WHERE Phutung.thanhpho=Nhacungcap.thanhpho

-- CÂU 33:
insert into Nhacungcap values ('S6','Duncan',30,'Paris')

-- CÂU 34:
UPDATE Nhacungcap
SET thanhpho='Sydney'
WHERE maNCC='S6'

-- CÂU 35:
UPDATE Nhacungcap
SET trangthai=trangthai+10
WHERE thanhpho='London'

-- CÂU 36:
DELETE FROM Nhacungcap
WHERE maNCC='S6'
