<?php
include "connect.php";
$x = $_GET['x'];
// $y = $_GET['y'];
// $z = $_GET['z'];
$sql = "select t.matour, tentour, count(*)
    from tour t join chitiet ct on t.matour=ct.matour
    group by t.matour, tentour 
    having count(*) >= $x";
// $sql = "select matour, tentour
//     from tour 
//     where songay = $y and sodem = $z";
$con = cnDB();
$rs = $con->query($sql);
$stt = 0;
echo "<table><tr><th>STT</th><th>Tentour</th><th>soddl</th></tr>";
while ($row = $rs->fetch_row()) {
    $stt++;
    echo "<tr><td>$stt</td><td>$row[1]</td><td>$row[2]</td></tr>";
}
echo "</table>";
$con->close();
