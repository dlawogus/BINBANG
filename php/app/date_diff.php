<?php
function date_diff($date1,$date2) {
	$tmp_date1 = explode("-",$date1);
	$tmp_date2 = explode("-", $date2);
	$tmp1 = mktime(0,0,0,$tmp_date1[1], $tmp_date1[2], $tmp_date1[0]);
	$tmp2 = mktime(0,0,0,$tmp_date2[1], $tmp_date2[2], $tmp_date2[0]);

	$return_date = ($tmp1 - $tmp2) / 86400;

	return $return_date;
}
?>