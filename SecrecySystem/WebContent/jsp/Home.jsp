<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>2014年杭州市网络攻防大赛</title>
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/ext/resources/css/ext-all.css" />
<style type="text/css">
.home-top-div{
	background-repeat:no-repeat;
	background-position:left;
	background-image:url(/SecrecySystem/resources/images/secrecytitle.png);
	width:100%;
	height:100%
}	
</style>	
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="/SecrecySystem/resources/ext/ext-all.js"></script>
<script type="text/javascript" src="/SecrecySystem/resources/js/Home.js"></script>
<script type="text/javascript">
	var menuString='${menus}';
	var username='${username}';
	var institution='${institution}';
</script>
</head>
<body>
<script type="text/javascript">
Ext.onReady(init);
</script>
</body>
</html>