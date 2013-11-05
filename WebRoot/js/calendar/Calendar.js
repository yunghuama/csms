/*------------------日历插件JS---------------------
*说明：在页面引入Calendar.js，Calendar.css，Jquery.js
*参数：cellBorder：是否显示日历表格线，true？显示：鼠标移上去显示
	  handler：点击日历单元格时的处理函数，参数为当前单元格日期对象
	  message：期望参数为数组{time:1，msg:1111}，用于显示检维修状态，例如：msg:1111，每一位表示一个状态,time:1表示日期为1号
	  simple：是否显示月份切换工具栏，true？鼠标移上去显示：不显示
*/
var Calendar = function(config){
	$.extend(this , Calendar.defaults , config);
	this.renderTo = typeof this.renderTo=='string'?$("#"+this.renderTo):this.renderTo;
	this.init();
};
//默认设置
Calendar.defaults = {
    height: 600
};
//扩展属性
Calendar.prototype = {
	init : function(){
		var now = this.nDate();
		this.drawCalendar(now.getFullYear(),now.getMonth()+1);
	},
	drawCalendar:function(year,month){
		var entity = this;
		var div = [];
			div.push('<div class="calendar" style="height:'+this.height+'px">');
		if(!this.simple){
			div.push('<div class="tools transparent"><div class="lastMonth"><<上月</div><div class="nextMonth">下月>></div><div class="dTitle">'+year+'&nbsp;年&nbsp;'+month+'&nbsp;月&nbsp;</div></div>');
		}
			div.push('<div class="content"></div>');
			div.push('</div>');
		var jCalendar = $(div.join('')).appendTo(this.renderTo);
        this.renderTo.css('overflow', 'hidden');
        this.renderTo.height(this.height);
		//添加日历详细信息
			jCalendar.find(".content").append(entity.calendarItem(year,month));
			
		if(!this.simple){
		//【上月】 添加点击事件
		jCalendar.find(".lastMonth").click(function(){
			entity.refresh(jCalendar,entity.tDate.year,entity.tDate.month-1);
		});
		//【下月】添加点击事件
		jCalendar.find(".nextMonth").click(function(){
			entity.refresh(jCalendar,entity.tDate.year,entity.tDate.month+1);
		});
		}
		//添加相应方法
		entity.calendarConfig(jCalendar);
	},
	calendarItem : function(year,month){
	var entity = this;
	//根据指定的时间绘制日历
	var weeks = ['日','一','二','三','四','五','六'];
	//--------计算该月有几天
	var dateTable = [];
		dateTable.push('<table class="calendarTable">');
		dateTable.push('<tr class="weeks">');
		//绘制周
		for(var i=0;i<weeks.length;i++){
			dateTable.push('<td><span>'+weeks[i]+'</span></td>');
		}
		dateTable.push("</tr>");
		var date = entity.cDate(year,month);
		entity.tDate = date;
		var column = 0;
		//绘制每一天
		//1.首先绘制第一周的空白时间
		for(var i=0;i<date.firstWeekDay;i++){
			if(column==0){
				dateTable.push('<tr class="day">');
			}
			dateTable.push('<td></td>');
			column++;
		 }
		//2.绘制每一天
		for(var i=1;i<=date.days;i++){
			if(column==0){
				dateTable.push('<tr class="day">');
			}
			dateTable.push('<td id="'+i+'" class="dayCell"><div class="tdDiv"><div class="cir"></div><div class="dayItem"><span>'+i+'</span></div><div class="msg"><div class="msgBody"><div class="d" title="维修"></div><div class="x" title="点检"></div><div class="r" title="润滑"></div><div class="b" title="保养"></div></div></div></div></td>');
			column++;
			if(column==7){
				dateTable.push('</tr>');
				column=0;
			}
		}
		//3.绘制剩余的空白时间
		for(var i=0;i<6-date.lastWeekDay;i++){
			dateTable.push('<td></td>');
		}
		if((6-date.lastWeekDay)!=0){
			dateTable.push('</tr>');
		}
		dateTable.push('</table>');
		
	    return dateTable.join('');
	},
	//配置日历
	calendarConfig : function(calendar){
		var entity = this;
		if(calendar){
			//设置日历单元格的宽度
			var width = calendar.find(".content").width();
			calendar.find(".weeks td").each(function(){
				$(this).width(width/7);
			});
			//为每个单元格添加点击事件
			if(entity.handler){
				calendar.find(".dayCell").bind("click",function(){
					var id = $(this).attr("id");
					var cDate = entity.tDate;
						cDate.day = id;
						entity.handler(cDate);
				}).bind("mouseover",function(event){
						$(this).css("background","#e1e1e1");
				}).bind("mouseout",function(event){
						$(this).css("background","");
				});
			}
			//改变周末的颜色
			calendar.find("tr").each(function(){
				$(this).find("td").eq(0).find("span").addClass("weekDay");
				$(this).find("td").eq(6).find("span").addClass("weekDay");
			});
			//改变今天的颜色
			var now = entity.nDate();
			if((now.getFullYear()+""+(now.getMonth()+1))==(entity.tDate.year+""+entity.tDate.month)){
				calendar.find("#"+now.getDate()).find(".cir").show();
				calendar.find("#"+now.getDate()).find(".dayItem span").css("color","#159306");
			}
			//设置图标的显示
			if(entity.message){
				entity.setMessage(calendar,entity.message);
			}
			//动态添加表格线
			if(!entity.cellBorder){
			$("table.calendarTable").hover(function(){
				$(".day td").addClass("tdHover");
			},function(){
				$(".day td").removeClass("tdHover");
			});
			}else {
			$(".day td").addClass("tdHover");
			}
			//添加tools 显示
			if(!entity.simple){
			calendar.find(".tools").hover(function(){
				calendar.find(".tools").addClass("transparent_").removeClass("transparent");;
			},function(){
				$(this).removeClass("transparent_").addClass("transparent");
			});
			calendar.find(".lastMonth,.nextMonth").bind("mouseover",function(){
				$(this).addClass("toolsHover");
			}).bind("mouseout",function(){
				$(this).removeClass("toolsHover");
			});
			}
		}
	},
	//根据日期，计算有关的时间点
    cDate : function(year,month){
    var date = {};
    	//计算该月有几天
    	var eDate = new Date(year,month,0);
    	date.year = year;
    	date.month = month;
    	date.days = eDate.getDate();
    	//计算该月的第一天是周几
    	eDate.setDate(1);
    	date.firstWeekDay = eDate.getDay();
    	//计算该月的最后一天是周几
    	eDate.setDate(date.days);
    	date.lastWeekDay = eDate.getDay();
    	return date;
    },
    //得到今天
    nDate : function(){
    	var now = new Date();
    	return now;
    },
    //设置提醒
    setMessage : function(calendar,msgArray){
    	for(var i=0;i<msgArray.length;i++){
    		var msgBody = msgArray[i];
    		//拆分
    		var msg = msgBody.msg.split('');
    		if(msg.length!=4){
    			continue;
    		}
    		var width = 0;
    		if(msg[0]=='1'){
    			calendar.find("#"+msgBody.time).find(".d").show();
    			width+=13;
    		}
    		if(msg[1]=='1'){
    			calendar.find("#"+msgBody.time).find(".x").show();
    			width+=13;
    		}
    		if(msg[2]=='1'){
    			calendar.find("#"+msgBody.time).find(".r").show();
    			width+=13;
    		}
    		if(msg[3]=='1'){
    			calendar.find("#"+msgBody.time).find(".b").show();
    			width+=13;
    		}
    		calendar.find("#"+msgBody.time).find(".msgBody").width(width);
    	}
    },
    refresh:function(calendar,year,month){
    	var entity = this;
    	month = month<1?1:month>12?12:month;
    	//删除原来的
    	calendar.find(".calendarTable").remove();
    	calendar.find(".content").append(entity.calendarItem(year,month));
    	calendar.find(".dTitle").html(year+'&nbsp;年&nbsp;'+month+'&nbsp;月&nbsp;');
    	entity.calendarConfig(calendar);
    }
};
