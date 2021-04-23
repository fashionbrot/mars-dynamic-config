


$("#templateKey").on("change",function () {
    var appCode = $("#appCode").val();
    var templateKey = $("#templateKey").val();
    initProperty(appCode,templateKey,"");
})

function initProperty(appCode,templateKey,databaseValue) {
    $("#dynamicDiv").html('');
    var databaseValueJson;
    if (databaseValue){
        databaseValueJson = eval('(' + databaseValue + ')');
    }

    if (appCode && templateKey){

        var propertyRows = common.getProperty(appCode,templateKey);
        var variableRows = common.getVariable();
        if (propertyRows){

            var html = "";
            for(var i=0;i<propertyRows.length;i++){
                var property = propertyRows[i];
                html +=initPropertyHtml(property,variableRows,databaseValueJson);
            }
            $("#dynamicDiv").html(html);
        }
        initDate();
        initEditor();
    }
}


function initPropertyHtml(property,variableRows,databaseValueJson) {
    var labelType    = property.labelType;
    var propertyName = property.propertyName;
    var propertyKey  = property.propertyKey;
    var propertyType = property.propertyType;
    var columnLength = property.columnLength;
    var labelRequired= property.labelRequired;
    var defaultValue = property.defaultValue;
    var labelValue   = property.labelValue;
    var variableKey  = property.variableKey;
    var variableKeyValue ;

    var otherHtml = " propertytype='"+propertyType+"'" +
        " labeltype='"+labelType+"'" +
        " columnlength ='"+columnLength+"'" +
        " labelrequired='"+labelRequired+"' "+
        " propertyname='"+propertyName+"'"+
        " propertykey='"+propertyKey+"'";

    var requiredHtml = labelRequired==1?"is-required":"";

    if (databaseValueJson){
        $.each(databaseValueJson,function (json) {
            if (propertyKey == json){
                defaultValue = databaseValueJson[json];

                var tempVariableKey = json+"Variable";
                if (databaseValueJson[tempVariableKey]){
                    variableKeyValue = databaseValueJson[tempVariableKey];
                    console.log("variableKeyValue",variableKeyValue)
                }
            }
        })
    }

    var html="";
    html += "<div class=\"form-group\">";
    html += "<label class=\"col-sm-3 control-label "+requiredHtml+" \">"+propertyName+"：</label>";
    if (labelType=='input'){

        otherHtml += " value= '"+defaultValue+"'  ";
        html += "<div class=\"col-sm-9\">";
        var eventHtml = "";
        var classHtml = "";
        var styleHtml = "";
        if (propertyType=='varchar'){
            eventHtml = " onkeyup='varcharEvent(this)' onafterpaste='varcharEvent(this)' "
            if (variableKey){
                styleHtml = " style='width:70%;' "
                html += getVariableHtml(variableRows,propertyKey,variableKeyValue);
            }
        }else if (propertyType=='bigint'){
            eventHtml = " onkeyup='bigintEvent(this)' onafterpaste='bigintEvent(this)' "
        }else if (propertyType=='decimal'){
            eventHtml = " onkeyup='doubleEvent(this)' onafterpaste='doubleEvent(this)' "
        }else if (propertyType=='date'){
            classHtml  = " input-date";
        }else if (propertyType=='datetime'){
            classHtml  = " input-datetime";
        }
        html +=" <input type=\"text\"  "+styleHtml+eventHtml+otherHtml+" class=\"form-control mars-input"+classHtml+"\" placeholder=\"请输入"+propertyName+"\">";
        html += "</div>";

    }else if (labelType=='select'){

        html += "<div class=\"col-sm-9\">";
        var valueJson=eval('(' + labelValue + ')');
        html += "<select   class='form-control-select mars-input' "+otherHtml+" style='width:100%;' >";
        for(var selectKey in valueJson){
            if(defaultValue==valueJson[selectKey]){
                html += "<option value='"+valueJson[selectKey]+"' selected='selected' >"+valueJson[selectKey]+" : "+selectKey+"</option>";
            }else{
                html += "<option value='"+valueJson[selectKey]+"'  >"+valueJson[selectKey]+" : "+selectKey+"</option>";
            }
        }
        html +="</select>";
        html += "</div>";

    }else if (labelType=='textarea'){
        var  eventHtml = " onkeyup='varcharEvent(this)' onafterpaste='varcharEvent(this)' "
        html += "<div class=\"col-sm-9\">";
        html += "<textarea  class='form-control mars-input' style=\"resize:none;\" "+eventHtml+otherHtml+" >"+defaultValue+"</textarea>";
        html += "</div>";
    }else if (labelType=='editor'){
        defaultValue = unescape(defaultValue);
        html += "<div class=\"col-sm-9\">";
        html += "<div class=\"summernote mars-input "+propertyKey+" \"  "+otherHtml+" >"+defaultValue+"</div>";
        html += "</div>";
    }

    html += "</div>";
    return html;
}

