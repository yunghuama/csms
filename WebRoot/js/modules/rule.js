var ruleFunctions = {
  openSaveRuleWindow : function(ids){
    new WindowPanel({
      id : 'saveRule',
      title : '设置计划',
      width : 450,
      height : 400,
      closeConfirm : true,
      html : '<iframe name="ruleSaveFrame" id="ruleSaveFrame" src="'+projectName+'/csms/rule/toSave.v?'+getFlushParam('saveRule')+'&ids='+ids+'" frameborder="0" scrolling="auto"></iframe>'
    });
  },
  openUpdateRuleWindow : function(id){
	    new WindowPanel({
	      id : 'updateRule',
	      title : '修改普通策略',
	      width : 450,
	      height : 400,
	      closeConfirm : true,
	      html : '<iframe name="ruleUpdateFrame" id="ruleUpdateFrame" src="'+projectName+'/csms/rule/toUpdate.v?'+getFlushParam('updateRule')+'&rule.id='+id+'" frameborder="0" scrolling="auto"></iframe>'
	    });
	  }
  ,
  openSaveGloRuleWindow : function(){
	    new WindowPanel({
	      id : 'saveGloRule',
	      title : '添加全局策略',
	      width : 350,
	      height : 200,
	      closeConfirm : true,
	      html : '<iframe name="gloRuleSaveFrame" id="gloRuleSaveFrame" src="'+projectName+'/csms/gloRule/toSave.v?'+getFlushParam('saveGloRule')+'" frameborder="0" scrolling="auto"></iframe>'
	    });
   },
  openUpdateGloRuleWindow : function(id){
	    new WindowPanel({
	      id : 'updateGloRule',
	      title : '修改全局策略',
	      width : 350,
	      height : 200,
	      closeConfirm : true,
	      html : '<iframe name="gloRuleUpdateFrame" id="gloRuleUpdateFrame" src="'+projectName+'/csms/gloRule/toUpdate.v?'+getFlushParam('updateGloRule')+'&rule.id='+id+'" frameborder="0" scrolling="auto"></iframe>'
	    });
	  }
};
