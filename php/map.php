<?php
	$lat = $_GET['lat'];
	$lng = $_GET['lng'];
?>
<!DOCTYPE html> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, target-densityDpi=device-dpi">
<style type="text/css">
html, body, #map {margin: 0; padding: 0; width: 100%; height: 100%}
</style>
<script type="text/javascript" src="http://apis.daum.net/maps/maps3.js?apikey=ef87b1b2b5011c18df936431452c5883" charset="utf-8"></script> 
<script type="text/javascript"> 
	var map;
	function init() {
		map = new daum.maps.Map(document.getElementById('map'), {
			center: new daum.maps.LatLng(<?echo $lat ?>, <?echo $lng ?>)
		});

		var marker = new daum.maps.Marker({
			position: new daum.maps.LatLng(<?echo $lat ?>, <?echo $lng ?>)
		});
		marker.setMap(map);
	}
</script> 
</head> 
<body onload="init()">
	<div id="map"></div>
</body> 
</html>