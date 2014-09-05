function backupUserExcel() {
	var f = document.getElementById('exportUserExcel');
	f.action = '../exportUserExcel.do';
	f.submit({
				failure : function(form, action) {
					var error = Ext.util.JSON
							.decode(action.response.responseText).error;
					Ext.MessageBox.alert('导出失败', "原因：" + error);
				}

	});
}

function backupQuestionExcel() {
	var f = document.getElementById('exportQuestionExcel');
	f.action = '../exportDanXuanInfoExcel.do';
	f.submit({
				failure : function(form, action) {
					var error = Ext.util.JSON
							.decode(action.response.responseText).error;
					Ext.MessageBox.alert('导出失败', "原因：" + error);
				}

	});
}

// 备份历史用户
var backupUserPanel = new Ext.Panel({
    id : 'backupUserPanel',
	labelWidth: 80,
    frame : true,
    bodyStyle:'padding:5',
    labelAlign : 'left',
    region : 'center',
    layout:'fit',
    buttonAlign : 'center',
	width: 450,
    height:250,
    hidden: false,
	title : '历史数据备份（第1步）',
    items:[
    {
    	xtype:'button',
    	text:'备份历史用户数据（第1步/共2步）',
    	pageX: 175,
    	iconCls : 'save',
    	pageY: 110,
    	width:15,
    	height:10,
    	style: {
    		marginBottom: '10px'
    	},
    	onClick: function() {
    		backupUserExcel();
    	}
    }],
    tbar:['->',{
		text:'下一步',
		iconCls : 'next',
		handler: function(){
			backupUserPanel.hidden = true;
			backupUserPanel.onHide();
			backupQuestionPanel.hidden = false;
			backupQuestionPanel.show();
			systemInitialWindow.doLayout();
		}
	}]
});

// 备份历史题目
var backupQuestionPanel = new Ext.Panel({
    id : 'backupQuestionPanel',
	labelWidth: 80,
    frame : true,
    bodyStyle:'padding:5',
    labelAlign : 'left',
    region : 'center',
    layout:'fit',
    buttonAlign : 'center',
	width: 450,
    height:250,
    hidden: true,
	title : '历史数据备份（第2步）',
    items:[
    {
    	xtype:'button',
    	text:'备份历史题目数据（第1步/共2步）',
    	pageX: 175,
    	iconCls : 'save',
    	pageY: 110,
    	width:15,
    	height:10,
    	style: {
    		marginBottom: '10px'
    	},
    	onClick: function() {
    		backupQuestionExcel();
    	}
    }],
    tbar:[{
    	text:"  上一步",
    	iconCls : 'forward',
    	handler: function() {
    		backupQuestionPanel.hidden = true;
    		backupQuestionPanel.onHide();
    		backupUserPanel.hidden=false;
    		backupUserPanel.show();
			systemInitialWindow.doLayout();
    	}
    }]
});

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
		text : '备份历史数据',
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

var wizardItems = [ backupUserPanel, backupQuestionPanel ];

var systemInitialWindow = new Ext.Window({
	title : '备份操作向导',
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
