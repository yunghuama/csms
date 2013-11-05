/*!
 * @fileOverview WindowPanel Javascript Component v2.0
 *
 * Copyright 2010, zhangqiang
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Build on jQuery JavaScript Library v1.4.4
 * Date: 2010.12.23
 *
 * Depends:
 *   Draggable.js
 *   Resizable.js
 *
 * @param {Object}   config
 *  {string  | number} config.id           窗口唯一标识(必须)
 *  {string}           config.title        窗口标题
 *  {string}           config.icon         窗口图标URL
 *  {boolean | Object} config.resizable    窗口是否可缩放,可传入true或false表示是否可拖放；传入对象进行缩放配置
 */
var WPS = {};
var WindowPanel = function (config) {
  $.extend(this, WindowPanel.defaults, config);
  this.id = 'jWindowPanel-' + this.id;
  if (WPS[this.id]) {
    WPS[this.id].restore();
		window.setTimeout(function(wpsId){
      return function(){
        if(WPS[wpsId].html !== config.html) {
          WPS[wpsId].jContent.html((WPS[wpsId].html = config.html));
        }
      }
    }(this.id), 300);
    return;
  }
  this.render = typeof this.renderTo === 'string' ? $('#' + this.renderTo) : this.renderTo;
  this._init();
  WPS[this.id] = this;
};

WindowPanel.defaults = {
  title: '',
  html: '',
  icon: null,
  draggable: true,
  hidden: false,
  modal: true,
  autoshow: true,
  minimizable: true,
  maximizable: true,
  closable: true,
  afterCloseHandler: null,
  closeConfirm: false,
  minTip: '最小化',
  maxTip: '最大化',
  restoreTip: '还原',
  closeTip: '关闭',
  width: '100%',
  height: '100%',
  tbar: null,
  //上层工具栏
  bbar: null,
  //下层工具栏
  renderTo: $(document.body),
  position: {}
};

