-- Tạo cơ sở dữ liệu
DROP DATABASE TravelDB;
CREATE DATABASE TravelDB;
GO

-- Chọn cơ sở dữ liệu vừa tạo
USE TravelDB;
USE SSIS_TEST;
GO

CREATE TABLE KHACHDULICH (
    MaKDL INT PRIMARY KEY,
    HoTenKDL VARCHAR(100),
    NgaySinhKDL DATE,
    DiaChiKDL VARCHAR(255),
    QuocGiaKDL VARCHAR(50)
);

CREATE TABLE HUONGDANVIEN (
    MaHDL INT PRIMARY KEY,
    HoTenHDL VARCHAR(100),
    NgaySinhHDL DATE,
    GioiTinh VARCHAR(10),
    QuocGiaHDL VARCHAR(50)
);

CREATE TABLE DIEMDEN (
    MaDiemDen INT PRIMARY KEY,
    Ten VARCHAR(100),
    MoTa TEXT,
    LoaiDiemDen VARCHAR(50),  -- Vùng du lịch, trung tâm du lịch, điểm du lịch, khu du lịch
    QuocGia VARCHAR(50)
);

CREATE TABLE LOAIHINHDULICH (
    MaLoaiHinhDL INT PRIMARY KEY,
    TenLoaiHinhDL VARCHAR(100)  -- Du lịch nghỉ dưỡng, du lịch sinh thái, v.v.
);

CREATE TABLE TOURDULICH (
    MaTour INT PRIMARY KEY,
    MaDiemDen INT,
    MaHDV INT,
    MaLoaiHinhDL INT,
    NgayDi DATE,
    SoNgay INT,
    FOREIGN KEY (MaDiemDen) REFERENCES DIEMDEN(MaDiemDen),
    FOREIGN KEY (MaHDV) REFERENCES HUONGDANVIEN(MaHDL),
    FOREIGN KEY (MaLoaiHinhDL) REFERENCES LOAIHINHDULICH(MaLoaiHinhDL)
	FOREIGN KEY (NgayDi) REFERENCES Dim_Date (Date_ID)
);

CREATE TABLE NHOM (
    MaTour INT,
    MaKDL INT,
    GiaTour DECIMAL(18, 2),
    PRIMARY KEY (MaTour, MaKDL),
    FOREIGN KEY (MaTour) REFERENCES TOURDULICH(MaTour),
    FOREIGN KEY (MaKDL) REFERENCES KHACHDULICH(MaKDL)
);

CREATE TABLE Dim_Date (
    Date_ID DATE PRIMARY KEY,   -- ID duy nhất cho mỗi ngày
    Day INT,                            -- Ngày trong tháng
    Month INT,                          -- Tháng (1-12)
    Quarter INT,                        -- Quý (1-4)
    Year INT                         -- Năm
);




INSERT INTO KHACHDULICH (MaKDL, HoTenKDL, NgaySinhKDL, DiaChiKDL, QuocGiaKDL)
VALUES
(1, 'Nguyen Thi Lan', '1990-05-12', '123 Nguyen Thi Minh Khai, Ho Chi Minh', 'Vietnam'),
(2, 'John Doe', '1985-07-22', '456 Main St, New York', 'USA'),
(3, 'Maria Garcia', '1992-03-19', '789 Avenida de la Reforma, Mexico City', 'Mexico'),
(4, 'David Smith', '1988-11-05', '101 King St, London', 'UK'),
(5, 'Sophia Lee', '1994-01-30', '202 Queen St, Sydney', 'Australia');

INSERT INTO HUONGDANVIEN (MaHDL, HoTenHDL, NgaySinhHDL, GioiTinh, QuocGiaHDL)
VALUES
(1, 'Nguyen Minh Tu', '1980-04-01', 'Female', 'Vietnam'),
(2, 'Michael Johnson', '1975-02-15', 'Male', 'USA'),
(3, 'Laura Hernandez', '1990-08-10', 'Female', 'Mexico'),
(4, 'James Brown', '1985-12-23', 'Male', 'UK'),
(5, 'Emily Davis', '1993-03-25', 'Female', 'Australia');

INSERT INTO DIEMDEN (MaDiemDen, Ten, MoTa, LoaiDiemDen, QuocGia)
VALUES
(1, 'Ha Long Bay', 'Vịnh đẹp nổi tiếng ở Việt Nam', 'Vùng du lịch', 'Vietnam'),
(2, 'Grand Canyon', 'Hẻm núi nổi tiếng ở Mỹ', 'Điểm du lịch', 'USA'),
(3, 'Chichen Itza', 'Di tích Maya cổ ở Mexico', 'Khu du lịch', 'Mexico'),
(4, 'Stonehenge', 'Đài kỷ niệm cổ xưa ở Anh', 'Trung tâm du lịch', 'UK'),
(5, 'Sydney Opera House', 'Nhà hát nổi tiếng ở Úc', 'Điểm du lịch', 'Australia');

INSERT INTO LOAIHINHDULICH (MaLoaiHinhDL, TenLoaiHinhDL)
VALUES
(1, 'Du lịch nghỉ dưỡng'),
(2, 'Du lịch sinh thái'),
(3, 'Du lịch văn hóa – lịch sử'),
(4, 'Du lịch thể thao'),
(5, 'Du lịch MICE');

INSERT INTO TOURDULICH (MaTour, MaDiemDen, MaHDV, MaLoaiHinhDL, NgayDi, SoNgay)
VALUES
(1, 1, 1, 1, '2023-06-15', 5),
(2, 2, 2, 2, '2023-07-20', 7),
(3, 3, 3, 3, '2023-08-10', 4),
(4, 4, 4, 4, '2023-09-05', 3),
(5, 5, 5, 5, '2023-10-12', 6);

INSERT INTO NHOM (MaTour, MaKDL, GiaTour)
VALUES
(1, 1, 500),
(1, 2, 500),
(2, 3, 700),
(3, 4, 400),
(5, 5, 600);

INSERT INTO Dim_Date (Date_ID, Day, Month, Quarter, Year)
VALUES
('2023-01-01', 1, 1, 1, 2023),  -- Ngày đầu tiên của năm 2023, Quý 1
('2023-03-15', 15, 3, 1, 2023), -- Giữa Quý 1
('2023-06-10', 10, 6, 2, 2023), -- Giữa Quý 2
('2023-09-25', 25, 9, 3, 2023), -- Cuối Quý 3
('2023-12-31', 31, 12, 4, 2023); -- Cuối năm 2023, Quý 4
