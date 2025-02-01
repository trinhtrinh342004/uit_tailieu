<?php
function cnDB() {
    $con = new mysqli('localhost', 'root', '', 'quanlytourdulich');
    return $con;
}
