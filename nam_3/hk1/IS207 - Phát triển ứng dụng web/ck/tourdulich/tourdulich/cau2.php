<!-- cau2.html -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
</html>
<body>
    <!-- <input  type='checkbox' name='gioitinh' value='1'> -->
    <form action="cau2xl.php" method="POST">
        <label>tentinhthanhpho</label>
        <select name="mattp">
            <?php
            include 'connect.php';
            $con = cnDB();
            $rs = $con->query("select * from tinhtp");
            while ($row = $rs->fetch_row()) {
                echo "<option value='$row[0]'>$row[1]</option>";
            }
            $con->close();
            ?>
        </select> <br>

        <label>maddl</label>
        <input type="text" name="maddl"><br>

        <label>ten</label>
        <input type="text" name="tenddl"><br>

        <label>dt</label>
        <input type="text" name="dt"><br>

        <button type="submit">Thêm</button>
    </form>
</body>
</html>