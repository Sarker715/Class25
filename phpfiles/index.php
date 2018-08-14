<?php
	try{
		$db = new PDO('mysql:host=localhost;dbname=biswajit_androidcourse;charset=utf8', 'biswajit_root', 'biswajit_root$$$');
		//echo "Connected";
	}catch (PDOException $e) {
		//print "Error!: " . $e->getMessage() . "<br/>";
		echo "Not Connected";
		die();
	}

	$query = $db->prepare("SELECT * FROM androidcourse_users");
	$query->execute();

	if($query->rowCount() > 0){
		$data = $query->fetchAll(PDO::FETCH_ASSOC);	
		
	echo json_encode($data);
		
	//	 echo json_encode(array("result"=>$data));
	}else{
		$json['success'] = 0;
		$json['message'] = 'No Data found';		
		$json['myintro'] = '';

		echo json_encode($json);
	}
?>