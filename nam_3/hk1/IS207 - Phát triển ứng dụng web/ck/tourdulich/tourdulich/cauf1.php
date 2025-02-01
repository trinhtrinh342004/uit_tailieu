<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

</head>

<body>
    <input id='sddl' type="number">
    <div id='dstour'></div>

    <script>
        $(document).ready(function() {
            $("#sddl").keydown(function(e) {
                if (e.key === "Tab") { // Check if Tab key is pressed
                    var x = $(this).val();
                    $.ajax({
                        type: 'get',
                        url: 'lietketour.php',
                        data: {
                            x: x
                        },
                        success: function(data) {
                            $('#dstour').html(data);
                        }
                    })
                }
            })
        })
    </script>
</body>

</html>