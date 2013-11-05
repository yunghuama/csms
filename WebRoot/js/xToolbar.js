var Toolbar = function(config){
	this.id = this.getId();
	this.items = config.items || [];
	this.border = config.border || 'bottom';
	this.renderTo = config.renderTo;
	this.icon = config.icon;
	this.render = typeof this.renderTo == 'string' ? $('#' + this.renderTo) : $('body');

	this._init();
};
Toolbar.AUTO_ID = 100;
Toolbar.prototype = {
	_init: function(){
		var _tbHTML = [];
		_tbHTML.push('<div class="toolbar toolbar-border-'+this.border+'" id="'+this.id+'">');
		_tbHTML.push(  '<table cellPadding="0" cellspacing="0" class="toolbar-table">');
		_tbHTML.push(    '<tbody>');
		_tbHTML.push(      '<tr>');
		_tbHTML.push(        '<td class="toolbar-left-spacer"></td>');
		_tbHTML.push(        '<td align="left" class="toolbar-left">');
		_tbHTML.push(          '<table cellspacing="0">');
		_tbHTML.push(            '<tbody>');
		_tbHTML.push(              '<tr class="toolbar-cell"></tr>');
		_tbHTML.push(            '</tbody>');
		_tbHTML.push(          '</table>');
		_tbHTML.push(        '</td>');
//		_tbHTML.push(        '<td align="right" class="toolbar-right">');
//		_tbHTML.push(          '<table cellspacing="0">');
//		_tbHTML.push(            '<tbody>');
//		_tbHTML.push(              '<tr class="toolbar-cell"></tr>');
//		_tbHTML.push(            '</tbody>');
//		_tbHTML.push(          '</table>');
//		_tbHTML.push(        '</td>');
//		_tbHTML.push(        '<td class="toolbar-right-spacer"></td>');
		_tbHTML.push(      '</tr>');
		_tbHTML.push(    '</tbody>');
		_tbHTML.push(  '</table>');
		_tbHTML.push('</div>');
		this.doc = $(_tbHTML.join(''));
		
		this.jBar   = $('#'+this.id, this.doc);
		this.jTable = $('.toolbar-table', this.doc);
		this.jLeft  = $('.toolbar-left .toolbar-cell', this.doc);
//		this.jRight = $('.toolbar-right .toolbar-cell', this.doc);
		
		
		for(var i=0,len=this.items.length; i<len; ++i){
			this.add(this.items[i]);
		}
		if(this.renderTo){
			this.render.append(this.doc);
		}
	},
	add: function(item){
		var ret,type = item.type||item;
		switch(type){
			case '-':
				ret = new Toolbar.Separator();
				break;
			case 'button':
				ret = new Toolbar.Button(item);
				if(item.position){
					ret.setIcon(this.icon);
					ret.$doPosition();
				}
				break;
			case 'textfield':
				ret = new Toolbar.TextField(item);
				break;
			case 'search':
				ret = new Toolbar.Button({
					id: 'search',
					text: '高级查询',
					position: {
            a: '0px -40px',
            b: '0px -160px'
          },
					handler: function(){
						$('.search-div').slideToggle(100);
					}
				});
        ret.setIcon(this.icon);
        ret.$doPosition();
				break;
			case 'filters':
				ret = new Toolbar.Filters(item);
				ret.setIcon(this.icon);
        ret.$doPosition();
				break;
		}
		var cell = $('<td></td>').append(ret.doc);
		ret.toolbar = this;
		this.jLeft.append(cell);
	},
	addTo: function(win, location){
		this.windowpanel = win;
	},
	remove: function(position){
		switch(typeof position) {
			case 'string': $('#'+position, this.jBar).remove();break;
			case 'number': this.jBar.find('.toolbar-compont').eq(position).remove();break;
		}
	},
	setIcon: function(icon){
		this.icon = icon;
	},
	getId: function(){
		return this.id || (this.id = 'AutoId-toolbar-' + (++Toolbar.AUTO_ID));
	}
};