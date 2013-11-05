/*-----------------------Field验证js DEMO-------------------------
|说明：通过添加addValidate(field,[{}]);来完成验证工作
	  参数一、field 的ID标识或者Jquery对象
	  参数二、验证类型数组
|相关DEMO
|
|1、验证整数
| 使用方法：addValidate('test1', [{type:'isInteger', valueType:'+',canNull:'T', message:'必须为正整数或者为空'}]);
| 参数说明：type 使用何种类型的验证器 ,'isInteger'
		  valueType 值的类型(缺省为正负均可),'+'：正整数、'-'：负整数
		  canNull 是否可以为空(缺省为不能为空),'T':可以为空、'F'：不能为空
		  message 验证失败时的提示信息
 2、验证浮点数		
| 使用方法：addValidate($("#test2"), [{type:'isDouble', valueType:'+',canNull:'T', message:'必须为正2位小数或者为空'}]);
| 参数说明：type 使用何种类型的验证器 ,'isDouble'
		  valueType 值的类型(缺省为正负均可),'+'：正整数、'-'：负整数
		  canNull 是否可以为空(缺省为不能为空),'T':可以为空、'F'：不能为空
		  value 小数的位数(缺省为小数位数不确定),1：一位小数 2: 两位小数，依次类推。
		  message 验证失败时的提示信息  
		 
 3、验证汉字
| 使用方法：addValidate($(".test3"), [{type:'isChinese', value:'T', message:'必须为汉字'}]);
| 参数说明：type 使用何种类型的验证器 ,'isChinese'
		  value 是否为汉字,'T':是、'F'：否
		  message 验证失败时的提示信息  
 
 4、验证邮箱
| 使用方法：addValidate($(":input"), [{type:'isMail', value:'T', message:'必须为邮箱格式'}]);
| 参数说明：type 使用何种类型的验证器 ,'isMail'
		  value 是否为邮箱,'T':是、'F'：否
		  message 验证失败时的提示信息   
		  
 5、验证是否为空
| 使用方法：addValidate('test5', [{type:'canNull', value:'F', message:'不能为空'}]);
| 参数说明：type 使用何种类型的验证器 ,'canNull'
		  value 是否可以为空,'T':是、'F'：否
		  message 验证失败时的提示信息   
		  
 6、验证字符串的最大长度
| 使用方法：addValidate('test6', [{type:'maxlength', value:'5', message:'最大长度为5'}]);
| 参数说明：type 使用何种类型的验证器 ,'maxlength'
		  value 长度值,1：最大长度为1,2：最大长度为2，依次类推
		  message 验证失败时的提示信息   
		  
 7、验证字符串的最小长度
| 使用方法：addValidate('test7', [{type:'minlength', value:'5', message:'最小长度为5'}]);
| 参数说明：type 使用何种类型的验证器 ,'minlength'
		  value 长度值,1：最小长度为1,2：最小长度为2，依次类推
		  message 验证失败时的提示信息   	  
		
 8、验证数字的最大值
| 使用方法：addValidate('test8', [{type:'maxvalue', value:'100', message:'最大值为100'}]);	  
| 参数说明：type 使用何种类型的验证器 ,'maxvalue'
		  value 最大值,100：最大值为100，依次类推
		  message 验证失败时的提示信息     
	
 9、验证数字的最小值
| 使用方法：addValidate('test9', [{type:'minvalue', value:'100', message:'最大值为100'}]);	  
| 参数说明：type 使用何种类型的验证器 ,'minvalue'
		  value 最小值,100：最小值为100，依次类推
		  message 验证失败时的提示信息     
		  
  10、验证字符的前缀
| 使用方法：addValidate('test10', [{type:'startwith', value:'cs', message:'开头为cs'}]);
| 参数说明：type 使用何种类型的验证器 ,'startwith'
		  value 字符前缀。
		  message 验证失败时的提示信息     
 
   11、验证字符的后缀
| 使用方法：addValidate('test11', [{type:'endwith', value:'wow', message:'结尾为wow'}]);
| 参数说明：type 使用何种类型的验证器 ,'endwith'
		  value 字符后缀。
		  message 验证失败时的提示信息   
		  
   12、验证是否包含某字符
| 使用方法：addValidate('test12', [{type:'include', value:'dota', message:'必须包含dota'}]);
| 参数说明：type 使用何种类型的验证器 ,'include'
		  value 需要包含字符。
		  message 验证失败时的提示信息   
*/

