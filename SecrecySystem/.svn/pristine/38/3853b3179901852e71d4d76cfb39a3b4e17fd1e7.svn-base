Ext.chart.Chart.CHART_URL = '/SecrecySystem/resources/ext/resources/charts.swf';

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

/*var scoreChart = new Ext.chart.Chart( {
    style: 'background:#fff',
    animate: true,        // 动画
    shadow: true,         // 阴影
    store: scoreSummaryStore,        // store
    legend: {             
      position: 'right'   // 图例
    },
    axes: [{
        type: 'Numeric',  // 显示图形类型- line：折线图；column：柱形图；radar：
        position: 'bottom',        
        xField: 'name',
        yField: ['score'],
        minimum: 0,  // 如果小于这个数，图标向下（相当于设置了一个起始点）
        label: {
            renderer: Ext.util.Format.numberRenderer('0,0')
        },
        grid: true,
        title: '得分统计'
    }, {
        type: 'Category',
        position: 'left',
        fields: ['name']
    }],
    series: [{
        type: 'bar',
        axis: 'bottom',
        xField: 'name',
        yField: ['score']
        }
    ]
});*/


var chartPanel = new Ext.Panel({
	title : '实时得分排名',
	region : 'center',
	layout : 'fit',
	items : [{  
        xtype : 'stackedbarchart',  
        style: 'background:#fff',
        animate: true,        // 动画
        shadow: true,         // 阴影
        showAlternateHGridColor : true,
        store : scoreSummaryStore,  
        yField : 'name', 
        xAxis: new Ext.chart.NumericAxis({
            stackingEnabled:true,
            minimum:0,
            labelRenderer : Ext.util.Format.numberRenderer('0,00')
        }),
        yAxis: new Ext.chart.CategoryAxis({
            displayName: '名称'
        }),
        style: {
            size: 15, //设定线条宽度
            color: 0xE1E100
        },
        axes: [{
            type: 'Numeric',  // 显示图形类型- line：折线图；column：柱形图；radar：
            position: 'bottom',        
            xField: 'name',
            yField: ['score'],
            minimum: 0,  // 如果小于这个数，图标向下（相当于设置了一个起始点）
            label: {
                renderer: Ext.util.Format.numberRenderer('0,0')
            },
            grid: true,
            title: '得分统计'
        },{
            type: 'Category',
            position: 'left',
            fields: ['name']
        }],
        series : [{  // 因为柱状栈图拥有多个横轴,所有要使用series代替 ,或者柱状可以拼接起来  
            xField : 'score',  
            displayName : '分  数',
            style: {
            	 color:0x9ACD32
            },
            renderer: function(sprite, record, attr, index, store) {
                var value = record.data.score;
                   if(value < 70){
                    color = '#eac11c';
                   }else if(value==30){
                    color = '#24205f';
                   }else{
                    color = '#2396ff';
                   }
                   return Ext.apply(attr,{fill:color});
            }
        }/*
			 * ,{ xField : 'load', displayName : 'load' }
			 */],  
/*        xAxis : new Ext.chart.NumericAxis({  
            //true表示图一,false表示图二  
            stackingEnabled : true  
        }),*/
        chartStyle: {
            padding: 10,   // 整个图标四周距离
            animationEnabled: true,    // 动画效果？好像设置为false也是一样滴！奇怪！
            font: {	//字体
                name: 'Tahoma',
                color: 0x050505,
                size: 18,
                bold: true
            },
            legend: {  // 最下面图标说明           
            	display : 'bottom',   // 图例
                font: {
                    name: 'Tahoma',
                    color: 0x15428B,
                    size: 30,
                    bold: true
                }
            },
			xAxis : { // x轴线
				color : 0x69aBc8,
				majorTicks : {
					color : 0x69aBc8,
					length : 4
				},
				minorTicks : {
					color : 0x69aBc8,
					length : 2
				},
				majorGridLines : {
					size : 1,
					color : 0xeeeeee
				}
			},
			yAxis : {
				color : 0x69aBc8,
				majorTicks : {
					color : 0x69aBc8,
					length : 4
				},
				minorTicks : {
					color : 0x69aBc8,
					length : 2
				},
				majorGridLines : {
					size : 1,
					color : 0xdfe8f6
				}
			},
            dataTip: {   // 鼠标移动上去显示数据渲染
                padding: 5,
                border: {
                    color: 0x99bbe8,
                    size:1
                },
                background: {
                    color: 0xDAE7F6,
                    alpha: .9    // 透明度:值越小越透明。
                },
                font: {
                    name: 'Tahoma',
                    color: 0x15428B,
                    size: 30,
                    bold: true
                }
            }
        }
   }]  
});

function loadScoreSortInfo() {
	CpAnswerRecordController.getSummaryScore(function(data){ 
		if(data) {
			scoreSummaryStore.loadData(data);
		}
	});
	
	setTimeout("loadScoreSortInfo()",120000);
}

// 入口
function ScoreChartInfoPageInit() {
	loadScoreSortInfo();
	// 初始化界面
	new Ext.Viewport({
		layout : 'border',
		hideMode : Ext.isIE ? 'offsets' : 'display',
		items : [chartPanel ],
		renderTo : Ext.getBody()
	});
}