var enterpriseFunctions = {
  openSaveEnterpriseWindow : function(parentId){
    new WindowPanel({
      id : 'saveEnterprise',
      title : '新增企业',
      width : 350,
      height : 205,
      closeConfirm : true,
      html : '<iframe name="enterpriseSaveFrame" id="enterpriseSaveFrame" src="'+projectName+'/csms/enterprise/toSave.v?'+getFlushParam('saveEnterprise')+'&parentId='+parentId+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('enterpriseSaveFrame').validate())
              submitFrameForm('enterpriseSaveFrame', 'enterpriseForm');
          }
        }]
      })
    });
  },
  openUpdateEnterpriseWindow : function(id){
	    new WindowPanel({
	      id : 'updateEnterprise',
	      title : '修改企业',
	      width : 350,
	      height : 205,
	      closeConfirm : true,
	      html : '<iframe name="enterprisetUpdateFrame" id="enterprisetUpdateFrame" src="'+projectName+'/csms/enterprise/toUpdate.v?'+getFlushParam('updateEnterprise')+'&enterprise.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
	            if(getFrame('enterprisetUpdateFrame').validate())
	              submitFrameForm('enterprisetUpdateFrame', 'enterpriseForm');
	          }
	        }]
	      })
	    });
	  },
	  openUpdateUsersWindow : function(id){
		    new WindowPanel({
		      id : 'updateUsers',
		      title : '添加/修改企业管理员',
		      width : 350,
		      height : 205,
		      closeConfirm : true,
		      html : '<iframe name="usersUpdateFrame" id="usersUpdateFrame" src="'+projectName+'/csms/enterprise/toUpdateUsers.v?'+getFlushParam('updateUsers')+'&enterprise.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
		            if(getFrame('usersUpdateFrame').validate())
		              submitFrameForm('usersUpdateFrame', 'userForm');
		          }
		        }]
		      })
		    });
		  }
  ,
  openImportWindow : function(id){
	    new WindowPanel({
		      id : 'import',
		      title : '导入号码',
		      width : 350,
		      height : 205,
		      closeConfirm : true,
		      html : '<iframe name="importFrame" id="importFrame" src="'+projectName+'/csms/enterprise/toImport.v?'+getFlushParam('import')+'&enterprise.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
		            if(getFrame('importFrame').validate())
		              submitFrameForm('importFrame', 'importForm');
		          }
		        }]
		      })
		    });
		  }
};
