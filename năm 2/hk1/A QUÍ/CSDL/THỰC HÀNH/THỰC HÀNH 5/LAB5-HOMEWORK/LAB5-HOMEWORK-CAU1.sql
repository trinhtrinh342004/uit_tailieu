﻿-- LAB5: BÀI TẬP VỀ NHÀ
-- CÂU 1: QuanLiGiaoVu II.1 đến II.4, III.1 đến III.18

-- II.1 Tăng hệ số lương thêm 0.2 cho những giáo viên là trưởng khoa.
UPDATE GIAOVIEN
SET HESO = HESO + 0.2
WHERE MAGV IN (SELECT TRGKHOA FROM KHOA)

-- II.2 Cập nhật giá trị điểm trung bình tất cả các môn học (DIEMTB) của mỗi học viên (tất cả các môn học đều có hệ số 1 và nếu học viên thi một môn nhiều lần, chỉ lấy điểm của lần thi sau cùng)
UPDATE HOCVIEN 
SET DIEMTB = 
(
	SELECT AVG(DIEM)
	FROM KETQUATHI kq1
	WHERE LANTHI = (SELECT MAX(LANTHI) FROM KETQUATHI kq2 WHERE kq1.MAHV=kq2.MAHV and kq1.MAMH=kq2.MAMH)
	GROUP BY MAHV
	HAVING HOCVIEN.MAHV=KQ1.MAHV
)

-- II.3 Cập nhật giá trị cho cột GHICHU là “Cam thi” đối với trường hợp: học viên có một môn bất kỳ thi lần thứ 3 dưới 5 điểm.
UPDATE HOCVIEN 
SET GHICHU = 'Cam thi'
WHERE MAHV IN 
			(SELECT DISTINCT MAHV 
			FROM KETQUATHI
			WHERE LANTHI = 3 AND DIEM<5)

-- II.4 Cập nhật giá trị cho cột XEPLOAI trong quan hệ HOCVIEN như sau:
-- Nếu DIEMTB >= 9 thì XEPLOAI = ”XS”
-- Nếu 8 <= DIEMTB < 9 thì XEPLOAI = “G”
-- Nếu 6.5 <= DIEMTB < 8 thì XEPLOAI = “K”
-- Nếu 5 <= DIEMTB < 6.5 thì XEPLOAI = “TB” 
-- Nếu DIEMTB < 5 thì XEPLOAI = ”Y” 
UPDATE HOCVIEN
SET XEPLOAI =
(
	CASE 
		WHEN DIEMTB >= 9 THEN 'XS'
		WHEN DIEMTB >= 8 AND DIEMTB < 9 THEN 'G'
		WHEN DIEMTB >= 6.5 AND DIEMTB < 8 THEN 'K'
		WHEN DIEMTB >= 5 AND DIEMTB < 6.5 THEN 'TB'
		WHEN DIEMTB < 5 THEN 'Y'
	END
)

-- III.1 In ra danh sách (mã học viên, họ tên, ngày sinh, mã lớp) lớp trưởng của các lớp
SELECT HOCVIEN.MAHV, HO + ' ' + TEN N'HỌ TÊN', NGSINH, HOCVIEN.MALOP
FROM HOCVIEN, LOP
WHERE HOCVIEN.MAHV = LOP.TRGLOP

-- III.2 In ra bảng điểm khi thi (mã học viên, họ tên , lần thi, điểm số) môn CTRR của lớp “K12”, sắp xếp theo tên, họ học viên.
SELECT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN',LANTHI, DIEM
FROM HOCVIEN, KETQUATHI
WHERE HOCVIEN.MAHV=KETQUATHI.MAHV AND HOCVIEN.MALOP='K12' AND MAMH = 'CTRR'
ORDER BY TEN, HO

-- III.3 In ra danh sách những học viên (mã học viên, họ tên) và những môn học mà học viên đó thi lần thứ nhất đã đạt.
SELECT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN', MAMH
FROM HOCVIEN, KETQUATHI
WHERE HOCVIEN.MAHV=KETQUATHI.MAHV AND KETQUATHI.LANTHI=1 AND KETQUATHI.KQUA='Dat'

