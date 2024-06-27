------------- Xóa các b?ng ------------
DROP TABLE DATVE
DROP TABLE NHANVIEN
DROP TABLE KHACHHANG
DROP TABLE LICHCHIEU
DROP TABLE SUATCHIEU
DROP TABLE Phim
DROP TABLE TheLoaiPhim
DROP TABLE GHE
DROP TABLE PHONG
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
CREATE TABLE KhachHang
(
    MaKH number NOT NULL,
    Ho varchar2(10) NOT NULL,
    Ten varchar2(20) NOT NULL,
    NgaySinh date NOT NULL,
    GioiTinh varchar2(10) NOT NULL,
    LoaiKH varchar2(15) DEFAULT 'Than Thien',
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
    TheLoai varchar2(20 ) NOT NULL,
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
    NgayPH date NOT NULL,
    DoanhThu number DEFAULT 0,
    CONSTRAINT PK_PHIM PRIMARY KEY(MaPhim)
);
CREATE SEQUENCE MaPhim_seq4 START WITH 1;
------ BANG SUAT CHIEU ------
CREATE  TABLE SuatChieu
(
    MaSuat number NOT NULL,
    ThoiGianBD date NOT NULL,
    ThoiGianKT date NOT NULL,
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
    MaNV number  CONSTRAINT FK_DV_NV REFERENCES NhanVien(MaNV) NOT NULL,
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
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,NGAYPH) VALUES (MaPhim_seq4.nextval,'Cua lai vo bau',2,'Nhat Trung','Galaxy',to_date('01-01-2019','dd-mm-yy'));
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,NGAYPH) VALUES (MaPhim_seq4.nextval,'Aquaman',1,'Jame Wan','Warner Bros',to_date('27-07-2018','dd-mm-yy'));
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,NGAYPH) VALUES (MaPhim_seq4.nextval,'Annabelle Comes Home',4,'Gary Dauberman','Atomic Monster Productions',to_date('27-04-2019','dd-mm-yy'));
INSERT INTO Phim(MAPHIM,TENPHIM,MATL,DAODIEN,NHAPH,NGAYPH) VALUES (MaPhim_seq4.nextval,'Thap Tam Muoi',5,'Khuong Ngoc,To Gia Tuan','Galaxy',to_date('29-03-2019','dd-mm-yy'));
---INSERT Du Lieu SUAT CHIEU----
select*from SuatChieu
delete from suatchieu
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('21:00','hh24:mi:ss'),to_date('22:45','hh24:mi:ss'),to_date('01-01-2019','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('19:00','hh24:mi:ss'),to_date('21:00','hh24:mi:ss'),to_date('27-07-2018','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('12:00','hh24:mi:ss'),to_date('14:50','hh24:mi:ss'),to_date('27-04-2019','dd-mm-yyyy'));
INSERT INTO SuatChieu VALUES (MaSuat_seq5.nextval,to_date('17:00','hh24:mi:ss'),to_date('19:03','hh24:mi:ss'),to_date('29-03-2019','dd-mm-yyyy'));
---- INSERT Phong chieu ----
select * from phong
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 01',150);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 02',100);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 03',100);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 04',100);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 05',150);
INSERT INTO Phong VALUES (MaPhong_seq6.nextval,'Phong 06',100);
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
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,11,'A',1,'NORMAL');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,12,'B',2,'NORMAL');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,13,'C',1,'NORMAL');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,14,'D',4,'VIP');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,15,'E',5,'NORMAL');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,16,'F',2,'NORMAL');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,17,'G',7,'VIP');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,18,'H',8,'NORMAL');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,19,'J',6,'VIP');
INSERT INTO Ghe VALUES (MaGhe_seq8.nextval,20,'K',3,'VIP');
---- Vi?t trigger v? ti?n tích l?y t?ng sau m?i l?n ??t vé -----
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_TICHLUY
AFTER INSERT OR UPDATE OF GIA ON DATVE
FOR EACH ROW
DECLARE
    V_MAKH KHACHHANG.MAKH%TYPE;
BEGIN
    SELECT MAKH INTO V_MAKH
    FROM KHACHHANG
    WHERE MAKH =:NEW.MAKH;
    
    UPDATE KHACHHANG
    SET TICHLUY = TICHLUY+:NEW.GIA
    WHERE MAKH= V_MAKH;
