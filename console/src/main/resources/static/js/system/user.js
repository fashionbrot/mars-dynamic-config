var editFlag;
var removeFlag;
var prefix = ctx + "system/user/";

$(function() {
    var panehHidden = false;
    if ($(this).width() < 769) {
        panehHidden = true;
    }
    $('body').layout({ initClosed: panehHidden, west__size: 185 });
    // 回到顶部绑定
    if ($.fn.toTop !== undefined) {
        var opt = {
            win:$('.ui-layout-center'),
            doc:$('.ui-layout-center')
        };
        $('#scroll-up').toTop(opt);
    }
    queryList();
    queryDeptTree();
});

function queryList() {
    var options = {
        url: prefix + "/queryAll",
        createUrl: prefix + "/index/add",
        updateUrl: prefix + "/index/edit/?id={id}",
        removeUrl: prefix + "/deleteById",
        exportUrl: prefix + "/export",
        importUrl: prefix + "/importData",
        importTemplateUrl: prefix + "/importTemplate",
        sortName: "createTime",
        sortOrder: "desc",
        modalName: "用户",
        columns: [
            {
                width:"5%",
                checkbox: true
            },
            {
                width:"20%",
                field: 'userName',
                title: '手机号',
                sortable: false
            },
            {
                width:"20%",
                field: 'realName',
                title: '姓名'
            },
            {
                width:"20%",
                field: 'idCard',
                title: '身份证号'
            },
            {
                width:"20%",
                visible: true,
                title: '用户状态',
                align: 'center',
                formatter: function (value, row, index) {
                    return statusTools(row);
                }
            },
            {
                width:"20%",
                field: 'createDate',
                title: '创建时间',
                sortable: false
            },
            {
                width:"20%",
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var actions = [];
                    if (row.superAdmin==0){
                        actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\',800,400)"><i class="fa fa-edit"></i>编辑</a> ');
                        actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> ');

                    }
                    return actions.join('');
                }
            }]
    };
    $.table.init(options);
}


/* 用户状态显示 */
function statusTools(row) {
    if (row.status == 1) {
        return '开启';
    } else {
        return '关闭';
    }
}


function queryDeptTree(){
    var url = ctx + "sys/org/queryList2";
    var options = {
        url: url,
        expandLevel: 2,
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
            }
        },
        onClick : zOnClick
    };
    $.tree.init(options);

    function zOnClick(event, treeId, treeNode) {
        console.log(treeNode.id);
        $("#orgId").val(treeNode.id);
        $.table.search();
    }
}

$('#btnExpand').click(function() {
    $._tree.expandAll(true);
    $(this).hide();
    $('#btnCollapse').show();
});

$('#btnCollapse').click(function() {
    $._tree.expandAll(false);
    $(this).hide();
    $('#btnExpand').show();
});

$('#btnRefresh').click(function() {
    queryDeptTree();
});

/* 用户管理-部门 */
function dept() {
    var url = ctx + "sys/org/index";
    $.modal.openTab("机构管理", url);
}
