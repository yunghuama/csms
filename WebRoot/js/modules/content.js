var contentFunctions = {
  openSaveContentWindow : function(){
    new WindowPanel({
      id : 'saveContent',
      title : '添加内容',
      width : 350,
      height : 205,
      closeConfirm : true,
      html : '<iframe name="contentSaveFrame" id="contentSaveFrame" src="'+projectName+'/csms/content/toSave.v?'+getFlushParam('saveContent')+'" frameborder="0" scrolling="auto"></iframe>',
      tbar : new Toolbar({
        icon: 'image/op.gif',
        items : [{
          type : 'button',
          text : '保存',
          position: {
            a: '-80px 0px',
            b: '-80px -120px'
          },
          handler : function(){
            if(getFrame('contentSaveFrame').validate())
              submitFrameForm('contentSaveFrame', 'contentForm');
          }
        }]
      })
    });
  },
  openUpdateContentWindow : function(id){
	    new WindowPanel({
	      id : 'updateContent',
	      title : '审核内容',
	      width : 350,
	      height : 205,
	      closeConfirm : true,
	      html : '<iframe name="contentUpdateFrame" id="contentUpdateFrame" src="'+projectName+'/csms/content/toUpdateA.v?'+getFlushParam('updateContent')+'&content.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
	      tbar : new Toolbar({
	        icon: 'image/op.gif',
	        items : [{
	          type : 'button',
	          text : '保存',
	          position: {
	            a: '-80px 0px',
	            b: '-80px -120px'
	          },
	          handler : function(){
	            if(getFrame('contentUpdateFrame').validate())
	              submitFrameForm('contentUpdateFrame', 'contentForm');
	          }
	        }]
	      })
	    });
	  }
};
