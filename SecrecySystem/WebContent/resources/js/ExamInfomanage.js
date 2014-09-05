var secrecy_currentSearchFilter = "";
//获取题目的游标
var curDanxuanProblemNumber = 0;
var curDanxuanProblemId = "";

var iscommit = false;
var isExamBegin = false;
var isTimeOut = false;

var checktime = "";
//是否参加过本次考试
var isExamed = false;


var testpaperrecordid = "";
var starttime = "";
var endtime = "";
var examname = "";
var totalnumber = -1;
var submitnumber = "";

var xuanzeQuestion = new Ext.data.Record.create([ {
	name : 'id'
}, {
	name : 'number'
}, {
	name : 'question'
}, {
	name : 'submit'
}
]);


var testpaperRecord = new Ext.data.Record.create([ {
	name : 'id'
}, {
	name : 'starttime'
}, {
	name : 'endtime'
},{
	name : 'examname'
}, {
	name : 'remark'
}
]);

var danxuanQuestionStore = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		totalProperty : 'totalProperty',
		root : 'root'
	}, xuanzeQuestion),
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : CpQuestionController.getQuestionRecordList
	})
});

var testpaperRecordStore = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		totalProperty : 'totalProperty',
		root : 'root'
	}, testpaperRecord),
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : CpTestpaperRecordController.getTestPaperRecordList
	})
});

var danxuanOperateform = new Ext.FormPanel({
	id : 'danxuanOperateform',
	height : 500,
	columnWidth : 0.2,
	width : '180',
	labelWidth : 60,
	frame : true,
	region : 'center',
	labelAlign : 'center',
	layout : 'anchor',
	items : [ {
		columnWidth : 1.0,
		layout : 'form',
		height : 40,
		labelWidth : 60,
		items : [ {
			xtype : 'button',
			text : '第一题',
			margin : '20 20 20 20',
			anchor : '80.5%',
			style : "position:relative;top:10px;",
			handler : getFirstDanxuanProblem
		} ]
	}, {
		columnWidth : 1.0,
		layout : 'form',
		height : 40,
		labelWidth : 60,
		items : [ {
			xtype : 'button',
			text : '上一题',
			anchor : '80.5%',
			style : "position:relative;top:10px;",
			handler : getPreviousDanxuanProblem
		} ]
	}, {
		columnWidth : 1.0,
		layout : 'form',
		height : 40,
		labelWidth : 60,
		items : [ {
			xtype : 'button',
			text : '下一题',
			anchor : '80.5%',
			style : "position:relative;top:10px;",
			handler : getNextDanxuanProblem
		} ]
	}, {
		columnWidth : 1.0,
		layout : 'form',
		height : 40,
		labelWidth : 60,
		items : [ {
			xtype : 'button',
			text : '最后一题',
			anchor : '80.5%',
			style : "position:relative;top:10px;",
			handler : getLastDanxuanProblem
		} ]
	}, {
		columnWidth : 1.0,
		layout : 'form',
		height : 40,
		labelWidth : 60,
		items : [ {
			xtype : 'button',
			text : '提交本题',
			anchor : '80.5%',
			style : "position:relative;top:10px;",
			handler : commitCurrentQuestion
		} ]
	} ]

});

var danxuanProblemform = new Ext.FormPanel({
	id : 'danxuanProblemform',
	height : 500,
	columnWidth : 0.8,
	labelWidth : 60,
	frame : true,
	labelAlign : 'left',
	layout : 'column',
	items : [ {
		id : 'danxuantiLeftForm',
		columnWidth : 1.0,
		layout : 'form',
		labelWidth : 60,
		items : [ {
			id : 'danxuanDisplayProblem',
			xtype : 'displayfield',
			height : 180,
			fieldLabel : "<span style='font-size:15px;top:5px'>题目</span>",
			labelWidth : 70,
			labelSeparator : "：",
			style : "position:relative;top:60px;right:'40px';fontSize:'25px'",
			value : ""
		} ]
	}, new Ext.Panel({
		id : 'danxuantiRightForm',
		columnWidth : 1.0,
		layout : 'column',
		border : false,
		labelWidth : 50,
		labelAlign : 'right',
		items : [{
			columnWidth : 1,
			layout : 'form',
			labelWidth : 90,
			items : [ {
				xtype : 'textfield',
				id : 'answerfield',
				fieldLabel : "<span style='font-size:15px;top:5px'>请输入答案</span>",
				name : 'answerfield',
				anchor : '55%',
				allowBlank : true
			}]
		}]
	}), {
		id : 'submitStatusForm',
		columnWidth : 1.0,
		layout : 'form',
		labelWidth : 60,
		items : [ {
			id : 'submitStatus',
			xtype : 'displayfield',
			height : 50,
			fieldLabel : "<span style='font-size:15px;top:30px'></span>",
			labelWidth : 30,
			labelSeparator : "",
			style : "position:relative;top:30px;fontSize:'25px'",
			value : "提交状态"
		} ]
	
	} ]
});

