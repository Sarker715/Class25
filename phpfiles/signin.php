<?php 

$email  = $_REQUEST['email'];
$password  = $_REQUEST['password'];
 
 $user = "root";$pass = "";$host= "localhost";$dbname="androidcourse";

$con = mysqli_connect($host,$user,$pass,$dbname);


$sql = "SELECT * FROM androidcourse_users WHERE email='".$email."' and password ='".$password."'";
 

 $r = mysqli_query($con,$sql);
 
 $res = mysqli_fetch_array($r);
 
  $result = array();
 
 if(sizeof($res)>0){
	 
    array_push($result,array("error"=>"no","id"=>$res['id'],"email"=>$res['email'],"password"=>$res['password']));
 	 
 }else { 
	 array_push($result,array("error"=>"yes"));
 };
 
 echo json_encode(array("result"=>$result));
 
 
 mysqli_close($con);
 
?>