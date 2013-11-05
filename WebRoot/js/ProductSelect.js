var rowIndex = document.getElementById("productTb").rows.length-1;//表格行标识
 
   /**
    *选择产品并将选择的产品信息插入产品表格
    */
    function selProduct(){
        var dlgStatus = "dialogWidth:650px;dialogHeight:500px;status:0;help:0;resizable:0";
        var reValue = showModalDialog(projectName+"/sales/sale/opportunity/listProduct.jsp", "选择产品", dlgStatus);
        
        //得到table最后一行的id中的序号，将序号+1赋值给下一行
        var ind = $("#productTb tr:last-child").attr("id");
        var rowInd = parseInt(ind.substring(12));
        rowInd += 1;
        
        var tb = document.getElementById("productTb");
        var rowsNum = tb.rows.length;
        if(reValue != undefined && reValue != ''){
        	var productStrArr = reValue.split(",");
	        for(var i = 0; i < productStrArr.length; i++){
	            var productArr = productStrArr[i].split("~");
	            newRow = tb.insertRow(rowsNum);
	            newRow.id="productTb_tr"+rowInd;
	            for(var j = 0; j < 7; j++){
	                cell = newRow.insertCell(j);
	                cell.id = "productTb_td"+rowInd+""+(j+1);
	                if(j == 0){
	                    cell.innerHTML = '<input type=hidden name="productList['+rowIndex+'].product.id" id="proId'+rowInd+'" value="'+productArr[0]+'"/><img src="'+projectName+'/image/WindowPanel/window-close.gif" id="delImg" style="cursor:pointer" onclick="del('+rowInd+');"/>';
	                }else if(j == 1){
	                    cell.innerHTML = '<input type="text" name="productList['+rowIndex+'].name" id="proName'+rowInd+'" value="'+productArr[1]+'" size="18" readonly/>';
	                }else if(j == 2){
	                    cell.innerHTML = '<input type="text" name="productList['+rowIndex+'].type" id="proType'+rowInd+'" value="'+productArr[2]+'" size="18" readonly/>';
	                }else if(j == 3){
	                    cell.innerHTML = '<input type="text" name="productList['+rowIndex+'].num" id="proNum'+rowInd+'" value="1" onpropertychange="sumPrice();" onblur="validateNumByAlert(this.id, 0, 99999999);" size="6"/>';
	                }else if(j == 4){
	                    cell.innerHTML = '<input type="text" name="productList['+rowIndex+'].unit" id="proUnit'+rowInd+'" value="'+productArr[3]+'" size="6" readonly/>';
	                }else if(j == 5){
	                    cell.innerHTML = '<input type="text" name="productList['+rowIndex+'].price" id="proPrice'+rowInd+'" value="'+productArr[4]+'" size="6" readonly/>';
	                }else if(j == 6){
	                    cell.innerHTML = '<input type="text" name="productList['+rowIndex+'].remark" id="proRemark'+rowInd+'" value="" onblur="validateLengthByAlert(this.id, 1000);" size="18"/>';
	                }
	            }
	            rowsNum++;
	            rowInd++;
	            rowIndex++;
	        }
        }
        sumPrice();
    }
   /**
    *删除表格指定行
    *rowsNum 要删除的行号
    */
    function del(rowInd){
         $("#productTb_tr"+rowInd).remove();
         sumPrice();
    }
    /**
     *计算合计
     */
     function sumPrice(){
        var sumPrice = 0;
        $("input[id^='proNum']").each(function(i){
            var num = $(this).val();
            var price = $("input[id^='proPrice']").eq(i).val();
            if(num!="" && !isNaN(num) && price!=="" && !isNaN(price)){
                sumPrice += Number(num)*Number(price);
            }

        });
        
        if(totalMoney_id!=undefined&&totalMoney_id!="")
        $("#"+totalMoney_id).val(sumPrice.toFixed(2));
     }

function loadProducts(json){
 clearTB();
        var prTable= $("#productTb");
        var productArray = json;
        for(var i=0;i<productArray.length;i++){
            var colIndex=1;
            var vo = productArray[i];
            var stringTR = new Array();
            stringTR.push('<tr id="productTb_tr'+(i+2)+'"><td id="productTb_td'+(i+2)+colIndex+'">');
            stringTR.push('<img src="'+projectName+'/image/WindowPanel/window-close.gif" id="delImg'+(i+2)+'" style="cursor:pointer" onclick="del('+(i+2)+');"/>');
            stringTR.push('<input type="hidden" name="productList['+i+'].product.id" value="'+vo.productId+'"/>');
            stringTR.push('<td id="productTb_td'+(i+2)+(colIndex+1)+'">');
            stringTR.push('<input type="text" value="'+vo.name+'" name="productList['+i+'].name" id="proName'+(i+2)+'" size="18" readonly/></td>');
            stringTR.push('<td id="productTb_td'+(i+2)+(colIndex+2)+'">');
            stringTR.push('<input type="text" value="'+vo.type+'" name="productList['+i+'].type" id="proType'+(i+2)+'" size="18" readonly/></td>');
            stringTR.push('<td id="productTb_td'+(i+2)+(colIndex+3)+'">');
            stringTR.push('<input type="text" value="'+vo.num+'" name="productList['+i+'].num" id="proNum'+(i+2)+'"  onpropertychange="sumPrice();" onblur="validateNumByAlert(this.id, 0, 99999999);" size="6"/></td>');
            stringTR.push('<td id="productTb_td'+(i+2)+(colIndex+4)+'">');
            stringTR.push('<input type="text" value="'+vo.unit+'" name="productList['+i+'].unit" id="proUnit'+(i+2)+'" size="6" readonly/></td>');
            stringTR.push('<td id="productTb_td'+(i+2)+(colIndex+5)+'">');
            stringTR.push('<input type="text" value="'+vo.price+'" name="productList['+i+'].price" id="proPrice'+(i+2)+'" size="6" readonly/></td>');
            stringTR.push('<td id="productTb_td'+(i+2)+(colIndex+6)+'">');
            stringTR.push('<input type="text" value="'+vo.remark+'" name="productList['+i+'].remark" id="proRemark'+(i+2)+'" onblur="validateLengthByAlert(this.id, 1000);" size="18"/></td></tr>');
            prTable.append(stringTR.join(''));
            rowIndex++;
         }
         sumPrice();

}
function  clearTB(){
    var prTable= $("#productTb").empty();
    prTable.append('<tr id="productTb_tr1"><td align="center" width="5%" nowrap></td><td align="center" width="22%" nowrap>产品名称</td><td align="center" width="22%" nowrap>型号</td><td align="center" width="8%" nowrap>数量</td><td align="center" width="8%" nowrap>单位</td><td align="center" width="10%" nowrap>单价</td><td align="center" nowrap>备注</td></tr>');
    rowIndex = document.getElementById("productTb").rows.length-1;
 }