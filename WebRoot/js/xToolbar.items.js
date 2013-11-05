(function () {

  var _prefix = {
		    separator: 'AutoId-toolbar-separator-',
		    button: 'AutoId-toolbar-button-',
		    filters: 'AutoId-toolbar-filters-',
		    textfield: 'AutoId-toolbar-textfield-'
		  },
      _autoId = Toolbar.AUTO_ID;

  //-------------------------------------------------------------------------------- sep
  Toolbar.Separator = function () {
    this.id = this.getId();
    this._init();
  }
  Toolbar.Separator.prototype = {
    _init: function () {
      this.doc = $('<span id="' + this.id + '" class="toolbar-seperater toolbar-compont"></span>');
    },
    getId: function () {
      return this.id || (this.id = _prefix.separator + (++_autoId));
    }
  }

  //-------------------------------------------------------------------------------- btn
  Toolbar.Button = function (config) {
    this.id = config.id || this.getId();
    //图标位置
    this.icon = config.icon;
    this.position = config.position;
    this.text = config.text || '';
    this.tip = config.tip || '';
    this.useable = config.useable || 'T';
    this.handler = config.handler || $.noop;
    this.enableToggle = !! config.enableToggle;
		
		if(this.position) {
			this.position.a = this.position.a || '';
			this.position.b = this.position.b || this.position.a;
		} else {
			this.position = {};
			this.position.a = this.position.b = '';
		}

    //	_beforeHandler: undefined,
    this._init();
  }
  Toolbar.Button.prototype = {
    _init: function () {
      var _btnHTML = [];
      _btnHTML.push('<table cellspacing="0" cellpadding="0" class="toolbar-button-table toolbar-compont">');
      _btnHTML.push('<tbody>');
      _btnHTML.push('<tr>');
      _btnHTML.push('<td class="toolbar-button-left"></td>');
      _btnHTML.push('<td class="toolbar-button-center"></td>');
      _btnHTML.push('<td class="toolbar-button-right"></td>');
      _btnHTML.push('</tr>');
      _btnHTML.push('</tbody>');
      _btnHTML.push('</table>');
      this.doc = $(_btnHTML.join(''));

      var self = this,
          doc = this.doc;

      this.jTable = $('.toolbar-button-table', doc);
      this.jItem = $('tbody tr', doc);
      this.jLeft = $('.toolbar-button-left', doc);
      this.jRight = $('.toolbar-button-right', doc);
      this.jCenter = $('.toolbar-button-center', doc);

      if(this.tip) {
				this.jItem.attr('title', this.tip);
			}

      this.jBtn = $('<div onselectstart="return false;"></div>').text(this.text).attr({
        'id': this.id
      }).appendTo(this.jCenter);

      this.setHandler(this.handler);
      this.setUseable(this.useable);
      this.setToggle(this.enableToggle);
      this.setIcon(this.icon);
    },
    disable: function () {
      this.jBtn.attr('disabled', 'true')
      this.jItem.unbind('.toolbar').addClass('toolbar-button-disabled');
      this.useable = 'F';
    },
    enable: function () {
      var self = this,
          item = this.jItem,
          buttion = this.jBtn;
      buttion.removeAttr('disabled');
			item.bind('click.toolbar', function () {
        self.handler.call(this, self, self.toolbar, self.toolbar.windowpanel);
      });
      item.bind('mouseenter.toolbar-hover', function () { //鼠标hover事件
        $(this).parent().parent().addClass('toolbar-button-over');
      }).removeClass('toolbar-button-disabled');
      item.bind('mouseleave.toolbar-hover', function () { //鼠标hover事件
        $(this).parent().parent().removeClass('toolbar-button-over');
      })
      this.useable = 'T';
    },
    setToggle: function (enableToggle) {
      var item = this.jItem;
			var position = this.position;
      if (enableToggle) {
        if (this.useable === 'T') {
          item.unbind('mousedown.toolbar-toggle mouseup.toolbar-toggle mouseleave.toolbar-toggle').bind('click.toolbar-toggle', function () {
            $(this).parent().parent().toggleClass('toolbar-button-down');
						$(this).find('.toolbar-button-icon').css('background-position', position.b);
          });
        }
      } else {
        item.unbind('click.toolbar-toggle');
        if (this.useable === 'T') {
          item.bind('mousedown.toolbar-toggle', function () { //鼠标按下、松起
            $(this).addClass('toolbar-button-down');
						$(this).find('.toolbar-button-icon').css('background-position', position.b);
          }).bind('mouseup.toolbar-toggle', function(){
						$(this).removeClass('toolbar-button-down');
            $(this).find('.toolbar-button-icon').css('background-position', position.a);
					}).bind('mouseleave.toolbar-toggle', function () { //非toggle模式下，鼠标移出
            $(this).removeClass('toolbar-button-down');
						$(this).find('.toolbar-button-icon').css('background-position', position.a);
          });
        }
      }
      this.enableToggle = enableToggle;
    },
    setUseable: function (useable) {
      useable = typeof useable === 'string' ? (useable === 'T') : false;
      if (useable) {
        this.enable();
      } else {
        this.disable();
      }
    },
    setHandler: function (handler) {
      if ($.isFunction(handler)) {
        this.handler = handler;
      }
    },
    $doPosition: function(){
    	if(this.jIcon && this.position && this.position.a){
		   this.jIcon.css('background-position', this.position.a);
    	}
    },
    setIcon: function(icon){
    	if(typeof icon==='string'){
    		(this.jIcon=$('<div class="toolbar-button-icon"></div>')).css({
    	          'background-image': 'url(' + icon + ')'
    	        }).insertBefore(this.jBtn);
    	    this.icon = icon;
    	}
    },
    setText: function (text) {
      this.jBtn.text(text);
      this.text = text;
    },
    getText: function () {
      return this.text;
    },
    setTitle: function (title) {
      this.jBtn.attr('title', title);
      this.title = title;
    },
    getId: function () {
      return this.id || (this.id = _prefix.button + (++_autoId));
    }
  }

  Toolbar.Filters = function (config) {
    this.id = config.id || this.getId();
    this.items = config.items || [];
    this.tip = config.tip || '';
    this.active = config.active;
    this.filters = {};
		
    this._init();
  }
  Toolbar.Filters.prototype = {
    _init: function () {
      var _tbHTML = [];
      _tbHTML.push('<table cellspacing="0" id="' + this.id + '" cellpadding="0" class="toolbar-filter-table toolbar-compont">');
      _tbHTML.push('<tbody>');
      _tbHTML.push('<tr class="toolbar-filter-render">');
      _tbHTML.push('</tr>');
      _tbHTML.push('</tbody>');
      _tbHTML.push('</table>');
      this.doc = $(_tbHTML.join(''));
			
      this.jRender = $('.toolbar-filter-render', this.doc);
      for (var i = 0, len = this.items.length; i < len; ++i) {
        this._add(this.items[i]);
      }
      this._activeBtn(this.active);
    },
    _add: function (config) {
      if(config.position) {
        config.position.a = config.position.a || '';
        config.position.b = config.position.b || config.position.a;
      } else {
        config.position = {};
        config.position.a = config.position.b = '';
      }
      var self = this;
      var btn = $('<a onselectstart="return false;" id="jFilter-'+config.id+'"></a>');
      config.jButton = btn;
      var center = $('<td class="filter-center"></td>').attr({
        'id': config.id || '',
        'title': config.tip || ''
      }).bind('click.filter', function () {
        self._activeBtn(center);
        config.handler.call(this, config.id);
      }).append(btn);
      this.jRender.append(center);
      this.filters[config.id] = config;
    },
    _activeBtn: function (btn) {
      btn = typeof btn === 'string' ? $('#' + btn, this.doc) : btn;
      if (btn && btn.length) {
        this.doc.find('.center-active').each(function () {
          $(this).removeClass('center-active');
        });
        this.filters[this.active].jButton.css('background-position', this.filters[this.active].position.a);
        btn.addClass('center-active');
        this.active = btn.attr('id');
        this.filters[this.active].jButton.css('background-position', this.filters[this.active].position.b);
      }
    },
		$doPosition: function(){
      for(var fid in this.filters) {
        if(this.active !== fid) {
          this.filters[fid].jButton.css('background-position', this.filters[fid].position.a);
        } else {
          this.filters[fid].jButton.css('background-position', this.filters[fid].position.b);
        }
      }
    },
    setIcon: function(icon){
      this.icon = icon;
      for(var fid in this.filters) {
        this.filters[fid].jButton.css({
          'background-image': 'url(' + icon + ')'
        });
      }
    },
    getId: function () {
      return this.id || (this.id = _prefix.filters + (++_autoId));
    }
  }


  Toolbar.TextField = function (config) {
    this.id = config.id || this.getId();
    //TODO 是否允许为空
    this.allowBlank = config.allowBlank !== undefined ? !! config.allowBlank : true;
    //TODO 文本框为空时的提示文字(不提交)
    this.blankText = config.blankText || '';
    this.useable = config.useable !== undefined ? !! config.useable : true;
    // 标签文字
    this.fieldLabel = config.fieldLabel || '';
    // 标签与文本框分隔符
    this.separator = config.separator || '';
    // TODO 验证函数
    this.invalid = config.invalid;
    // TODO 验证失败提示
    this.invalidText = config.invalidText || '';
    // 文本框name属性
    this.name = config.name || this.id;
    // 文本框提示文字
    this.tip = config.tip || '';
    // 文本框初始值
    this.value = config.value;
    // 标签宽度
    this.labelWidth = config.labelWidth;
    // 文本框宽度
    this.width = config.width || 120;
    //keyup
    this.keyup = config.keyup;

    this._init();
  }
  Toolbar.TextField.prototype = {
    _init: function () {
      var _txtHTML = [];
      _txtHTML.push('<div class="toolbar-textfield toolbar-compont">');
      _txtHTML.push('<div class="toolbar-textfield-label"></div>');
      _txtHTML.push('<input type="text" class="toolbar-textfield-content"/>');
      _txtHTML.push('</div>');
      this.doc = $(_txtHTML.join(''));
      this.jLabel = $('.toolbar-textfield-label', this.doc);
      this.jInput = $('.toolbar-textfield-content', this.doc);

      this.jLabel.html(this.fieldLabel + this.separator);
      this.jInput.attr({
        id: this.id,
        name: this.name,
        value: this.value,
        title: this.tip
      });
      if(this.keyup) {
        this.jInput.keyup(this.keyup);
      }

      this.setWidth(this.width);
      this.setLabelWidth(this.labelWidth);
      this.setUseable(this.useable);
    },
    setWidth: function (width) {
      this.jInput.width(width);
      this.width = width;
    },
    setLabelWidth: function (width) {
      if (width) {
        this.jLabel.css('width', width);
        this.labelWidth = width;
      }
    },
    setUseable: function (useable) {
      this.jInput.attr('disabled', !useable);
      this.useable = useable;
    },
    isUseable: function () {
      return this.useable;
    },
    getId: function () {
      return this.id || (this.id = _prefix.textfield + (++_autoId));
    }
  }

})();