<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>集团名片</title>
    <style type="text/css">
    html,body {
      padding:0;
      margin:0;
      width:100%;
      height:100%;
      background-color:#dfdfdf;
      overflow:hidden;
    }
    body { }
    form {
      height:300px;
      width:300px;
      position:absolute;
      z-index:2;
      left:100px;
    }

    .textInput {
      color:#4E4E4E;
      font-size:22px;
      font-family:verdana,simsun,sans-serif;
      font-weight:700;
      width:150px;
      height:30px;
      line-height:30px;
      background:transparent;
      border:none;
    }

    .sbtDiv {
      left:225px;
      top:260px;
      cursor:pointer;
      width:80px;
      height:40px;
      border:none;
      background-image: url(image/login/login_button.png);

    }

    .remember {
      background:transparent url(image/login/remember.gif) no-repeat;
    }
    #topdiv {
      height:500px;
      width:100%;
      background:transparent url(image/index/main_bg.png);
      position:absolute;
      left:0;
      top:0;
      z-index:1;
    }
    #centerdiv {
       width:1024px;
       height:400px;
       margin:0px auto;
    }
    #loginlogo {

    }
    #centerdiv-left {
       float:left;
        position: relative;
       width:636px;
    }

    #centerdiv-right {
        float:left;
        position: relative;
        width:388px;

    }
    div.accountdiv {
       width:220px;
       height:60px;
       position: relative;
    }

    div.accountdiv img {
        position: absolute;
        border: none;
        width:210px;
        height:40px;
    }

    div.accountdiv input {
        width:200px;
        height:30px;
        top:3px;
        left:5px;
        border: none;
        position: absolute;
        z-index: 2;
        font-size:13px;
        color:black;
    }

    div.accountdiv span {
        top:12px;
        position: absolute;
        font-size:13px;
        color:#7d7d7d;
        left:5px;
        font-family: '宋体';
    }

    div.passworddiv {
       width:220px;
       height:60px;
       position: relative;
    }

    div.passworddiv img {
        position: absolute;
        border: none;
        width:210px;
        height:40px;
    }

    div.passworddiv input {
        width:200px;
        height:30px;
        top:3px;
        left:5px;
        border: none;
        position: absolute;
        z-index: 2;
        font-size:13px;
        color:black;
    }

    div.passworddiv span {
        top:12px;
        left:5px;
        position: absolute;
        font-size:13px;
        color:#7d7d7d;
        font-family: '宋体';
    }

    #submitdiv {
        width:200px;
        height:20px;
        position: relative;
        margin-top: 20px;
    }

    #submitdiv img {
        width:210px;
        height:50px;
        position: absolute;

    }

    #submitdiv input {
        width:210px;
        height:50px;
        position: absolute;
        opacity: 0;
    }

    div.clear {
        clear: both;
    }

    </style>
  </head>

  <body>
    <div id="topdiv">
    <div id="centerdiv">
          <div id="centerdiv-left">
              <img src="image/index/login_logo.png" alt="" id="loginlogo"/>
          </div>
          <div id="centerdiv-right">
              <form id="loginForm" method="post" action="<%=path%>/login.v">
                  <input type="hidden" id="remember" name="remember" value="${cookie.remember.value}"/>
                  <div class="accountdiv">
                  <img src="<%=path%>/image/login/input_bg.png" alt="" >
                  <input type="text" id="accountName" name="accountName" class="acnDiv textInput" value="${cookie.accountName.value}"/>
                  <span>请输入用户名</span>
                  </div>
                  <div class="passworddiv">
                  <img src="<%=path%>/image/login/input_bg.png" alt="">
                  <input type="password" id="password" name="password" class="pwdDiv textInput" value="${cookie.password.value}"/>
                  <span>请输入密码</span>
                  </div>
                  <div id="submitdiv">
                  <img src="image/login/login_button.png" alt="">
                  </div>
              </form>
          </div>
        <div class="clear"></div>
    </div>
    </div>

    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    var remember = $('#remember');
    $(document).ready(function(){
      if(remember.val()=='T') {
        $('#rememberTrigger').addClass('remember');
        $('#loginForm').submit();
      }
      $('#centerdiv').css('margin-top', ($(document.body).height()/2)-173);

      $('#submitdiv').hover(function(){
          $("#submitdiv img").attr("src","image/login/login_button_press.png");
      },function(){
          $("#submitdiv img").attr("src","image/login/login_button.png");
      });
      $('#rememberTrigger').click(function(){
        if(remember.val()=='T') {
          remember.val('F');
          $(this).removeClass('remember');
        } else {
          remember.val('T');
          $(this).addClass('remember');
        }
      });
      $("#accountName").click(function(){
          $(this).next("span").hide();

      });
        $("#password").click(function(){
            $(this).next("span").hide();

        });

      $("#submitdiv").click(function(){
             $("#loginForm").submit();
      });
      $(window).resize(function(){
        $('#centerdiv').css('margin-top', ($(this).height()/2)-173);
      });
      if('${param.PW}'=='1') {
        alert('用户名或密码错误，请重新登录');
        //注销一下，防止拿缓存数据去验证而不停的alert
        window.location = '<%=path%>/logout.v';
      }
      if('${param.PW}'=='2') {
        alert('该用户已被禁用，无法登录');
      }
    });
    </script>
  </body>
</html>