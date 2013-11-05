InnerGrid = function(config) {
  this.title = config.title || '';
  this.columns = config.columns || config.cm || [];
  this.values = config.values || config.val || [];
  this.render = config.render;
  
  //不指定时自动计算各个列的宽度和。如果会把父级Grid的td挤开，需要指定一个最大宽度
  this.width = config.maxWidth; 
  this.id = config.id || this.getId();
  if(!this.render.jquery){alert('InnerGrid组件的render属性必须为jQuery对象!');}
  this._init();
}
InnerGrid.AUTO_ID = 100;
InnerGrid.prototype = {

  _init : function() {
    var len = this.columns.length;
    var _html = [];
    var width = 0;
    
    _html.push('<div id="'+this.id+'" class="inner-grid">');
    _html.push('<table class="inner-table" cellpadding="0" cellspacing="0">');
    _html.push('<tr class="inner-title">');
    _html.push(  '<td colspan="'+len+'"><span>'+this.title+'</span></td>');
    _html.push('</tr>');
    
    _html.push('<tr class="inner-head">');
    
    for(var i=0; i<len; ++i){
      var cm = this.columns[i];
      _html.push('<td align="center" style="width:'+cm.width+'px"><span style="width:'+(cm.width-10)+'px">'+cm.header+'</span></td>');
      width += (cm.width+1);
    }
    _html.push('</tr>');
    
    for(var j=0; j<this.values.length; ++j){
      var val = this.values[j];
      _html.push('<tr class="inner-content">');
      
      for(var k=0; k<len; ++k){
        var cm = this.columns[k];
        var key = $.isArray(val) ? k : cm.mapping;
        _html.push('<td align="'+(cm.align||'left')+'" style="width:'+cm.width+'px"><span '+(cm.tip?'title="'+val[key]+'"':'')+' style="width:'+(cm.width-10)+'px">'+val[key]+'</span></td>');
      }
      _html.push('</tr>');
    }
    _html.push('</table>');
    _html.push('</div>');
    
    var DOM = $(_html.join(''));
    this.render.append(DOM);
    if ($.browser.msie) {
      DOM.slideDown();
    }else{
      DOM.fadeIn('slow');
    }

    if(!this.width){
      this.width = width+1;
    }
    $('#'+this.id).width(this.width+17);
  },
  getId: function(){
      return this.id || (this.id = 'AutoId-InnerGrid-' + (++Toolbar.AUTO_ID));
  }
};

//行的展开、收起
InnerGrid.toggle = function(callback, render){
  var target = render || $('#dataTable div.expand');
  if(target){
    target.toggle(
      function(){
        var crTr = $(this).closest('tr.row');
        var nxTr = crTr.next('tr.ext-tr');
        var exTd = nxTr.find('td.ext-td');
        nxTr.show();
        exTd.text('loading...');
        $(this).addClass('shrink');
        callback.call(crTr, exTd);
      },
      function(){
        $(this).closest('tr.row').next('tr.ext-tr').hide();
        $(this).removeClass('shrink');
      }
    );
  }
};

//展开
$(function(){
  InnerGrid.toggle(function(render){
    var businessKey = $(this).attr('id');
    $.ajax({
      url: projectName+'/workflow/listExamination.v',
      type: 'post',
      data: 'businessKey='+businessKey,
      dataType: 'json',
      success: function(data){
        render.empty();
        //审批记录表
        new InnerGrid({
          render: render,
          title: '流程记录',
          maxWidth: 600,
          columns: [
            {header: '下达日期', mapping: 'startTime', width: 80, align:'center'},
            {header: '处理日期', mapping: 'endTime', width: 80, align:'center'},
            //{header: '耗时', mapping: 'last', width: 80, align:'right',tip: true},
            {header: '操作', mapping: 'operation', width: 90, tip: true},
            {header: '操作人', mapping: 'operator', width: 60, align:'center'},
            {header: '结果', mapping: 'motivation', width: 60, align:'center'},
            {header: '意见', mapping: 'comment', width: 150, tip: true}
          ],
          values: data
        });
      }
    });
  });
});