var validateArray = new Array();
var errorArray = new Array();
var elementArray = new Array();
var form_success = true;

//验证不通过的样式
var Style = {
		input_invalid : {
		border:'#CC3300 1px solid',
		backgroundImage:'url(../../image/invalid_line.gif)',
		backgroundPosition:'bottom center',
		backgroundRepeat:'repeat-x'
		},
		input_old : {
			border : '1px solid #A6B2CA'
		}
	}


/*添加日期类型验证*/
function addDateValidate(webId, rules) {
	var dateInput = $("#" + webId);
	dateInput.addClass("Wdate");
	dateInput.attr("readonly", true);
	for (var i = 0; i < rules.length; i++) {
		dateInput.attr("title", dateInput.attr("title") + "[" + rules[i].message + "]");
	}
	dateInput.bind("click", function () {
		if (rules.length == 1) {
			eval("WdatePicker({" + rules[0].type + ":" + rules[0].value + "})");
		} else {
			if (rules.length == 2) {
				eval("WdatePicker({" + rules[0].type + ":'" + rules[0].value + "'," + rules[1].type + ":'" + rules[1].value + "'})");
			} else {
				eval("WdatePicker()");
			}
		}
	});
}
/*添加方法验证*/
function addFunctionValidate(fuc, message) {
	validateArray.push(function () {
		if (!fuc()) {
			errorArray.push(message);
			form_success = false;
		}
	});
}
/*删除验证*/
function removeValidate(index){
	validateArray[index] = function(){};
}

