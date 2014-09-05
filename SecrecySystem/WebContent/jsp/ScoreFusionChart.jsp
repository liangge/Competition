<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/awesomeuploader/Ext.ux.form.FileUploadField.css" />
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/awesomeuploader/AwesomeUploader.css" />
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/ext-all.js"></script>
<script type='text/javascript' src='/SecrecySystem/dwr/engine.js'></script>
<script type='text/javascript' src='/SecrecySystem/dwr/util.js'></script>
	
<script type='text/javascript'
	src='/SecrecySystem/dwr/interface/CpTestpaperRecordController.js'></script>	
<script type='text/javascript'
	src='/SecrecySystem/dwr/interface/CpAnswerRecordController.js'></script>
<script type='text/javascript'
	src='/SecrecySystem/dwr/interface/CpPushController.js'></script>	
	
<script type="text/javascript"
	src="/SecrecySystem/resources/custom/DwrProxy.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/Ext.ux.form.FileUploadField.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/Ext.ux.XHRUpload.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/swfupload.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/swfupload.swfobject.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/AwesomeUploader.js"></script>

</head>
<body style=height:100%; >
	<script type="text/javascript" src="/SecrecySystem/resources/js/FusionCharts.js" ></script>
	<script type="text/javascript" src="/SecrecySystem/resources/js/Bar2D1.js" ></script>
	<script type="text/javascript">
		Ext.chart.Chart.CHART_URL = '/SecrecySystem/resources/ext/resources/charts.swf';
		Ext.chart.Chart.CHART_URL = 'Bar2D.swf';
		var myChart;
		var jsonChart;
		var data = [];
		var num = 1;
		var chart = {
/*  			    "caption": "比赛分数排名",  // 图表主标题 */
		/* 	    "yaxisname": "分数",		// 横向坐标轴(x/y轴)名称	 */
/* 			    "xaxisname": "名称", */
			    "bgcolor": "F1F1F1",	// 图表背景色，6位16进制颜色值
			    "baseFont": "bold",		// 字体格式
			    "baseFontSize" : "15", // Canvas里面的字体大小范围在0-72,图表字体大小
			    "baseFontColor" : "000000",
			    "outCnvbaseFont" : "bold",
			    "outCnvBaseFontSize" : "13", // Canvas外面的字体大小范围在0-72,即标题、子标题、X/Y轴名称字体
			    "outCnvbaseFontColor" : "000000",
			    "showvalues": "1",	// 柱状图顶部显示数据
			    "canvasborderthickness": "1",
			    "canvasbordercolor": "999999",
			    "plotfillangle": "330",
			    "plotbordercolor": "999999",
			    "showalternatevgridcolor": "1",
			    "divlinealpha": "0",
			    "formatNumberScale": "0", // 是否格式化数字,默认为1(True),自动的给你的数字加上K（千）或M（百万）；若取0,则不加K或M
			    
			    "numDivLines" : "10",
			    "showAlternateHGridColor" : "1",
			    
			    "alternatevgridcolor": "AFD8F8"
		};
	
		var scoreSummaryFields = Ext.data.Record.create([ {
			name : 'name'
		}, {
			name : 'score'
		}
		]);
	
		var scoreSummaryStore = new Ext.data.JsonStore({
			fields : scoreSummaryFields,
			sortInfo : {field:"score",direction:"DESC"}
		});
	
		var chartPanel = new Ext.Panel({
			title : '实时得分排名',
			region : 'center',
			layout : 'fit',
			html : "<div id='fusionChartDiv' style='height:100%'></div>"
		});	
	
		function loadScoreSortInfo() {
			CpAnswerRecordController.getSummaryScore(function(data){ 
				if(data) {
					scoreSummaryStore.loadData(data);
					
					data = [];
					for (var i = 0; i < scoreSummaryStore.getCount(); i++) {
						var item = {
							"name" : scoreSummaryStore.getAt(i).get("name"),
							"value" : scoreSummaryStore.getAt(i).get("score")
						};
						data.push(item);
					}
					jsonChart = {
						"chart" : chart,
						"data" : data
					};

					// myChart.setXMLData(dataString);
					myChart.setJSONData(jsonChart);
					if(num == 1) {
						myChart.render("fusionChartDiv");
						num ++;
					}
					
				}
			});
			
			setTimeout("loadScoreSortInfo()", 6000);
		}
		
		myChart = new FusionCharts("/SecrecySystem/resources/js/Bar2D.swf", "ChartId",
				"100%", "100%", "0", "1");
		
		loadScoreSortInfo();
		
		// 初始化界面
		new Ext.Viewport({
			layout : 'border',
			hideMode : Ext.isIE ? 'offsets' : 'display',
			items : [chartPanel ],
			renderTo : Ext.getBody()
		});
	</script>
</body>

</html>