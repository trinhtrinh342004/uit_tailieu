<!-- cau1.php -->
<?php
include 'connect.php';
$con = cnDB();

$matour = $_POST['matour'];
$tentour = $_POST['tentour'];
$ngaykh = $_POST['ngaykh'];
$songay = $_POST['songay'];
$sodem = $_POST['sodem'];
$gia = $_POST['gia'];

$con->query("insert into tour values ('$matour', '$tentour', '$ngaykh', '$songay', '$sodem', '$gia')");
$con->close();
