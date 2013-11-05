/**
 * 上传控件类
 * Marker.King
 */
Upload = function(config){
  this.renderTo = config.renderTo || $(document.body);
  this.title = config.title || '添加附件';
  this.multiple = config.multiple;
  this.renamable = config.renamable;
  this.include = config.include;
  this.allowString = config.allowString;
  this.renderContent = typeof this.renderTo == 'string' ? $('#'+this.renderTo) : this.renderTo;
  
  this.count = 0;
  
  this.init();
};

Upload.prototype = {
  
  init : function(){
    this.ct = $('<DIV class="upFix"></DIV>');
    this.ct.appendTo(this.renderContent);
    
    this.fileA = $('<A></A>');
    this.fileA.addClass('uploadBtn');
    this.fileA.appendTo(this.ct);
    
    this.fileA.text(this.title);
    
    this.fileContent = $('<DIV class="fileContent"></DIV>');
    this.fileContent.appendTo(this.renderContent);
    
    this.addFile();
  },
  
  addFile : function(){
    
    var uploadEntity = this;
    
    this.ct.find('.uploadFile').each(function(){
      $(this).removeClass('uploadFile');
      $(this).css('display', 'none');
    });
    
    var fileInput = $('<INPUT type="file" name="upload" class="uploadFile" id="uploadInput_'+this.count+'"/>');
    this.fileA.after(fileInput);
    
    fileInput.bind('mouseout', function(){
      //如果选择了文件
      if(this.value) {
        if(uploadEntity.include) {
          if(!uploadEntity.include.test(this.value)) {
            alert("只能上传以下格式："+uploadEntity.allowString);
            $('#'+this.id+'-aFile').remove();
            $(this).remove();
            uploadEntity.addFile();
            return;
          }
        }
        //显示路径
        var vl = this.value.split('\\');
        var fns = vl[vl.length-1].split('.');
        fns.splice(fns.length-1, 1);
        var fn = fns.join('.');

        //如果是批量上传
        if(uploadEntity.multiple) {
          //创建文件选择后显示的内容层
          var aFile = $('<DIV class="aFile" id="'+this.id+'-aFile"></DIV>');
          
          var uploadPath = $('<DIV class="uploadPath"></DIV>');
          uploadPath.appendTo(aFile);
          //如果可以重命名
          if(uploadEntity.renamable) {
            
            var uploadTitleInput = $('<INPUT name="uploadTitle" class="text rename inputHover"/>');
            uploadTitleInput.appendTo(uploadPath);
            uploadTitleInput.val(fn);
            
            uploadTitleInput.bind('focus', function(){
              $(this).removeClass('inputHover');
            });
            uploadTitleInput.bind('blur', function(){
              $(this).addClass('inputHover');
            });
          }
          uploadPath.append('<span>'+vl[vl.length-1]+'</span>');
          
          uploadEntity.fileContent.prepend(aFile);
          
          //删除
          var deletor = $('<IMG src="'+projectName+'/image/deletor.gif"/>');
          //绑定事件
          deletor.bind('click', function(fileId){
            return function(){
              //将显示层移除
              $('#'+fileId).remove();
              //将File删除掉
              $('#'+fileId.split('-')[0]).remove();
            }
          }(aFile.attr('id')));
          
          deletor.addClass('deletor');
          uploadPath.prepend(deletor);
          
          //计数器++
          uploadEntity.count++;
          //添加一个File
          uploadEntity.addFile();
        }
        //如果是单个文件上传,并且还未选择过文件(仅注释与if中不同的地方)
        else if(uploadEntity.fileContent.find('.aFile').length==0) {
          var aFile = $('<DIV class="aFile" id="'+this.id+'-aFile"></DIV>');
          
          var uploadPath = $('<DIV class="uploadPath"></DIV>');
          uploadPath.appendTo(aFile);
          //如果可以重命名
          if(uploadEntity.renamable) {
            
            var uploadTitleInput = $('<INPUT name="uploadTitle" class="text newInput"/>');
            uploadTitleInput.appendTo(uploadPath);
            uploadTitleInput.val(vl[vl.length-1]);
            
            uploadTitleInput.bind('focus', function(){
              $(this).removeClass('newInput');
            });
            uploadTitleInput.bind('blur', function(){
              $(this).addClass('newInput');
            });
          }
          uploadPath.append('<span>'+vl[vl.length-1]+'</span>');
          
          aFile.appendTo(uploadEntity.fileContent);
          
          var deletor = $('<IMG src="'+projectName+'/image/deletor.gif"/>');
          
          deletor.bind('click', function(fileId){
            return function(){
              $('#'+fileId).remove();
              $('#'+fileId.split('-')[0]).remove();
              //删除掉已选文件后直接添加一个新的文件选择
              uploadEntity.addFile();
            }
          }(aFile.attr('id')));
          
          deletor.addClass('deletor');
          deletor.appendTo(uploadPath);
        }
        //修改显示区文件路径
        else {
          //修改内容
          var aFile = $('.aFile');
          $('.newInput').val(vl[vl.length-1]);
          var uploadPath = $('.uploadPath');
          uploadPath.html('<span>'+vl[vl.length-1]+'</span>');
          
          var deletor = $('<IMG/>');
          deletor.attr('src', projectName+'/image/deletor.gif');
          
          deletor.bind('click', function(fileId){
            return function(){
              $('#'+fileId).remove();
              $('#'+fileId.split('-')[0]).remove();
              uploadEntity.addFile();
            }
          }(aFile.attr('id')));
          
          deletor.addClass('deletor');
          deletor.appendTo(uploadPath);
        }
      }
    });
  }
  
};