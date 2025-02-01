<?php
include 'connect.php';

$maddl = $_POST['maddl'];
$tenddl = $_POST['tenddl'];
$mattp = $_POST['mattp'];
$dactrung = $_POST['dt'];

$sql = "update diemdl SET tenddl = '$tenddl', 
mattp = '$mattp', dactrung = '$dactrung' 
        WHERE maddl = '$maddl'";
$con = cnDB();

$con->query($sql);
$con->close();
