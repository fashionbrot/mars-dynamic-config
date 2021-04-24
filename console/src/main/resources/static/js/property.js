
function groupHide(id) {
    $("#"+id).parent().parent().hide();
}
function groupShow(id) {
    $("#"+id).parent().parent().show();
}

$("#propertyType").on("change",function () {
    var propertyType=$("#propertyType").val();
    if (propertyType=="varchar" || propertyType=="bigint" || propertyType=="decimal"){
        groupShow("propertyType");
        groupShow("columnLength");
        $("#columnLength").val("");
    }else{
        groupHide("columnLength");
        $("#columnLength").val("0");
    }
})


function loadLabelType(type,value){

    if (type=="input"){
        groupShow("propertyType");
        groupShow("variableKey");
        groupShow("columnLength");
        if (!value){
            $("#columnLength").val("");
        }

    }else if (type=="textarea"){
        groupHide("propertyType");
        groupHide("variableKey");
        groupShow("columnLength");
        if (!value){
            $("#columnLength").val("");
        }
    }else if (type=="editor"){
        groupHide("propertyType");
        groupHide("variableKey");
        groupHide("columnLength");
        if (!value){
            $("#columnLength").val("0");
        }
    }else if (type=="select"){
        groupHide("propertyType");
        groupHide("variableKey");
        groupHide("columnLength");
        if (!value){
            $("#columnLength").val("0");
        }
    }

    var html="";
    if (type=='input'){
        html =  "<input type=\"text\" value='"+value+"' class=\"form-control\" />";
    }else if (type=='textarea'){
        html =  "<textarea  class=\"form-control\" >"+value+"</textarea>";
    }else if (type=='select'){
        if (value){
            var valueJson=eval('(' + value + ')');
            html = getForJsonHtml(valueJson);
        }else{
            html = selectHtml("","");
        }
    }else if (type=='editor'){
        html = "<div class=\"summernote\">"+value+"</div>";
    }
    $("#labelTypeDiv").html(html);
    if (type=='editor'){
        $('.summernote').summernote({
            lang: 'zh-CN'
        });
    }
}

function getForJsonHtml(json) {
    var html='';
    for(var k in json){
        html+=selectHtml(k,json[k]);
    }
    return html;
}

function selectHtml(key,value) {
    var propertyHtml="<div style='margin-top:5px;display: flex;line-height:34px;' class='propertyGroupValue' > ";
    if(value!=null && value!=''){
        propertyHtml+="属性名称：<input class='form-control key' placeholder='中文名称' onkeyup=\"value=value.replace(/[^\\w\u4E00-\u9FA5]/g,'')\" value='"+key+"' style='width:31%;' /> ";
    }else{
        propertyHtml+="属性名称：<input class='form-control key' placeholder='中文名称' onkeyup=\"value=value.replace(/[^\\w\u4E00-\u9FA5]/g,'')\" style='width:31%;' /> ";
    }
    if (key!=null && key!=''){
        propertyHtml+="属性值:<input class='form-control value' placeholder='对应值' onKeyUp=\"value=value.replace(/[\\W]/g,'')\" value='"+value+"'  style='width:31%;' /> ";
    }else{
        propertyHtml+="属性值:<input class='form-control value'  placeholder='对应值' onKeyUp=\"value=value.replace(/[\\W]/g,'')\"  style='width:31%;' /> ";
    }
    propertyHtml+="&nbsp;";
    propertyHtml+="<button type='button' class='btn-xs btn-success' onclick='addPropertyButton(\""+"labelTypeDiv"+"\")' >添加</button>";
    propertyHtml+="&nbsp;";
    propertyHtml+="<button type='button' class='btn-xs btn-warning' onclick='$(this).parent().remove();' >删除</button>";
    propertyHtml+="</div>";
    return propertyHtml;
}
function addPropertyButton(divClass) {
    $("#"+divClass).append(selectHtml('',''));
}



$("#labelType").on("change",function () {
    var labelType = $("#labelType").val();
    loadLabelType(labelType,"");
});


function getSelectOptionValue() {
    var select = $(".propertyGroupValue");
    var labelValue ="";
    if (select){
        var json="";
        for(var i=0;i<select.length;i++){
            var key =  $(select[i]).find(".key").val();
            var value =  $(select[i]).find(".value").val();
            if (json==""){
                json = "'"+key+"':'"+value+"'";
            }else{
                json +=",'"+key+"':'"+value+"'";
            }
        }
        if (json!=""){
            labelValue= "{"+json+"}";
        }else{
            labelValue= "";
        }
    }
    return labelValue;
}