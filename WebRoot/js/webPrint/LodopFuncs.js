//该方法主要是解决了不同浏览器对控件的加载问题
function getLodop(oOBJECT, oEMBED) {
	 var strHtml1="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop.exe'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
     var strHtml2="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop.exe'>执行升级</a>,升级后请重新进入。</font>";
     var strHtml3="<br><br><font color='#FF00FF'>(注：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸载它)</font>";
	var LODOP = oEMBED;
	try {		     
		//判断浏览器是否为IE，如果是IE则通过Object方式获得，如果不是IE则通过embed方式获得
		if (navigator.appVersion.indexOf("MSIE") >= 0) {
			LODOP = oOBJECT;
		}
		//如果控件加载失败，则检查控件自身问题，
		if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
	    
			if (navigator.userAgent.indexOf("Firefox") >= 0) {
				document.documentElement.innerHTML = strHtml3 + document.documentElement.innerHTML;
			}
			if (navigator.appVersion.indexOf("MSIE") >= 0) {
				document.write(strHtml1);
			} else {
				document.documentElement.innerHTML = strHtml1 + document.documentElement.innerHTML;
			}
		} else {
			if (LODOP.VERSION < "6.0.1.0") {
				if (navigator.appVersion.indexOf("MSIE") >= 0) {
					document.write(strHtml2);
				} else {
					document.documentElement.innerHTML = strHtml2 + document.documentElement.innerHTML;
				}
			}
		}
	     
		return LODOP;
	}
	catch (err) {
		document.documentElement.innerHTML = "Error:" + strHtml1 + document.documentElement.innerHTML;
		return LODOP;
	}
}

