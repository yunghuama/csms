<%@ page language="java" pageEncoding="UTF-8"%>
<script>
/*
 * 遮挡页面加载过程
 * 需要将此页面嵌入到所有页面的body的第一行中,保证此脚本阻塞页面加载
 */
(function(){
  var body = document.getElementsByTagName('body');
  if(body.length){
    var div = document.createElement('div');
    div.style.width = '100%';
    div.style.height = '100%';
    div.style.backgroundColor = '#FFF';
    div.style.position = 'absolute';
    div.style.top = '0px';
    div.style.left = '0px';
//  div.style.cursor = 'wait';
    div.style.zIndex = '100000';
    div.setAttribute('id','btMask');
    document.body.appendChild(div);
  }
})();
</script>