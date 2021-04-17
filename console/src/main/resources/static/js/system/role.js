var editFlag;
var removeFlag;
var prefix = ctx + "system/role/";
$(function () {

    queryList();
});

function queryList() {
    var options = {
        url: prefix + "/queryAllList",
        createUrl: prefix + "/index?view=add",
        updateUrl: prefix + "/edit/{id}",
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
                title: '登录账号',
                sortable: false
            },
            {
                width:"20%",
                field: 'realName',
                title: '账号名称'
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
                    actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\',800,400)"><i class="fa fa-edit"></i>编辑</a> ');
                    actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> ');
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
