<?php
include "connect.php";
$con = cnDB();
$maddl = $_POST['maddl'];
$rs = $con->query("select * from diemdl where maddl = '$maddl'");
echo "<form action='updateddl.php' method='post'>";
while ($row = $rs->fetch_row()) {
    echo "ma diem du lich: <input type='text' value='$row[0]' name='maddl' readonly></input><br>";
    echo "ten diem du lich: <input type='text' value='$row[1]' name='tenddl'></input><br>";
    $ttp = $con->query("select * from tinhtp");
    echo "<select name='mattp'>";
    while ($rowttp = $ttp->fetch_row()) {
        $selected = ($rowttp[0] == $row[2]) ? "selected" : "";
        echo "<option value='$rowttp[0]' $selected>$rowttp[1]</option>";
    }
    echo "</select><br>";
    echo "dac trung: <input type='text' value='$row[3]' name='dt'></input><br>";

    echo "<button type='submit'>Update</button></form>";
}
$con->close();
