var groupFunctions = {
  openSaveGroupWindow : function(){
    new WindowPanel({
      id : 'saveGroup',
      title : '添加部门',
      width : 350,
      height : 80,
      closeConfirm : true,
      html : '<iframe name="groupSaveFrame" id="groupSaveFrame" src="'+projectName+'/csms/group/toSave.v?'+getFlushParam('saveGroup')+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('groupSaveFrame').validate())
              submitFrameForm('groupSaveFrame', 'groupForm');
          }
        }]
      })
    });
  },
  openUpdateGroupWindow : function(id){
	    new WindowPanel({
	      id : 'updateGroup',
	      title : '修改部门',
	      width : 350,
	      height : 80,
	      closeConfirm : true,
	      html : '<iframe name="groupUpdateFrame" id="groupUpdateFrame" src="'+projectName+'/csms/group/toUpdate.v?'+getFlushParam('updateGroup')+'&group.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
	      tbar : new Toolbar({
	        icon: 'image/op.gif',
	        items : [{
	          type : 'button',
	          text : '修改',
	          position: {
	            a: '-80px 0px',
	            b: '-80px -120px'
	          },
	          handler : function(){
	            if(getFrame('groupUpdateFrame').validate())
	              submitFrameForm('groupUpdateFrame', 'groupForm');
	          }
	        }]
	      })
	    });
	  }
};
