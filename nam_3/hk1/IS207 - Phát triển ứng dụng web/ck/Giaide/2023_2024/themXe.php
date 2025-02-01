<!-- themXe.php -->
<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    include 'connection.php';

    // Lấy dữ liệu từ form
    $maXe = $_POST['maXe'];
    $tenXe = $_POST['tenXe'];
    $hangXe = $_POST['hangXe'];
    $socho = $_POST['soCho'];
    $namSX = $_POST['namSX'];
    $donGia = $_POST['donGia'];

    // Kết nối CSDL
    $conn = connectToDatabase();

    // Thực hiện truy vấn
    $sql = "INSERT INTO XE (SOXE, TENXE, HANGXE, SOCHO, NAMSX, DGTHUE, TINHTRANG) VALUES ('$maXe', '$tenXe', '$hangXe', '$socho', '$namSX', '$donGia', '0')";

    if ($conn->query($sql) === TRUE) {
        echo "Thêm xe thành công";
    } else {
        echo "Lỗi: " . $sql . "<br>" . $conn->error;
    }

    // Đóng kết nối
    $conn->close();
}
?>