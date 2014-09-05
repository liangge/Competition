var competitionInfoCondition = "";  // 记录竞赛基本信息
var id = "";
var examname;
var starttime;
var endtime;

var competition = new Ext.data.Record.create([ {
	name : 'id'
}, {
	name : 'examname'
}, {
	name : 'starttime'
}, {
	name : 'endtime'
} ]);

var competitionTimeStore = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		totalProperty : 'totalProperty',
		root : 'root'
	}, competition),
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : CpTestpaperRecordController.getTestPaperRecordList
	})
});

// 渲染竞赛基本信息
function showCompetitionTime(value, metaData, record, rowIndex, colIndex, store) {
	var str = "";
	str += "</br>" + "<span style='font-size:15px;font-weight:bold;'>" + record.data.examname + "</span>";
	str += "</br></br>" + "<span style='font-size:13px;'>" + "开始 : " + record.data.starttime + "</span>";
	str += "</br>" + "<span style='font-size:13px;'>" + "结束 : " + record.data.endtime + "</span>";
	return str; 
}

// 修改保存竞赛时间基本信息
function saveCompetitionTime() {
	if(setCompetitionTimeForm.getForm().isValid()){
		var params = {};
		competitionInfoCondition = setCompetitionTimeForm.getForm().getValues();
		params.id = id;
		params.examname = competitionInfoCondition.examnameform;
		params.examdate = competitionInfoCondition.examdateform;
		params.starttime = competitionInfoCondition.starttimeform;
		params.endtime = competitionInfoCondition.endtimeform;
		
		CpTestpaperRecordController.saveCompetitionTime(params, function(data) {
			if(data){
				Ext.MessageBox.alert('提示',"保存竞赛时间信息成功！");
				competitionTimeStore.reload();
				Ext.getCmp("saveCompetitionTime").setText("修改保存");
			} else {
				Ext.MessageBox.alert('提示',"保存竞赛时间信息失败！");
			}
	  	});
	} else {
		Ext.MessageBox.alert('提示',"所有信息必填！");
	}
}

var competitionTimeGrid = new Ext.grid.GridPanel({
	region : 'center',
	id : 'competitionTimeGrid',
	store : competitionTimeStore,
	title : '竞赛时间信息',
	loadMask : true,
	stripeRows : true,
	autoScroll : true,
	viewConfig : {
		sortDescText : '降序',
		sortAscText : '升序',
		columnsText : '显示列',
		forceFit : false
	},
	columns : [ new Ext.grid.RowNumberer({
		width : 28
	}), {
		id : 'examname',
		header : '竞赛基本信息',
		dataIndex : 'examname',
		width : 500,
		sortable : true,
		renderer : showCompetitionTime
	}, {
		id : 'starttime',
		header : '开始时间',
		dataIndex : 'starttime',
		width : 80,
		sortable : true,
		hidden : true
	}, {
		id : 'endtime',
		header : '结束时间',
		dataIndex : 'endtime',
		width : 80,
		sortable : true,
		hidden : true
	}]
});


// 考试出卷填写基本信息
var setCompetitionTimeForm = new Ext.form.FormPanel({
	title : "设置竞赛时间信息",
	region : "north",
	frame : true,
	height : 220,
	labelAlign : 'left',
	layout : 'column',
	items : [ {
		columnWidth : 0.6,
		layout : 'form',
		height : 200,
		frame : true,
		items : [{
			xtype : 'textfield',
			fieldLabel : '竞赛名称',
			id : 'examnameform',
			name : 'examnameform',
			readOnly : false,
			allowBlank : false,
			value : "2014年浙江省网络攻防竞赛",
			anchor : '98%'
		},{
		   xtype:'datefield',
		   fieldLabel:'竞赛日期',
		   id : 'examdateform',
		   name:'examdateform',
		   format:'Y-m-d',
		   cls:"Wdate",
		   allowBlank:false,
		   style : "position:relative;top:2px;",
		   anchor : '98%'
		},{
			  xtype:'timefield',
			  fieldLabel:'开始时间',
			  name:'starttimeform',
			  id : 'starttimeform',
			  format:'H:i',
			  anchor:'98%',
			  cls:"Wdate",
			  minValue: '7:00',  
		      maxValue: '21:00',
			  allowBlank:false,
			  listeners : {     
	            select : function(combo, record, index){ 
	            	Ext.getCmp("endtimeform").setValue(combo.value);
	            	Ext.getCmp("endtimeform").setRangeValue(combo.value,'22:00');
	            }
	          }
		},{
	    	  xtype : 'timefield',
	    	  fieldLabel : '结束时间',
	    	  name : 'endtimeform',
	    	  id : 'endtimeform',
	    	  format : 'H:i',
	    	  anchor : '98%',
	    	  cls : "Wdate",
	    	  allowBlank : false,
		}, {
			xtype : 'button',
			id : 'saveCompetitionTime',
			text : '修改保存',
			style : "position:relative;top:15px;",
			minWidth: 100,
			handler : saveCompetitionTime
		}]
	}]
});

function WindowInit(){
	competitionTimeStore.load({
		callback : function(record, options, success) {
			if(success) {
				if(competitionTimeStore.getCount() > 0) {
					starttime = competitionTimeStore.getAt(0).get('starttime');
					endtime = competitionTimeStore.getAt(0).get('endtime');
					examname = competitionTimeStore.getAt(0).get('examname');
					id = competitionTimeStore.getAt(0).get('id');
					var start = new Array(); // 定义一数组
					var end = new Array();
					start = starttime.split(" "); //字符分割 
					end = endtime.split(" ");
					Ext.getCmp('examnameform').setValue(examname);
					Ext.getCmp('examdateform').setValue(start[0]);
					Ext.getCmp('starttimeform').setValue(start[1]);
					Ext.getCmp('endtimeform').setValue(end[1]);
					Ext.getCmp("saveCompetitionTime").setText("修改保存");
					
					Ext.getCmp("endtimeform").setRangeValue(start[1],'22:00');
				} else {
					Ext.getCmp("saveCompetitionTime").setText("保存时间");
				}
			}	
		}
	});
}

// 窗口初始化
function setCompetitionPaperInit() {
	new Ext.Viewport({
		layout : 'border', // 表格布局
		id : 'mainpanel',
		bodyPadding : 5,
		hideMode : Ext.isIE ? 'offsets' : 'display',	
		items : [ setCompetitionTimeForm, competitionTimeGrid],
		renderTo : Ext.getBody()
	});
	WindowInit();
}