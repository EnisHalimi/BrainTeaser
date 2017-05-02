<?php

$db_name = "brainteaser";
$mySQL_username = "root";
$mySQL_password = "";
$server_name = "localhost";	

$conn = mysqli_connect($server_name, $mySQL_username, $mySQL_password, $db_name);
if($conn){
    echo("Connection success");
}
else 
	echo("Connection not succesful");


?>