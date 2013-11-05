function getFlushParam(windowPanelId) {
  return 'tabId=' + tabpanel.active + '&windowPanelId=' + windowPanelId;
}
function showLoadMask(tabId, msg){
  if($('#'+tabId).length==0) {
    $('#load-mask').show();
    $('#load-mask').fadeTo(0, 0.5);
    if(msg && typeof msg==='string'){
      $('#load-msg').text(msg);
    }else{
      $('#load-msg').text('数据加载中...');
    }
    $('#load-msg').css('left', (($(document.body).width()/2) - ($('#load-msg').width()/2)));
    $('#load-msg').css('top', (($(document.body).height()/2) - ($('#load-msg').height()/2)));
    $('#load-msg').show();
  }
}
function submitFrameForm(frameId, formId){	
  if ($.browser.mozilla) {
  	document.getElementById(frameId).contentWindow.document.forms[formId].submit();
  } else {
  	window.frames[frameId].document.forms[formId].submit();
  }
}
var tabpanel;

$(document).ready(function(){
  document.onselectstart = function(){
    return false;
  };
  $('#main').height($(document.body).height() - $('.footer').outerHeight() - $('.header').outerHeight() - $('.module').outerHeight());

  
  addModuleEvent();
  
 
});

//菜单监听事件
function addModuleEvent() {
  $('.module-third li[id!=""]').hover(function(){
    $(this).addClass('over');
  }, function(){
    $(this).removeClass('over');
  });
  
  $('.od').hover(function(){
    window.clearTimeout($(this).data('timer'));
    if ($('.module-second', this).length === 0) {
      return;
    }
    if(!$(this).hasClass('active')) {
      $('.od:visible').removeClass('active');
      $('.module-second:visible').hide();
      $('.module-third').hide();
      $(this).addClass('active');
      $('.module-second li.second').width($(this).width()+50).each(function(){
        if($(this).find('ul.module-third').size()){
          var that = $(this);
          $(this).children('a').addClass('parent').hover(
            function(){
              $(this).addClass('over');
              var _top = that.position().top-1;
              var _left = that.position().left+that.width()+16;
              
              that.find('ul.module-third')
                  .css({'left': _left, 'top': _top})
                  .show();
            },
            function(){
              $(this).removeClass('over');
              $('.module-third').hide();
            }
          );
        }
      }).hover(
        function(){$(this).addClass('over');},
        function(){$(this).removeClass('over');}
      );
      $('.module-second').css('left', $(this).offset().left);
      $('.module-second', this).show();
    }
  }, function(){
    var _s = $(this);
    var _timer = window.setTimeout(function(){
      _s.removeClass('active');
      $('.module-second', _s).hide();
    }, 500);
    _s.data('timer', _timer);
  });
  
  $('.module-third').hover(
    function(){$(this).show();},
    function(){$(this).hide();}
  );
  
}

function openChangePassword(){
  new WindowPanel({
    id : 'changePasswordWindow',
    title : '修改密码',
    width : 270,
    height : 125,
    dragable : true,
    html : '<form id="pwForm" class="form"><div class="form-group">请输入您的新密码</div><table class="form-table" cellspacing="0" cellpadding="0"><tr><td class="form-left"><span class="form-required">*</span>旧密码</td><td class="form-right"><input type="password" name="password" class="text"/></td></tr><tr><td class="form-left"><span class="form-required">*</span>新密码</td><td class="form-right"><input type="password" name="passwordRe" class="text"/></td></tr><tr><td class="form-left"><span class="form-required">*</span>确认新密码</td><td class="form-right"><input type="password" name="passwordReRe" class="text"/></td></tr></table></form>',
    tbar : new Toolbar({
	    icon : 'image/op.gif',
	    items : [{
	      type : 'button',
	      text : '修改密码',
	      position : {
	        a: '-60px 0px',
	        b: '-60px -120px'
	      },
	      handler : function(self, toolbar, windowpanel){
	        $.ajax({
	          url : projectName+'/system/ajax/changePassword.v',
	          data : $('#pwForm').serialize(),
	          success : function(json){
	            switch(json) {
	              case '1' : alert('密码修改成功！');windowpanel.close();break;
	              case '2' : alert('旧密码输入错误，请重新输入！');break;
	              case '3' : alert('输入的新密码不同，请重新输入！');break;
	            }
	          }
	        });
	      }
	    }]
	  })
  });
}