--- III.4 In ra danh sách học viên (mã học viên, họ tên) của lớp “K11” thi môn CTRR không đạt (ở lần thi 1)
SELECT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN'
FROM HOCVIEN, KETQUATHI
WHERE HOCVIEN.MAHV=KETQUATHI.MAHV AND HOCVIEN.MALOP='K11' AND MAMH = 'CTRR' AND KETQUATHI.KQUA='Khong Dat' AND LANTHI=1

--- III.5 Danh sách học viên (mã học viên, họ tên) của lớp “K” thi môn CTRR không đạt (ở tất cả các lần thi).
SELECT DISTINCT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN'
FROM HOCVIEN, KETQUATHI KQ1
WHERE HOCVIEN.MAHV=KQ1.MAHV AND HOCVIEN.MALOP LIKE 'K%' AND MAMH = 'CTRR' 
														AND NOT EXISTS (SELECT * FROM KETQUATHI WHERE MAMH='CTRR' AND KQUA='Dat' AND MAHV=HOCVIEN.MAHV)
												


-- III.6 Tìm tên những môn học mà giáo viên có tên “Tran Tam Thanh” dạy trong học kỳ 1 năm 2006.
SELECT DISTINCT MONHOC.TENMH
FROM MONHOC, GIANGDAY, GIAOVIEN
WHERE GIANGDAY.MAGV=GIAOVIEN.MAGV AND GIANGDAY.MAMH=MONHOC.MAMH AND GIAOVIEN.HOTEN='Tran Tam Thanh' AND GIANGDAY.HOCKY=1 AND NAM=2006

-- III.7 Tìm những môn học (mã môn học, tên môn học) mà giáo viên chủ nhiệm lớp “K11” dạy trong học kỳ 1 năm 2006.
SELECT MONHOC.MAMH, MONHOC.TENMH
FROM MONHOC, GIANGDAY, LOP
WHERE GIANGDAY.MAMH=MONHOC.MAMH AND GIANGDAY.MALOP=LOP.MALOP AND HOCKY=1 AND NAM=2006 AND LOP.MALOP='K11' AND GIANGDAY.MAGV=LOP.MAGVCN

-- III.8 Tìm họ tên lớp trưởng của các lớp mà giáo viên có tên “Nguyen To Lan” dạy môn “Co So Du Lieu”
SELECT HO+' '+TEN N'HỌ TÊN'
FROM HOCVIEN, GIAOVIEN,GIANGDAY,LOP, MONHOC
WHERE GIANGDAY.MALOP=LOP.MALOP AND LOP.TRGLOP= HOCVIEN.MAHV AND GIANGDAY.MAGV=GIAOVIEN.MAGV AND GIANGDAY.MAMH=MONHOC.MAMH
	AND GIAOVIEN.HOTEN ='Nguyen To Lan' AND MONHOC.TENMH='Co So Du Lieu'

--- III.9 In ra danh sách những môn học (mã môn học, tên môn học) phải học liền trước môn “Co So Du Lieu”.
SELECT DIEUKIEN.MAMH_TRUOC, MHT.TENMH
FROM MONHOC MH, MONHOC MHT, DIEUKIEN
WHERE MH.MAMH=DIEUKIEN.MAMH AND MHT.MAMH=DIEUKIEN.MAMH_TRUOC AND MH.TENMH='Co So Du Lieu' 

--- III.10 Môn “Cau Truc Roi Rac” là môn bắt buộc phải học liền trước những môn học (mã môn học, tên môn học) nào
SELECT DIEUKIEN.MAMH, MH.TENMH
FROM MONHOC MH, MONHOC MHT, DIEUKIEN
WHERE MH.MAMH=DIEUKIEN.MAMH AND MHT.MAMH=DIEUKIEN.MAMH_TRUOC AND MHT.TENMH='Cau Truc Roi Rac' 

--- III.11 Tìm họ tên giáo viên dạy môn CTRR cho cả hai lớp “K11” và “K12” trong cùng học kỳ 1 năm 2006
(SELECT GIAOVIEN.HOTEN
FROM GIAOVIEN,GIANGDAY
WHERE GIAOVIEN.MAGV=GIANGDAY.MAGV AND GIANGDAY.MAMH='CTRR' AND HOCKY=1 AND NAM=2006 AND MALOP='K11')
INTERSECT
(SELECT GIAOVIEN.HOTEN
FROM GIAOVIEN,GIANGDAY
WHERE GIAOVIEN.MAGV=GIANGDAY.MAGV AND GIANGDAY.MAMH='CTRR' AND HOCKY=1 AND NAM=2006 AND MALOP='K12')

