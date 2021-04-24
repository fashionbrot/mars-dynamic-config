
$.fn.serializeJson = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            o[this.name] = o[this.name].concat(",").concat(this.value);
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function getQuery(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

function radioAttr(name,isChecked) {
   var names = $('input[name="'+name+'"]');
   if (names){
       for(var i=0;i<names.length;i++){
           var name = names[i];
           $(name).attr("checked",isChecked);
           if (isChecked){
               $(name).parent().addClass("checked")
           }else{
               $(name).parent().removeClass("checked")
           }
       }
   }
}
function radioChecked(name,value,checked) {
    var names = $('input[name="'+name+'"]');
    if (names){
        for(var i=0;i<names.length;i++){
            var name = names[i];
            if ($(name).val()==value){
                $(name).attr("checked",checked);
                $(name).parent().addClass("checked")
            }else{
                $(name).attr("checked",!checked);
                $(name).parent().removeClass("checked")
            }
        }
    }
}



function loadRole(id,selectId) {
    var prefix = ctx + "sys/role";

    $.operate.get2(prefix + "/queryList",function (data) {
        if (data.code==0){
            var rows=data.data;
            var html="<option value=''>请选择</option>";
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                if (selectId && row.id==selectId){
                    html+="<option selected='selected' value='"+row.id+"'>"+row.roleName+"</option>";
                }else{
                    html+="<option value='"+row.id+"'>"+row.roleName+"</option>";
                }
            }
            $("#"+id).html(html);
            $('#'+id).select2({
                placeholder: "请选择",
                allowClear: false
            });
        }
    });
}

function loadMenuLevel(id,selectId,level) {
    var prefix = ctx + "sys/menu";
    $.operate.get2(prefix + "/queryList?menu_level="+level,function (data) {
            if (data.code==0){
                var rows=data.data;
                var html="<option value=''>请选择</option>";
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    if (selectId && row.id==selectId){
                        html+="<option selected='selected' value='"+row.id+"'>"+row.menuName+"</option>";
                    }else{
                        html+="<option value='"+row.id+"'>"+row.menuName+"</option>";
                    }
                }
                $("#"+id).html(html);
                $('#'+id).select2({
                    placeholder: "请选择",
                    allowClear: false
                });
            }
    });
}

var common={
    loadEnv:function (id,selectId) {
        var prefix = ctx + "m/env";
        $.operate.get2(prefix + "/queryList",function (data) {
            if (data.code==0){
                var rows=data.data;
                var html="<option value=''>请选择环境</option>";
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    if (selectId && row.envCode==selectId){
                        html+="<option selected='selected' value='"+row.envCode+"'>"+row.envDesc+"</option>";
                    }else{
                        html+="<option value='"+row.envCode+"'>"+row.envDesc+"</option>";
                    }
                }
                $("#"+id).html(html);
            }
        });
    },
    loadApp:function (id,selectId) {
        var prefix = ctx + "m/app";
        $.operate.get2(prefix + "/queryList",function (data) {
            if (data.code==0){
                var rows=data.data;
                var html="<option value=''>请选择应用</option>";
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    if (selectId && row.appCode==selectId){
                        html+="<option selected='selected' value='"+row.appCode+"'>"+row.appDesc+"</option>";
                    }else{
                        html+="<option value='"+row.appCode+"'>"+row.appDesc+"</option>";
                    }
                }
                $("#"+id).html(html);
            }
        });
    },
    loadTemplate:function (id,selectId,appCode) {
        var prefix = ctx + "m/template/queryList";
        if (appCode){
            prefix = prefix+"?app_code="+appCode;
        }
        $.operate.get2(prefix,function (data) {
            if (data.code==0){
                var rows=data.data;
                var html="<option value=''>请选择模板</option>";
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    if (selectId && row.templateKey==selectId){
                        html+="<option selected='selected' value='"+row.templateKey+"'>"+row.templateName+"</option>";
                    }else{
                        html+="<option value='"+row.templateKey+"'>"+row.templateName+"</option>";
                    }
                }
                $("#"+id).html(html);
            }
        });
    },
    loadVariable:function (id,selectId) {
        var prefix = ctx + "m/env/variable/queryList";

        $.operate.get2(prefix,function (data) {
            if (data.code==0){
                var rows=data.data;
                var html="<option value=''>请选择</option>";
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    if (selectId && row.variableKey==selectId){
                        html+="<option selected='selected' value='"+row.variableKey+"'>"+row.variableName+"</option>";
                    }else{
                        html+="<option value='"+row.variableKey+"'>"+row.variableName+"</option>";
                    }
                }
                $("#"+id).html(html);
                $('#'+id).select2({
                    allowClear: false
                });
            }
        });
    },
    getProperty:function(appCode,templateKey,showTable) {
        var prefix = ctx + "m/property/queryList?appCode="+appCode+"&templateKey="+templateKey;
        if (showTable){
            prefix+="&showTable="+showTable;
        }
        var rows;
        $.ajax({
            url: prefix,
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code==0){
                    rows = data.data;
                    return rows;
                }
            }
        });
        return rows;
    },
    getVariable:function() {
        var prefix = ctx + "m/env/variable/queryList";
        var rows;
        $.ajax({
            url: prefix,
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code==0){
                    rows = data.data;
                    return rows;
                }
            }
        });
        return rows;
    }
}