var danxuanPanel = new Ext.Panel({
	id : "danxuan",
	title : "填空题",
	layout : 'column',
	items : [ danxuanProblemform, danxuanOperateform ],
	frame : true, // 圆角边框
});

var form = new Ext.form.FormPanel({
	title : "2014年杭州市攻防竞赛信息",
	region : "north",
	frame : true,
	height : 200,
	labelAlign : 'left',
	layout : 'column',
//	frame : true,
	items : [ {
		columnWidth : 0.8,
		layout : 'form',
		height : 150,
		frame : true,
		items : [ {
			xtype : 'displayfield',
			fieldLabel : "<span style='font-size:15px;top:5px'>考试名称</span>",
			id : 'examname',
			labelWidth : 72,
			labelSeparator : "：",
			style : "position:relative;top:2px;",
			value : ""
		},{
			xtype : 'displayfield',
			fieldLabel : "<span style='font-size:15px;top:5px'>考试时间</span>",
			id : 'examtime',
			labelWidth : 72,
			labelSeparator : "：",
			style : "position:relative;top:2px;",
			value : ""
		},{
			xtype : 'displayfield',
			fieldLabel : "<span style='font-size:15px;top:5px'>当前时间</span>",
			id : 'curtime',
			labelWidth : 70,
			labelSeparator : "：",
			style : "position:relative;top:2px;",
			value : ""
		},{
			xtype : 'displayfield',
			fieldLabel : "<span style='font-size:15px;top:5px'>总  题  量</span>",
			id : 'numberofquestion',
			labelWidth : 70,
			labelSeparator : "：",
			style : "position:relative;top:2px;",
			value : ""
		}/*,{
			xtype : 'displayfield',
			fieldLabel : "<span style='font-size:15px;top:5px'>已提交量</span>",
			id : 'numberofsubmit',
			labelWidth : 70,
			labelSeparator : "：",
			style : "position:relative;top:2px;",
			value : ""
		}*/]
	}, {
		columnWidth : 0.2,
		layout : 'form',
		height : 115,
		items : [ {
			xtype : 'button',
			text : '开始答题',
			anchor : '78%',
			style : "position:relative;top:48px;",
			handler : examBegin
		}]
	} ]
});

var exampanel = new Ext.TabPanel({
	id : "centerTabPanel",
	region : 'center', // a center region is ALWAYS required for border layout
	deferredRender : false,
	activeTab : 0, // first tab initially active
	resizeTabs : true,
	layoutOnTabChange : true,
	defaults : {
		autoScroll : true
	},
	items : [],

	initEvents : function() {
		Ext.TabPanel.superclass.initEvents.call(this);
		this.on('add', this.onAdd, this, {
			target : this
		});
		this.on('remove', this.onRemove, this, {
			target : this
		});
		this.mon(this.strip, 'mousedown', this.onStripMouseDown, this);
		this.mon(this.strip, 'contextmenu', this.onStripContextMenu, this);
		if (this.enableTabScroll) {
			this.mon(this.strip, 'mousewheel', this.onWheel, this);
		}
		this.mon(this.strip, 'dblclick', this.onTitleDbClick, this);
	},
	onTitleDbClick : function(e, target, o) {
		var t = this.findTargets(e);
		if (t.item.fireEvent('beforeclose', t.item) !== false) {
			t.item.fireEvent('close', t.item);
			this.remove(t.item);
		}
	}
});

