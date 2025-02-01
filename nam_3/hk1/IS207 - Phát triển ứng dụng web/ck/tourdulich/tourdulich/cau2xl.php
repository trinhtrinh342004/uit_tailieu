<?php
include 'connect.php';
$con = cnDB();

$mattp = $_POST['mattp'];
$maddl = $_POST['maddl'];
$tenddl = $_POST['tenddl'];
$dactrung = $_POST['dt'];

$con->query("insert into diemdl values ('$maddl', '$tenddl', '$mattp', '$dactrung')");
$con->close();
