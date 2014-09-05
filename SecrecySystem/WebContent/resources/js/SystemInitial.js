function clearHistoryData(confirm) {
	if (confirm == "yes") {
		cleardata = "1";
		Ext.MessageBox.wait('正在执行操作...', '请等待');
		ClearHistoryDataController.clearHistoryData(function(data) {
			var jsonData = Ext.util.JSON.decode(data);
			if (jsonData.success == true) {
				Ext.MessageBox.alert('提示', jsonData.errors.info, function() {

				});
			} else {
				Ext.MessageBox.alert('提示', jsonData.errors.info, function() {

				});
			}
		});
	}
}

var clearHistoryPanel = new Ext.Panel({
	id : 'clearHistoryPanel',
	title : '清除历史数据',
	labelWidth : 80,
	frame : true,
	bodyStyle : 'padding:5',
	labelAlign : 'left',
	region : 'center',
	layout : 'fit',
	buttonAlign : 'center',
	width : 450,
	height : 250,
	hidden : false,
	items : [ {
		xtype : 'button',
		text : '清除历史数据',
		arrowAlign : 'bottom',
		iconCls : 'remove',
		style : {
			marginBottom : '10px'
		},
		onClick : function() {
			Ext.MessageBox.confirm('提示', '是否要清空历史数据？', clearHistoryData);
		}
	} ]
});

var wizardItems = [ clearHistoryPanel ];

var systemInitialWindow = new Ext.Window({
	title : '初始操作向导',
	width : 470,
	height : 290,
	closeAction : 'hide',
	layout : 'fit',
	border : false,
	modal : true,
	shadow : true,
	hideMode : Ext.isIE ? 'offsets' : 'display',
	plain : true,
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	items : wizardItems,
	listeners : {
		"hide" : function() {
			var temp = window.top.tabPanel.getActiveTab();
			window.top.tabPanel.remove(temp);
		}
	}
});