// 初始入口
function examInfoManagerInit() {
	new Ext.Viewport({
		layout : 'border', // 表格布局
		id : 'mainpanel',
		// autoWidth:true,
		bodyPadding : 5,
		hideMode : Ext.isIE ? 'offsets' : 'display',
		items : [ form, exampanel ],
		renderTo : Ext.getBody()
	});
	addExamProblem(danxuanPanel);
	testpaperRecordStoreInit();
}

function addExamProblem(panel) {
	tabPanel = Ext.getCmp('centerTabPanel');
	tabPanel.add(panel);
	tabPanel.doLayout(true, true);
	tabPanel.show();
	tabPanel.setActiveTab("danxuan");
}

// 单选store取值
function danxuanQuestionStoreInit() {
	var params = {
			loginname : loginname	
	};
	danxuanQuestionStore.load({params:params,
		callback: function(data) {
			if(danxuanQuestionStore.getCount() == 0) {
				Ext.Msg.alert('提示', "获取题目失败或者数据库已经没有选择的题目!");
			} else {
				totalnumber = danxuanQuestionStore.totalLength;
				Ext.getCmp('numberofquestion').setValue("<span style='font-size:15px;top:5px'>" + totalnumber + "</span>");
				getFirstDanxuanProblem();
			}
		}
	});
}

// 出卷记录store取值
function testpaperRecordStoreInit() {
	testpaperRecordStore.load({callback: function(data) {
		if(data == null) {
			Ext.Msg.alert('提示', "获取比赛信息失败!");
		} else {
			// 获取提交数量
			getCommitNumber(loginname);
		}
	}});
}

// 得到题目的总数
function getTotalDanxuanProblemNumber() {
	totalDanxuanProblemNumber = totalnumber;
}

// 将单选题的题目和选项展示到页面
function getDanxuanProblemAndAnswer(localCurProblemNum) {
	var firstProblem = danxuanQuestionStore.getAt(localCurProblemNum).get(
			'question');
	var curquestionnumber = danxuanQuestionStore.getAt(localCurProblemNum).get(
	'number');
	curDanxuanProblemId = danxuanQuestionStore.getAt(localCurProblemNum).get(
	'id');
	
	Ext.getCmp('danxuanDisplayProblem').setValue("<span style='font-size:15px;top:5px'>" + curquestionnumber  + ". " + firstProblem + "</span>");
	Ext.getCmp('answerfield').setValue("");
	
	if(danxuanQuestionStore.getAt(localCurProblemNum).get('submit') == "yes") {
		Ext.getCmp('submitStatus').setValue("<span style='font-weight:bold;'>" + "已经提交" + "</span>");
	} else {
		Ext.getCmp('submitStatus').setValue("<span style='color:red;font-weight:bold;'>" + "还未提交" + "</span>");
	}
}

// 将第一题的内容从store中取出放入题目区域
function getFirstDanxuanProblem() {
	if(isExamBegin == true){
		curDanxuanProblemNumber = 0;
		getDanxuanProblemAndAnswer(curDanxuanProblemNumber);
	}else{
		Ext.MessageBox.alert("提示", "请点击开始答题");
	}

}

function getPreviousDanxuanProblem() {
	if(isExamBegin == true){
		if(curDanxuanProblemNumber == -1){
			Ext.MessageBox.alert("提示", "请选择第一题开始作答");
		}else{
			if (curDanxuanProblemNumber == 0) {
				Ext.MessageBox.alert("提示", "当前已是第一题");
			} else {
				curDanxuanProblemNumber--;
				getDanxuanProblemAndAnswer(curDanxuanProblemNumber);
			}
		}
	}else{
		Ext.MessageBox.alert("提示", "请点击开始答题");
	}
	
	
}
function getNextDanxuanProblem() {
	if(isExamBegin == true){
		getTotalDanxuanProblemNumber();
		if (curDanxuanProblemNumber == totalDanxuanProblemNumber - 1) {
			getDanxuanProblemAndAnswer(curDanxuanProblemNumber);
			Ext.MessageBox.alert("提示", "当前已是最后一题");
			
		} else {
			curDanxuanProblemNumber++;
			getDanxuanProblemAndAnswer(curDanxuanProblemNumber);
		}
	}else{
		Ext.MessageBox.alert("提示", "请点击开始答题");
	}
	
}
function getLastDanxuanProblem() {
	if(isExamBegin == true){
		getTotalDanxuanProblemNumber();
		curDanxuanProblemNumber = totalDanxuanProblemNumber - 1;
		getDanxuanProblemAndAnswer(curDanxuanProblemNumber);
	}else{
		Ext.MessageBox.alert("提示", "请点击开始答题");
	}

}

