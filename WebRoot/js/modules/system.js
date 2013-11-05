var dictionaryFunctions = {
  openSaveDictionaryWindow : function(superId){
    new WindowPanel({
      id: 'saveDictionary',
      title: '添加字典',
      width: 323,
      height: 175,
      closeConfirm : true,
      html : '<iframe name="dictionarySaveFrame" id="dictionarySaveFrame" src="'+projectName+'/system/dictionary/toSave.v?'+getFlushParam('saveDictionary')+'&superId='+superId+'" frameborder="0" scrolling="auto"></iframe>',
      tbar: new Toolbar({
        icon: 'image/op.gif',
        items : [{
          type : 'button',
          text : '保存',
          position: {
            a: '-80px 0px',
            b: '-80px -120px'
          },
          handler : function(){
            if(getFrame('dictionarySaveFrame').validate()) {
              submitFrameForm('dictionarySaveFrame', 'dictionaryForm');
            }
          }
        }]
      })
    });
  },
  openUpdateDictionaryWindow : function(id){
    new WindowPanel({
      id : 'updateDictionary',
      title : '修改字典',
      width: 323,
      height: 145,
      closeConfirm : true,
      html : '<iframe name="dictionaryUpdateFrame" id="dictionaryUpdateFrame" src="'+projectName+'/system/dictionary/toUpdate.v?'+getFlushParam('updateDictionary')+'&dictionary.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('dictionaryUpdateFrame').validate())
              submitFrameForm('dictionaryUpdateFrame', 'dictionaryForm');
          }
        }]
      })
    });
  },
  openUpdateDictionarySortWindow : function(superId){
    new WindowPanel({
      id : 'sortDictionary',
      title : '字典排序',
      width : 230,
      height : 287,
      closeConfirm : true,
      html : '<iframe name="dictionarySortFrame" id="dictionarySortFrame" src="'+projectName+'/system/dictionary/toUpdateSort.v?'+getFlushParam('sortDictionary')+'&superId='+superId+'" frameborder="0" scrolling="auto"></iframe>',
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
            getFrame('dictionarySortFrame')._selectall(getFrame('dictionarySortFrame').document.getElementById('idList'));
            submitFrameForm('dictionarySortFrame', 'dictionaryForm');
          }
        },'-',{
          type : 'button',
          tip : '置顶',
          position: {
            a: '-140px -80px',
            b: '-80px -200px'
          },
          handler : function(){
            getFrame('dictionarySortFrame')._top(getFrame('dictionarySortFrame').document.getElementById('idList'));
          }
        },{
          type : 'button',
          tip : '向上',
          position: {
            a: '-80px -80px',
            b: '-80px -200px'
          },
          handler : function(){
            getFrame('dictionarySortFrame')._up(getFrame('dictionarySortFrame').document.getElementById('idList'));
          }
        },'-',{
          type : 'button',
          tip : '向下',
          position: {
            a: '-100px -80px',
            b: '-100px -200px'
          },
          handler : function(){
            getFrame('dictionarySortFrame')._down(getFrame('dictionarySortFrame').document.getElementById('idList'));
          }
        },{
          type : 'button',
          tip : '置底',
          position: {
            a: '-120px -80px',
            b: '-120px -200px'
          },
          handler : function(){
            getFrame('dictionarySortFrame')._bottom(getFrame('dictionarySortFrame').document.getElementById('idList'));
          }
        }]
      })
    });
  }
};

