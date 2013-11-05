Grid = function(config) {
  this.titleTable = $('#'+config.titleTable);
  this.dataTable = $('#'+config.dataTable);
  this.widths = config.widths;
  this.height = config.height;
  this.autoResize = config.autoResize;
  this.dbView = config.dbView;
  this.autoTip = config.autoTip!=false;
  this.draggable = config.draggable;  //拖拽
  this.init();
}

Grid.prototype = {
  
  init : function() {
    var entity = this;
    
    this.titleTable.wrap('<DIV class="tableColumn"></DIV>');
    this.titleDiv = this.titleTable.parent();
    
    this.dataTable.wrap('<DIV class="tableContent"></DIV>');
    this.dataDiv = this.dataTable.parent();
    
    this.dataDiv.scroll(function(){
      entity.titleTable.css('margin-left', $(this).scrollLeft()*-1);
    });
    /******************IE在添加样式后,会将Form撑出滚动条,因此需要先隐藏表内容,设置完高度后再显示*******************/
    //隐藏表内容
    this.dataDiv.css('display', 'none');
    //设置高度
    if(this.height)
		  this.updateHeight();
    //显示表内容
    this.dataDiv.css('display', 'block');
    //设置宽度
		if(this.widths)
      this.updateWidth();
      
      //拖拽宽度
     if(this.draggable){
      var edge = 5;
      var that = this;
      $('td[class!=box][class!=num]', this.titleTable).bind('mousemove.resizable', function(e){
        var dir = Grid.resizeLocation($(this), e);
        if(dir=='border'){
          $(this).css('cursor','e-resize');
        }else{
          $(this).css('cursor','default');
        }
      }).bind('mousedown.resizable', function(e){
        var $this = $(this);
        var dir = Grid.resizeLocation($(this), e);
        if(dir==''){
          return;
        }
        $(document).bind('mousedown.resizable', function(e){
          $(this).data('start', e.clientX);
          $(this).data('index', $this.index());
          return false;
        });
        
        $(document).bind('mousemove.resizable', function(e){
            return false;
        });
        
        $(document).bind('mouseup.resizable', function(e){
          var start = $(this).data('start');
          var end = e.clientX;
          var index = $(this).data('index');
          //表头文字宽度
          var titleTxtWidth = $.trim($this.text()).length * 12 + 6;
          var titleTxtWidthNext = $.trim($this.next('td').text()).length * 12 + 6;
          
          if(that.widths[index]+end-start>titleTxtWidth){
            that.widths[index] = that.widths[index]+end-start;
            
            if(that.widths[index+1]-end+start>titleTxtWidthNext){
              that.widths[index+1] = that.widths[index+1]-end+start;
            }else{
              that.widths[index+1] = titleTxtWidthNext;
            }
          }else{
            that.widths[index] = titleTxtWidth;
          }
          
          $(document).unbind('.resizable');
          that.updateWidth.call(that);
          return false;
        });
        
      });
    }
      
      
    $('tr.row', this.dataTable).hover(
       function(){$(this).addClass('grid-hover');},
       function(){$(this).removeClass('grid-hover');}
     );
  },
  updateAll : function() {
    if(this.height)
		  this.updateHeight();
    if(this.widths)
      this.updateWidth();
  },
  updateWidth : function() {
    
    var entity = this;
    var countWidth = 0;
    var isFull = true;
    
		//设置表头宽度
    var titleTableTds = this.titleTable.find('tr:first td');
    titleTableTds.each(function(i){
      if(entity.widths[i]) {
        $(this).width(entity.widths[i]);
        countWidth += entity.widths[i];
      } else {
        isFull = false;
      }
    });
    
    //更新表格宽度(判断是否需要显示滚动条)
    if(isFull) {
      this.titleTable.width(countWidth+(this.widths.length*3));
      this.dataTable.width(countWidth+(this.widths.length*3));
      
      if(this.dataDiv.get(0).clientHeight < this.dataDiv.get(0).scrollHeight) {
        this.dataDiv.css({'overflow' : 'scroll', 'overflow-x' : 'auto'});
      } else if(this.dataDiv.get(0).clientWidth < this.dataDiv.get(0).scrollWidth) {
        this.dataDiv.css({'overflow' : 'scroll', 'overflow-y' : 'auto'});
      } else {
        //this.dataDiv.css('overflow', 'hidden');//工作日志快速展开受影响，所以屏蔽掉
      }
      
    } else {
      if(this.dataDiv.get(0).clientHeight < this.dataDiv.get(0).scrollHeight) {
        this.titleTable.width(this.titleDiv.width()-16);
        this.dataTable.width(this.titleDiv.width()-16);
        this.dataDiv.css({
          'overflow' : 'scroll',
          'overflow-x' : 'hidden'
        });
      } else {
        this.titleTable.width(this.titleDiv.width());
        this.dataTable.width(this.titleDiv.width());
        this.dataDiv.css({
          'overflow' : 'hidden'
        });
      }
    }
    
    //更新列宽度
    var dataTableTdsFirst = this.dataTable.find('tr.row');
    dataTableTdsFirst.each(function(){
      if(entity.dbView) {
        $(this).dblclick(function(){
          entity.dbView($(this).find('input:checkbox').val());
        });
      }
      $(this).children('td').each(function(i){
        var td = $(this);
        var tdSpan = td.children('span');
        var haveSpan = (tdSpan.length>0);
        var spanWidth;
        
        if(entity.widths[i]) {
          td.width(entity.widths[i]);
          spanWidth = entity.widths[i]-10;
        } else if(entity.autoResize) {
          td.width(titleTableTds.eq(i).width());
          spanWidth = titleTableTds.eq(i).width()-10;
        } else {
          spanWidth = titleTableTds.eq(i).width()-10;
        }
        
        if(haveSpan) {
          tdSpan.width(spanWidth);
        }
      });
    });
    
    //自动提示
    if(this.autoTip){
      $('td[class!=box][class!=num]', this.dataTable).each(function(){
        var _width = $(this).width();
        var _txt = $(this).text();
        /**
        var maxWidth = parseInt(_width / 6) - 12;
        var sum = 0;
        for(var i=0; i<_txt.length; ++i){
          if(_txt.charAt(i).charCodeAt(0)>=299){
            sum += 2;
          }else{
            sum++;
          }
        }
        if(sum > maxWidth){
          $(this).attr('title',_txt);
        }else{
          $(this).removeAttr('title');
        }
        **/
        
        $(this).attr('title',_txt);
        
      });
      //letter-spacing
      //font-size 12px
    }
    
  },
  updateHeight : function() {
    switch(typeof(this.height)) {
      case 'number' : this.dataDiv.height(this.height-this.titleTable.get(0).offsetHeight);break;
      case 'function' : this.dataDiv.height(this.height());break;
    }
  }
  
};

Grid.resizeLocation = function($this, e){
  var loa = $this.offset();
  var h = $this.outerWidth();
  var dir = '';
  if(e.clientX < loa.left+h && e.clientX > loa.left+h - 5){
    dir = 'border';
  }
  return dir
}