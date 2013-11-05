RadioSelect = function(config) {

  this.boxName = config.boxName;
  this.handler = config.handler;
  this.init();
}

RadioSelect.prototype = {
  init : function(){
    //选择控件实体
    var radioSelectEntity = this;
    //tr集合
    this.trs = [];
    //获得所有namebox
    this.boxes = $('input:radio[name="'+this.boxName+'"]');
    var aTable = this.boxes.eq(0).parents('TABLE');
    aTable.addClass('unselect');
    aTable.bind('selectstart', function(){return false;});
    
    //循环
    this.boxes.each(function(){
      var pTr = $(this).parents('TR');
      pTr.css('cursor', 'pointer');
      radioSelectEntity.trs.push(pTr);
      pTr.unbind('click');
      pTr.bind('click', function(event){
        radioSelectEntity.checkLine(this, event);
      });
    });
  },
  //选择行
  checkLine : function(trObj, e){

    //选择控件实体
    var radioSelectEntity = this;
    //获得event
    var eve = window.event ? window.event : e;
    var srcElement = eve.srcElement || eve.target;
    
    //转换tr
    var jTr = $(trObj);
    //得到tr所属的table
    var jTable = jTr.parents('TABLE');
    //得到box
    var jBox = jTr.find('input:radio[name="'+this.boxName+'"]');
    
    if(!jBox.attr('checked')) {
      jBox.attr('checked', true);
    }
    
    //取消掉所有的样式
    jTable.find('tr.selected').each(function(){
      $(this).removeClass('selected');
    });
    
    //被选添加样式,没选取消样式
    if(jBox.attr('checked'))
      jTr.addClass('selected');
    else
      jTr.removeClass('selected');
    if(this.handler) {
      this.handler();
    }
  }
};