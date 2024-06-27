-- Thuc hanh LAB 4
-- Phan III bai tap QuanLyBanHang. Hoan thanh tu cau 20 den cau 45


-- Cau 20:
Select count(*) 
from HOADON
where MAKH is null

-- Cau 21:
Select count(distinct masp)
From CTHD,HOADON
Where CTHD.SOHD = HOADON.SOHD and year(HOADON.NGHD)=2006

-- Cau 22:
select max(trigia) 'Max', min(trigia) 'Min'
From HOADON

-- Cau 23:
select AVG(trigia) 'Tri gia trung binh'
From HOADON
where year(NGHD)=2006

-- Cau 24:
select sum(trigia) 'Doan thu ban hang'
from HOADON
where year(NGHD)=2006

-- Cau 25:
select top 1 with ties SOHD 
from HOADON
where year(NGHD)=2006
order by TRIGIA desc

-- Cau 26:
Select HOTEN
From KHACHHANG, HOADON
where KHACHHANG.MAKH = HOADON.MAKH and year(NGHD)=2006 and HOADON.TRIGIA = (select max(trigia) from HOADON)

-- Cau 27:
select top 3 MAKH,HOTEN
From KHACHHANG
order by DOANHSO desc

-- Cau 28:
Select MASP,TENSP
from SANPHAM
where GIA in (select distinct top 3  GIA from SANPHAM order by GIA desc)


-- Cau 29:
Select MASP,TENSP
from SANPHAM
where NUOCSX='Thai Lan' and GIA in (select distinct top 3 GIA from SANPHAM order by GIA desc)

-- Cau 30:
Select MASP,TENSP
from SANPHAM
where NUOCSX='Trung Quoc' and GIA in (select distinct top 3 GIA from SANPHAM where NUOCSX='Trung Quoc' order by GIA desc)


-- Cau 31:
select * 
from KHACHHANG
where DOANHSO in (select distinct top 3 DOANHSO from KHACHHANG order by DOANHSO desc)

-- Cau 32:
select count(masp) 'So san pham'
from SANPHAM
where NUOCSX='Trung Quoc'

-- Cau 33:
select NUOCSX,COUNT(masp) 'So san pham'
from SANPHAM
group by NUOCSX

-- Cau 34:
select NUOCSX,max(gia) 'Gia ban cao nhat', min(gia) 'Gia ban thap nhat', avg(gia) 'Gia ban trung binh'
from SANPHAM
group by NUOCSX

-- Cau 35:
select NGHD, sum(trigia) 'Doanh thu'
from HOADON
group by NGHD

-- Cau 36:
select MASP, Sum(SL) 'Tong so luong'
from HOADON, CTHD
where YEAR(NGHD)=2006 and MONTH(NGHD)=10 and HOADON.SOHD=CTHD.SOHD
group by MASP

-- Cau 37:
select month(NGHD) 'Thang', sum(trigia) 'Doanh thu'
from HOADON
where year(NGHD)=2006
group by MONTH(NGHD)


-- Cau 38:
select SOHD, count(masp) 'So san pham khac nhau'
from CTHD
group by SOHD
having count(masp)>=4



-- Cau 39:
Select SOHD, count(CTHD.MASP) 'So san pham khac nhau do Viet Nam san xuat'
FROM CTHD,SANPHAM
Where CTHD.MASP=SANPHAM.MASP and NUOCSX='Viet Nam'
group by SOHD
having count(CTHD.MASP) = 3 

-- Cau 40:
select top 1 with ties KHACHHANG.MAKH, HOTEN, COUNT(SOHD) 'So lan mua'
from KHACHHANG, HOADON
where KHACHHANG.MAKH = HOADON.MAKH 
group by KHACHHANG.MAKH,HOTEN
order by COUNT(SOHD) desc

-- Cau 41:
select top 1 with ties month(NGHD) 'Thang', sum(trigia) 'Doanh so'
from HOADON
where year(NGHD)=2006
group by month(NGHD)
order by sum(trigia) desc

-- Cau 42:
select top 1 with ties SANPHAM.MASP,TENSP, sum(sl) 'So luong ban ra'
from SANPHAM,CTHD,HOADON
where SANPHAM.MASP = CTHD.MASP and CTHD.SOHD=HOADON.SOHD and year(NGHD)=2006
group by SANPHAM.MASP,TENSP 
order by sum(sl) asc

-- Cau 43:
select NUOCSX, MASP, TENSP
FROM SANPHAM sp1
where GIA = (select top 1 gia from SANPHAM sp2 where NUOCSX=sp1.NUOCSX order by gia desc)



-- Cau 44:
select NUOCSX, count(distinct GIA) 'So luong muc gia ban'
from SANPHAM
group by NUOCSX
having COUNT(distinct Gia) >=3


-- Cau 45:
select top 1 with ties kh1.MAKH,HOTEN
from KHACHHANG kh1, HOADON
where kh1.MAKH = HOADON.MAKH and exists (Select top 10 * from KHACHHANG kh2 where kh1.MAKH=kh2.MAKH  order by kh2.DOANHSO desc)
group by kh1.MAKH, HOTEN
order by count(SOHD) desc

	






