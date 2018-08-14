<?php


$email = $_POST['email'];
$password = $_POST['password'];

$user = "biswajit_root";
$pass = "biswajit_root$$$";
$host= "localhost";
$dbname="biswajit_androidcourse";

$con = mysqli_connect($host,$user,$pass,$dbname);

$sql="INSERT INTO androidcourse_users(email,password) VALUES('".$email."','".$password."')";

if(mysqli_query($con,$sql)){
	echo  "Registered Successfully";
}else{
	echo "Failed";
}

?>