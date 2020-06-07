layui.use(['table'], function () {
    var table = layui.table
    $ = layui.$;
    // 执行一个table实例
    table.render({
        elem: '#user'
        , height: 600
        , url: 'http://localhost:8080/demo/users/getAllUsers' //数据接口
        , title: '视频表'
        , page: true //开启分页
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [
            [ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', sort: true, fixed: 'left'}
                , {field: 'username', title: '用户名'}
                , {field: 'faceImage', title: '头像', sort: true, totalRow: true}
                , {filed: 'nickname', title: '昵称', sort: true}
                , {field: 'fanCounts', title: '粉丝人数', sort: true, totalRow: true}
                , {field: 'followCounts', title: '关注人数'}
                , {field: 'receiveLikeCounts', title: '收获点赞数'}
                , {field: '', fixed: 'right', title: '', width: 165, align: 'center', toolbar: '#barDemo'}
            ]
        ]
    });



    table.on('tool(videotest)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'detail') {
            layer.msg('查看操作');
        } else if (layEvent === 'del') {
            // console.log(data.id)
            layer.confirm('真的删除行么', function (index) {
                //向服务端发送删除指令
                $.ajax({
                    type: "GET"
                    , url: "http://localhost:8080/demo/users/del?id=" + data.id
                    , dataType: "json"   // 规定后台返回数据类型
                    , success: function (res) {
                        // 请求成功后的回调函数
                        // 对res中的数据进行判定
                        if (res.code == 0) {
                            obj.del()
                        } else {
                            layer.msg(res.msg)
                        }
                    }
                })
                layer.close(index)
            });
        } else if (layEvent === 'edit') {
            layer.msg('编辑操作');
        }
    })

    //监听头工具栏事件
    table.on('toolbar(videotest)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,datas = checkStatus.data; //获取选中的数据
        var arry = new Array();
        // 将选中的所有数据的id存放到arry数组中
        for (var i in datas) {
            arry[i] = datas[i].id
        }
        switch(obj.event){
            case 'add':
                layer.msg('添加');
                break;
            case 'update':
                if(datas.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if(datas.length === 0){
                    layer.msg('请至少选择一行');
                } else {
                    layer.confirm('真的删除吗', function (index) {
                        $.ajax({
                            type: "POST"
                            , url: "http://localhost:8080/demo/users/delete"
                            , dataType: "json"
                            , data: {
                                "ids": arry
                            }
                            , traditional: true
                            , success: function (res) {
                                console.log("in func")
                                if (res.code == 0) {
                                    // obj.del()
                                    window.location.href = window.location.href;
                                } else {
                                    layer.msg(res.msg)
                                    console.log("in else")
                                }
                            }
                            , error: function () {
                                console.log("in error")
                                // console.log(res.code)
                            } // 请求失败回调函数
                        })
                        layer.close(index)
                    })
                }
                break;
        };
    });

})
