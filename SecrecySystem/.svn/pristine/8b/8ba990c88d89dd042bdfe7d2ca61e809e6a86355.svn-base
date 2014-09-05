<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/ext/resources/css/ext-all.css" />
<style>
.ccdiv1 {
	position: absolute;
	width: 820px;
	height: 100px;
	left: 50%;
	top: 50%;
	margin-left: -410px;
	margin-top: -340px;
	background-color: #205983;
	filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090,
		direction=120, strength=4); /*ie*/
	-moz-box-shadow: 2px 2px 10px #909090; /*firefox*/
	-webkit-box-shadow: 2px 2px 10px #909090; /*safari或chrome*/
	box-shadow: 2px 2px 10px #909090; /*opera或ie9*/
}

.ccdiv2 {
	position: absolute;
	width: 820px;
	height: 470px;
	left: 50%;
	top: 50%;
	margin-left: -410px;
	margin-top: -240px;
	/* border-style: solid;
  	border-color: black;
  	border-width: 2px; */
	background-image:
		url(/SecrecySystem/resources/images/bottomBackground.jpg);
	filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090,
		direction=120, strength=4); /*ie*/
	-moz-box-shadow: 2px 2px 10px #909090; /*firefox*/
	-webkit-box-shadow: 2px 2px 10px #909090; /*safari或chrome*/
	box-shadow: 2px 2px 10px #909090; /*opera或ie9*/
}

.ccdiv3 {
	position: absolute;
	width: 400px;
	height: 20px;
	left: 50%;
	top: 50%;
	margin-left: -170px;
	margin-top: 250px;
}

.copyright {
	font-family: Microsoft Simhei, serif;
	font-size: 17px;
	color: #000000;
	vertical-align: middle;
}

body {
	background-image: url(/SecrecySystem/resources/images/loginbg.png);
	background-repeat: repeat;
}

input,select {
	vertical-align: middle;
}

p.topFont {
	position: relative;
	left: 5%;
	top: 37%;
	font-family: Microsoft Simhei, serif;
	font-weight: bold;
	font-size: 30px;
	color: #FFFFFF;
	text-shadow: black 2px 2px 2px;
	vertical-align: middle;
}

table.loginTable {
	position: relative;
	left: 50%;
	top: 15%;
	width: 370px;
	height: 320px;
	font-family: Microsoft Simhei, serif;
	font-weight: bold;
	border-collapse: collapse;
	background-color: #EFF2FD;
	filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090,
		direction=120, strength=4); /*ie*/
	-moz-box-shadow: 2px 2px 10px #909090; /*firefox*/
	-webkit-box-shadow: 2px 2px 10px #909090; /*safari或chrome*/
	box-shadow: 2px 2px 10px #909090; /*opera或ie9*/
}

.loginTableCell {
	position: relative;
	left: 5%;
	top: 0%;
	width: 100%;
	height: 15%;
	font-family: Microsoft Simhei, serif;
	font-weight: bold;
	font-size: 20px;
	border-collapse: collapse;
	vertical-align: middle;
}

.login-tab {
	position: relative;
	left: 0%;
	top: 0%;
	height: 20%;
	width: 100%;
	/* 	border-bottom: 4px solid #8DB5EB; */
}

.login-tab-item {
	position: relative;
	width: 50%;
	height: 15%;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
	background: #EFF2FD;
	font-family: Microsoft Simhei, serif;
	font-weight: bold;
	font-size: 20px;
	color: black;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
}

.loginButtonCell {
	position: relative;
	width: 50%;
	heigth: 15%;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
	font-weight: normal;
	color: black;
	font-size: 20px;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
}

.uncurrent {
	background: #8DB5EB;
	font-weight: bold;
	color: white;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/awesomeuploader/Ext.ux.form.FileUploadField.css" />
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/awesomeuploader/AwesomeUploader.css" />
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/ext-all.js"></script>
<script type="text/javascript" src="/SecrecySystem/resources/js/MD5.js"></script>
<script type="text/javascript">
		function login() {
 			if (document.getElementById('field1').value == "") {

				document.getElementById("msg").innerHTML = "<font color=red>用户不能为空</font>";

				return;
			}
			if (document.getElementById('field2').value == "") {
				document.getElementById("msg").innerHTML = "<font color=red>密码不能为空</font>";
				return;
			}

			document.getElementById("loadingImage").style.display = "block";
			document.getElementById("msg").innerHTML = "<font color=green>正在登陆</font>";
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
								document.getElementById("msg").innerHTML = "<font color=green>"
										+ jsonData.errors.info + " 正在跳转</font>";
								window.location.href = 'Home.do';
							} else if (jsonData.success == false) {
								document.getElementById("msg").innerHTML = "<font color=red>"
										+ jsonData.errors.info + "</font>";
							}

						},
						failure : function(response, options) {
							document.getElementById("loadingImage").style.display = "none";

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>2014年杭州市网络攻防大赛</title>
</head>
<body>
	<div id="topBackground" class="ccdiv1">
		<p class="topFont">2014年杭州市网络攻防大赛</p>
	</div>
	<div id="bottomBackground" class="ccdiv2">
		<table id="loginTable" class="loginTable">
			<tr class="login-tab">
				<td id="secrecyTab" colspan="2" class="login-tab-item">用&nbsp;户&nbsp;登&nbsp;录</td>
			</tr>
			<tr>
				<td class="loginTableCell" colspan="2" id="showName1">用&nbsp;&nbsp;户:</td>
			</tr>
			<tr>
				<td class="loginTableCell" colspan="2"><input type="text"
					id="field1" style="width: 90%; height: 80%; font-size: 20px;">
				</td>
			</tr>
			<tr>
				<td class="loginTableCell" colspan="2" id="showName2">密&nbsp;&nbsp;码:</td>
			</tr>
			<tr>
				<td class="loginTableCell" colspan="2">
					<div id="div1" style="display: block; height: 100%">
						<input type="password" id="field2"
							style="width: 90%; height: 80%; font-size: 20px;">
					</div>
				</td>
			</tr>
			<tr>
				<td id="examCollegeTab" align="center"><img id='loadingImage'
					src='resources/icons/waiting.gif' style="display: none"
					align="left"></img> <a id="msg"></a></td>
				<td id="adminTab" class="loginButtonCell" align="center"><input
					type="button" value="登录" onClick="login()"
					style="width: 80%; height: 75%; font-size: 20px"></td>
			</tr>
		</table>
	</div>
	<div id="topBackground" class="ccdiv3">
		<p class="copyright">杭州电子科技大学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2014年7月</p>
	</div>
</body>
</html>