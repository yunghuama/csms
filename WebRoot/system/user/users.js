function choose(obj) {
  obj = $(obj);
  var patn = /\.jpg$|\.jpeg$|\.gif$|\.png$/i;
  var allowString ="jpg,jpeg,gif,png";
  var filePath = obj.val();
  if(filePath !== '') {
    if(!patn.test(filePath)) {
      alert("图片格式不正确，只能上传以下图片格式："+allowString);
      obj.parent().html(obj.parent().html());
    } else {
      var url = $('#userForm').attr('action');
      $('#userForm').attr({
        'action': projectName+'/tempUpload.v?callback=changeImg',
        'target': 'tempUpload'
      });
      $('#userForm').submit();
      $('#userForm').attr({
        'action': url
      }).removeAttr('target');
    }
  }
}
function cancel() {
  $('#preview').attr('src', defaultPath);
  $('#fileContent').html($('#fileContent').html());
  $('#imagePath').val('');
}
function changeImg(filePath) {
  $('#imagePath').val(filePath);
  $('#preview').attr('src', projectName+filePath);
}
function rebuildSize(_m) {
  var pSize = 168, iSize = 168;
  if(_m.height() < pSize && _m.width() < pSize) {
    _m.css('margin-top', (pSize-_m.height())/2);
  } else if(_m.height() >= _m.width()) {
    _m.height(iSize);
    _m.css('margin-top', 0);
    _m.width('');
  } else {
    _m.width(iSize);
    _m.height('').css('margin-top', (pSize-_m.height())/2);
  }
}
$(function(){
  $('#preview').load(function(){
    rebuildSize($(this));
  });
});