// time out
function setTestTimeOut() {
	isTimeOut = true;
	
	Ext.MessageBox.alert("提示", "答题时间结束！", function() {
		var temp = window.top.tabPanel.getActiveTab();
		window.top.tabPanel.remove(temp);
	});
}

//显示时间的函数,到时间交卷
function clockon()
{
    var now = new Date();
    
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    
    if(month<10)month="0"+month;
    if(date<10)date="0"+date;
    if(hour<10)hour="0"+hour;
    if(minu<10)minu="0"+minu;
    if(sec<10)sec="0"+sec;
    var time = "";
    time = year+"-"+month+"-"+date+" "+hour+":"+minu+":"+sec;
    checktime = year+"-"+month+"-"+date+" "+hour+":"+minu;
    Ext.getCmp('curtime').setValue("<span style='font-size:15px;top:5px'>" + time + "</span> ");
    // Ext.getCmp('numberofsubmit').setValue("<span style='font-size:15px;top:5px'><b>" + submitnumber + "</b></span>");
    
    if(endtime == checktime){
    	setTestTimeOut();
    } 
    
    setTimeout("clockon()",200);
}

//读取出卷记录里面的内容到全局变量
function readTestPaperRecordInfo(){
	testpaperrecordid =  testpaperRecordStore.getAt(0).get('id');
	starttime = testpaperRecordStore.getAt(0).get('starttime');
	endtime = testpaperRecordStore.getAt(0).get('endtime');
	examname = testpaperRecordStore.getAt(0).get('examname');
	
	//设置考试时间
	Ext.getCmp('examtime').setValue("<span style='font-size:15px;top:5px'>" + starttime + "  至  " + endtime + "</span>");
	Ext.getCmp('examname').setValue("<span style='font-size:15px;top:5px'><b>" + examname + "</b></span>");
	
}

//开始考试，加载题目
function examBegin(){
	if(isExamBegin == false){
		
		readTestPaperRecordInfo();
		judgeCurrentTime();

	}
	
}

function judgeCurrentTime(){
			
			//如果当前时间>=开始时间才可以考试 并且 <= 结束时间 才可以考试
			var dstart=new Date(starttime.replace(/-/g,"/"));
			var dend=new Date(endtime.replace(/-/g,"/"));
			var dcheck=new Date(checktime.replace(/-/g,"/"));
			if(Date.parse(dcheck) >= Date.parse(dstart) && Date.parse(dcheck) <= Date.parse(dend)){
				isExamBegin = true;
				danxuanQuestionStoreInit();
			}
			else
				Ext.MessageBox.alert("提示", "当前非答题时间");
}

function commitCurrentQuestion(){
	if(isExamBegin == true){
		var useranswer = Ext.getCmp("answerfield").getValue();
		if(useranswer == "") {
			Ext.MessageBox.alert("提示", "不能为空，请重新作答！");
			return;
		}
		var userloginname = loginname;
		var questionid = curDanxuanProblemId;
		Ext.MessageBox.confirm('提示', '你确定提交本题 ? 如果提交，将不能修改!', function(
				button) {
			if (button == 'yes') {
				// 判断是否该考生已经提交过本题并提交答案
				CpAnswerRecordController.commitRecordByPerson(useranswer,userloginname,questionid,function(data){
					if(data == false){
						// Ext.MessageBox.alert("提示", "您已经提交过本题，不能再提交，请选择其它题目作答");
						Ext.MessageBox.alert("提示", "存储数据出错！");
					}else{
						// 下一题
						var localCurProblemNum = danxuanQuestionStore.find("id", curDanxuanProblemId);
						var rec = danxuanQuestionStore.getAt(localCurProblemNum);
						rec.set('submit', "yes");
						rec.commit();
						getNextDanxuanProblem();
						submitnumber++;
					}
					
				});
			}
		});

	}else{
		Ext.MessageBox.alert("提示", "请点击开始答题");
	}
	
}
//获取已经提交的题量
function getCommitNumber(userloginname){
	CpAnswerRecordController.getCommitNumber(userloginname,function(data){
		if(data != null)
			submitnumber = data;
	});
}

clockon();

