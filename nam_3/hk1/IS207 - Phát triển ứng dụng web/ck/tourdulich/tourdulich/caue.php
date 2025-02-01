<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

</head>

<body>
    <input id='sddl' type="number">
    <!-- <input id='y' type="number">
    <input id='z' type="number">
    <button id="view">Xem</button> -->
    <div id='dstour'></div>

    <script>
        $(document).ready(function() {
            $("#sddl").change(function() {
                x = $(this).val();
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
            })
        })
        // $(document).ready(function() {
        //     $("#view").click(function() {
        //         y = $('#y').val();
        //         z = $('#z').val();
        //         $.ajax({
        //             type: 'get',
        //             url: 'lietketour.php',
        //             data: {
        //                 y: y,
        //                 z: z
        //             },
        //             success: function(data) {
        //                 $('#dstour').html(data);
        //             }
        //         })
        //     })
        // })
    </script>
</body>

</html>