var userFunctions = {
  openSaveUserWindow : function(deptId){
    new WindowPanel({
      id : 'saveUser',
      title : '添加用户',
      width : 300,
      height : 300,
      closeConfirm : true,
      html : '<iframe name="userSaveFrame" id="userSaveFrame" src="'+projectName+'/system/users/toSave.v?'+getFlushParam('saveUser')+'&deptId='+deptId+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('userSaveFrame').validate())
              submitFrameForm('userSaveFrame', 'userForm');
          }
        }]
      })
    });
  },
  openUpdateUserWindow : function(id){
    new WindowPanel({
      id : 'updateUser',
      title : '修改用户',
      width : 300,
      height : 300,
      closeConfirm : true,
      html : '<iframe name="userUpdateFrame" id="userUpdateFrame" src="'+projectName+'/system/users/toUpdate.v?'+getFlushParam('updateUser')+'&userId='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('userUpdateFrame').validate())
              submitFrameForm('userUpdateFrame', 'userForm');
          }
        }]
      })
    });
  },
  openSaveUsersRoleWindow : function(userId){
    new WindowPanel({
      id : 'saveUsersRole',
      title : '关联角色',
      width : 350,
      height : 430,
      html : '<iframe name="usersRoleSaveFrame" id="usersRoleSaveFrame" src="'+projectName+'/system/users/listUsersRole.v?'+getFlushParam('saveUsersRole')+'&userId='+userId+'" frameborder="0" scrolling="auto"></iframe>',
      tbar : new Toolbar({
        icon: 'image/op.gif',
        items : [{
          type : 'button',
          text : '关联',
          position: {
            a: '-60px -60px',
            b: '-60px -180px'
          },
          handler : function(){
            getFrame('usersRoleSaveFrame').linkRole('T');
          }
        },'-',{
          type : 'button',
          text : '取消',
          position: {
            a: '-80px -60px',
            b: '-80px -180px'
          },
          handler : function(){
            getFrame('usersRoleSaveFrame').linkRole('F');
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function(){
            submitFrameForm('usersRoleSaveFrame','listForm');
          }
        }]
      })
    });
  }
};

var departmentFunctions = {
  openSaveDepartmentWindow : function(deptId){
    new WindowPanel({
      id : 'saveDepartment',
      title : '添加部门',
      width : 350,
      height : 205,
      closeConfirm : true,
      html : '<iframe name="departmentSaveFrame" id="departmentSaveFrame" src="'+projectName+'/system/department/toSave.v?'+getFlushParam('saveDepartment')+'&deptId='+deptId+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('departmentSaveFrame').validate())
              submitFrameForm('departmentSaveFrame', 'departmentForm');
          }
        }]
      })
    });
  },
  openUpdateDepartmentWindow : function(deptId){
    new WindowPanel({
      id : 'updateDepartment',
      title : '修改部门',
      width : 350,
      height : 205,
      closeConfirm : true,
      html : '<iframe name="departmentUpdateFrame" id="departmentUpdateFrame" src="'+projectName+'/system/department/toUpdate.v?'+getFlushParam('updateDepartment')+'&deptId='+deptId+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('departmentUpdateFrame').validate())
              submitFrameForm('departmentUpdateFrame', 'departmentForm');
          }
        }]
      })
    });
  }
};

