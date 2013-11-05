/*!
 * Draggable v1.0
 * Build on jQuery JavaScript Library v1.4
 * 
 * 实现组件简单拖拽效果
 * @author :  zhangqiang
 * @date   :  2010-12-22
 * 
 * TODO IE6 无效
 * 
 * 用法:
 *   target.draggable(config);
 * 说明:
 *   target :  待拖拽的目标组件(需要为jQuery对象)
 *   trigger:  可拖拽的组件位置(需要为jQuery对象)
 *   config :  TODO 配置项
 *     {
 *   
 *     }
 */
(function($){
	$.fn.draggable = function(trigger, config) {
		var target = this,
			doc = $(document),
			body = $('body');
		
		trigger.bind('mousedown.draggable', function(e){
			
			var pageX = e.clientX - trigger.offset().left;
			var pageY = e.clientY - trigger.offset().top;
			var $targetCopy = $('<div class="drag_copy"></div>');
			$targetCopy.css({
				width: target.width(),
        height: target.height(),
				left: e.clientX - pageX,
        top: e.clientY - pageY
			}).appendTo(body);
			
			doc.bind('mousemove.draggable', function(e){
				var wd = target.width()+2,
					bdwd = body.width(),
					ht = target.height()+2,
					bdht = body.height(),
					left,
					top;
				if((e.clientX - pageX) < 0) {
					left = 0;
				} else if((e.clientX - pageX + wd) > bdwd) {
					left = bdwd - wd;
				} else {
					left = e.clientX - pageX;
				}
				if((e.clientY - pageY) < 0) {
					top = 0;
				} else if((e.clientY - pageY + ht) > bdht) {
					top = bdht - ht;
				} else {
					top = e.clientY - pageY;
				}
				target.css('display','none');
				$targetCopy.css({
					left: left,
					top: top
				});
			});
			doc.bind('mouseup.draggable', function(){
				doc.unbind('mousemove.draggable');
				doc.unbind('mouseup.draggable');
				target.css({
					cursor: 'default',
					display: 'block',
					left: $targetCopy.offset().left-1,
					top: $targetCopy.offset().top-1
				});
				$targetCopy.remove();
			});
		});
	};
	//TODO 一些效果的可配性暂时未做
	$.fn.draggable.defaults = {
		type: 'border',    //'border'拖拽时显示边框;   'body' 拖拽时整体
		reserve: true,     //border模式下是否原窗体的展示
		opacity: 0.2       //拖拽过程中窗体的透明度 border默认0.2  body默认1
	};
}(jQuery));