END;
----- insert dat ve ---
select * from Datve
SELECT * FROM KHACHHANG
DELETE  FROM DATVE
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,1,1,to_date('01-01-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,2,2,to_date('01-01-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,2,3,2,to_date('27-07-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,2,2,to_date('27-07-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,2,3,2,2,to_date('01-01-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,2,4,2,3,to_date('27-07-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,3,3,3,3,to_date('27-07-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,3,4,4,4,to_date('29-03-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,3,5,3,2,to_date('29-03-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,4,2,4,3,to_date('29-03-2019','dd-mm-yy'),'ONLINE','The');
INSERT INTO DATVE(MAVE,MAKH,MANV,MALICHCHIEU,MAGHE,NGAYBAN,HINHTHUC,THANHTOAN) VALUES (Mave_seq9.nextval,1,1,2,2,to_date('01-01-2019','dd-mm-yy'),'OFFLINE','Tien Mat');
------------------------------------------
/*Loai khach hang gom Than Thien, VIP, SuperVIP*/
ALTER TABLE KHACHHANG
ADD CHECK (LOAIKH IN('Than Thien','VIP','Super VIP'));
/*Ghe co hai loai VIP, Normal*/
ALTER TABLE GHE
ADD CHECK(LOAIGHE in('VIP','NORMAL'));
/*Hinh thuc thanh toan co hai hinh thuc online va offline*/
SELECT * FROM DATVE
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
/* Username khach hang la duy nhat*/
ALTER TABLE KHACHHANG
ADD CONSTRAINT DOC_Q UNIQUE(username);
----------- 
/*Vi?t trigger*/
/*Ngay sinh cua khach hang < ngay ban ve*/
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
          RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
          ELSE 
           DBMS_OUTPUT.PUT_LINE('Sua thanh cong');
        end if;
    END LOOP;
END;
SELECT * FROM KHACHHANG;
SELECT * FROM DATVE;
UPDATE KHACHHANG
SET NGAYSINH ='01/02/2021'
WHERE MAKH =1;
---- Trigger tren bang DATVE----
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
SELECT * FROM DATVE
INSERT INTO DatVe VALUES (10,1,1,1,4,to_date('29-03-2099','dd-mm-yy'),'ONLINE','The');
/*Ngay sinh cua nhan vien  < ngay ban ve*/
SET SERVEROUTPUT ON;
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_BANVE_NHANVIEN
AFTER UPDATE OF NGAYSINH ON NHANVIEN
FOR EACH ROW
DECLARE
    v_ngayban DATVE.NGAYBAN%TYPE;
    cur_kv DATVE.MANV%type;
    CURSOR cur IS SELECT MAVE
                FROM DATVE
                WHERE MANV =:NEW.MANV;
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
          RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
          ELSE 
           DBMS_OUTPUT.PUT_LINE('Sua thanh cong');
        end if;
    END LOOP;
END;
---- Trigger tren bang DATVE----
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_NHANVIEN_BANVE
AFTER INSERT OR UPDATE OF NGAYBAN ON DATVE
FOR EACH ROW
DECLARE
    v_ngaysinh NHANVIEN.NGAYSINH%TYPE;
BEGIN
    SELECT NHANVIEN.NGAYSINH INTO V_NGAYSINH
    FROM NHANVIEN
    WHERE NHANVIEN.MANV =:NEW.MANV;
    IF(:NEW.NGAYBAN < V_NGAYSINH)
    THEN
      RAISE_APPLICATION_ERROR(-2000, 'LOI !!!');
    END IF;
END;
/*Ngay phat hanh cua mot bo phim  < ngay ban ve cua bo phim do*/
SELECT * FROM PHIM
SELECT * FROM DATVE
SET SERVEROUTPUT ON;
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

---- Trigger tren bang DATVE----
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
/*Khong cho update doanh thu*/

----------- Tinh doanh thu phim----------
/*Khong viet trigger vi gia tri mac dinh la 1 và không ???c s?a thu?c tính DOANHTHU*/
--- DoanhThu----
select * from datve
select * from phim
/*Them and update*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_PHIM_DOANHTHU
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
/*xoa*/
SET DEFINE OFF;
CREATE OR REPLACE TRIGGER TRIGGER_PHIM_DOANHTHU_XOA
AFTER DELETE ON DATVE
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
---- Vi?t Store thông tin khách hàng----
create or replace procedure thongtinKH (IN_MaKH IN KHACHHANG.MAKH%TYPE)
AS
    ho_in KHACHHANG.HO%TYPE;
    ten_in KHACHHANG.TEN%TYPE;
    loai_in KHACHHANG.LOAIKH%TYPE;
begin
    SELECT HO,TEN,LOAIKH INTO HO_IN,TEN_IN,LOAI_IN
    FROM KHACHHANG
    WHERE MAKH=IN_MAKH;
    DBMS_OUTPUT.PUT_LINE(HO_IN);
    DBMS_OUTPUT.PUT_LINE(TEN_IN);
    DBMS_OUTPUT.PUT_LINE(LOAI_IN);
end;

---- Vi?t Store thông tin nhan vien----
select * from NHANVIEN
create or replace procedure thongtinNV (IN_MaNV IN NHANVIEN.MANV%TYPE)
AS
    ho_in NHANVIEN.HO%TYPE;
    ten_in NHANVIEN.TEN%TYPE;
    luong_in NHANVIEN.LUONG%TYPE;
begin
    SELECT HO,TEN,LUONG INTO HO_IN,TEN_IN,LUONG_IN
    FROM NHANVIEN
    WHERE MANV=IN_MANV;
    DBMS_OUTPUT.PUT_LINE(HO_IN);
    DBMS_OUTPUT.PUT_LINE(TEN_IN);
    DBMS_OUTPUT.PUT_LINE(LUONG_IN);
end;

---- Viet Procedure in ra thong tin phim ----
set serveroutput on;
CREATE OR REPLACE PROCEDURE thongtinPhim (IN_MaPhim IN PHIM.MaPhim%TYPE)
AS
    TenPhim_in PHIM.TenPhim%TYPE;
    DoanhThu_in PHIM.DoanhTHu%TYPE;
BEGIN 
    SELECT PHIM.TENPHIM, DoanhThu INTO TenPhim_in,DoanhThu_in
    FROM PHIM
    WHERE MaPhim=IN_MaPhim;
    DBMS_OUTPUT.PUT_LINE(TenPhim_in);
    DBMS_OUTPUT.PUT_LINE(DoanhThu_in);
END;


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
select * from khachhang;