var intercomFunctions = {
  openSaveIntercomWindow : function(id){
    id = id || '';
    new WindowPanel({
      id : 'saveIntercom',
      title : '新建内部通信',
      width : 655,
      height : 426,
      closeConfirm : true,
      html : '<iframe name="intercomSaveFrame" id="intercomSaveFrame" src="'+projectName+'/system/intercom/toSave.v?'+getFlushParam('saveIntercom')+'&intercomId='+id+'" frameborder="0" scrolling="auto"></iframe>',
      tbar : new Toolbar({
        icon: 'image/op.gif',
        items : [{
          type : 'button',
          text : '发送',
          position: {
            a: '-180px -20px',
            b: '-180px -140px'
          },
          handler : function(){
            if(getFrame('intercomSaveFrame').validate()) {
              submitFrameForm('intercomSaveFrame','intercomForm');
            }
          }
        }]
      })
    });
  },
  openViewIntercomWindow : function(id, forwardable){
    new WindowPanel({
      id : 'viewIntercom',
      title : '查看内部通信',
      width : 617,
      height : 540,
      html : '<iframe name="intercomViewFrame" id="intercomViewFrame" src="'+projectName+'/system/intercom/view.v?'+getFlushParam('viewIntercom')+'&intercomId='+id+'" frameborder="0" scrolling="auto"></iframe>',
      tbar : new Toolbar({
        icon: 'image/op.gif',
        items : [{
          id: 'forward',
          type : 'button',
          text : '转发',
          useable: forwardable,
          position: {
            a: '-180px -20px',
            b: '-180px -140px'
          },
          handler : function(){
            intercomFunctions.openSaveIntercomWindow(id);
            WPS['jWindowPanel-viewIntercom'].close();
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function(){
            submitFrameForm('intercomViewFrame','intercomForm');
          }
        }]
      })
    });
    
    /**
        if(top.tabpanel.$tabs['viewIntercom']){//如果已经打开了浏览标签，则更新html，显示新内容
            top.tabpanel.setHtml('viewIntercom','<iframe name="intercomViewFrame" id="intercomViewFrame" src="'+projectName+'/system/intercom/listViewIntercom.v?intercom.id='+id+'" frameborder="0" scrolling="auto"></iframe>');
            top.tabpanel.show('viewIntercom');  
            
        }else{//如果没有找到标签，则打开新标签
            top.tabpanel.addTab({
              id: 'viewIntercom',
              title: '浏览内部通信',
              html: '<iframe name="intercomViewFrame" id="intercomViewFrame" src="'+projectName+'/system/intercom/listViewIntercom.v?intercom.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
              position: {
                a: '0px -110px',
                b: '-22px -110px'
              },
              closable: true
            });
        }
        */ 
  }
};

var roleFunctions = {
  openSaveRoleWindow : function(){
    new WindowPanel({
      id : 'saveRole',
      title : '新建角色',
      width : 350,
      height : 175,
      closeConfirm : true,
      html : '<iframe name="roleSaveFrame" id="roleSaveFrame" src="'+projectName+'/system/role/toSave.v?'+getFlushParam('saveRole')+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('roleSaveFrame').validate())
              submitFrameForm('roleSaveFrame','roleForm');
          }
        },{
          type : 'button',
          text : '下一步',
          position: {
            a: '-160px -20px',
            b: '-160px -140px'
          },
          handler : function(){
            if(getFrame('roleSaveFrame').validate())
              getFrame('roleSaveFrame').ajaxSaveRole();
          }
        }]
      })
    });
  },
  openUpdateRoleWindow : function(id){
    new WindowPanel({
      id : 'updateRole',
      title : '修改角色',
      width : 350,
      height : 175,
      closeConfirm : true,
      html : '<iframe name="roleUpdateFrame" id="roleUpdateFrame" src="'+projectName+'/system/role/toUpdate.v?'+getFlushParam('updateRole')+'&role.id='+id+'" frameborder="0" scrolling="auto"></iframe>',
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
            if(getFrame('roleUpdateFrame').validate())
              submitFrameForm('roleUpdateFrame','roleForm');
          }
        }]
      })
    });
  },
  openSaveRoleUsersWindow : function(roleId){
    new WindowPanel({
      id : 'saveRoleUsers',
      title : '关联用户',
      width : 379,
      height : 330,
      html : '<iframe name="roleUsersSaveFrame" id="roleUsersSaveFrame" src="'+projectName+'/system/role/listRoleUsers.v?'+getFlushParam('saveRoleUsers')+'&roleId='+roleId+'" frameborder="0" scrolling="auto"></iframe>',
      tbar : new Toolbar({
        icon: 'image/op.gif',
        items : [{
          type : 'button',
          text : '关联',
          position: {
            a: '-60px -60px',
            b: '-60px -180px'
          },
          handler : function(){
            getFrame('roleUsersSaveFrame').linkUsers('T');
          }
        },'-',{
          type : 'button',
          text : '取消',
          position: {
            a: '-80px -60px',
            b: '-80px -180px'
          },
          handler : function(){
            getFrame('roleUsersSaveFrame').linkUsers('F');
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function(){
            submitFrameForm('roleUsersSaveFrame','listForm');
          }
        }]
      })
    });
  }
};