function varcharEvent(obj) {
    var value = $(obj).val();
    $(obj).val(value.replaceAll(/\'/g,"‘").replaceAll(/\"/g,"“"));
}
function bigintEvent(obj) {
    var value = $(obj).val();
    $(obj).val(value.replaceAll(/\D/g,""))
}
function doubleEvent(obj) {
    var value = $(obj).val();
    //得到第一个字符是否为负号
    var t = value.charAt(0);
    //先把非数字的都替换掉，除了数字和.
    value = value.replaceAll(/[^\d\.]/g,'');
    //必须保证第一个为数字而不是.
    value = value.replaceAll(/^\./g,'');
    //保证只有出现一个.而没有多个.
    value = value.replaceAll(/\.{2,}/g,'.');
    //保证.只出现一次，而不能出现两次以上
    value = value.replaceAll('.','$#$').replaceAll(/\./g,'').replaceAll('$#$','.');
    //如果第一位是负号，则允许添加
    if(t == '-'){
        value = '-'+value;
    }
    $(obj).val(value);
}




function initDate() {
    var s1 = $("input.input-date");
    for(var i=0;i<s1.length;i++){
        $(s1[i]).datetimepicker({
            format: "yyyy-mm-dd",
            minView: "month",
            autoclose: true
        });
    }
    var s2 = $("input.input-datetime");
    for(var j=0;j<s2.length;j++){
        $(s2[j]).datetimepicker({
            format: "yyyy-mm-dd HH:mm:ss",
            autoclose: true
        });
    }
}
function initEditor() {
    $('.summernote').summernote({
        lang: 'zh-CN'
    });
}

function  getVariableHtml(variableRows,propertyKey,variableKeyValue) {
    var html="";

    if (variableRows){
        html+="<select id='"+propertyKey+"Variable' style='width: 30%;float:left;' class='form-control-select'  >"
        html+="<option value=''>无前缀</option>";
        for(var k=0;k<variableRows.length;k++){
            var variable = variableRows[k];
            if (variableKeyValue == variable.variableKey){
                html+="<option value='"+variable.variableKey+"' selected='selected' >"+variable.variableName+"</option>";
            }else{
                html+="<option value='"+variable.variableKey+"'>"+variable.variableName+"</option>";
            }
        }
        html+="</select>";
    }
    return html;
}

function getTempJson(callback) {

    var inputs = $("#dynamicDiv").find(".mars-input");
    if (inputs){
        var array = new Array();
        for(var i=0;i<inputs.length;i++){
            var input = inputs[i];
            var propertyType    = $(input).attr("propertytype");
            var propertyKey     = $(input).attr("propertykey");
            var columnLength    = $(input).attr("columnlength");
            var labelRequired   = $(input).attr("labelrequired");
            var labelType       = $(input).attr("labelType");
            var variableKey     = $("#"+propertyKey+"Variable").val();
            var value           = $(input).val();
            var propertyName    = $(input).attr("propertyname");

            var number=false;
            if (labelType=="input"){
                if (variableKey!='undefined' && variableKey!="" && variableKey!=null){
                    array.push("\""+propertyKey+"Variable"+"\":"+"\""+variableKey+"\"") ;
                }
            }else if (labelType=="textarea"){

            }else if (labelType=="editor"){
                var editorText = $(".summernote.mars-input."+propertyKey).summernote('code');
                value = escape(editorText);

            }else if (labelType=="select"){

            }
            console.log(propertyType,propertyKey,columnLength,labelRequired,labelType,variableKey,value,propertyName);
            if (labelRequired=="1"){
                console.log(propertyKey,value);
                if ($.common.isEmpty(value)){
                    $(input).focus();
                    $.modal.msgError(propertyName+"必填");
                    return ;
                }
            }
            if ($.common.isNotEmpty(value) && value.length>columnLength && labelType!="editor"){
                $(input).focus();
                $.modal.msgError(propertyName+"最多"+columnLength+"字");
                return ;
            }

            if (number){
                array.push("\""+propertyKey+"\":"+value) ;
            }else{
                array.push("\""+propertyKey+"\":"+"\""+value+"\"") ;
            }

        }
        var json = "{"+array.join(",")+"}";
        console.log(json)
        callback(json);
    }

}