/*添加验证*/
function addValidate(webId, rules) {
	for (var i = 0; i < rules.length; i++) {
		if (rules[i].type == "canNull") {
			validateArray.push(function (index) {
				return function () {
					validateNullable(webId, rules[index].value, rules[index].message);
				};
			}(i));
			return validateArray.length-1;
		} else {
			if (rules[i].type == "maxlength") {
				validateArray.push(function (index) {
					return function () {
						validateMaxlength(webId, rules[index].value, rules[index].message);
					};
				}(i));
				return validateArray.length-1;
			} else {
				if (rules[i].type == "minlength") {
					validateArray.push(function (index) {
						return function () {
							validateMinlength(webId, rules[index].value, rules[index].message);
						};
					}(i));
					return validateArray.length-1;
				} else {
					if (rules[i].type == "maxvalue") {
						validateArray.push(function (index) {
							return function () {
								validateMaxvalue(webId, rules[index].value, rules[index].message);
							};
						}(i));
						return validateArray.length-1;
					} else {
						if (rules[i].type == "minvalue") {
							validateArray.push(function (index) {
								return function () {
									validateMinvalue(webId, rules[index].value, rules[index].message);
								};
							}(i));
							return validateArray.length-1;
						} else {
							if (rules[i].type == "startwith") {
								validateArray.push(function (index) {
									return function () {
										validateStartwith(webId, rules[index].value, rules[index].message);
									};
								}(i));
								return validateArray.length-1;
							} else {
								if (rules[i].type == "endwith") {
									validateArray.push(function (index) {
										return function () {
											validateEndwith(webId, rules[index].value, rules[index].message);
										};
									}(i));
									return validateArray.length-1;
								} else {
									if (rules[i].type == "include") {
										validateArray.push(function (index) {
											return function () {
												validateInclude(webId, rules[index].value, rules[index].message);
											};
										}(i));
										return validateArray.length-1;
									} else {
										if (rules[i].type == "isInteger") {
											validateArray.push(function (index) {
												return function () {
													validateInteger(webId,rules[index].message,rules[index].canNull, rules[index].valueType);
												};
											}(i));
											return validateArray.length-1;
										} else {
											if (rules[i].type == "isDouble") {
												validateArray.push(function (index) {
													return function () {
														validateDouble(webId, rules[index].value, rules[index].message, rules[index].canNull, rules[index].valueType);
													};
												}(i));
												return validateArray.length-1;
											} else {
												if (rules[i].type == "isMail") {
													validateArray.push(function (index) {
														return function () {
															validateMail(webId, rules[index].value, rules[index].message);
														};
													}(i));
													return validateArray.length-1;
												} else {
													if (rules[i].type == "isChinese") {
														validateArray.push(function (index) {
															return function () {
																validateChinese(webId, rules[index].value, rules[index].message);
															};
														}(i));
														return validateArray.length-1;
													} else {
														if (rules[i].type == "isMobile") {
	                                                        validateArray.push(function (index) {
	                                                            return function () {
	                                                                validateMobile(webId, rules[index].value, rules[index].message);
	                                                            };
	                                                        }(i));
	                                                        return validateArray.length-1;
                                                        }
													}
												} 
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
/*表单验证*/
function validate(config) {
	var j=0;
	form_success = true;
	errorArray = new Array();
	elementArray = new Array();
	$("#form_error_content").remove();
	for (var i = 0; i < validateArray.length; i++) {
		validateArray[i]();
	}
	if (form_success) {
		return true;
	} else if(config!=null&&config.theme=='beautiful'){
		for (var i = 0; i < errorArray.length; i++) {
			(function (i){
				if (errorArray[i] != "") {
					var elementObj =  elementArray[i];
					var errorText = errorArray[i];
					var oldTitle = elementObj.attr('title');
					//将该元素设置为
					elementObj.removeClass("input_invalid");
					elementObj.addClass("input_invalid");
					elementObj.attr('title',errorText);
					if($.browser.msie){
						//解决IE坑爹的propertychange问题
						elementObj.bind("keyup",function(){
							if($(this).is(".input_invalid")){
								$(this).removeClass("input_invalid");
								$(this).attr('title',oldTitle);
							}
						});
				        }else {
				        elementObj.bind("input",function(){
				        	if($(this).is(".input_invalid")){
				        		$(this).removeClass("input_invalid");
				        		$(this).attr('title',oldTitle);
							}
				        });
				    }
				}
			})(i)
		}
		return false;
	}else{
		var errorContent = $("<DIV id=\"form_error_content\" class=\"validateError\"></DIV>").appendTo($(document.body));
		var errorCloser = $("<DIV title=\"\u5173\u95ed\" class=\"closer\"></DIV>").click(function () {
			errorContent.remove();
		}).appendTo(errorContent);
		var errorUl = $("<UL></UL>");
		errorUl.appendTo(errorContent);
    //显示错误信息
		for (var i = 0; i < errorArray.length; i++) {
			if (errorArray[i] != "") {
				var errorLi = $("<LI>");
				errorLi.html(errorArray[i]);
				errorUl.append(errorLi);
			}
		}
		return false;
	}
}
/*验证空*/
function validateNullable(objId, nullable, error) {
//根据传入的ID或者Jquery对象进行验证
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			if (nullable == "F" && $.trim($(this).val())=== "") {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证最长*/
function validateMaxlength(objId, maxlength, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			if ($(this).val().length > maxlength) {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证最短*/
function validateMinlength(objId, minlength, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			if ($(this).val().length < minlength) {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证最大*/
function validateMaxvalue(objId, maxvalue, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var objvalue = parseFloat($(this).val());
			maxvalue = parseFloat(maxvalue);
			if (objvalue > maxvalue) {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证最小*/
function validateMinvalue(objId, minvalue, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var objvalue = parseFloat($(this).val());
			minvalue = parseFloat(minvalue);
			if (objvalue < minvalue) {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证以?开始*/
function validateStartwith(objId, str, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			if ($(this).val().indexOf(str) != 0) {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证以?结束*/
function validateEndwith(objId, str, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var obj = $("#" + objId);
			if ($(this).val().indexOf(str) != ($(this).val().length - str.length)) {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证是否为整形*/
function validateInteger(objId, error,canNull,type) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var reg = /(^-?[1-9]+)\d*$|^0$/;
			var val = $(this).val();
			if (!reg.test(val)) {
				if (!(canNull=="T" && val == "")) {
					errorArray.push(error);
					elementArray.push($(this));
					form_success = false;
					return;
				}
			}else {
				if (type == "+" && val < 0) {
					errorArray.push(error);
					elementArray.push($(this));
					form_success = false;
					return;
				}
				if (type == "-" && val > 0) {
					errorArray.push(error);
					elementArray.push($(this));
					form_success = false;
					return;
				}
			}
		});
	}
}
/*验证邮箱*/
function validateMail(objId, str, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var reg = /^[\w_]+[\w\d_.]*@[\d\w]+[\d\w_.]*[\d\w]$/;
			if (!reg.test($(this).val()) && str == "T") {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证手机号*/
function validateMobile(objId, str, error) {
    var obj = typeof objId === "string" ? $("#" + objId) : objId;
    if (obj.jquery) {
    //迭代Jquery对象
        obj.each(function () {
            var reg = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
            if($(this).val() != '' && $(this).val() != undefined){
            	if (!reg.test($(this).val()) && str == "T") {
	                errorArray.push(error);
	                elementArray.push($(this));
	                form_success = false;
	            }
            }
        });
    }
}
/*验证汉字*/
function validateChinese(objId, str, error) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var reg = /^[\u0391-\uFFE5]+$/;
			if (!reg.test($(this).val()) && str == "T") {
				errorArray.push(error);
				elementArray.push($(this));
				form_success = false;
			}
		});
	}
}
/*验证浮点型*/
function validateDouble(objId, str, error, canNull, type) {
	var obj = typeof objId === "string" ? $("#" + objId) : objId;
	if (obj.jquery) {
  	//迭代Jquery对象
		obj.each(function () {
			var reg = /((^-?[1-9])\d*(\.\d)?\d*$)|(^0|^-0)\.\d+$/;
			var val = $(this).val();
			if (!reg.test(val)) {
				if (!(canNull=="T" && val == "")) {
					errorArray.push(error);
					elementArray.push($(this));
					form_success = false;
					return;
				}
			} else {
				if (str) {
					if (!(val.indexOf(".") > 0) || !(val.length - 1 - val.indexOf(".") == str)) {
						errorArray.push(error);
						elementArray.push($(this));
						form_success = false;
						return;
					}
				}else {
					//if(val.indexOf(".")<0){
					//	errorArray.push(error);
				//	form_success = false;
					//	return;
					//}
				}
				if (type == "+" && val < 0) {
					errorArray.push(error);
					elementArray.push($(this));
					form_success = false;
					return;
				}
				if (type == "-" && val > 0) {
					errorArray.push(error);
					elementArray.push($(this));
					form_success = false;
					return;
				}
			}
		});
	}
}
/*必须包含*/
function validateInclude(objId, str, error) {
	var obj = $("#" + objId);
	if (obj.val().indexOf(str) < 0) {
		errorArray.push(error);
		elementArray.push($(this));
		form_success = false;
	}
}


//===========================================================================================//
/**新增验证方法*/
//===========================================================================================//

/**
 * 验证数值
 * objId 对象id
 * min 最小值
 * max 最大值
 */
function validateNumByAlert(objId, min, max){ 
	var regu =  /^\d+$/;
	var re = new RegExp(regu);
	var obj = $('#'+objId);
    var thisVal = obj.val();
    var flag = true;
    if (thisVal.length == 0) {
        obj.val('1');
        thisVal = "1";
    }
    var intVal = parseFloat(thisVal);
    if(isNaN(intVal)){
        flag = false;
    }else {
        if (!(re.test(thisVal)) || intVal<parseFloat(min)||intVal>parseFloat(max)){
            flag = false;
        }
    }
    if(!flag){
    	alert("请输入"+min+"~"+max+"之间的整数！");
    	obj.val('');
    	obj.focus();
    }
} 
/*验证长度*/
function validateLengthByAlert(objId, maxlength) {
  var obj = $('#'+objId);
  if(obj.val() != '' && obj.val() != undefined) {
    if(obj.val().length > maxlength){
         alert("超出最大长度"+maxlength+"!");
         obj.focus();
    }
  }
}