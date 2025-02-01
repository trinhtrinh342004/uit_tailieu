<!-- connection.php -->
<?php
function connectToDatabase() {
    $servername = "localhost";
    $username = "root";
    $password = "root";
    $dbname = "quanlyxe";
    $conn = new mysqli($servername, $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    return $conn;
}
?>