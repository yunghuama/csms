function _selectall(obj)
{
  obj = (typeof obj == "string") ? document.getElementById(obj) : obj;
  if(obj.tagName.toLowerCase() != "select")
    return;
  for(var i=0; i<obj.length; i++)
  {
    obj[i].selected = true;
  }
}

function _top(obj)
{
  obj = (typeof obj == "string") ? document.getElementById(obj) : obj;
  if (obj.tagName.toLowerCase() != "select" && obj.length < 2)
    return false;
  var elements = new Array();
  for(var i=0; i<obj.length; i++)
  {
    if(obj[i].selected)
      elements[elements.length] = new Array((document.body.innerHTML ? obj[i].innerHTML : obj[i].text), obj[i].value, obj[i].style.color, obj[i].style.backgroundColor, obj[i].className, obj[i].id, obj[i].selected);
  }
  for(i=0; i<obj.length; i++)
  {
    if(!obj[i].selected)
      elements[elements.length] = new Array((document.body.innerHTML ? obj[i].innerHTML : obj[i].text), obj[i].value, obj[i].style.color, obj[i].style.backgroundColor, obj[i].className, obj[i].id, obj[i].selected);
  }
  for(i=0; i<obj.length; i++)
  {
    if(document.body.innerHTML)
      obj[i].innerHTML = elements[i][0];
    else
      obj[i].text = elements[i][0];

    obj[i].value = elements[i][1];
    obj[i].style.color = elements[i][2];
    obj[i].style.backgroundColor = elements[i][3];
    obj[i].className = elements[i][4];
    obj[i].id = elements[i][5];
    obj[i].selected = elements[i][6];
  }
}

function _bottom(obj)
{
  obj = (typeof obj == "string") ? document.getElementById(obj) : obj;
  if (obj.tagName.toLowerCase() != "select" && obj.length < 2)
    return false;
  var elements = new Array();
  for(var i=0; i<obj.length; i++)
  {
    if(!obj[i].selected)
      elements[elements.length] = new Array((document.body.innerHTML ? obj[i].innerHTML : obj[i].text), obj[i].value, obj[i].style.color, obj[i].style.backgroundColor, obj[i].className, obj[i].id, obj[i].selected);
  }
  for (i=0; i<obj.length; i++)
  {
    if (obj[i].selected)
      elements[elements.length] = new Array((document.body.innerHTML ? obj[i].innerHTML : obj[i].text), obj[i].value, obj[i].style.color, obj[i].style.backgroundColor, obj[i].className, obj[i].id, obj[i].selected);
  }
  for(i=obj.length-1; i>-1; i--)
  {
    if(document.body.innerHTML)
      obj[i].innerHTML = elements[i][0];
    else
      obj[i].text = elements[i][0];

    obj[i].value = elements[i][1];
    obj[i].style.color = elements[i][2];
    obj[i].style.backgroundColor = elements[i][3];
    obj[i].className = elements[i][4];
    obj[i].id = elements[i][5];
    obj[i].selected = elements[i][6];
  }
}

function _up(obj)
{
  obj = (typeof obj == "string") ? document.getElementById(obj) : obj;
  if(obj.tagName.toLowerCase() != "select" && obj.length < 2)
    return false;
  var sel = new Array();
  for(var i=0; i<obj.length; i++)
  {
    if(obj[i].selected == true)
    {
      sel[sel.length] = i;
    }
  }
  for(var i=0; i<sel.length; i++)
  {
    if(sel[i] != 0 && !obj[sel[i]-1].selected)
    {
      var tmp = new Array((document.body.innerHTML ? obj[sel[i]-1].innerHTML : obj[sel[i]-1].text), obj[sel[i]-1].value, obj[sel[i]-1].style.color, obj[sel[i]-1].style.backgroundColor, obj[sel[i]-1].className, obj[sel[i]-1].id);
      
      if(document.body.innerHTML)
        obj[sel[i]-1].innerHTML = obj[sel[i]].innerHTML;
      else obj[sel[i]-1].text = obj[sel[i]].text;
        obj[sel[i]-1].value = obj[sel[i]].value;

      obj[sel[i]-1].style.color = obj[sel[i]].style.color;
      obj[sel[i]-1].style.backgroundColor = obj[sel[i]].style.backgroundColor;
      obj[sel[i]-1].className = obj[sel[i]].className;
      obj[sel[i]-1].id = obj[sel[i]].id;

      if(document.body.innerHTML)
        obj[sel[i]].innerHTML = tmp[0];
      else
        obj[sel[i]].text = tmp[0];

      obj[sel[i]].value = tmp[1];
      obj[sel[i]].style.color = tmp[2];
      obj[sel[i]].style.backgroundColor = tmp[3];
      obj[sel[i]].className = tmp[4];
      obj[sel[i]].id = tmp[5];
      obj[sel[i]-1].selected = true;
      obj[sel[i]].selected = false;
    }
  }
}

function _down(obj)
{
  obj = (typeof obj == "string") ? document.getElementById(obj) : obj;
  if (obj.tagName.toLowerCase() != "select" && obj.length < 2)
    return false;
  var sel = new Array();
  for(var i=obj.length-1; i>-1; i--)
  {
    if(obj[i].selected == true)
      sel[sel.length] = i;
  }
  for(var i=0; i<sel.length; i++)
  {
    if(sel[i] != obj.length-1 && !obj[sel[i]+1].selected)
    {
      var tmp = new Array((document.body.innerHTML ? obj[sel[i]+1].innerHTML : obj[sel[i]+1].text), obj[sel[i]+1].value, obj[sel[i]+1].style.color, obj[sel[i]+1].style.backgroundColor, obj[sel[i]+1].className, obj[sel[i]+1].id);
      if(document.body.innerHTML)
        obj[sel[i]+1].innerHTML = obj[sel[i]].innerHTML;
      else
        obj[sel[i]+1].text = obj[sel[i]].text;

      obj[sel[i]+1].value = obj[sel[i]].value;
      obj[sel[i]+1].style.color = obj[sel[i]].style.color;
      obj[sel[i]+1].style.backgroundColor = obj[sel[i]].style.backgroundColor;
      obj[sel[i]+1].className = obj[sel[i]].className;
      obj[sel[i]+1].id = obj[sel[i]].id;
      
      if(document.body.innerHTML)
        obj[sel[i]].innerHTML = tmp[0];
      else
        obj[sel[i]].text = tmp[0];

      obj[sel[i]].value = tmp[1];
      obj[sel[i]].style.color = tmp[2];
      obj[sel[i]].style.backgroundColor = tmp[3];
      obj[sel[i]].className = tmp[4];
      obj[sel[i]].id = tmp[5];
      obj[sel[i]+1].selected = true;
      obj[sel[i]].selected = false;
    }
  }
}

function _inarray(v,a)
{
  for(var i in a)
  {
    if(a[i] == v)
      return true;
  }
  return false;
}