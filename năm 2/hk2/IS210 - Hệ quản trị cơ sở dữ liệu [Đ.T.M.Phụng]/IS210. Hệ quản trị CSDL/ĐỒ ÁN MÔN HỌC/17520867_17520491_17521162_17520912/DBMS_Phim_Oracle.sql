------------- DELETE BANG ------------
DROP TABLE DATVE;
DROP TABLE NHANVIEN;
DROP TABLE KHACHHANG;
DROP TABLE LICHCHIEU;
DROP TABLE SUATCHIEU;
DROP TABLE Phim;
DROP TABLE TheLoaiPhim;
DROP TABLE GHE;
DROP TABLE PHONG;
-------------------
/* XÓA CÁC SEQUENCE*/
DROP SEQUENCE MAKH_SEQ1;
DROP SEQUENCE MANV_SEQ2;
DROP SEQUENCE MATL_SEQ3;
DROP SEQUENCE MAPHIM_SEQ4;
DROP SEQUENCE MASUAT_SEQ5;
DROP SEQUENCE MAPHONG_SEQ6;
DROP SEQUENCE MALICHCHIEU_SEQ7;
DROP SEQUENCE MAGHE_SEQ8;
DROP SEQUENCE MAVE_SEQ9;
------------------- BANG KHACH HANG ----
select * from khachhang
CREATE TABLE KhachHang
(
    MaKH number NOT NULL,
    Ho varchar2(10) NOT NULL,
    Ten varchar2(20) NOT NULL,
    NgaySinh date NOT NULL,
    GioiTinh varchar2(10) NOT NULL,
    LoaiKH varchar2(15) DEFAULT 'Than thiet',
    username varchar2(30) not null unique,
    password varchar2(30) not null,
    tichluy number default 0,
    CONSTRAINT PK_KH PRIMARY KEY(MaKH)
);
CREATE SEQUENCE MaKH_seq1 START WITH 1;
--------------- BANG NHAN VIEN ----
CREATE TABLE NhanVien
(
    MaNV number NOT NULL,
    Ho varchar2(10) NOT NULL,
    Ten varchar2(10) NOT NULL,
    NgaySinh date NOT NULL,
    NgayLam date NOT NULL,
    GioiTinh varchar2(10) NOT NULL,
    CMND number NOT NULL,
    SoDT varchar2 (10) NOT NULL,
    DiaChi varchar2(60) NOT NULL,
    Luong number NOT NULL,
    CONSTRAINT PK_NV PRIMARY KEY(MaNV)    
);
CREATE SEQUENCE MaNV_seq2 START WITH 1;
-------- BANG THE LOAI PHIM -------
CREATE TABLE TheLoaiPhim
(
    MaTL number NOT NULL,
    TheLoai varchar2(20) NOT NULL,
    GhiChu varchar2(200),
    CONSTRAINT PK_TheLoai PRIMARY KEY(MaTL)
);
CREATE SEQUENCE MaTL_seq3 START WITH 1;
-------- BANG PHIM -------
CREATE TABLE Phim
(
    MaPhim number NOT NULL,
    TenPhim varchar2(50) NOT NULL,
    MaTL number CONSTRAINT FK_TL_PHIM REFERENCES TheLoaiPhim(MaTL) NOT NULL,
    DaoDien varchar2(50) NOT NULL,
    NhaPH varchar2(50) NOT NULL,
    DienVien varchar(250) NOT NULL,
    NgayPH date NOT NULL,
    DoanhThu number DEFAULT 0,
    Thoiluong date NOT NULL,
    HinhAnh varchar2(500),
    CONSTRAINT PK_PHIM PRIMARY KEY(MaPhim)
);
CREATE SEQUENCE MaPhim_seq4 START WITH 1;
------ BANG SUAT CHIEU ------
CREATE  TABLE SuatChieu
(
    MaSuat number NOT NULL,
    ThoiGianBD date NOT NULL,
    NgayChieu date NOT NULL,
    CONSTRAINT PK_SC PRIMARY KEY (MaSuat)
);
CREATE SEQUENCE MaSuat_seq5 START WITH 1;
------ BANG PHONG --------
CREATE TABLE Phong
(
    MaPhong number NOT NULL,
    TenPhong varchar2(20) NOT NULL,
    SoLuongGhe number NOT NULL,
    CONSTRAINT PK_PHONG PRIMARY KEY(MaPhong)
);
CREATE SEQUENCE MaPhong_seq6 START WITH 1;
------ BANG LICH CHIEU -----
CREATE TABLE LichChieu
(
    MaLichChieu number NOT NULL,
    MaSuat number CONSTRAINT FK_SC_LC REFERENCES SuatChieu(MaSuat)NOT NULL,
    MaPhim number CONSTRAINT FK_PHIM_LC REFERENCES Phim(MaPhim)NOT NULL,
    MaPhong number CONSTRAINT FK_PHONG_LC REFERENCES Phong(MaPhong) NOT NULL,
    CONSTRAINT PK_LC PRIMARY KEY (MaLichChieu)
);
CREATE SEQUENCE MaLichChieu_seq7 START WITH 1;
------ BANG GHE -----
CREATE TABLE Ghe
(
    MaGhe number NOT NULL,
    SoGhe varchar2(10) NOT NULL,
    HangGhe varchar2(10) NOT NULL,
    MaPhong number CONSTRAINT FK_GHE_PHONG REFERENCES Phong(MaPhong) NOT NULL,
    LoaiGhe varchar2(20) NOT NULL,
    CONSTRAINT PK_GHE PRIMARY KEY(MaGhe)
);
CREATE SEQUENCE MaGhe_seq8 START WITH 1;
----- BANG DAT VE -----
CREATE TABLE DatVe
(
    MaVe number NOT NULL,
    MaKH number CONSTRAINT FK_DV_KH REFERENCES KhachHang(MaKH) NOT NULL,
    MaNV number  CONSTRAINT FK_DV_NV REFERENCES NhanVien(MaNV),
    MaLichChieu number CONSTRAINT FK_DV_LC REFERENCES LichChieu(MaLichChieu)NOT NULL,
    MaGhe number CONSTRAINT FK_DV_GHE REFERENCES Ghe(MaGhe) NOT NULL,
    Gia number default 100000,
    NgayBan date NOT NULL,
    HinhThuc varchar2(20) NOT NULL,
    ThanhToan varchar2(20) NOT NULL,
    CONSTRAINT PK_DV PRIMARY KEY(MaVe)
);
CREATE SEQUENCE MAVE_SEQ9 START WITH 1;
----- INSERT DU LIEU KHACH HANG-----
ALTER SESSION SET NLS_DATE_FORMAT ='DD/MM/YYYY HH24:MI:SS';
SELECT * FROM KHACHHANG
INSERT INTO KHACHHANG(MAKH,HO,TEN,NGAYSINH,GIOITINH,USERNAME,PASSWORD) VALUES (MaKH_seq1.nextval,'TRAN','VAN NHU Y','16/04/1991','Nam','nhuy1999','nhuy123');
INSERT INTO KHACHHANG(MAKH,HO,TEN,NGAYSINH,GIOITINH,USERNAME,PASSWORD) VALUES (MaKH_seq1.nextval,'LE','THI TRUC HOA','02/09/1999','Nu','truchoa1999','duyphuong123');
INSERT INTO KHACHHANG(MAKH,HO,TEN,NGAYSINH,GIOITINH,USERNAME,PASSWORD) VALUES (MaKH_seq1.nextval,'HOANG','THUY TRINH','05/01/1999','Nu','thuytrinh1999','thuytrinh123');
INSERT INTO KHACHHANG(MAKH,HO,TEN,NGAYSINH,GIOITINH,USERNAME,PASSWORD) VALUES (MaKH_seq1.nextval,'PHAM','NHAT TUAN','01/01/1999','Nam','nhattuan1999','nhattuan123');
---- INSERT DU LIEU NHAN VIEN ----
SELECT * FROM NHANVIEN
INSERT INTO NhanVien VALUES (MaNV_seq2.nextval,'LE','HOA',to_date('02-09-1999','dd-mm-yyyy'),To_Date('03-02-2019','dd-mm-yyyy'),'Nu','251476647','0374349383','324/8 Xo Viet Nghe Tinh, quan Binh Thanh, TP Ho Chi Minh',5000000);
INSERT INTO NhanVien VALUES (MaNV_seq2.nextval,'PHAM','QUANG',to_date('13-04-1997','dd-mm-yyyy'),To_Date('14-02-2019','dd-mm-yyyy'),'Nam','251875678','0964133688','150 Nguyen Thi Minh Khai, quan 1, TP Ho Chi Minh',5000000);
INSERT INTO NhanVien VALUES (MaNV_seq2.nextval,'HOANG','TRINH',to_date('05-01-1999','dd-mm-yyyy'),To_Date('11-04-2019','dd-mm-yyyy'),'Nu','25855449','0934140608','33 Le Loi, quan 9, TP Ho Chi Minh',5000000);
INSERT INTO NhanVien VALUES (MaNV_seq2.nextval,'NGUYEN ','NHUT',to_date('09-07-1999','dd-mm-yyyy'),To_Date('05-02-2019','dd-mm-yyyy'),'Nam','24545372','0933456711','1060 Truong Sa, quan 3, TP Ho Chi Minh',5000000);
INSERT INTO NhanVien VALUES (MaNV_seq2.nextval,'PHAM','TUAN',to_date('24-08-1998','dd-mm-yyyy'),To_Date('18-01-2019','dd-mm-yyyy'),'Nam','25124088','0934340849','373 Ly Thuong Kiet, quan Tan Binh, TP Ho Chi Minh',5000000);
---- INSERT DU LIEU BANG THE LOAI PHIM -----
SELECT * FROM THELOAIPHIM
INSERT INTO TheLoaiPhim VALUES (MaTL_seq3.nextval,'Hanh Dong',null);
INSERT INTO TheLoaiPhim VALUES (MaTL_seq3.nextval,'Tinh cam',null);
INSERT INTO TheLoaiPhim VALUES (MaTL_seq3.nextval,'Tam Ly',null);
INSERT INTO TheLoaiPhim VALUES (MaTL_seq3.nextval,'Kinh di',null);
INSERT INTO TheLoaiPhim VALUES (MaTL_seq3.nextval,'Hai Huoc',null);
---- INSERT Du lieu bang phim ----
SELECT DISTINCT * FROM PHIM,THELOAIPHIM WHERE PHIM.MATL = THELOAIPHIM.MATL;
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,DIENVIEN,NGAYPH,THOILUONG) VALUES (MaPhim_seq4.nextval,'Cua lai vo bau',2,'Nhat Trung','Galaxy','Tran Thanh, Ninh Duong Lan Ngoc',to_date('01-01-2019','dd-mm-yy'),to_date('21:00','hh24:mi:ss'));
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,DIENVIEN,NGAYPH,THOILUONG) VALUES (MaPhim_seq4.nextval,'Aquaman',1,'Jame Wan','Warner Bros','Joson Momoa, Amber Heard',to_date('27-07-2018','dd-mm-yy'));
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,DIENVIEN,NGAYPH,THOILUONG) VALUES (MaPhim_seq4.nextval,'Annabelle Comes Home',4,'Gary Dauberman','Atomic Monster Productions','Annabelle Wallis, Ward Horton',to_date('27-04-2019','dd-mm-yy'));
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,DIENVIEN,NGAYPH,THOILUONG) VALUES (MaPhim_seq4.nextval,'Thap Tam Muoi',5,'Khuong Ngoc,To Gia Tuan','Galaxy','Thu Trang, Tien Luat',to_date('29-03-2019','dd-mm-yy'));
---INSERT Du Lieu SUAT CHIEU----
select to_char(ThoigianBd,'hh24:mi:ss')from SuatChieu
select * from suatchieu;
delete from suatchieu
update suatchieu
set thoigianbd = to_date('00:00','hh24:mi:ss')
where masuat = 31;
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('21:00','hh24:mi:ss'),to_date('01-01-2019','dd-mm-yyyy'));

INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('21:00','hh24:mi:ss'),to_date('01-01-2019','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('23:00','hh24:mi:ss'),to_date('01-01-2019','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('19:00','hh24:mi:ss'),to_date('27-07-2018','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('12:00','hh24:mi:ss'),to_date('27-04-2019','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('17:00','hh24:mi:ss'),to_date('29-03-2019','dd-mm-yyyy'));
---- INSERT Phong chieu ----
    select * from phong
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 01',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 02',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 03',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 04',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 05',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 06',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 07',50);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 08',50);
----- INSERT LICHCHIEU----
SELECT * FROM LICHCHIEU
/*DELETE FROM LICHCHIEU
DROP SEQUENCE MALICHCHIEU_SEQ7*/
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,1,1,1);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,1,2,2);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,2,3,3);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,2,4,1);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,3,1,8);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,3,2,7);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,3,2,6);
INSERT INTO LichChieu VALUES (MaLichChieu_seq7.nextval,3,2,4);
----- INSERT INTO GHE ----
SELECT * FROM GHE
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,1,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,1,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,1,'Thuong');
--Phong2
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,2,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,2,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,2,'Thuong');
--Phong3
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,3,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,3,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,3,'Thuong');
--Phong4
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,4,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,4,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,4,'Thuong');
--Phong5
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,5,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,5,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,5,'Thuong');
--Phong6
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,6,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,6,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,6,'Thuong');

