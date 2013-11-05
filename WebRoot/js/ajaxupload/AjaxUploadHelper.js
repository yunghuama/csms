/* ----------------------Ajax文件上传封装类,AjaxUploadHelper.js
|	功能：支持文件的Ajax上传
|   作者：mayh
|   时间：2011年12月

--------------------------------------------------------------*/

var AjaxUpload = function(config){
	//属性复制
	$.extend(this,AjaxUpload.defaults,config);
	this.renderTo = typeof this.renderTo == 'string'?$("#"+this.renderTo):this.renderTo;
	this.init();
}
//添加默认属性
AjaxUpload.defaults = {
	renderTo:$(document.body),
	timer:500,
	title : '添加附件',
	multiple:false
}
AjaxUpload.prototype = {
	init : function(){
		var $this = this;
		//创建一个DIV容器，用来存放和上传有关的内容
		$this.uploadContent = $('<div class="ajaxupload"></div>');
		$this.uploadContent.appendTo($this.renderTo);
		//创建一个添加按钮
		$this.addButton = $('<div class="ajaxupload-button-addfile">'+$this.title+'</div>');
		$this.addButton.appendTo($this.uploadContent);
		//创建一个loading图片
		$this.loadingButton = $('<img/>');
		$this.loadingButton.addClass("ajaxupload-button-loading-hidden");
		$this.loadingButton.addClass("ajaxupload-button-loading");
		$this.loadingButton.attr("src",projectName+"/image/loading.gif");
		$this.addButton.after($this.loadingButton);
		//创建文件显示区域
		$this.listFiles = $('<div class="ajaxupload-content-listfiles"></div>');
		$this.listFiles.appendTo($this.uploadContent);
		var id = $this.guid();
		//创建一个透明的File域
		$this.fileF = $('<input type="file" name="upload" class="ajaxupload-files" id='+id+'>');
		$this.fileF.appendTo($this.uploadContent);
		//为file添加鼠标移动事件
		var mouseout = function(){
			//首先判断有没有File域
			if($(".ajaxupload").find(":file").size()>0&&$(".ajaxupload").find(":file").eq(0).val().length > 0){
				if($.browser.msie){
					alert("暂不支持IE，请使用谷歌浏览器");
					return;
				}
				var oldFile =  $(".ajaxupload").find(":file").eq(0);
				//判断File域中有没有选择
				if(oldFile.val().length==0){
					return;
				}
				//克隆不会把File的Value复制过去
				var newFile = oldFile.clone(true);
				$this.ajaxUpload(oldFile);
				$(".ajaxupload").find(":file").remove();
				newFile.appendTo($this.uploadContent);
				newFile.attr("id",$this.guid());
			}
		}
		$this.fileF.bind("mouseout",mouseout);
		$this.loadUploadFiles();
	},
	ajaxUpload:function($file){
		var $this = this;
		//文件上传成功后的回调函数
		var uploadCallback = function(){
			var data = $this.formatData($("#"+$this.iframeId).contents().find("pre").html());
			$this.success(data);
			//上传之后执行的内容
			if($this.after)
			$this.after();
			//去掉loading
			$this.loadingButton.addClass("ajaxupload-button-loading-hidden");
		}
		//上传之前执行的内容
		if($this.before)
			if(!$this.before())
			return;
		//创建上传的form
		$this.createForm($file);
		//提交form
		$("#"+$this.formId).submit();
		//添加回调函数
		$("#"+$this.iframeId).bind("load",uploadCallback);
	},
	//载入已经上传的文件
	loadUploadFiles : function(){
		var $this = this;
		if($this.loadData==""||$this.loadData==null){
			return;
		}
		//拆分数据
		var filesArray = $this.loadData.split("&");
		for(var i=0;i<filesArray.length;i++){
			if($.trim(filesArray[i])!=""){
			var fileArray = $.trim(filesArray[i]).split(",");
			var file = {};
			file.fileName = $.trim(fileArray[1]);
			file.id = $.trim(fileArray[0]);
			$this.drawUploadFiles(file);
			}
		}
	},
	//上传成功后的处理函数
	success : function(data){
		var $this = this;
		if(data&&data.length>0){
			for(var i=0;i<data.length;i++){
				$this.drawUploadFiles(data[i]);
			}
		}
	},
	drawUploadFiles : function(file){
		var $this = this;
		//创建一个文本域显示已经上传的文件
		var $fileA = $('<li><a class="uploaded-files" href="'+projectName+'/down.v?id='+file.id+'">'+file.fileName+'</a><input type="hidden" name="upload" value='+file.id+'></li>');
		$fileA.appendTo($this.listFiles);
		//创建一个删除按钮
		var $delector = $('<IMG/>');
		$delector.addClass("ajaxupload-button-deletor");
		$delector.prependTo($fileA);
		$delector.attr("src",projectName+"/image/deletor.gif");
		//为该按钮绑定点击事件
		$delector.bind("click",function(){
			if(confirm("确认删除！"))
			$this.ajaxDelete($(this));
		});
	},
	ajaxDelete : function($file){
		var id = $file.nextAll("input").eq(0).val()
		var $this = this;
			$.ajax({
				url : $this.deleteUrl,
				type : 'post',
				data : 'attachedFileId='+id,
				success : function(data){
				if("T"==data){
					//移除显示区域
					$file.parent("li").remove();
				}
			}
			});
	},
	//创建提交form
	createForm : function($file){
		var $this = this;
		//添加loading
		$this.loadingButton.removeClass("ajaxupload-button-loading-hidden");
		//创建两个ID，一个为IFRAME，一个为FORM
		$this.iframeId = $this.guid();
		$this.formId =  $this.guid();
		//创建一个IFRAME
		$this.iframe = $('<iframe id='+$this.iframeId+' name='+$this.iframeId+'></iframe>'); 
		$this.iframe.attr("src","javascript:false");
		//将IFRAME加入到DOM中
		$this.iframe.appendTo('body');
		//将IFRAME移出可视区域
		$this.iframe.css({position:'absolute',left:'-2000px',top:'-2000px'});
		//创建FORM
		$this.form = $('<form id='+$this.formId+'></form>');
		$this.form.attr("enctype","multipart/form-data");
		$this.form.attr("method","post");
		$this.form.attr("target",$this.iframeId);
		$this.form.attr("action",$this.postUrl);
		$this.form.css({position:'absolute',left:'-2000px',top:'-2000px'});
		$this.form.appendTo('body');
		if($file==null||$file.size()==0){
			alert("未选择文件");
		}
		//将file域添加到form中
		$this.form.append($file);
		//创建一个SuperID
		$this.idInput = $('<input type="hidden" name="superId" value="'+$this.superId+'">');
		$this.form.append($this.idInput);
	},
	formatData : function (data){
		var $this = this;
		var type = $this.type == null ? 'json' : $this.type;
        if ( type == "xml") 
        if ( type == "script" )
            jQuery.globalEval( data );
        if ( type == "json" )
            eval( "data = " + data );
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();
        return data;
	},
   //产生一个Guid
	guid : function(){
		var guid = 'v-0000-0000-0000-0000-0000'
		return guid.replace(/0/g, function(){return Math.random().toString(16).substr(2,1)});
	}
}
