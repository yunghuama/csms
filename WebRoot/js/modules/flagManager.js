/* ---------------------消息提醒旗帜标志,FlagManager.js
|	功能：允许对消息种类进行标志
|   作者：mayh
|   时间：2012年2月

--------------------------------------------------------------*/

var FlagManager = function(config){
	//属性复制
	$.extend(this,FlagManager.defaults,config);
	this.renderTo = typeof this.renderTo == 'string'?$("#"+this.renderTo):this.renderTo;
	this.init();
}
//添加默认属性
FlagManager.defaults = {
	renderTo:$(document.body),
	success:function(color){}
}
FlagManager.prototype = {
	init : function(){
	var $this = this;
	//初始化几种小旗
	var flagTypeArray = [{name:'灰旗',color:'#ADADAC'},{name:'红旗',color:'#F71F34'},{name:'绿旗',color:'#49C73A'},{name:'橙旗',color:'#F78B1F'},{name:'蓝旗',color:'#43C2DB'},{name:'粉旗',color:'#F2A8DE'},{name:'青旗',color:'#19B1B5'},{name:'黄旗',color:'#E5D806'},{name:'紫旗',color:'#D687FF'},{name:'无',color:'#000000'}];
	//创建一个弹出式div
	$this.baseContent = $('<div class="baseContent"><div class="title"><span>选择旗帜</span></div><div class="content"><ul></ul></div></div>');
	$this.baseContent.appendTo($this.renderTo);
	//将小旗添加到容器
	for(var i=0;i<flagTypeArray.length;i++){
		var li = '<li class="flag_'+i+'" name="'+flagTypeArray[i].color+'"><span class="color_'+i+'">'+flagTypeArray[i].name+'</span></li>';
		$(li).appendTo($this.baseContent.find('ul')).bind("mouseover",function(){
			$(this).addClass("mouseOver");
		}).bind("mouseout",function(){
			$(this).removeClass("mouseOver");
		}).bind("click",function(){
			$this.success($(this).attr("name"),$(this).find("span").attr("class"));
			$this.baseContent.hide();
		});
	  }
	//计算位置
	var left = $this.renderTo.width()/2-100;
	var top = $this.renderTo.height()/2-150;
	//设置相关的CSS
	$(".baseContent").css({left:left,top:top,display:'none'});
	},
	//切换状态
	toggle : function(){
		var $this = this;
		$this.baseContent.toggle();
	}
}
