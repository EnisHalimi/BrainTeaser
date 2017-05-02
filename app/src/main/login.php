<?php
require "connection.php";

$username = $_POST["username"];
$password = $_POST["password"]

$mysql_query = "SELECT * FROM users WHERE username ='".$username."' AND password ='".$password."' ";
$result = mysqli_query($conn, $mysql_query);
if(mysqli_num_rows($result) > 0)
	echo("Login Success");
else 
	echo ("Login Failed");

?>