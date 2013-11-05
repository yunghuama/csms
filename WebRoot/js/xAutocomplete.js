/*!
 * Autocomplete Javascript Component v2.0
 *
 * 全文检索搜索提示
 * Copyright 2011, zhangqiang
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Build on jQuery JavaScript Library v1.4.4
 * Date: 2011.08.24
 */
(function($){
    var autocomplete = function(config, jQuery){
        $.extend(this, autocomplete.defaults, config);
        this._init(jQuery);
    };
    autocomplete.prototype = {
        //private
        _init : function(jQuery){
            this.$input = jQuery;
            this.$DOM = $('<div class="x-autocomplete-main"></div>');
            this.$TOP = $('<div class="x-autocomplete-item-top"></div>');
            this.$TITLE = $('<div class="listtitle"></div>');
            this.$RESULT = $('<div class="listresult"></div>');
            this.$HIDDEN = $('<input type="hidden" id="'+this.hiddenName+'" name="'+this.hiddenName+'"/>');
            
            var that = this;
            var offset = jQuery.offset();
            this.$DOM.css({
              'width' : this.width-22,  //padding各3,父容器padding8
              'max-width' : this.maxWidth,
              'min-width' : this.minWidth,
              'top' : offset.top+jQuery.height()+6,
              'left' : offset.left
            });
            if(this.url){
                this.setUrl(this.url);
            }else{
                this.setData(this.data);
            }
        },
        setListTitle: function(str){
            this.listTitle = str;
            this.$TITLE.text(this.listTitle);
        },
        setListResult: function(str){
            this.listResult = str;
            str = str.replace('{total}', this._total)
                     .replace('{topN}', this._topN);
            this.$RESULT.text(str);
        },
        setData: function(data){
            var len = data.length;
            var that = this;

            if($.isArray(data)){
                that.$input.unbind('keyup.autocomplete');
                that.$input.bind('keyup.autocomplete', function(e){
                    var V = $.trim($(this).val()).toLowerCase();
                    if(V===''){
                        that.$DOM.remove();
                        return !!V;
                    }
                    var items = [];
                    that.$HIDDEN.val('');
                    for(var i=0,s=0; i<len; ++i){
                        var _id = data[i]['id'],
                            _code = data[i]['code'],
                            _name = data[i]['name'],
                            _ct = data[i]['ct'],
                            _reg = new RegExp('^'+V.toLowerCase());
                        if((_reg.test(_code.toLowerCase()) || _reg.test(_name)) && ++s){
                            if(s > that.maxItem){
                                continue;
                            }
                            var toAppend = $('<div class="x-autocomplete-item">'+'('+_code+')&nbsp;'+_name+'</div>').hover(
                                function(){
                                    $(this).addClass('x-autocomplete-hover');
                                },
                                function(){
                                    $(this).removeClass('x-autocomplete-hover');
                                }
                            ).bind('click.autocomplete', {name: _name, id: _id, ct: _ct}, function(e){
                                that.$input.val(e.data.name);
                                that.$HIDDEN.val(e.data.id+','+e.data.ct);
                                that.onEnter();
                            }).data('info', {id: _id, name: _name, ct: _ct});
                            items.push(toAppend);
                        }
                    }
                    if(items.length){
                        var current = $('div.x-autocomplete-hover', that.$DOM);
                        if(e.keyCode===40){
                            var def = $('div.x-autocomplete-item:eq(0)', that.$DOM);
                            if(!!!current.size()){
                                def.addClass('x-autocomplete-hover');
                            }else{
                                current.removeClass('x-autocomplete-hover');
                                if(current.next('div.x-autocomplete-item')){
                                    current.next('div.x-autocomplete-item').addClass('x-autocomplete-hover');
                                }
                            }
                        }else if(e.keyCode===38){
                            var def = $('div.x-autocomplete-item:last', that.$DOM);
                            if(!!!current.size()){
                                def.addClass('x-autocomplete-hover');
                            }else{
                                current.removeClass('x-autocomplete-hover');
                                if(current.prev('div.x-autocomplete-item')){
                                    current.prev('div.x-autocomplete-item').addClass('x-autocomplete-hover');
                                }
                            }
                        }else if(e.keyCode===13){
                            if(!!current.size()){
                                var info = current.data('info');
                                if(info){
                                  that.$input.val(info.name);
                                  that.$HIDDEN.val(info.id+','+info.ct);
                                  that.$DOM.remove();
                                }
                            }
                        }else{
                            that._total = s;
                            that._topN = that.maxItem < items.length ? that.maxItem : items.length;
                            that.$input.after(that.$HIDDEN);
                            that.$TOP.append(that.$TITLE).append(that.$RESULT);
                            that.$DOM.empty('.x-autocomplete-item').append(that.$TOP);
                            that.setListTitle(that.listTitle);
                            that.setListResult(that.listResult);
                            for(var j=0; j<items.length; ++j){
                                that.$DOM.append(items[j]);
                            }
                            $('body').append(that.$DOM);
                            $(document).bind('click.autocomplete', function(){
                                that.$DOM.remove();
                                $(this).unbind('.autocomplete');
                            });
                        }
                    }else{
                        that.$DOM.remove();
                    }
                    if(e.keyCode===13){
                      that.onEnter();
                    }
                });
            }
        },
        setUrl: function(url){
            var that = this;
            this.url = url;
            $.ajax({
                url: url,
                dataType: 'script',
                success: function(list){
                    if(list!==''){
                        that.data = $.parseJSON(list);
                        that.setData.call(that, that.data);
                    }
                },
                error: function(){
                
                }
            });
        },
        getData: function(){
            return this.data;
        },
        getListTitle: function(){
            return this.listTitle;
        },
        getListResult: function(){
            return this.listResult;
        }
        
    };
    
    $.fn.autocomplete = function(config){
        return new autocomplete(config, this);
    };
    
    //-------------------------默认配置
    autocomplete.defaults = {
        data: [],
        url: '',
        maxItem: 10,
        listTitle: '结果列表',
        listResult: '共 {total} 条数据, 当前显示 {topN} 条',
        maxWidth: 400,  //IE 不行
        minWidth: 250,  //IE 不行
        hiddenName: '',
        loadMask: false,
        onEnter: $.noop
    };

})(jQuery);
