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
*{
	margin: 0;
	padding: 0;
}	
body{
	width: 100%;
	height: auto;
	margin: 0 auto;
	background-image: url(/SecrecySystem/resources/images/background.png);
	background-size: 100%;
}
#but{
	position: relative;top:134px;left:0px;height: 38px;width: 250px;background: url(/SecrecySystem/resources/images/login.png) -5px 38px;border-radius: 4px;border:0px;
}
#field1{
	line-height:20px;overflow:hidden;width: 250px;height: 39px;position: relative;top:105px;background: url();border-radius: 8px;text-indent: 3em;border:0px;
}
#field2{
	border-radius: 10px;width: 254px;height: 43px;position: relative;top: 119px;background: url();text-indent: 3em;border:0px;
}
.top{
	height: 160px;
	width: 100%;
	position: relative;
	top: 83px;
	text-align: center;
}
.middle{
	height: 440px;
	width: 100%;
	position: relative;
	top: 58px;
	text-align: center;
	margin:0 auto;
}
.for{
	width:260px;
	height: 255px;
	position:relative;
	top: 3px;
	background: url(/SecrecySystem/resources/images/login.png);
	margin:0 auto;
}
.footer{
	height: 58px;
	width: 100%;
	position: absolute;
	text-align: center;
	bottom: 0px;
}
.footer span{
	height: 15px;
	background: url(/SecrecySystem/resources/images/xiaoanniu.png) no-repeat;
	padding-left: 15px;
	padding-right: 25px;
	color: rgb(130,120,116);
	font-size: 12px;
}
input:-webkit-autofill {
-webkit-box-shadow: 0 0 0px 1000px white inset;
border: 1px solid #CCC!important;
}
</style>
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/ext-all.js"></script>
<script type="text/javascript" src="/SecrecySystem/resources/js/MD5.js"></script>	
<script type="text/javascript">
function login() {
		if (document.getElementById('field1').value == "") {

		// document.getElementById("msg").innerHTML = "<font color=red>用户不能为空</font>";
		Ext.MessageBox.alert('提示', "用户不能为空");
		return;
	}
	if (document.getElementById('field2').value == "") {
		// document.getElementById("msg").innerHTML = "<font color=red>密码不能为空</font>";
		Ext.MessageBox.alert('提示', "密码不能为空");
		return;
	}

	//document.getElementById("loadingImage").style.display = "block";
	//document.getElementById("msg").innerHTML = "<font color=green>正在登陆</font>";
	setTimeout("loginAJAX()", 500); 
}
function loginAJAX() {
	var urlTemp;
	var paramsTemp;

	urlTemp = "Login.do";
	paramsTemp = {
		name : document.getElementById('field1').value,
		password : hex_md5(document.getElementById('field2').value)

	};

	Ext.Ajax
			.request({
				url : urlTemp,
				method : 'post',
				waitMsg : '正在提交，请等待……',
				params : paramsTemp,
				success : function(response, options) {
					var jsonData = Ext.util.JSON
							.decode(response.responseText);
					if (jsonData.success == true) {
						window.location.href = 'Home.do';
					} else if (jsonData.success == false) {
						Ext.MessageBox.alert('提示', "<font color=red>"
								+ jsonData.errors.info + "</font>");		
					}
				},
				failure : function(response, options) {
					Ext.Msg
							.alert(
									'提示',
									'原因如下：'
											+ Ext.util.JSON
													.decode(response.responseText).errors.info);
				}
			});

}
if (document.addEventListener) {
	//如果是Firefox   
	document.addEventListener("keypress", fireFoxHandler, true);
} else {
	document.attachEvent("onkeypress", ieHandler);
}
function fireFoxHandler(evt) {
	if (evt.keyCode == 13) {
		login(); //你的代码 
	}
}
function ieHandler(evt) {
	//alert("IE"); 
	if (evt.keyCode == 13) {
		login();//你的代码  
	}
}

</script>
</head>
<body><div class="footer">
			<span>杭州市经济和信息化委员会</span>
			<span>浙江禾晨信用管理有限公司</span>
			<span>杭州电子科技大学</span>
		</div>
		<div class="top">
			<img src="/SecrecySystem/resources/images/front.png">
		</div>
		<div class="middle">
			
			<div class="for">
				<form>
					<input type="text" id="field1">
					<input type="password" id="field2">
					<input id="but" type="button" value=" " onclick="login()">
				</form>
			</div>
		</div>
	</body>
</html>