<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String username = (String)request.getSession().getAttribute("name");
%>
<html>
<head>
<meta http-equiv=”X-UA-Compatible” content=”IE=EmulateIE8″ />
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/ext/resources/css/ext-all.css" />
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/ext-all.js"></script>
<script type="text/javascript" src="/SecrecySystem/dwr/engine.js"></script>
<script type="text/javascript" src="/SecrecySystem/dwr/util.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/dwr/interface/ModifyPasswordController.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/custom/DwrProxy.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/js/ModifyPassword.js"></script>
<script type="text/javascript" src="/SecrecySystem/resources/js/MD5.js"></script>
<style type="text/css">
.icon-grid {
	background-image: url(../resources/icons/grid.gif) !important;
}

.add {
	background-image: url(../resources/icons/add.gif) !important;
}

.edit {
	background-image: url(../resources/icons/edit.gif) !important;
}

.remove {
	background-image: url(../resources/icons/delete.gif) !important;
}

.save {
	background-image: url(../resources/icons/save.gif) !important;
}

.back {
	background-image: url(../resources/icons/back.gif) !important;
}

.refresh {
	background-image: url(../resources/icons/refresh.gif) !important;
}

.upload-icon {
	background: url(../resources/icons/image_add.png) no-repeat 0 0
		!important;
}

.dIcon {
	background-image: url(../resources/icons/data.gif) !important;
}

.lIcon {
	background-image: url(../images/database_table.png) !important;
}

.pIcon {
	background-image: url(../images/house.png) !important;
}
</style>

<script type="text/javascript">
	var username = "${username}";
</script>

</head>
<body>
	<div id="showname" style="display: none" ><%=username %></div>
	<script type="text/javascript">
	var show = document.getElementById("showname").innerText;
	// alert("" + show);
/* 			Ext.onReady(function(){
				this.pageInit();
			}); */
			
			Ext.onReady(pageInit);
  		</script>
</body>

</html>