WindowPanel.prototype = {
  isMaximized: false,
  isMinimized: false,
  _init: function () {
    //--------------------------------------------------------组件HTML
    var _winHTML = [];
    _winHTML.push('<div class="windowpanel" id="' + this.id + '">');
    _winHTML.push('<div class="windowpanel-header" onselectstart="return false;">');
    _winHTML.push('<button style="display:none;" accessKey="c"/>');
    _winHTML.push('<div class="windowpanel-title"></div>');
    _winHTML.push('<div class="operators"></div>');
    _winHTML.push('</div>');
    _winHTML.push('<div class="windowpanel-body">');
    _winHTML.push('<div class="windowpanel-content">' + this.html + '</div>');
    _winHTML.push('</div>');
    _winHTML.push('</div>');
    this.render.append(_winHTML.join(''));
    //--------------------------------------------------------获得DOM
    this.jPanel     = $('#' + this.id);
    this.jHeader    = $('.windowpanel-header', this.jPanel);
    this.jTitle     = $('.windowpanel-title', this.jHeader);
    this.jOperators = $('.operators', this.jHeader);
    this.jQickClose = $('button', this.jPanel);
    this.jBody      = $('.windowpanel-body', this.jPanel);
    this.jContent   = $('.windowpanel-content', this.jPanel);
    //--------------------------------------------------------工具栏
    if (this.tbar) {
      this.jTbar = this.add(this.tbar, 'top');
    }
    if (this.bbar) {
      this.jBbar = this.add(this.bbar, 'bottom');
    }
    //---------------------------------------------------------按钮
    if (this.modal) {
    	var $windowModal = $('#windowpanel_modal');
    	if ($windowModal.length == 0) {
    		$windowModal = $('<div id="windowpanel_modal"></div>').appendTo(this.render);
    	}
        //抖抖更健康
        var winPanel = this.jPanel;
    	$windowModal.bind('click', function(){
          var pos = winPanel.position();
          winPanel.animate({'top':  pos.top-1, 'left': pos.left+1}, 50)
                  .animate({'top':  pos.top+1, 'left': pos.left-1}, 50)
                  .animate({'top':  pos.top-1, 'left': pos.left+1}, 50)
                  .animate({'top':  pos.top+1, 'left': pos.left-1}, 50)
                  .animate({'top':  pos.top-1, 'left': pos.left+1}, 50)
                  .animate({'top':  pos.top+1, 'left': pos.left-1}, 50);
        }).hide();
    }
    var zIndex = $.data(WindowPanel, 'zIndex') || 100;
    zIndex++;
    $.data(WindowPanel, 'zIndex', zIndex);
    this.jPanel.hide().css('z-index', zIndex).bind('mousedown', {scope: this}, this._moveToTop);
    if (this.closable) {
      this._addTool({cls: 'close', hoverCls: 'over_close', title: this.closeTip, handler: this.close}, this);
      if(this.jQickClose){
        var that = this;
        this.jQickClose.bind('click', function(){
          that.close();
        });
      }
    }
    if (this.maximizable) {
      this._addTool({cls: 'max', hoverCls: 'over_max', title: this.maxTip, handler: this.maximize}, this);
      this._addTool({cls: 'restore_max', hoverCls: 'over_restore', title: this.restoreTip, handler: this.restore}, this);
    }
    if (this.minimizable) {
      this._addTool({cls: 'min', hoverCls: 'over_min', title: this.minTip, handler: this.minimize}, this);
      this._addTool({cls: 'restore_min', hoverCls: 'over_restore', title: this.restoreTip, handler: this.restore}, this);
    }
    //---------------------------------------------------------其他选项
    var isDraggable = this.draggable;
    if(isDraggable) {
      this.jPanel.draggable(this.jTitle, $.isPlainObject(isDraggable) ? isDraggable : {});
    }
    if (this.autoshow) {
      this.show(this.position);
    }
  },
  //private 添加工具条
  _addTool: function (o, scope) {
    $('<div class="operate-button"></div>').addClass(o.cls).attr('title', o.title).bind('click', {
      scope: scope
    }, o.handler).hover(function () {
      $(this).addClass(o.hoverCls)
    }, function () {
      $(this).removeClass(o.hoverCls)
    }).appendTo(this.jOperators);
  },
  _markSize: function () {
    var content = this.jContent;
    var panel = this.jPanel;
    content.data('rheight.windowpanel', parseInt(content.height()));
    content.data('rwidth.windowpanel', parseInt(content.width()));
    panel.data('rtop.windowpanel', parseInt(panel.css('top')));
    panel.data('rleft.windowpanel', parseInt(panel.css('left')));
  },
  _obtainSize: function () {
    var content = this.jContent;
		var panel = this.jPanel;
    return {
      height: content.data('rheight.windowpanel'),
      width: content.data('rwidth.windowpanel'),
      top: panel.data('rtop.windowpanel'),
      left: panel.data('rleft.windowpanel')
    };
  },
  _moveToTop: function (e) {
    var scope = e.data.scope,
        maxIndex = 1000;
    var zIndex = $.data(WindowPanel, 'zIndex');
    zIndex++;
    $.data(WindowPanel, 'zIndex', zIndex);
    scope.jPanel.css('z-index', zIndex);
  },
  _showModal: function () {
    if ($('.windowpanel').length === $('.windowpanel-minimized').length) {
      $('#windowpanel_modal').hide();
    } else {
      $('#windowpanel_modal').show();
    }
  },
	_getFixHeight: function() {
		var _fixHeight = 0;
    _fixHeight += (this.jHeader.outerHeight() - 1);
    if(this.tbar) {
      _fixHeight += 26;
    }
    if(this.bbar) {
      _fixHeight += 26;
    }
		return _fixHeight;
	},
  minimize: function (e) {
    var win = e && e.data.scope || this;
    if (!win.minimizable || win.isMinimized) {
      return;
    }
    if (!win.isMaximized) {
      win._markSize();
    }
    var head = win.jHeader;
    win.jPanel.addClass('windowpanel-minimized');
    win._showModal();
    win.jPanel.animate({
      top: win.render.outerHeight() - head.outerHeight(),
      left: ($('.windowpanel-minimized').length - 1) * 149,
      width: 150,
      height: head.outerHeight()
    }, 200, function () {
      if ($.browser.msie && $.browser.version === '6.0') {
        win.jTitle.width(100);
      }
      win.jBody.hide();
      if (win.isMaximized) {
        head.find('.max').show();
        head.find('.restore_max').hide();
        win.isMaximized = false;
      }
      head.find('.min,.restore_min').toggle();
      win.isMinimized = true;
    });
    win.jPanel.draggable(win.jTitle, {
      disabled: true
    });
  },
  restore: function (e) {
    var win = e && e.data.scope || this;
    var size = win._obtainSize(),
        head = win.jHeader,
				jContent = win.jContent;
    win.jPanel.removeClass('windowpanel-minimized');
    win._showModal();
    $('.windowpanel-minimized').each(function (i) {
      $(this).css('left', 149 * i);
    });
		jContent.css({
      height: size.height,
      width: size.width
    });
    win.jPanel.animate({
      width: size.width + 2,
			height: size.height + win._getFixHeight() + 2,
      left: size.left,
      top: size.top
    }, 200, function () {
      win.jBody.show();
      if (win.isMaximized) {
        head.find('.max,.restore_max').toggle();
        win.isMaximized = false;
      }
      if (win.isMinimized) {
        head.find('.min,.restore_min').toggle();
        win.isMinimized = false;
      }
    });
  },
  maximize: function (e) {
    var win = e && e.data.scope || this;
    if (!win.maximizable || win.isMaximized) {
      return;
    }
    if (!win.isMinimized) {
      win._markSize();
    }
    var head = win.jHeader,
        jContent = win.jContent
				jPanel = win.jPanel;
    jPanel.removeClass('windowpanel-minimized');
    win._showModal();
    $('.windowpanel-minimized').each(function (i) {
      $(this).css('left', 149 * i);
    });
		var _borderWidth = 2;
		jContent.css({
      width: win.render.outerWidth() - _borderWidth,
      height: win.render.outerHeight() - _borderWidth - win._getFixHeight()
    });
    jPanel.animate({
      width: win.render.outerWidth(),
      height: win.render.outerHeight(),
      left: 0,
      top: 0
    }, 200, function () {
      win.jBody.show();
      if (win.isMinimized) {
        head.find('.min').show();
        head.find('.restore_min').hide();
        win.isMinimized = false;
      }
      head.find('.max,.restore_max').toggle();
      win.isMaximized = true;
    });
    win.jPanel.draggable(win.jTitle, {disabled: true});
  },
  close: function (e) {
    var win = e && e.data.scope || this;
    if (win.closable) {
      if (e && win.closeConfirm) {
      	
      	win.destroy(true);
      	
      	/**关闭弹出窗口时不提示
        if (window.confirm('数据未保存，是否关闭窗口？')) {
          win.destroy(true);
        } else {
          return;
        }
        ***/
      } else {
        win.destroy(true);
      }
      win._showModal();
      $('.windowpanel-minimized').each(function (i) {
        $(this).css('left', 149 * i);
      });
      delete WPS[win.id];
      if(win.afterCloseHandler)
        win.afterCloseHandler.call(this);
    }
    
    top.loadReady();//2011-10-24 GJ 防止loading不结束
    
  },
  hide: function () {
    this.jPanel.hide();
  },
  destroy: function (auto) {
    if (auto) {
      this.jPanel.empty();
    }
    this.jPanel.remove();
  },
  show: function (loca) {
    this.setHeight(this.height);
    this.setWidth(this.width);
    this.setTitle(this.title);
    this.jPanel.show();
    var pageX, pageY, panel = this.jPanel[0],
        render = this.render[0];
    loca = !loca ? {} : loca;
    pageX = loca.x || (this.render.get(0).offsetWidth - this.jPanel.get(0).offsetWidth) * 0.5;
    pageY = loca.y || (this.render.get(0).clientHeight - this.jPanel.get(0).clientHeight) * 0.5;
    this.setPosition(pageX, pageY);
    this._showModal();
  },
  add: function (bar, location) {
    var clazz = 'windowpanel-'+location+'bar';
    var jbar = $('<div class="' + clazz + '"></div>');
    if (location === 'top') {
      this.jContent.before(jbar);
    } else if (location === 'bottom') {
      this.jContent.after(jbar);
    }
    jbar.append(bar.doc)
    bar.renderTo = jbar;
    bar.windowpanel = this;
		return jbar;
  },
  setHeight: function (height) {
    this.height = height;
    this.jContent.height(height);
		this.jPanel.height(height + this._getFixHeight() + 4);
  },
  setWidth: function (width) {
    this.width = width;
    this.jContent.width(width);
    this.jPanel.width(width + 2);
  },
  setTitle: function (title) {
    this.title = title;
    this.jTitle.text(title);
  },
  setPosition: function (x, y) {
    this.jPanel.css({
      left: x,
      top: y
    });
  },
  getId:     function () { this.id; },
  getHeight: function () { this.height; },
  getWidth:  function () { this.width; },
  getTitle:  function () { this.title; }
};