--Phong8
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,1,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,2,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,3,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,4,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,5,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,6,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,7,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,8,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,9,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,1,0,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,1,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,2,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,3,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,4,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,5,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,6,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,7,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,8,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,9,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,2,0,8,'VIP');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,1,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,2,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,3,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,4,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,5,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,6,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,7,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,8,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,9,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,3,0,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,1,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,2,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,3,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,4,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,5,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,6,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,7,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,8,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,9,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,4,0,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,1,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,2,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,3,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,4,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,5,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,6,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,7,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,8,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,9,8,'Thuong');
INSERT INTO GHE VALUES (Maghe_seq8.nextval,5,0,8,'Thuong');
----- insert dat ve ---
select * from Datve
SELECT * FROM KHACHHANG
update khachhang set ngaysinh ='26-06-1999' where makh=1;
SELECT * FROM NHANVIEN
select * from phim
SELECt * FROM GHE
SELECT * FROM LICHCHIEU
SELECT * FROM PHONG
UPDATE DATVE
SET GIA = 75000
WHERE MAVE =26;
DELETE FROM DATVE WHERE MAKH =1
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,95,500,to_date('26-05-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,95,361,to_date('21-06-2018','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,9,51,to_date('21-06-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,9,52,to_date('22-06-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,2,2,to_date('01-01-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,2,3,2,to_date('27-07-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,2,2,to_date('27-07-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,2,3,4,2,to_date('28-05-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,2,4,2,3,to_date('27-07-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,3,3,3,3,to_date('27-07-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,3,4,4,4,to_date('29-03-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,3,5,3,2,to_date('29-03-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,4,2,4,3,to_date('29-03-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,2,2,to_date('01-01-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,1,3,to_date('01-01-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
------------------------------------------
/*Loai khach hang gom Than Thien, VIP, SuperVIP*/
ALTER TABLE KHACHHANG
ADD CHECK (LOAIKH IN('Than thiet','VIP','Super VIP'));
/*Ghe co hai loai VIP, Normal*/
ALTER TABLE GHE
ADD CHECK(LOAIGHE in('VIP','Thuong'));
/*Hinh thuc thanh toan co hai hinh thuc online va offline*/
ALTER TABLE DATVE
ADD CHECK(HINHTHUC IN('ONLINE','OFFLINE'));
/*Hinh thuc thanh toan la tien mat hoac the*/
ALTER TABLE DATVE
ADD CHECK(THANHTOAN IN('Tien Mat','The'));
/*Gi?i tính khach hang la 'Nam' hoac  'Nu'*/
ALTER TABLE KHACHHANG
ADD CHECK (GIOITINH IN('Nam','Nu'));
/*Gi?i tính khach hang la 'Nam' hoac  'Nu'*/
ALTER TABLE NHANVIEN
ADD CHECK (GIOITINH IN('Nam','Nu'));
-----------------------------------------------------------------
/*Danh sách TRIGGER HE THONG*/
-----------------------------------------------------------------
/*Trigger 1. Khi khach hang them mot ve moi maghe va malichchieu phai cung mot phong.*/
SET SERVEROUTPUT ON;
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_DATVE_PHONG
AFTER INSERT OR UPDATE ON DATVE
FOR EACH ROW
DECLARE
    v_phong1 phong.maphong%TYPE;
    v_phong2 phong.maphong%TYPE;
BEGIN
    SELECT MAPHONG INTO V_PHONG1
    FROM LICHCHIEU
    WHERE MALICHCHIEU = :NEW.MALICHCHIEU;
    
    SELECT MAPHONG INTO V_PHONG2
    FROM GHE
    WHERE MAGHE =:NEW.MAGHE;
    
    IF(v_PHONG1 != v_PHONG2)
    THEN
        RAISE_APPLICATION_ERROR(-20000, 'Ngay sinh cua khach hang < ngay dat');
    ELSE 
        DBMS_OUTPUT.PUT_LINE('Ghe, lich chieu phai chung mot phong!');
    end if;
END;
-----------------------------------------------------------------
/*Trigger 2. Khi update mat khách hàng ngày sinh cua khách hàng < ngày dat vé*/
SET SERVEROUTPUT ON;
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_BANVE_KHACHHANG
AFTER UPDATE OF NGAYSINH ON KHACHHANG
FOR EACH ROW
DECLARE
    v_ngayban DATVE.NGAYBAN%TYPE;
    cur_kv DATVE.MAKH%type;
    CURSOR cur IS SELECT MAVE
                FROM DATVE
                WHERE MAKH =:NEW.MAKH;
BEGIN
    OPEN cur;
    LOOP
        FETCH cur INTO cur_kv;
        EXIT WHEN cur%NOTFOUND;
        SELECT DV.NGAYBAN INTO V_NGAYBAN
        FROM DATVE DV
        WHERE DV.MAVE =CUR_KV;
        IF(:NEW.NGAYSINH > V_NGAYBAN)
        then
          RAISE_APPLICATION_ERROR(-20002, 'Ngay sinh cua khach hang phai be hon ngay ban ve');
          ELSE 
           DBMS_OUTPUT.PUT_LINE('Sua thanh cong');
        end if;
    END LOOP;
END;
-----------------------------------------------------------------
/*Trigger 3. Khi insert, update m?t vé m?i thì ngày mua vé > ngày sinh c?a khách hàng*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_KHACHHANG_BANVE
AFTER INSERT OR UPDATE OF NGAYBAN ON DATVE
FOR EACH ROW
DECLARE
    v_ngaysinh KHACHHANG.NGAYSINH%TYPE;
BEGIN
    SELECT KHACHHANG.NGAYSINH INTO V_NGAYSINH
    FROM KHACHHANG
    WHERE KHACHHANG.MAKH =:NEW.MAKH;
    IF(:NEW.NGAYBAN < V_NGAYSINH)
    THEN
      RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
    END IF;
END;
-----------------------------------------------------------------
/*Trigger 4. Khi update mot bo phim ngay phat hanh < ngay ban ve*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_BANVE_PHIM
AFTER UPDATE OF NGAYPH ON PHIM
FOR EACH ROW
DECLARE
    v_ngayban DATVE.NGAYBAN%TYPE;
    cur_kv DATVE.MALICHCHIEU%type;
    CURSOR cur IS SELECT MAVE
                FROM DATVE, LICHCHIEU
                WHERE DATVE.MALICHCHIEU= LICHCHIEU.MALICHCHIEU AND LICHCHIEU.MAPHIM =:NEW.MAPHIM;
BEGIN
    OPEN cur;
    LOOP
        FETCH cur INTO cur_kv;
        EXIT WHEN cur%NOTFOUND;
        SELECT DV.NGAYBAN INTO V_NGAYBAN
        FROM DATVE DV
        WHERE DV.MAVE =CUR_KV;
        IF(:NEW.NGAYPH > V_NGAYBAN)
        then
          RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
          ELSE 
           DBMS_OUTPUT.PUT_LINE('Sua thanh cong');
        end if;
    END LOOP;
END;
-----------------------------------------------------------------
/*Trigger 5. Khi insert, update mot ve thi ngay ban ve > ngay phat hanh cua bo phim*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_phim_banve
AFTER INSERT OR UPDATE OF NGAYBAN ON DATVE
FOR EACH ROW
DECLARE
    v_ngayphathanh PHIM.NGAYPH%TYPE;
BEGIN
    SELECT PHIM.NGAYPH INTO v_ngayphathanh
    FROM PHIM,LICHCHIEU
    WHERE PHIM.MAPHIM =LICHCHIEU.MAPHIM AND LICHCHIEU.MALICHCHIEU=:NEW.MALICHCHIEU;
    IF(:NEW.NGAYBAN < V_ngayphathanh)
    THEN
      RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
    END IF;
END;
-----------------------------------------------------------------
/*Trigger 6. Khi insert, update mot ve moi thi doanh thu cung thay doi*/
DROP TRIGGER TRIGGER_PHIM_DOANHTHU_new
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_PHIM_DOANHTHU_new
AFTER INSERT OR UPDATE OF GIA ON DATVE
FOR EACH ROW
DECLARE
    v_MaPhim PHIM.MAPHIM%TYPE;
BEGIN
    SELECT phim.MAPHIM INTO V_MAPHIM
    FROM PHIM,LICHCHIEU
    WHERE PHIM.MAPHIM =LICHCHIEU.MAPHIM AND LICHCHIEU.MALICHCHIEU=:NEW.MALICHCHIEU;
    
    UPDATE PHIM
    SET DOANHTHU= DOANHTHU + :NEW.GIA
    WHERE MAPHIM = V_MAPHIM;
END;
-----------------------------------------------------------------
/*Trigger 7. Khi xoa mot ve thi doanh thu cua phim cung giam theo--*/
DROP TRIGGER TRIGGER_PHIM_DOANHTHU_old
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_PHIM_DOANHTHU_old
AFTER DELETE OR UPDATE OF GIA ON DATVE
FOR EACH ROW
DECLARE
    v_MaPhim PHIM.MAPHIM%TYPE;
BEGIN
    SELECT phim.MAPHIM INTO V_MAPHIM
    FROM PHIM,LICHCHIEU
    WHERE PHIM.MAPHIM =LICHCHIEU.MAPHIM AND LICHCHIEU.MALICHCHIEU=:OLD.MALICHCHIEU;
    
    UPDATE PHIM
    SET DOANHTHU= DOANHTHU - :OLD.GIA
    WHERE MAPHIM = V_MAPHIM;
END;
-----------------------------------------------------------------
/*Trigger 8. Cap nhat loai khach hang*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_KHACHHANG_TICHLUY_NEW
AFTER INSERT OR UPDATE OF GIA ON DATVE
FOR EACH ROW
DECLARE
    v_MaKH KHACHHANG.MAKH%TYPE;
    v_Tichluy KHACHHANG.TICHLUY%TYPE;
BEGIN
    SELECT MAKH INTO V_MAKH
    FROM KHACHHANG
    WHERE MAKH = :NEW.MAKH;
    
    UPDATE KHACHHANG
    SET TICHLUY=TICHLUY + :NEW.GIA
    WHERE MAKH = V_MAKH;
    
    SELECT TICHLUY INTO V_TICHLUY
    FROM KHACHHANG
    WHERE MAKH = :NEW.MAKH;
    IF(V_TICHLUY > 500000)
    THEN
        UPDATE KHACHHANG
        SET LOAIKH = 'VIP'
        WHERE MAKH = :NEW.MAKH;
    end if;
    IF(V_TICHLUY > 1500000)
    THEN
        UPDATE KHACHHANG
        SET LOAIKH ='Super VIP'
        WHERE MAKH = :NEW.MAKH;
    END IF;
END;
-----------------------------------------------------------------
/*Trigger 9. Xoa mot ve*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_KHACHHANG_TICHLUY_OLD
AFTER DELETE OR UPDATE OF GIA ON DATVE
FOR EACH ROW
DECLARE
    v_MaKH KHACHHANG.MAKH%TYPE;
    v_Tichluy KHACHHANG.TICHLUY%TYPE;
BEGIN
    SELECT MAKH INTO V_MAKH
    FROM KHACHHANG
    WHERE MAKH = :OLD.MAKH;
    
    UPDATE KHACHHANG
    SET TICHLUY=TICHLUY - :OLD.GIA
    WHERE MAKH = V_MAKH;
    
    SELECT TICHLUY INTO V_TICHLUY
    FROM KHACHHANG
    WHERE MAKH = :OLD.MAKH;
    IF(V_TICHLUY <= 500000)
    THEN
        UPDATE KHACHHANG
        SET LOAIKH = 'Than thiet'
        WHERE MAKH = :OLD.MAKH;
    END IF;
    IF(V_TICHLUY < 1500000 and V_TICHLUY >500000)
    THEN
        UPDATE KHACHHANG
        SET LOAIKH = 'VIP'
        WHERE MAKH = :OLD.MAKH;
    END IF;
END;
/*Trigger 10*/
create or replace TRIGGER TRIGGER_DATVE_PHONG_RB
BEFORE INSERT ON DATVE
FOR EACH ROW
DECLARE
    v_phong1 phong.maphong%TYPE;
    v_phong2 phong.maphong%TYPE;
    v_count number;
BEGIN
    SELECT MAPHONG INTO V_PHONG1
    FROM LICHCHIEU
    WHERE MALICHCHIEU = :NEW.MALICHCHIEU;

    SELECT MAPHONG INTO V_PHONG2
    FROM GHE
    WHERE MAGHE =:NEW.MAGHE;

    IF(v_PHONG1 != v_PHONG2)
    THEN
        RAISE_APPLICATION_ERROR(-2000, 'Thanh Cong');
    ELSE 
        DBMS_OUTPUT.PUT_LINE('Ghe, lich chieu phai chung mot phong!');
    end if;

    SELECT COUNT(*) INTO V_COUNT
    FROM DATVE
    WHERE MALICHCHIEU = :NEW.MALICHCHIEU AND MAGHE= :NEW.MAGHE;

    IF(V_COUNT >0)
    THEN
         RAISE_APPLICATION_ERROR(-20000, 'Ve da duoc dat roi!');
    END IF;
END;
/*Trigger 11*/
select * from lichchieu
create or replace TRIGGER TRIGGER_LICHCHIEU_RB
BEFORE INSERT OR UPDATE ON LICHCHIEU
FOR EACH ROW
DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
    v_count number;
BEGIN
    IF UPDATING OR INSERTING THEN 
    
        SELECT COUNT(*) INTO V_COUNT
        FROM lichchieu
        WHERE masuat =:new.masuat and maphong =:new.maphong;

    IF(V_COUNT >0)
    THEN
         RAISE_APPLICATION_ERROR(-20005,'L?ch ?ã có r?i!');
         COMMIT;
    END IF;
    END IF;
END;

/*Trigger 12*/

create or replace TRIGGER TRIGGER_SUATCHIEU_RB
BEFORE INSERT OR UPDATE ON SUATCHIEU
FOR EACH ROW
DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
    v_count number;
BEGIN
    IF UPDATING OR INSERTING THEN 
    
        SELECT COUNT(*) INTO V_COUNT
        FROM SUATCHIEU
        WHERE thoigianbd =:NEW.ThoigianBD and ngaychieu =:new.ngaychieu;

    IF(V_COUNT >0)
    THEN
         RAISE_APPLICATION_ERROR(-20004, 'Thoi gian bat dau cua ngay nay da co roi!');
         COMMIT;
    END IF;
    END IF;
END;

-----------------------------------------------------------------
/*He thong Store Procedure*/
/*Giai quyet van de lost update*/
-----------------------------------------------------------------
/*2.Hàm Sleep*/
CREATE OR REPLACE PROCEDURE sleep (in_time number)
AS
 v_now date;
BEGIN
 SELECT SYSDATE
 INTO v_now
 FROM DUAL;

 LOOP
 EXIT WHEN v_now + (in_time * (1/86400)) <= SYSDATE;
 END LOOP;
end;

/*-------------------------------------------------------------
update phim set doanhthu=0 where maphim =3;
select * from phim
select * from lichchieu
select * from khachhang;
select * from ghe;
delete from datve where malichchieu=101;
select * from datve;

begin
    SET TRANSACTION ISOLATION LEVEL Serializable;
    sp_datve(1,1,51,51,'ONLINE','The');
    SLEEP(10);
    COMMIT;
end;
select * from phim where tenphim ='Cua lai vo bau'
-----------------------------------------------------------------*/

CREATE OR REPLACE PROCEDURE sp_datve(in_makh in DATVE.MAKH%TYPE, in_manv in DATVE.MANV%TYPE, in_lichchieu in DATVE.MALICHCHIEU%TYPE, in_maghe in DATVE.MAGHE%TYPE,/*in_ngay in DATVE.NGAYBAN%type,*/in_hinhthuc in DATVE.Hinhthuc%TYPE, in_thanhtoan in DATVE.THANHTOAN%TYPE)
IS
    v_loaikh KHACHHANG.LOAIKH%type;
    v_loaighe GHE.LOAIGHE%type;
    v_gia DATVE.GIA%TYPE;
    v_date DATVE.NGAYBAN%TYPE;
    v_phim PHIM.MAPHIM%TYPE;
    v_doanhthu PHIM.DOANHTHU%TYPE;
BEGIN
    SELECT LOAIKH into v_loaikh
    FROM KHACHHANG
    WHERE MAKH = in_makh;
    
    SELECT LOAIGHE into v_loaighe
    FROM GHE,LICHCHIEU,PHONG
    WHERE LICHCHIEU.MAPHONG = PHONG.MAPHONG AND GHE.MAPHONG = PHONG.MAPHONG AND LICHCHIEU.MALICHCHIEU = in_lichchieu and ghe.maghe = in_maghe;
    IF(v_loaighe='VIP')
    THEN
        IF (v_loaikh = 'Than thiet')
        THEN 
            v_gia := 100000*0.95;
        ELSIF (v_loaikh ='VIP')
        THEN
            v_gia := 100000*0.9;
        ELSE v_gia := 100000*0.85;
        END IF; 
    ELSIF(v_loaighe ='Thuong')
    THEN
        IF (v_loaikh = 'Than thiet')
        THEN 
            v_gia := 75000*0.95;
        ELSIF (v_loaikh ='VIP')
        THEN
            v_gia := 75000*0.9;
        ELSE v_gia := 75000*0.85;
        END IF;
    END IF;
    SELECT SYSDATE INTO v_date
    FROM DUAL;
  
     SET TRANSACTION ISOLATION LEVEL Read committed;
    INSERT INTO DATVE VALUES(Mave_seq9.nextval, in_makh, in_manv, in_lichchieu, in_maghe, v_gia, v_date, in_hinhthuc, in_thanhtoan);
        LOCK TABLE DATVE IN EXCLUSIVE MODE;
    
    SLEEP(10);
    COMMIT;
 
END;
-----------------------------------------------------------------
---- Vi?t Store thông tin khách hàng----
select * from khachhang;
SET SERVEROUTPUT ON;
create or replace procedure sp_inthongtinKH (in_username IN KHACHHANG.USERNAME%TYPE)
IS
    makh_in KHACHHANG.MAKH%TYPE;
    ho_in KHACHHANG.HO%TYPE;
    ten_in KHACHHANG.TEN%TYPE;
    ngay_in KHACHHANG.ngaysinh%type;
    sex_in KHACHHANG.gioitinh%type;
    loai_in KHACHHANG.LOAIKH%TYPE;
    tich_luy KHACHHANG.tichluy%TYPE;
begin
    SET TRANSACTION ISOLATION LEVEL read committed;
    SELECT MAKH,HO,TEN,NGAYSINH,GIOITINH,LOAIKH,TICHLUY INTO MAKH_IN,HO_IN,TEN_IN,NGAY_IN,SEX_IN,LOAI_IN,TICH_LUY
    FROM KHACHHANG
    WHERE USERNAME=IN_USERNAME;
    DBMS_OUTPUT.PUT_LINE('Day là thông tin cua khách hàng:'||in_username||'-----------');
    DBMS_OUTPUT.PUT_LINE('Ma Khách hàng:'||MAKH_IN);
    DBMS_OUTPUT.PUT_LINE('Ho:'||HO_IN);
    DBMS_OUTPUT.PUT_LINE('Ten:'||TEN_IN);
    DBMS_OUTPUT.PUT_LINE('Ngay sinh:'||LOAI_IN);
    DBMS_OUTPUT.PUT_LINE('Gioi tinh:'||SEX_IN);
    DBMS_OUTPUT.PUT_LINE('Tich luy:'||TICH_LUY);
    DBMS_OUTPUT.PUT_LINE('-----------------------------------------------------------');
end;
--- Test---
select * from khachhang;
DECLARE

BEGIN
    sp_inthongtinKH('truchoa1999');
END;
--- end test ---
---- Vi?t Store thông tin nhan vien----
select * from NHANVIEN
create or replace function thongtinNV_f (INf_MaNV IN NHANVIEN.MANV%TYPE)
RETURN VARCHAR2
AS
    ho_in NHANVIEN.HO%TYPE;
    ten_in NHANVIEN.TEN%TYPE;
    luong_in NHANVIEN.LUONG%TYPE;
begin
    SELECT HO,TEN,LUONG INTO HO_IN,TEN_IN,LUONG_IN
    FROM NHANVIEN
    WHERE MANV=INf_MANV;
    RETURN ''||HO_IN||' '||TEN_IN||'Luong:'||LUONG_IN;
end;
set SERVEROUTPUT ON;
DECLARE 
    result varchar2(10000);
BEGIN
    result:=THONGTINNV_F(1);
    DBMS_OUTPUT.PUT_LINE(result);
END;
---- Viet Procedure in ra thong tin phim ----
set serveroutput on;
select * from phim;
CREATE OR REPLACE PROCEDURE sp_inthongtinphim(MAPHIM_IN IN PHIM.MAPHIM%TYPE)
IS
    TENPHIM_IN PHIM.TENPHIM%type;
    DAODIEN_IN PHIM.DAODIEN%TYPE;
    NHAPH_IN PHIM.NHAPH%TYPE;
    NGAYPH_IN PHIM.NGAYPH%TYPE;
    DIENVIEN_IN PHIM.DIENVIEN%TYPE;
    PHONG_IN LICHCHIEU.MAPHONG%TYPE;
    DOANHTHU_IN PHIM.DOANHTHU%TYPE;
    CUR_MALICHCHIEU LICHCHIEU.MALICHCHIEU%TYPE;
    CURSOR CUR IS SELECT LICHCHIEU.MALICHCHIEU
                    FROM LICHCHIEU
                    WHERE LICHCHIEU.MAPHIM = MAPHIM_IN;
BEGIN
    SELECT TENPHIM,DAODIEN,NHAPH,NGAYPH,DIENVIEN,DOANHTHU
    INTO TENPHIM_IN,DAODIEN_IN, NHAPH_IN,NGAYPH_IN,DIENVIEN_IN,DOANHTHU_IN
    FROM PHIM
    WHERE PHIM.MAPHIM = MAPHIM_IN;
    DBMS_OUTPUT.PUT_LINE('Bo phim can tim là'); 
    DBMS_OUTPUT.PUT_LINE('** Tên phim: '||TENPHIM_IN||' (Dao dien: '||DAODIEN_IN||')');
    DBMS_OUTPUT.PUT_LINE('** Ngày phát hành: '||NGAYPH_IN||' Nhà phát hành: '||NHAPH_IN);
    DBMS_OUTPUT.PUT_LINE('Danh sách dien viên tham gia');
    DBMS_OUTPUT.PUT_LINE(DIENVIEN_IN);
    DBMS_OUTPUT.PUT_LINE('** Doanh thu phim: '||DOANHTHU_IN);
    OPEN CUR;
    LOOP
      FETCH CUR INTO CUR_MALICHCHIEU;
      EXIT WHEN CUR%NOTFOUND;
      SELECT LICHCHIEU.MAPHONG
      INTO PHONG_IN
      FROM LICHCHIEU
      WHERE LICHCHIEU.MALICHCHIEU=CUR_MALICHCHIEU;
      DBMS_OUTPUT.PUT_LINE('----- Lich chieu: '||CUR_MALICHCHIEU||' PHONG SO: '||PHONG_IN);
    END LOOP;
END;
BEGIN
    sp_inthongtinphim(1);
END;
SELECT * FROM GHE

---- Procedure B?ng DatVe ---
CREATE OR REPLACE PROCEDURE thongtinDV(IN_MaVe IN DATVE.MaVe%TYPE)
AS
    MaKH_in DATVE.MaKH%TYPE;
    MaNV_in DATVE.MaNV%TYPE;
    MaLichChieu_in DATVE.MaLichChieu%TYPE;
    MaGhe_in DATVE.MaGhe%TYPE;
    Gia_in DATVE.Gia%TYPE;
    NgayBan_in DATVE.NgayBan%TYPE;
    HinhThuc_in DATVE.HinhThuc%TYPE;
    ThanhToan_in DATVE.ThanhToan%TYPE;
BEGIN
    SELECT MaKH, MaNV, MaLichChieu, MaGhe, Gia, NgayBan, HinhThuc, ThanhToan INTO MaKH_in,MaNV_in,MaLichChieu_in,MaGhe_in,Gia_in, NgayBan_in,HinhThuc_in,ThanhToan_in
    FROM DATVE
    WHERE MaVe=IN_MaVe;
    DBMS_OUTPUT.PUT_LINE(MaKH_in);
    DBMS_OUTPUT.PUT_LINE(MaNV_in);
    DBMS_OUTPUT.PUT_LINE(MaLichChieu_in);
    DBMS_OUTPUT.PUT_LINE(MaGhe_in);
    DBMS_OUTPUT.PUT_LINE(Gia_in);
    DBMS_OUTPUT.PUT_LINE(NgayBan_in);
    DBMS_OUTPUT.PUT_LINE(HinhThuc_in);
    DBMS_OUTPUT.PUT_LINE(ThanhToan_in);
END;
set serveroutput on;
select * from khachhang;
DECLARE
  V_N INT;
BEGIN
  V_N:=&V_N;
  for 
  THONGTINNV(V_N);
END;
---------
select * from phim
delete  from datve where mave>290
select * from datve
select * from khachhang;
select * from ghe
select * from lichchieu 
select * from phim, lichchieu where phim.maphim = lichchieu.maphim and masuat =1;
select maghe from ghe where maphong =1 and concat(to_char(soghe),to_char(hangghe))='11';
---------------------------
/*Procedure update thong tin phim*/
CREATE OR REPLACE PROCEDURE UPDATE_PHIM(v_Ma IN PHIM.MAPHIM%type, v_Ten IN PHIM.TENPHIM%type, v_MATL IN PHIM.MATL%type, v_NgPH IN PHIM.NGAYPH%type, v_TL IN PHIM.THOILUONG%type, 
v_DD IN PHIM.DAODIEN%type, v_DV IN PHIM.DIENVIEN%type, v_NPH IN PHIM.NHAPH%type, v_HINH IN PHIM.HINHANH%type) IS
BEGIN
   
  UPDATE PHIM
  SET TENPHIM = v_TEN, MATL = v_MATL, NGAYPH =v_NgPH, THOILUONG = v_TL, DAODIEN = v_DD, DIENVIEN = v_DV, NHAPH = v_NPH, HINHANH = v_HINH
  WHERE MAPHIM = v_MA;
    LOCK TABLE DATVE IN EXCLUSIVE MODE;
    SLEEP(10);
  COMMIT;
END;
CREATE OR REPLACE PROCEDURE UPDATE_PHIM(v_Ma IN PHIM.MAPHIM%type, v_Ten IN PHIM.TENPHIM%type, v_MATL IN PHIM.MATL%type, v_DD IN PHIM.DAODIEN%type, v_DV IN PHIM.DIENVIEN%type) IS
BEGIN
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
  UPDATE PHIM
  SET TENPHIM = v_TEN, MATL = v_MATL, DAODIEN = v_DD, DIENVIEN = v_DV
  WHERE MAPHIM = v_MA;
    LOCK TABLE DATVE IN EXCLUSIVE MODE;
    COMMIT;
END;
/*SELECT * FROM PHIM
BEGIN
    UPDATE_PHIM(4,'DCTC',1,'ABC','DEF');
END;

BEGIN
UPDATE_PHIM(4,'??ng c?p thú c?ng',5,'Chris Renaud','Lake Bell, Hannibal Buress, Dana Carvey, Harrison Ford,...');

END;*/
------------------
sELECT * FROM PHONG
SELECT * FROM GHE
/*Procedure Them Lich Chieu*/
SELECT * FROM SUATCHIEU
CREATE OR REPLACE PROCEDURE Insert_LICHCHIEU(v_MASUAT IN LICHCHIEU.MASUAT%type, v_MAPHIM IN LICHCHIEU.MAPHIM%type, v_MAPHONG IN LICHCHIEU.MAPHONG%type) IS
BEGIN
  INSERT INTO LICHCHIEU (MALICHCHIEU,MASUAT,MAPHIM,MAPHONG) 
  VALUES (MALICHCHIEU_SEQ7.NEXTVAL,v_MASUAT,v_MAPHIM,v_MAPHONG);
  COMMIT;
END;
SELECT * fROM LICHCHIEU
DELETE FROM LICHCHIEU WHERE MASUAT =22 AND MAPHIM = 4 AND MAPHONG =2;
BEGIN
    Insert_LICHCHIEU(22,4,2);
END;
/*Deadlock*/
CREATE OR REPLACE PROCEDURE UPDATE_PHIM(v_Ma IN PHIM.MAPHIM%type, v_Ten IN PHIM.TENPHIM%type, v_MATL IN PHIM.MATL%type, v_DD IN PHIM.DAODIEN%type, v_DV IN PHIM.DIENVIEN%type) IS
BEGIN

  UPDATE PHIM
  SET TENPHIM = v_TEN, MATL = v_MATL, DAODIEN = v_DD, DIENVIEN = v_DV
  WHERE MAPHIM = v_MA;
   LOCK TABLE DATVE IN EXCLUSIVE MODE;

END;
SELECT * FROM PHIM;
BEGIN
    UPDATE_PHIM(4,'??ng c?p thú c?ng',5,'Chris Renaud','Lake Bell, Hannibal Buress, Dana Carvey, Harrison Ford,...');
    UPDATE_PHIM(1,'Câu chuy?n ?? ch?i',5,'Josh Cooley','Tom Hanks, Tim Allen, Joan Cusack');

END;

BEGIN
    UPDATE_PHIM(4,'DCTC',5,'Chris Renaud','Lake Bell, Hannibal Buress, Dana Carvey, Harrison Ford,...');
    SLEEP(10);
 
    UPDATE_PHIM(1,'CCDC',5,'Josh Cooley','Tom Hanks, Tim Allen, Joan Cusack');
    COMMIT;
END;
------------------------------
/*Cac procedure insert*/

--Procedure INSERT
--Procedure INSERT KHACHHANG
CREATE OR REPLACE PROCEDURE Insert_KHACHHANG(v_Ho IN KHACHHANG.HO%type, v_Ten IN KHACHHANG.TEN%type, v_NgS IN KHACHHANG.NGAYSINH%type, 
v_GT IN KHACHHANG.GIOITINH%type, v_tk IN KHACHHANG.USERNAME%type, v_mk IN KHACHHANG.PASSWORD%type) IS
BEGIN
  INSERT INTO KHACHHANG (MAKH,HO,TEN,NGAYSINH,GIOITINH,USERNAME,PASSWORD) 
  VALUES (MAKH_SEQ1.NEXTVAL,v_Ho,v_Ten,v_NgS,v_GT,v_tk,v_mk);
  COMMIT;
END;

--Procedure INSERT NHANVIEN
CREATE OR REPLACE PROCEDURE Insert_NHANVIEN(v_Ho IN NHANVIEN.HO%type, v_Ten IN NHANVIEN.TEN%type, v_NgS IN NHANVIEN.NGAYSINH%type, 
v_NgL IN NHANVIEN.NGAYLAM%type, v_GT IN NHANVIEN.GIOITINH%type, v_CMND IN NHANVIEN.CMND%type, v_SDT IN NHANVIEN.SODT%type,v_DC IN NHANVIEN.DIACHI%type) IS
BEGIN
  INSERT INTO NHANVIEN (MANV,HO,TEN,NGAYSINH,NGAYLAM,GIOITINH,CMND,SODT,DIACHI) 
  VALUES (MANV_SEQ2.NEXTVAL,v_Ho,v_Ten,v_NgS,v_NgL,v_GT,v_CMND,v_SDT,v_DC);
  COMMIT;
END;

--Procedure INSERT THELOAIPHIM
CREATE OR REPLACE PROCEDURE Insert_THELOAIPHIM(v_Ten IN THELOAIPHIM.THELOAI%type, v_GC IN THELOAIPHIM.GHICHU%type) IS
BEGIN
  INSERT INTO THELOAIPHIM (MATL,THELOAI,GHICHU) VALUES (MATL_SEQ3.NEXTVAL,v_Ten,v_GC);
  COMMIT;
END;

--Procedure INSERT PHIM
CREATE OR REPLACE PROCEDURE Insert_PHIM(v_Ten IN PHIM.TENPHIM%type, v_MATL IN PHIM.MATL%type, v_NgPH IN PHIM.NGAYPH%type, v_TL IN PHIM.THOILUONG%type, 
v_DD IN PHIM.DAODIEN%type, v_DV IN PHIM.DIENVIEN%type, v_NPH IN PHIM.NHAPH%type, v_HINH IN PHIM.HINHANH%type) IS
BEGIN
  INSERT INTO PHIM (MAPHIM,TENPHIM,MATL,NGAYPH,THOILUONG,DAODIEN,DIENVIEN,NHAPH,HINHANH) 
  VALUES (MAPHIM_SEQ4.NEXTVAL,v_Ten,v_MATL,v_NgPH,v_TL,v_DD,v_DV,v_NPH,v_HINH);
  COMMIT;
END;

--Procedure INSERT PHONG
SELECT * FROM GHE
CREATE OR REPLACE PROCEDURE Insert_PHONG(v_Ten IN PHONG.TENPHONG%type, v_SL IN PHONG.SOLUONGGHE%type)
AS
    Maphong_var PHONG.MAPHONG%TYPE;
    HangGhe_var GHE.HANGGHE%TYPE;
    SoGhe_var GHE.SOGHE%TYPE;
BEGIN
  MAPHONG_VAR :=MAPHONG_SEQ6.NEXTVAL;  
  INSERT INTO PHONG (MAPHONG,TENPHONG,SOLUONGGHE) 
  VALUES (MAPHONG_VAR,v_Ten,v_SL);
  
  SOGHE_var:=1;
  HANGGHE_var:=0;
  FOR SOGHE_VAR IN 1..5
  LOOP
    IF(SOGHE_VAR<=2)
    THEN
        FOR HANGGHE_VAR IN 0..9
        LOOP
            INSERT INTO GHE VALUES (MAGHE_SEQ8.NEXTVAL,SOGHE_VAR,HANGGHE_VAR,MAPHONG_VAR,'VIP');
        END LOOP;
    ELSE
        FOR HANGGHE_VAR IN 0..9
        LOOP
            INSERT INTO GHE VALUES (MAGHE_SEQ8.NEXTVAL,SOGHE_VAR,HANGGHE_VAR,MAPHONG_VAR,'Thuong');
        END LOOP;
    END IF;
  END LOOP;
  COMMIT;
END;
BEGIN
 INSERT_PHONG('Phong_test',50);
END;
SELECT * FROM PHONG;
SELECT * FROM GHE;

--Procedure INSERT GHE
CREATE OR REPLACE PROCEDURE Insert_GHE(v_SOGHE IN GHE.SOGHE%type, v_HANGGHE IN GHE.HANGGHE%type, v_MAPHONG IN GHE.MAPHONG%type, v_LOAI IN GHE.LOAIGHE%type) IS
BEGIN
  INSERT INTO GHE (MAGHE,SOGHE,HANGGHE,MAPHONG,LOAIGHE) 
  VALUES (MAGHE_SEQ8.NEXTVAL,v_SOGHE,v_HANGGHE,v_MAPHONG,v_LOAI);
  COMMIT;
END;

--Procedure INSERT SUATCHIEU
CREATE OR REPLACE PROCEDURE Insert_SUATCHIEU(v_TGBD IN SUATCHIEU.THOIGIANBD%type, v_NGCHIEU IN SUATCHIEU.NGAYCHIEU%type) IS
BEGIN
  INSERT INTO SUATCHIEU (MASUAT,THOIGIANBD,NGAYCHIEU) 
  VALUES (MASUAT_SEQ5.NEXTVAL,v_TGBD,v_NGCHIEU);
  COMMIT;
END;

--Procedure INSERT LICHCHIEU
CREATE OR REPLACE PROCEDURE Insert_LICHCHIEU(v_MASUAT IN LICHCHIEU.MASUAT%type, v_MAPHIM IN LICHCHIEU.MAPHIM%type, v_MAPHONG IN LICHCHIEU.MAPHONG%type) IS
BEGIN
  INSERT INTO LICHCHIEU (MALICHCHIEU,MASUAT,MAPHIM,MAPHONG) 
  VALUES (MALICHCHIEU_SEQ7.NEXTVAL,v_MASUAT,v_MAPHIM,v_MAPHONG);
  COMMIT;
END;
SELECT * FROM PHIM
DELETE FROM PHIM WHERE MAPHIM = 62;