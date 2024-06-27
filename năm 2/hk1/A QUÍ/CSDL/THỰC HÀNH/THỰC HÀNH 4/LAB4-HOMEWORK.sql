-- BTTH 4 - HOMEWORK
-- BAI QUAN LI GIAO VU. HOAN THANH TU CAU 19 DEN 35 (PHAN III)

-- Cau 19:
select top 1 with ties MAKHOA, TENKHOA
from KHOA
order by NGTLAP

-- Cau 20:
select count(MAGV) N'Số giáo viên'
from GIAOVIEN
where HOCHAM in ('GS','PGS')

-- Cau 21:
select MAKHOA,HOCVI, COUNT(*) N'Số giáo viên'
from GIAOVIEN
group by MAKHOA,HOCVI
order by MAKHOA

-- Cau 22:
select MAMH, KQUA, count(distinct MAHV) N'Số lượng'
from KETQUATHI kq1
where LANTHI = (select max(LANTHI) from KETQUATHI kq2 where kq1.MAMH=kq2.MAMH and kq1.MAHV=kq2.MAHV)
group by MAMH, KQUA
order by MAMH

-- Cau 23:
select GIAOVIEN.MAGV,HOTEN
from GIAOVIEN,LOP,GIANGDAY
where GIAOVIEN.MAGV=GIANGDAY.MAGV and GIANGDAY.MALOP=LOP.MALOP and GIAOVIEN.MAGV=LOP.MAGVCN
group by GIAOVIEN.MAGV,HOTEN
having count(GIANGDAY.MAMH)>=1

-- Cau 24:
select top 1 with ties HO+' '+TEN N'Họ tên'
from HOCVIEN,LOP
where LOP.TRGLOP=HOCVIEN.MAHV
order by LOP.SISO desc

-- Cau 25:
select HO+' '+TEN N'Họ tên', count(*)
from HOCVIEN,LOP,KETQUATHI kq1
where LOP.TRGLOP=HOCVIEN.MAHV and HOCVIEN.MAHV=kq1.MAHV and kq1.KQUA='Khong Dat' 
		and LANTHI = (select max(LANTHI) from KETQUATHI kq2 where kq1.MAHV=kq2.MAHV and kq1.MAMH=kq2.MAMH)
group by HOCVIEN.MAHV,HO,TEN
having count(*)>3

-- Cau 26:
select top 1 with ties HOCVIEN.MAHV,HO+' '+TEN N'Họ tên'
from HOCVIEN,KETQUATHI
where HOCVIEN.MAHV=KETQUATHI.MAHV and KETQUATHI.DIEM >=9
group by HOCVIEN.MAHV,HO,TEN
order by count(*) desc

-- Cau 27:
select hv1.MALOP, hv1.MAHV, HO+' '+TEN N'Họ tên'
from HOCVIEN hv1,KETQUATHI kq1 
where hv1.MAHV=kq1.MAHV and DIEM>=9
group by hv1.MALOP, hv1.MAHV, HO,TEN
having count(*) = 
	(select top 1 count(*) from KETQUATHI kq2,HOCVIEN hv2 where kq2.MAHV=hv2.MAHV and DIEM>=9 and hv2.MALOP=hv1.MALOP group by hv2.MAHV order by count(*) desc)


-- Cau 28:
select GIAOVIEN.MAGV, GIAOVIEN.HOTEN,NAM,HOCKY,count(distinct MAMH) N'Số môn học', count(distinct MALOP) N'Số lớp'
From GIAOVIEN,GIANGDAY
where GIAOVIEN.MAGV=GIANGDAY.MAGV
group by GIAOVIEN.MAGV, GIAOVIEN.HOTEN,NAM,HOCKY
order by GIAOVIEN.MAGV

-- Cau 29:
select NAM,HOCKY, GIAOVIEN.MAGV,GIAOVIEN.HOTEN, count(*) N'Số lần giảng dạy'
From GIAOVIEN,GIANGDAY gd1
where GIAOVIEN.MAGV=gd1.MAGV 
group by GIAOVIEN.MAGV, GIAOVIEN.HOTEN,gd1.NAM,gd1.HOCKY
having count(*) = 
	(select top 1 count(*) from GIANGDAY gd2 where gd1.NAM=gd2.nam and gd1.HOCKY=gd2.HOCKY group by gd2.MAGV order by count(*) desc)
order by NAM

-- Cau 30:
select top 1 with ties MONHOC.MAMH, MONHOC.TENMH
from MONHOC,KETQUATHI
where MONHOC.MAMH=KETQUATHI.MAMH and KETQUATHI.KQUA='Khong Dat' and LANTHI=1
group by MONHOC.MAMH,MONHOC.TENMH
order by count(*) desc

-- Cau 31:
select distinct HOCVIEN.MAHV, HO+' '+Ten N'Họ tên'
from KETQUATHI kq1,HOCVIEN
where kq1.MAHV=HOCVIEN.MAHV and LANTHI=1 and not exists (Select * from KETQUATHI kq2 where LANTHI=1 and kq2.KQUA='Khong Dat' and kq1.MAHV=kq2.MAHV)

-- Cau 32:
select distinct HOCVIEN.MAHV, HO+' '+Ten N'Họ tên'
from KETQUATHI kq1,HOCVIEN
where kq1.MAHV=HOCVIEN.MAHV and kq1.LANTHI = (select max(LANTHI) from KETQUATHI kq3 where kq1.MAHV=kq3.MAHV and kq1.MAMH=kq3.MAMH)
							and not exists (Select * from KETQUATHI kq2 
											where 
											kq2.LANTHI = (select max(LANTHI) from KETQUATHI kq3 where kq2.MAHV=kq3.MAHV and kq2.MAMH=kq3.MAMH)
											and kq2.KQUA='Khong Dat' and kq2.MAHV=kq1.MAHV)

-- Cau 33:
select HOCVIEN.MAHV, HO+' '+TEN N'Họ tên'
from HOCVIEN, KETQUATHI
where HOCVIEN.MAHV=KETQUATHI.MAHV and LANTHI=1 and KETQUATHI.KQUA='Dat'
group by HOCVIEN.MAHV,HO,TEN
having count(distinct KETQUATHI.MAMH) = (select count(*) from MONHOC)

-- Cau 34:
select HOCVIEN.MAHV, HO+' '+TEN N'Họ tên'
from HOCVIEN, KETQUATHI kq1
where HOCVIEN.MAHV=kq1.MAHV and kq1.KQUA='Dat' and LANTHI = (select max(LANTHI) from KETQUATHI kq2 where  kq2.MAHV=kq1.MAHV and kq2.MAMH=kq1.MAMH)
group by HOCVIEN.MAHV,HO,TEN
having count(distinct kq1.MAMH) = (select count(*) from MONHOC)

-- Cau 35:
select kq1.MAMH, HOCVIEN.MAHV, HO+' '+TEN N'Họ tên', kq1.DIEM
from KETQUATHI kq1,HOCVIEN
where kq1.MAHV=HOCVIEN.MAHV and LANTHI = (select max(LANTHI) from KETQUATHI kq2 where  kq2.MAHV=kq1.MAHV and kq2.MAMH=kq1.MAMH)
and kq1.DIEM = (select max(Diem) from KETQUATHI kq3 where kq1.MAMH=kq3.MAMH)
order by kq1.MAMH




