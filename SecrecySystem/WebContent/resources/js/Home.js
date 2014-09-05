var menuGroup = new Array();
var viewport = null;

function init() {
	frameInit();
};
function frameInit() {
	Ext.QuickTips.init();
	 function date(){
		 var dateTime=new Date();
         var hh=dateTime.getHours();//小时
         var mm=dateTime.getMinutes();//分钟
         var ss=dateTime.getSeconds();//秒钟

         var yy=dateTime.getFullYear();//年份
         var MM=dateTime.getMonth()+1;  //月份-因为1月这个方法返回为0，所以加1
         var dd=dateTime.getDate();//日数
         
         var week=dateTime.getDay();//周(0~6,0表示星期日)
              var days=[ "日 ", "一 ", "二 ", "三 ", "四 ", "五 ", "六 ",];

             return yy+" 年 "+MM+" 月 "+dd+" 日 "+"星 期 "+days[week];

             //self.setInterval('date()',1000);
		
	}
	var leftMenu = new com.egov.LeftMenu({
		title : '功能菜单',
		trees : buildMenuGroup(menuGroup, menuString)
	});

	// 建立leftMenu叶子的点击触发事件
	leftMenu.on("nodeClick", function(nodeAttr) {
		// 调用menuOnclick函数处理数据显示
		menuOnclick(nodeAttr.text, nodeAttr.url, nodeAttr.text);
	});

	viewport = new Ext.Viewport(
			{
			layout : 'border', 
				items : [
							/*	{
								xtype:'panel',
								region:'north',
								height:100,
								layout:'fit',
								frame:true,
								html:'<div class="home-top-div"></div>',
								defaults:{
									border:false
								},
								bbar:[
									'->',
									{
										text:"首选项"
									},
									{
										text:"退出"
									}
								]
							},*/
						new Ext.BoxComponent(
								{
									region : 'north',
									height : '63px', // give north and south
									// regions a height
									margin : '0 0 0 0',
									xtype : 'box',
									autoEl : {
										tag : 'div',
										html : '<table border="0" height="63px" width="100%" background=/SecrecySystem/resources/images/banner_bg.png>' 
												+ '<td width="500"><img src="/SecrecySystem/resources/images/secrecytitle.png"/></td>'
												// + '<td align="left" valign="middle"><font >&nbsp;&nbsp;&nbsp;当前用户:'
												+ '<td style="border:0;"><div >&nbsp;&nbsp;&nbsp;当前用户:'
												+'&nbsp;'+ '<strong><font color="#0000FF">' + username + '</font></strong>'
												+ '  &nbsp;&nbsp;当前角色:'
												+' &nbsp;'+ '<strong><font color="#0000FF">' + institution + '</font></strong>'	
												+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+ date()
												+ '</div></td>' 
												+ '<td  valign="middle"><a onclick="window.location.href=\'/SecrecySystem/Logout.do\'" style=\"cursor:pointer;\"><font size="4px">注&nbsp;销</font></a></td></table>'
									}
								}),
						leftMenu,
						new Ext.TabPanel(
								{
									id : "centerTabPanel",
									region : 'center', // a center region is
									// ALWAYS required for
									// border layout
									deferredRender : false,
									activeTab : 0, // first tab initially
									// active
									resizeTabs : true,
									layoutOnTabChange : true,
									height : document.body.clientHeight,
									defaults : {
										autoScroll : true
									},
									items : [],

									initEvents : function() {
										Ext.TabPanel.superclass.initEvents
												.call(this);
										this.on('add', this.onAdd, this, {
											target : this
										});
										this.on('remove', this.onRemove, this,
												{
													target : this
												});
										this.mon(this.strip, 'mousedown',
												this.onStripMouseDown, this);
										this.mon(this.strip, 'contextmenu',
												this.onStripContextMenu, this);
										if (this.enableTabScroll) {
											this.mon(this.strip, 'mousewheel',
													this.onWheel, this);
										}
										// ADD:monitor title dbclick
										this.mon(this.strip, 'dblclick',
												this.onTitleDbClick, this);
									},
									onTitleDbClick : function(e, target, o) {
										var t = this.findTargets(e);
										if (t.item.fireEvent('beforeclose',
												t.item) !== false) {
											t.item.fireEvent('close', t.item);
											this.remove(t.item);
										}
									}
								}) ]
			});
	viewport.setHeight(document.body.clientHeight);
	viewport.doLayout();
	Ext.getCmp('westPanel').doLayout();
	Ext.getCmp('centerTabPanel').doLayout();
}
// 创建菜单栏根结点
function buildMenuGroup(topMenu, data) {
	var menuGroupJSON = Ext.util.JSON.decode(data);
	for (var i = 0; i < menuGroupJSON.length; i++) {
		var topMenuJSON = menuGroupJSON[i];

		var childJSON = topMenuJSON.child;
		topMenu.push(new Ext.tree.TreePanel({
			// 构建树组件
			border : false,
			rootVisible : false, // 根节点不可视
			root : new Ext.tree.AsyncTreeNode({// 通过根节点来实现所有节点数据
				text : topMenuJSON.name,
				expanded : true,// 起初展开根状态
				// 创建根节点的子节点
				children : buildChild(childJSON)
			})
		}));
	}
	return topMenu;
}
// 创建根节点的子节点
function buildChild(childJSON) {
	var childGroup = new Array();
	for (var j = 0; j < childJSON.length; j++) {
		childGroup.push({
			id : childJSON[j].tabId, // text为节点显示的文本，leaf为叶子节点
			text : childJSON[j].name,
			leaf : true, // 说明他是叶子节点
			url : childJSON[j].script

		});
	}
	return childGroup;
}
// 点击以后调用
function menuOnclick(tabId, script, tabTitle) {
	tabPanel = Ext.getCmp('centerTabPanel');
	if (tabPanel.findById(tabId) == null) {
		tabPanel
				.add({
					id : tabId,
					title : tabTitle,
					height : 500,
					autoScroll : true,
					autoWidth : true,
					closable : true,
					// frame : true,
					html : '<iframe id="'
							+ tabId
							+ '_Frame" src="'
							+ script
							+ '" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>',
					listeners : {
						activate : function() {
							this.getUpdater().refresh();
						},
						beforeclose : function() {
							var frame = document.getElementById(this.id
									+ '_Frame');
							if (frame.scr != null)
								frame.src = "javascript:false";
						}
					}
				});
	}
	viewport.doLayout(true, true);
	tabPanel.show();
	tabPanel.setActiveTab(tabId);
};

