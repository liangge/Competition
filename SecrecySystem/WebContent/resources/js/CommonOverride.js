// 本js共用重写控件
// 重写TimeField控件，使得可以自动设置时间的min和max
Ext.override(Ext.form.TimeField, {
	setRangeValue : function(minValue, maxValue) {
		if (!Ext.isEmpty(minValue)) {
			this.minValue = this.parseDate(minValue);
		}
		if (!Ext.isEmpty(maxValue)) {
			this.maxValue = this.parseDate(maxValue);
		}
		var min = this.parseDate(this.minValue);
		if (!min) {
			min = new Date(this.initDate).clearTime();
		}
		var max = this.parseDate(this.maxValue);
		if (!max) {
			max = new Date(this.initDate).clearTime().add('mi', (24 * 60) - 1);
		}
		var times = [];
		while (min <= max) {
			times.push([ min.dateFormat(this.format) ]);
			min = min.add('mi', this.increment);
		}
		this.store.loadData(times);
	}
});

var t = new Ext.form.TimeField({
	minValue : '9:00 AM',
	maxValue : '6:00 PM',
	increment : 30,
	renderTo : document.body
});

var b = new Ext.Button({
	renderTo : document.body,
	text : '设置结束时间为20:00',
	handler : function() {
		t.setRangeValue(null, '10:00 PM')
	}
})
