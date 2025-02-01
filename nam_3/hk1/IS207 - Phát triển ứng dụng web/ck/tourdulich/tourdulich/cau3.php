<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
</head>
<body>
    <table>
        <tr>
            <th>STT</th>
            <th>MaDDL</th>
            <th>TenDDL</th>
            <th>TenTP</th>
            <th>DacTrung</th>
            <th>ChucNang</th>
        </tr>
        <?php
        include "connect.php";
        $con = cnDB();
        $rs = $con->query("select * from diemdl");
        // $ttp = $con->query("select * from tinhtp");
        $stt = 0;
        while ($row = $rs->fetch_row()) {
            $stt++;
            echo "<tr>
                <td>$stt</td>
                <td>$row[0]</td>
                <td>$row[1]</td>";
            $ttp = $con->query("select * from tinhtp");
            while ($rowttp = $ttp->fetch_row()) {
                if ($rowttp[0] == $row[2]) {
                    echo "<td>$rowttp[1]</td>";
                }
            }
            echo "
                <td>$row[3]</td>
                <td>
                <a href='#' class='view' maddl='$row[0]'>View</a>
                <button class='delete' maddl='$row[0]'>Delete</button>
                </td></tr>
            ";
        }
        $con->close();
        ?>
    </table> <br>
    <div class='formUpdate'></div>

    <script>
        $(document).ready(function() {
            $(".view").click(function() {
                maddl = $(this).attr("maddl");
                $.ajax({
                    type: 'post',
                    url: 'xemddl.php',
                    data: {
                        maddl: maddl
                    },
                    success: function(data) {
                        $('.formUpdate').html(data);
                    }
                })
            })
            $(".delete").click(function() {
                maddl = $(this).attr("maddl");
                $(this).parent().parent().remove();
                $.ajax({
                    type: 'post',
                    url: 'xoaddl.php',
                    data: {
                        maddl: maddl
                    },
                    success: function(data, status) {}
                })
            })
        })
    </script>
</body>

</html>