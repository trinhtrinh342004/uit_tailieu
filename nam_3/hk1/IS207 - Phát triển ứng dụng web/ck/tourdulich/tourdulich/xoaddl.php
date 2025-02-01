<?php
include "connect.php";
$con = cnDB();
$maddl = $_POST['maddl'];
$rs = $con->query("delete from diemdl where maddl = '$maddl'");
$rs = $con->query("delete from chitiet where maddl = '$maddl'");
$con->close();
