var districtFunctions = {
  openSaveDistrictWindow : function(parentId){
    new WindowPanel({
      id : 'saveDistrict',
      title : '添加行政区',
      width : 350,
      height : 205,
      closeConfirm : true,
      html : '<iframe name="districtSaveFrame" id="districtSaveFrame" src="'+projectName+'/csms/district/toSave.v?'+getFlushParam('saveDistrict')+'&parentId='+parentId+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('districtSaveFrame').validate())
              submitFrameForm('districtSaveFrame', 'districtForm');
          }
        }]
      })
    });
  },
  openUpdateDistrictWindow : function(id){
	    new WindowPanel({
	      id : 'updateDistrict',
	      title : '修改行政区',
	      width : 350,
	      height : 205,
	      closeConfirm : true,
	      html : '<iframe name="districtUpdateFrame" id="districtUpdateFrame" src="'+projectName+'/csms/district/toUpdate.v?'+getFlushParam('updateDistrict')+'&districtId='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
	            if(getFrame('districtUpdateFrame').validate())
	              submitFrameForm('districtUpdateFrame', 'districtForm');
	          }
	        }]
	      })
	    });
	  }
};
