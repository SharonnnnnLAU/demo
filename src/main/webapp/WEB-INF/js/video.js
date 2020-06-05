function clickToPaly(path) {
    console.log(path)
    var s = '<video src="../' + path + '" width="100%", height="100%", controls="controls"> </video>'
    layui.use(['layer'], function () {
        var layer = layui.layer
        layer.open({
            type: 1,
            title: "预览",
            area: ['57%', '60%'],
            content: s
        })
    })
}

layui.use(['table'], function () {
    var table = layui.table
    $ = layui.$;
    // 执行一个table实例
    table.render({
        elem: '#video'
        , height: 600
        , url: 'http://localhost:8080/demo/videos/getAllVideos' //数据接口
        , title: '视频表'
        , page: true //开启分页
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [
            [ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', sort: true, fixed: 'left'}
                , {field: 'userId', title: '发布者'}
                , {field: 'videoDesc', title: '视频描述'}
                , {
                field: 'videoPath', title: '播放'
                , templet: function (d) {
                    var path = d.videoPath;
                    return "<button class='layui-btn layui-btn-radius layui-btn-warm layui-btn-sm' onclick='clickToPaly(\"" + path + "\")'>点击播放</button>";
                }
            }
                , {field: 'videoSeconds', title: '时长', sort: true}
                , {
                field: 'coverPath', title: '封面图',
                templet: function (d) {
                    var path = d.coverPath;
                    return "<img src=../" + path + " style='height: 28px, width=100% ' />"
                }
            }
                , {field: 'likeCounts', title: '点赞次数'}
                , {field: 'status', title: '状态'}
                , {field: 'createTime', title: '创建时间', width: 135, sort: true, totalRow: true}
                , {field: 'tool', fixed: 'right', title: '', width: 165, align: 'center', toolbar: '#barDemo'}
            ]
        ]
    });

    table.on('tool(videotest)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'detail') {
            layer.msg('查看操作');
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //向服务端发送删除指令
                $.ajax({
                    type: "GET"
                    , url: "http://localhost:8080/demo/videos/del?id=" + data.id
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
                            , url: "http://localhost:8080/demo/videos/delete"
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

});
