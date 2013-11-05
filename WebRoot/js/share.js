/*弹出居中窗口*/
function openCenterWindow(config) {
  var _left = (screen.width - config.width) / 2;
  var _top = (screen.height - config.height) / 2;
  var options = new Array();
  options.push('toolbar=no,scrollbars=no,menubar=no,location=no,resizable=yes');
  options.push(',width=');
  options.push(config.width);
  options.push(',height=');
  options.push(config.height);
  options.push(',left=');
  options.push(_left);
  options.push(',top=');
  options.push(_top);
  window.open(config.url, '', options.join(''));
}

/**获得Frame*/
function getFrame(frameId)
{
  if($.browser.mozilla) {
    return document.getElementById(frameId).contentWindow;
  } else {
    return window.frames[frameId];
  }
}

/**
 * 分页组件的JS脚本
 * 
 * @param {int} page 页数
 * @param {int} pageSize 每页显示数量
 * @param {Object} com 页面被点击的对象
 * @param {Object} formObj 需要提交的表单
 * @param {string} formUrl 需要提交的路径
 */
function pagination(page, pageSize, com, formObj, formUrl)
{
  var page_form = formObj || $(com).parents('form');
	$('#page_currPage').val(page);
  $('#page_pageSize').val(pageSize);
  if(formUrl)
    page_form.attr('action', formUrl);
  page_form.submit();
}

/**
 * 初始化表格
 */
function initGrid(config) {
  setTableHeight(config);
  setTitleWidth(config.widths, config.tableId);
}

/**
 * 动态设置表格宽度
 */
function setTitleWidth(array, id){
  
  var tables = id ? $('#'+id+'.table') : $('div.table');
  tables.each(function(i){
    var tableColumn = $(this).children('div.tableColumn');
    var tableContent = $(this).children('div.tableContent');
    var tableColumnTable = tableColumn.children('table');
    var tableContentTable = tableContent.children('table');
    
    var tableColumnTableTds = tableColumnTable.find('tr td');
    var tableContentTableTdsFirst = tableContentTable.find('tr:first td');
    
    //本次循环设置宽度是为了有些列缩小后才出现滚动条,因此先将第一行宽度设置好,就会知道是否出现滚动条
    tableContentTableTdsFirst.each(function(i){
      if(array[i])
        $(this).css('width', array[i]);
    });
    
    if(tableContent.get(0).clientHeight < tableContent.get(0).scrollHeight)
    {
      tableColumnTable.css('width', (tableColumn.width()-17));
      tableContentTable.css('width', (tableColumn.width()-17));
    }
    else
    {
      tableColumnTable.css('width', (tableColumn.width()));
      tableContentTable.css('width', (tableColumn.width()));
    }
    
    tableColumnTableTds.each(function(i){
      $(this).css('width', array[i]);
    });
    
    tableContentTable.find('TR.group').each(function(){
      $(this).children('td').attr('colSpan', array.length);
    });
    
    tableContentTable.find('tr').each(function(){
      $(this).children('td').each(function(i){
        if(array[i])
          $(this).css('width', array[i]);
        else
          $(this).css('width', tableColumnTableTds.eq(i).width());
      });
    });
    
  });
}

/**
 * 动态设置表格高度
 */
function setTableHeight(config){

  var tableContent = config.id ? $('#'+config.id+'.tableContent') : $('.tableContent');
  var fixHeight = config.hasPage ? 1 : 0;//这1个像素是分页栏的边框
  if(config.toolbarId)
    fixHeight += $('#'+config.toolbarId).height();
    
  if(config.hasPage)
    fixHeight += $('.pagination').height();
  
  fixHeight += $('.tableColumn').height();
  
  if(config.height)
    tableContent.css('height', config.height);
  else
    tableContent.css('height', ($(document.body).height() - fixHeight));
}

/**
 * 动态设置表格高度
 */
function getGridHeight(config){

  var fixHeight = config.hasPage ? 1 : 0;//这1个像素是分页栏的边框
  if(config.toolbarId)
    fixHeight += $('#'+config.toolbarId).height();
    
  if(config.hasPage)
    fixHeight += $('.pagination').height();
  
  fixHeight += $('.tableColumn').height();
  
  if(config.height)
    return config.height;
  else
    return ($(document.body).height() - fixHeight);
}

/**
 * 动态设置树形菜单高度
 */
function setTreeHeight(array, fixHeight){
  if(!fixHeight)
    fixHeight = 0;
  for(var i=0; i<array.length; i++)
  {
    fixHeight += $('#'+array[i]).height();
  }
  $('.treePanel').height($(document.body).height() - fixHeight);
}

function getFirstID(promFlag,name)
{
  promFlag = promFlag || '';//提示信息显示标志，如果为空，则显示下面的提示信息
  name = name || 'idList';
  var c_val = '';
  var boxes = document.getElementsByName(name);
  for(var i=0; i<boxes.length; i++)
  {
    if(boxes[i].checked)
    {
      c_val = boxes[i].value;
      break;
    }
  }
  if(c_val=='')
  {
  	if(promFlag == '')
  	{
  		c_val = false;
        alert('请选择需要操作的记录');
  	}
    
  }
  return c_val;
}

