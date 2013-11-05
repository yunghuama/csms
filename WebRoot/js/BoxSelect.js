BoxSelect = function(config) {

  this.allId = config.allId;
  this.boxName = config.boxName;
  this.handler = config.handler;
  this.init();
}

BoxSelect.prototype = {
  init : function(){
    //选择控件实体
    var boxSelectEntity = this;
    //获得全选Box
    this.allBox = $('#'+this.allId);
    //tr集合
    this.trs = [];
    //获得所有namebox
    this.boxes = $('input:checkbox[name^="'+this.boxName+'"]');
    if(this.boxes.length > 0) {
      var aTable = this.boxes.eq(0).parents('TABLE');
      aTable.addClass('unselect').unbind('selectstart').bind('selectstart', function(){return false;});
      this.allBox.attr('disabled', false);
    } else {
      this.allBox.attr('disabled', true);
    }
    
    //循环
    this.boxes.each(function(){
      var pTr = $(this).parents('TR');
      boxSelectEntity.trs.push(pTr);
  //    pTr.css('cursor', 'pointer');
      pTr.unbind('click');
      pTr.bind('click', function(event){
        boxSelectEntity.checkLine(this, event);
      });
    });
    
    //绑定全选事件
    this.allBox.unbind('click');
    this.allBox.click(function(){
      boxSelectEntity.checkAll();
    });
  },
  //选择行
  checkLine : function(trObj, e){
    //选择控件实体
    var boxSelectEntity = this;
    //获得event
    var eve = window.event ? window.event : e;
    var srcElement = eve.srcElement || eve.target;
    
    //转换tr
    var jTr = $(trObj);
    //得到tr所属的table
    var jTable = jTr.parents('TABLE');
    //得到box
    var jBox = jTr.find('input:checkbox[name^="'+this.boxName+'"]');
    
    //如果没有按住CTRL并且按的不是checkbox,按的是TD或者SPAN
    if(!eve.ctrlKey && !eve.shiftKey && (srcElement.tagName=='TD' || srcElement.tagName=='SPAN') && srcElement.type!='checkbox')
    {
      if(!jBox.attr('checked'))
      {
        jBox.attr('checked', true);
        
        //取消掉所有的样式
        jTable.find('tr.selected').each(function(){
          $(this).removeClass('selected');
          $(this).find('input:checkbox[name^="'+boxSelectEntity.boxName+'"]').removeAttr('checked');
        });
      } else {
        jBox.attr('checked', false);
        //取消掉所有的样式
        jTable.find('tr.selected').each(function(){
          $(this).removeClass('selected');
          $(this).find('input:checkbox[name^="'+boxSelectEntity.boxName+'"]').removeAttr('checked');
        });
      }
    }
    //按住了CTRL
    else if(eve.ctrlKey && (srcElement.tagName=='TD' || srcElement.tagName=='SPAN') && srcElement.type!='checkbox')
    {
      //取消或选择
      if(jBox.attr('checked'))
        jBox.removeAttr('checked');
      else
        jBox.attr('checked', true);
    }
    //按住了SHIFT
    else if(eve.shiftKey)
    {
      //获得被选中的box
      var selecteds = jTable.find('tr.selected');
      //如果有被选中
      if(selecteds.length>0)
      {
        //获得开始下标
        var s_index = this.boxes.index(selecteds.eq(0).find('input:checkbox[name^="'+this.boxName+'"]'));
        //获得结束下标
        var e_index = this.boxes.index(jBox);
        //如果开始大于结束,互换值
        if(s_index>e_index)
        {
          s_index += e_index;
          e_index = s_index - e_index;
          s_index -= e_index;
        }
        //取消掉所有的样式
        jTable.find('tr.selected').each(function(){
          $(this).removeClass('selected');
          $(this).find('input:checkbox[name^="'+boxSelectEntity.boxName+'"]').removeAttr('checked');
        });
        //标记选中
        for(var i=s_index; i<=e_index; i++)
        {
          this.boxes.eq(i).attr('checked', true);
          this.boxes.eq(i).parents('TR').addClass('selected');
        }
      }
      else
      {
        //取消或选择
        if(jBox.attr('checked'))
          jBox.removeAttr('checked');
        else
          jBox.attr('checked', true);
      }
    }
    
    //被选添加样式,没选取消样式
    if(jBox.attr('checked'))
      jTr.addClass('selected');
    else
      jTr.removeClass('selected');
    
    //如果全部选择了,激活allBox
    if($('input:checkbox[name^="'+this.boxName+'"]:checked').length==this.boxes.length)
      this.allBox.attr('checked', true);
    else
      this.allBox.removeAttr('checked');

    if(this.handler) {
      this.handler();
    }
  },
  //全选或者全部取消
  checkAll : function(){
    if(this.allBox.attr('checked'))
    {
      for(var i=0; i<this.boxes.length; i++)
      {
        this.boxes.eq(i).attr('checked', true);
      }
      for(var i=0; i<this.trs.length; i++)
      {
        this.trs[i].addClass('selected');
      }
    }
    else
    {
      for(var i=0; i<this.boxes.length; i++)
      {
        this.boxes.eq(i).removeAttr('checked');
      }
      for(var i=0; i<this.trs.length; i++)
      {
        this.trs[i].removeClass('selected');
      }
    }
    if(this.handler) {
      this.handler();
    }
  }
};