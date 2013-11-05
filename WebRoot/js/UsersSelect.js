/**
 * 用户选择控件
 * */
UsersSelect = function(config) {
  this.url = config.url;
  this.width = config.width;
  this.windowWidth = config.windowWidth || 500;
  this.windowHeight = config.windowHeight || 300;
  this.avgWidth = (this.windowWidth - 89) / 3;
  this.inputId = config.inputId;
  this.windowTitle = config.title;
  this.multiple = config.multiple;
  this.viewType = config.viewType || 'grid';
  this.init();
}

UsersSelect.prototype = {
  init : function() {
    var selectEntity = this;
    var inputHidden = $('#'+this.inputId);
    
    var content_div = $('<DIV></DIV>');
    inputHidden.after(content_div);
    
    this.link_div = $('<DIV class="link_content"></DIV>');
    content_div.append(this.link_div);
    this.link_div.width(this.width - 19);
    
    this.link_hider = $('<DIV class="link_content_hider"></DIV>');
    this.link_hider.appendTo(this.link_div);
    this.link_hider.width(this.width - 19);
    this.link_inner = $('<DIV class="link_content_inner"></DIV>');
    this.link_inner.css('width', 1000);
    this.link_inner.appendTo(this.link_hider);
    
    var but = $('<DIV class="userSelectButton"></DIV>');
    this.link_div.after(but);
    
    but.click(function(){
      selectEntity.openDataSelectWindow();
    }).mousedown(function(){
      $(this).addClass('userSelectButtonDown').mouseout(function(){
        $(this).removeClass('userSelectButtonDown');
      });
    }).mouseup(function(){
      $(this).removeClass('userSelectButtonDown');
    });
  },
  openDataSelectWindow : function() {
    var usersSelectEntity = this;
    this.userPanel = new WindowPanel({
      id : 'usersSelect'+this.inputId,
      title : this.windowTitle,
      width : this.windowWidth,
      height : this.windowHeight,
      maximizable : false,
      minimizable : false,
      modal: false,
      draggable : false,
      html : this.showData(),
      tbar : new Toolbar({
        id: '__user_select',
        icon: '../../image/op.gif',
        items : [{
          id : 'userFilter',
          type : 'textfield',
          width : 80,
          keyup : function(){
            usersSelectEntity.userPanel.jContent.html(usersSelectEntity.showData('realName='+$('#userFilter').val()));
            usersSelectEntity.itemEvent();
          }
        },'-',{
          type : 'button',
          text : '确定',
          position: {
            a: '-80px -100px'
          },
          handler : function(){
            usersSelectEntity.ok();
          }
        },{
          type: 'filters',
          active: usersSelectEntity.viewType,
          items: [{
            id : 'grid',
            tip : '网格',
            position: {
              a: '-100px -100px',
              b: '-100px -220px'
            },
            handler : function(){
              usersSelectEntity.viewType = 'grid';
              usersSelectEntity.userPanel.jContent.html(usersSelectEntity.showData());
              usersSelectEntity.itemEvent();
              $('#jWindowPanel-usersSelect' + usersSelectEntity.inputId + ' .headimg').load(function(){
                usersSelectEntity.rebuildPosition($(this));
              });
            }
          },{
            id : 'list',
            tip : '列表',
            position: {
              a: '-120px -100px',
              b: '-120px -220px'
            },
            handler : function(){
              usersSelectEntity.viewType = 'list';
              usersSelectEntity.userPanel.jContent.html(usersSelectEntity.showData());
              usersSelectEntity.itemEvent();
              $('#jWindowPanel-usersSelect' + usersSelectEntity.inputId + ' .headimg').load(function(){
                usersSelectEntity.rebuildPosition($(this));
              });
            }
          }]
        }]
      })
    });
  
    if(this.multiple) {
      this.userPanel.tbar.add({
        type : 'button',
        text : '全选',
        position: {
          a: '-60px -100px'
        },
        handler :  function(){
          usersSelectEntity.chooseAll();
        }
      });
    }
    this.userPanel.tbar.add({
      type : 'button',
      text : '清空',
      position: {
        a: '0px -20px'
      },
      handler :  function(){
        usersSelectEntity.clear();
      }
    });
    this.itemEvent();
    $('#jWindowPanel-usersSelect' + usersSelectEntity.inputId + ' .headimg').load(function(){
      usersSelectEntity.rebuildPosition($(this));
    });
  },
  rebuildPosition : function(_m) {
    var pSize = this.viewType === 'grid' ? 54 : 20;
    var iSize = this.viewType === 'grid' ? 50 : 16;
    if(_m.height() < _m.width()) {
      _m.css('margin-top', (pSize-_m.height())/2);
    }
  },
  itemEvent : function() {
    var usersSelectEntity = this;
    //获得所有选项元素
    var items = $('.content-body-item-'+this.viewType);
    //添加hover事件
    items.hover(function(){
      $(this).addClass('content-body-item-hover');
    }, function(){
      $(this).removeClass('content-body-item-hover');
    });
    //添加点击事件
    if(this.multiple) {
      items.click(function(){
        var _i = $(this);
        if(_i.hasClass('content-body-item-click')) {
          _i.removeClass('content-body-item-click');
          _i.children('.selected').remove();
        } else {
          _i.addClass('content-body-item-click');
          _i.append('<img src="'+projectName+'/image/UsersSelect/'+usersSelectEntity.viewType+'_selected.gif" class="selected"/>');
        }
      });
    } else {
      items.click(function(){
        var _i = $(this);
        $('.content-body-item-click').removeClass('content-body-item-click').children('.selected').remove();
        if(!_i.hasClass('content-body-item-click')) {
          _i.addClass('content-body-item-click');
          _i.append('<img src="'+projectName+'/image/UsersSelect/'+usersSelectEntity.viewType+'_selected.gif" class="selected"/>');
        }
      });
    }
    //勾选已选中的
    var ids = document.getElementById(this.inputId).value.split(',');
    for(var i = ids.length; i >= 0; i--) {
      if(ids[i]) {
        $('#'+ids[i]).addClass('content-body-item-click').append('<img src="'+projectName+'/image/UsersSelect/'+usersSelectEntity.viewType+'_selected.gif" class="selected"/>');
      }
    }
  },
  showData : function(params) {
    var usersSelectEntity = this;
    var tables = new Array();
    tables.push('<div class="user-select-content">');
    $.ajax({
      url : this.url,
      data : params,
      cache : false,
      async : false,
      success : function(json) {
        var _userList_ = json;
        var tempId = '';
        for(var i = 0; i < _userList_.length; i++) {
          tables.push('<div id="'+_userList_[i].userId+'" class="content-body-item-'+usersSelectEntity.viewType+'" title="'+_userList_[i].realName+'">');
          tables.push(  '<div class="content-body-item-body">');
          tables.push(    '<div class="content-body-item-body-img">');
          if(_userList_[i].normalImage) {
            if(usersSelectEntity.viewType == 'grid') {
              tables.push(      '<img class="headimg" src="'+projectName+_userList_[i].normalImage+'"/>');
            } else {
              tables.push(      '<img class="headimg" src="'+projectName+_userList_[i].smallImage+'"/>');
            }
          } else {
            tables.push(      '<img src="'+projectName+'/image/UsersSelect/'+usersSelectEntity.viewType+'_sex_'+_userList_[i].sex+'.png"/>');
          }
          tables.push(    '</div>');
          tables.push(    '<div class="content-body-item-body-info">');
          if(usersSelectEntity.viewType === 'grid') {
            tables.push(      '<div class="info-bold">'+_userList_[i].realName+'</div>');
            tables.push(      '<div>'+_userList_[i].accountName+'</div>');
            tables.push(      '<div>'+_userList_[i].deptName+'</div>');
          } else {
            tables.push(      '<div style="width:'+usersSelectEntity.avgWidth+'px;" class="info-bold">'+_userList_[i].realName+'</div>');
            tables.push(      '<div style="width:'+usersSelectEntity.avgWidth+'px;">'+_userList_[i].accountName+'</div>');
            tables.push(      '<div style="width:'+usersSelectEntity.avgWidth+'px;">'+_userList_[i].deptName+'</div>');
          }
          tables.push(    '</div>');
          tables.push(  '</div>');
          tables.push('</div>');
        }
      }
    });
    tables.push('</div>');
    return tables.join('');
  },
  choose : function() {
    this.link_inner.html('');
  	var list = $('.content-body-item-click');
    var values = new Array();
    var links = new Array();
    var names = new Array();
    var usersSelectEntity = this;
    for(var i=0;i<list.length;i++) {
      var s_id = list.eq(i).attr('id');
      var s_realName = list.eq(i).attr('title');
      values.push(s_id);
      names.push(s_realName);
      var u_user = this.getUserLink({userId:s_id, realName:s_realName, showInfo:true});
      var u_html = $('<DIV id="'+this.inputId+'_'+s_id+'" ></DIV>');
      u_html.append(u_user[0]);
      u_html.append(u_user[1]);
      this.link_inner.append(u_html);
      
    }
    $('#'+this.inputId).val(values.join(','));
    this.link_inner.attr("title",names.join(','));
    this.update();
  },
  ok : function() {
    this.choose();
    WPS['jWindowPanel-usersSelect'+this.inputId].close();
  },
  chooseAll : function() {
  	var list = $('.content-body-item-'+this.viewType);
    for(var i=0;i<list.length;i++) {
      if(!list.eq(i).hasClass('content-body-item-click')) {
        list.eq(i).addClass('content-body-item-click');
        list.eq(i).append('<img src="'+projectName+'/image/UsersSelect/'+this.viewType+'_selected.gif" class="selected"/>');
      }
    }
  },
  clear : function() {
  	$('.content-body-item-click').removeClass('content-body-item-click').children('.selected').remove();
  },
  update : function() {
    if(this.multiple) {
      //判断是否溢出
      var linkas_widths = 0;
      this.link_inner.children('DIV').each(function(){
        linkas_widths += $(this).outerWidth(true);
      });
      if(linkas_widths > this.link_div.width()) {
        if(!this.hasMore) {
          var realNames = [];
          this.link_inner.find('.user_selected').each(function(){
            realNames.push($(this).text());
          });
          this.link_div.append((this.link_fix = $('<div class="more" title="'+realNames.join(',')+'">...</div>')));
          this.hasMore = true;
        }
        this.link_hider.width(this.link_div.width() - this.link_fix.outerWidth());
      } else {
        this.link_hider.width(this.link_div.width());
        if(this.hasMore) {
          this.link_fix.remove();
          this.hasMore = false;
        }
      }
    }
  },
  removeUser : function(uid) {
    var hInput = $('#'+this.inputId);
    var str = hInput.val().replace(uid, '').replace(',,', ',');
    if(str.substr(0,1) == ','){
        str = str.substr(1,str.length-1);    	
    }
    hInput.val(str);
    //处理title
    var title = this.link_inner.attr("title").replace($('#'+this.inputId+'_'+uid).find('.user_selected').text(), '').replace(',,', ',');
    if(title.substr(0,1) == ','){
        title = title.substr(1,title.length-1);     
    }
    if(title.substr(title.length-1,title.length) == ','){
        title = title.substr(0,title.length-1);   
    }
    this.link_inner.attr("title",title);
    
    $('#'+this.inputId+'_'+uid).remove();
    this.update();
  },
  getUserLink : function(config) {
    var entity = this;
    var showInfo = config.showInfo ? 'void(0);' : 'void(0);';
    var user = $('<div class="user_selected" onclick="'+showInfo+'">'+config.realName+'</div>');
    var u_delete = $('<div title="取消\''+config.realName+'\'" class="closer"></div>');
    u_delete.click(function(){
      entity.removeUser(config.userId);
    });
    return [user, u_delete];
  },
  defaultChoose : function(values,names){
      for(var i=0;i<values.length;i++) {
        var u_user = this.getUserLink({userId:values[i], realName:names[i], showInfo:true});
        var u_html = $('<DIV id="'+this.inputId+'_'+values[i]+'"></DIV>');
        u_html.append(u_user[0]);
        u_html.append(u_user[1]);
        this.link_inner.append(u_html);
      }
      $('#'+this.inputId).val(values.join(','));
      this.link_inner.attr("title",names.join(','));
      this.update();
  }
}