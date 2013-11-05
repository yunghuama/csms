ListSelect = function(config) {
    this.url = config.url;
    this.width = config.width;  //显示框的宽度
    this.windowWidth = config.windowWidth || 700;  //弹出选择窗口宽度
    this.windowHeight = config.windowHeight || 400; //弹出选择窗口高度

    this.inputId = config.inputId;  //隐藏域ID
    this.contentId = config.contentId; //显示层Id
    this.content = config.content;  //默认显示内容
    this.param = config.param || ''; //参数
    this.init();
  }
  ListSelect.prototype = {
  init : function() {
    var selectEntity = this;
    var inputHidden = $('#'+this.inputId);
    
    var content_div = $('<DIV></DIV>');
    inputHidden.after(content_div);
    
    this.link_div = $('<DIV id ="'+this.contentId+'" style="padding:0 0 0 3px" class="link_content"></DIV>');
    content_div.append(this.link_div);
    this.link_div.width(this.width - 22);
    
    this.link_hider = $('<DIV class="link_content_hider"></DIV>');
    this.link_hider.appendTo(this.link_div);
    this.link_hider.width(this.width - 22);
    this.link_inner = $('<DIV class="link_content_inner"></DIV>');
    this.link_inner.css('width', 1000);
    this.link_inner.appendTo(this.link_hider);
    
    var but = $('<DIV class="userSelectButton"></DIV>');
    this.link_div.after(but);
    
    if(this.content!=undefined&&this.content!=""){
      document.getElementById(this.contentId).innerHTML='<div style="height:20px;overflow:hidden"><div style="height:20px;cursor: pointer;float:left;overflow:hidden" ondblclick="document.getElementById(\''+this.inputId+'\').value=\'\';document.getElementById(\''+this.contentId+'\').innerHTML=\'\'" title="'+this.content+'">'+this.content+'</div>'
          +'<div title="取消选择"  onclick="document.getElementById(\''+this.inputId+'\').value=\'\';document.getElementById(\''+this.contentId+'\').innerHTML=\'\'" style="margin-left:3px;float:left; height: 20px;width:7px;cursor: pointer;background: transparent url('+projectName+'/image/l_close.gif) no-repeat center;" /></div>';
    }

    
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
   var _left = (screen.width - this.windowWidth) / 2;
   var _top = (screen.height - this.windowHeight) / 2;
   var options = new Array();
   options.push('toolbar=no,scrollbars=auto,menubar=no,location=no,resizable=yes');
   options.push(',width=');
   options.push(this.windowWidth);
   options.push(',height=');
   options.push(this.windowHeight);
   options.push(',left=');
   options.push(_left);
   options.push(',top=');
   options.push(_top);

   window.open(this.url+"?inputId="+this.inputId+"&contentId="+this.contentId+"&"+this.param, '', options.join(''));

   }
  }