function hasChecked(name)
{
  name = name || 'idList';
  var boxes = document.getElementsByName("idList");
  for(var i=0; i<boxes.length; i++)
  {
    if(boxes[i].checked)
    {
      return true;
    }
  }
  alert('请选择需要操作的记录');
  return false;
}

function loadReady()
{
  top.$('#load-mask').hide();
  top.$('#load-msg').hide();
  $('#btMask').hide();
}

function validateBeforeDelete(config) {
  if(config.validateTable && getFirstID()) {
    config.validateMessage = config.validateMessage || '标红记录已关联重要信息，不能被删除！';
    config.deleteMessage = config.deleteMessage || '是否删除选中记录？';
    $.ajax({
      url : config.validateURL,
      data : 'tableName=' + config.validateTable + '&' + config.validateParams,
      async : false,
      success : function(json) {
        if(json.length>0) {
          for(var i=0; i<json.length; i++) {
            $('input:checkbox[name="idList"][value="'+json[i]+'"]').parent('TD').addClass('cantDelete');
          }
          alert(config.validateMessage);
        } else {
          $.prompt(config.deleteMessage, {
            buttons:{删除:true,取消:false},
            callback: function(v,m) {
              if(v) {
                document.getElementById('listForm').action = config.deleteURL;
                document.getElementById('listForm').submit();
              }
            }
          });
        }
      }
    });
  }
}

function validateBeforeMoreDelete(config) {
  if(config.validateTable && config.validateParams) {
    config.validateMessage = config.validateMessage || '记录已关联重要信息，不能被删除！';
    config.deleteMessage = config.deleteMessage || '是否删除选中记录？';
    $.ajax({
      url : config.validateURL,
      data : 'tableName=' + config.validateTable + '&' + config.validateParams,
      async : false,
      success : function(json) {
        if(json.length>0) {
          alert(config.validateMessage);
        } else {
          $.prompt(config.deleteMessage, {
            buttons:{删除:true,取消:false},
            callback: function(v,m) {
              if(v) {
                window.location = config.deleteURL + '?' + config.validateParams;
              }
            }
          });
        }
      }
    });
  }
}

function del(config) {
  if(getFirstID()) {
    config.deleteMessage = config.deleteMessage || '确定进行此操作？';
    $.prompt(config.deleteMessage, {
      buttons:{'确定':true,'取消':false},
      callback: function(v,m) {
        if(v) {
          if(config.callback)
            config.callback();
          document.getElementById('listForm').action = config.deleteURL;
          document.getElementById('listForm').submit();
        }
      }
    });
  }
}

function findOtherUsersOperate(usersId, webId) {
  var useable = 'F';
  $.ajax({
    url : projectName+'/system/ajax/findOtherUsersOperate.v',
    data : 'usersId='+usersId+'&webId='+webId,
    async : false,
    success : function(json){
      useable = json;
    }
  });
  return useable;
}
function findOtherOperate(usersId,departmentId,webId,ownerDeptId,ownerId) {
  var useable = 'F';
  $.ajax({
    url : projectName+'/system/ajax/findOtherUsersOperate.v',
    data : 'usersId='+usersId+'&webId='+webId+'&departmentId='+departmentId+'&ownerDeptId='+ownerDeptId+'&ownerId='+ownerId,
    async : false,
    success : function(json){
      useable = json;
    }
  });
  return useable;
}

function getNoDataHTML() {
  var noDateHTML = $('<div class="noData"></div>');
  $(document.body).append(noDateHTML);
  var text = $('<div class="textDiv">未找到相关数据记录</div>');
  text.css({
    left : noDateHTML.width()/2 - 103,
    top : noDateHTML.height()/2 - 28
  });
  noDateHTML.append(text);
}
/**
 * 根据传入的目标参数，打开目标标签
 */
function showTabPanel(trigger) {
    top.$('#'+trigger).click();
}

function jumpTo(id,url) {
	$('.split').find('div').each(function (i){
		if($(this).attr('class')=='opened'){
			$(this).removeClass('opened');
			$(this).addClass('closed');
		}
	});
	$('#'+id).removeClass('closed');
    $('#'+id).addClass('opened');
    parent.mainFrame.location = url;
}

/**
 * 坐标
 * @param x
 * @param y
 * @return
 */
function CPos(x, y)
{
    this.x = x;
    this.y = y;
}
/**
 * 得到对象的相对浏览器的坐标
 * @param ATarget
 * @return
 */
function GetObjPos(ATarget)
{
    var target = ATarget;
    var pos = new CPos(target.offsetLeft, target.offsetTop);
    
    var target = target.offsetParent;
    while (target)
    {
        pos.x += target.offsetLeft;
        pos.y += target.offsetTop;
        
        target = target.offsetParent
    }
    return pos;
}