-- III.12 Tìm những học viên (mã học viên, họ tên) thi không đạt môn CSDL ở lần thi thứ 1 nhưng chưa thi lại môn này.
SELECT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN'
FROM HOCVIEN,KETQUATHI KQ1
WHERE HOCVIEN.MAHV=KQ1.MAHV AND KQ1.MAMH='CSDL' AND KQ1.KQUA='Khong Dat' 
									AND (SELECT MAX(LANTHI) FROM KETQUATHI KQ2 WHERE MAMH='CSDL' AND KQ1.MAHV=KQ2.MAHV)=1

-- III.13 Tìm giáo viên (mã giáo viên, họ tên) không được phân công giảng dạy bất kỳ môn học nào.
SELECT GIAOVIEN.MAGV, GIAOVIEN.HOTEN
FROM GIAOVIEN
WHERE NOT EXISTS (SELECT * FROM GIANGDAY WHERE GIANGDAY.MAGV=GIAOVIEN.MAGV)

-- III.14 Tìm giáo viên (mã giáo viên, họ tên) không được phân công giảng dạy bất kỳ môn học nào thuộc khoa giáo viên đó phụ trách.
SELECT GIAOVIEN.MAGV, GIAOVIEN.HOTEN
FROM GIAOVIEN
WHERE NOT EXISTS (SELECT * FROM GIANGDAY,MONHOC WHERE GIANGDAY.MAMH=MONHOC.MAMH AND MONHOC.MAKHOA=GIAOVIEN.MAKHOA AND GIANGDAY.MAGV=GIAOVIEN.MAGV)

-- III.15 Tìm họ tên các học viên thuộc lớp “K11” thi một môn bất kỳ quá 3 lần vẫn “Khong dat” hoặc thi lần thứ 2 môn CTRR được 5 điểm.
(SELECT HO+' '+TEN N'HỌ TÊN'
FROM HOCVIEN
WHERE MALOP='K11' AND EXISTS (SELECT * FROM KETQUATHI WHERE LANTHI>3 AND KETQUATHI.KQUA='Khong Dat' AND HOCVIEN.MAHV = KETQUATHI.MAHV))
UNION
(SELECT HO+' '+TEN N'HỌ TÊN'
FROM HOCVIEN,KETQUATHI
WHERE HOCVIEN.MAHV=KETQUATHI.MAHV AND HOCVIEN.MALOP='K11' AND MAMH='CTRR' AND LANTHI=2 AND DIEM=5)

-- III.16 Tìm họ tên giáo viên dạy môn CTRR cho ít nhất hai lớp trong cùng một học kỳ của một năm học
SELECT DISTINCT GIAOVIEN.MAGV, GIAOVIEN.HOTEN
FROM GIAOVIEN,GIANGDAY
WHERE GIAOVIEN.MAGV=GIANGDAY.MAGV AND GIANGDAY.MAMH='CTRR' 
GROUP BY GIAOVIEN.MAGV, GIAOVIEN.HOTEN, HOCKY,NAM
HAVING COUNT(*)>=2

-- III.17 Danh sách học viên và điểm thi môn CSDL (chỉ lấy điểm của lần thi sau cùng)
SELECT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN', DIEM
FROM HOCVIEN,KETQUATHI KQ1
WHERE HOCVIEN.MAHV=KQ1.MAHV AND MAMH='CSDL' AND LANTHI = (SELECT MAX(LANTHI) FROM KETQUATHI KQ2 WHERE KQ2.MAMH='CSDL' AND KQ1.MAHV=KQ2.MAHV)

-- III.18 Danh sách học viên và điểm thi môn “Co So Du Lieu” (chỉ lấy điểm cao nhất của các lần thi)
SELECT HOCVIEN.MAHV, HO+' '+TEN N'HỌ TÊN', DIEM
FROM HOCVIEN,KETQUATHI, MONHOC
WHERE HOCVIEN.MAHV=KETQUATHI.MAHV AND KETQUATHI.MAMH=MONHOC.MAMH AND MONHOC.TENMH='Co So Du Lieu'
		AND DIEM >= ALL (SELECT DIEM FROM KETQUATHI WHERE KETQUATHI.MAMH=MONHOC.MAMH AND HOCVIEN.MAHV=KETQUATHI.MAHV)