Ext.ns("com.egov.LeftMenu", "com", "com.egov");
com.egov.LeftMenu = function(config) {
	var d = Ext.apply({
		region : 'west',
		id : 'westPanel', // see Ext.getCmp() below
		// title : '功能菜单',
		split : true,
		width : 200,
		minSize : 175,
		maxSize : 400,
		collapsible : true,
		margins : '0 0 0 5',
		cmargins : '0 5 5 5',
		layout : 'accordion',
		layoutConfit : {
			animate : true
		}
	}, config || {});// 动画方式的accordion
	// 采用accordion布局来进行配置。这两个是导航菜单组件的核心，不能配置
	config = Ext.apply(d, {
		layout : 'accordion',
		collapsible : true
	});
	// 我们通过Function类的call方法来改变Panel类的作用域
	// 同时把配置项也传递给Panel类。继承Ext.Panel类中的构造函数功能
	com.egov.LeftMenu.superclass.constructor.call(this, config);
	// 实现本类的功能

	// 遍历配置项trees集合
	for (var i = 0; i < this.trees.length; i++)
		this.add({
			title : this.trees[i].getRootNode().text,
			items : [ this.trees[i] ]
		});
	// 注册的事件名
	this.addEvents('nodeClick');
	// 运行事件监听函数
	this.initTreeEvent();
};

Ext.extend(com.egov.LeftMenu, Ext.Panel, {
	// LeftMenu类的方法
	initTreeEvent : function() {
		if (!this.items) {
			return;
		}
		// 导航菜单每个子组件
		for (var i = 0; i < this.items.length; i++) {
			// 每个子组件
			var p = this.items.itemAt(i);
			if (p) {
				var t = p.items.itemAt(0);
				if (t)
					t.on({
						// 通过属组建click事件来构建LeftMenu组件的nodeClick事件
						'click' : function(node, event) {
							// 叶子节点，取消其默认事件处理
							if (node && node.isLeaf()) {
								event.stopEvent();
								// 执行注册nodeClick事件注册的回调函数
								this.fireEvent('nodeClick', node.attributes);
							}
						},
						scope : this
					});
			}
		}
	}
});
