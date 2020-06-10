function clickToPaly(path) {
    // console.log(path)
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

layui.use(['table', 'form', 'upload', 'laydate'], function () {
    var table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = laydate
    $ = layui.$;    // 声明jQuery
    // 执行一个table实例
    table.render({
        elem: '#video'
        , height: 600
        , url: '/demo/videos/getAllVideos' //数据接口
        , title: '视频表'
        , page: true //开启分页
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [
            [ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', sort: true, fixed: 'left'}
                , {field: 'userId', title: '发布者'}
                , {field: 'videoDesc', title: '视频描述', edit: 'text'}
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
                , {field: 'likeCounts', title: '点赞次数', edit: 'text'}
                , {
                field: 'status', title: '状态', templet: function (d) {
                    // d.id 放到input的value  需要自定义返回一个switch开关
                    // 需要通过判断数据库中的status字段来设置开关的开闭状态
                    if (d.status == 1) {
                        return '<input type="checkbox" name="status" value="' + d.id + '}}" lay-skin="switch" lay-text="异常|正常" lay-filter="statusBox" checked>'
                    } else {
                        return '<input type="checkbox" name="status" value="' + d.id + '" lay-skin="switch" lay-text="异常|正常" lay-filter="statusBox">'
                    }
                }
            }
                , {
                field: 'createTime', title: '创建时间', width: 135, sort: true, totalRow: true,
                templet: function (d) {
                    var date = new Date(d.createTime)
                    date = layui.util.toDateString(date, 'yyyy/mm/dd HH:mm:ss')
                    return date
                }
            }
                , {field: 'tool', fixed: 'right', title: '', width: 165, align: 'center', toolbar: '#barDemo'}
            ]
        ]
    });

    // 监听行工具栏事件
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
                    , url: "/demo/videos/del?id=" + data.id
                    , dataType: "json"   // 规定后台返回数据类型
                    , success: function (res) {
                        // 请求成功后的回调函数
                        // 对res中的数据进行判定
                        if (res.code == 0) {
                            // obj.del()
                            tableIns.reload();
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

    // 监听头工具栏事件
    table.on('toolbar(videotest)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , datas = checkStatus.data; //获取选中的数据
        var arry = new Array();
        // 将选中的所有数据的id存放到arry数组中
        for (var i in datas) {
            arry[i] = datas[i].id
        }
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2
                    , title: "上传视频"
                    , area: ["60%", "60%"]
                    , content: "addVideos.html"
                })
                layer.msg('添加');
                break;
                break;
            case 'delete':
                if (datas.length === 0) {
                    layer.msg('请至少选择一行');
                } else {
                    layer.confirm('真的删除吗', function (index) {
                        $.ajax({
                            type: "POST"
                            , url: "/demo/videos/delete"
                            , dataType: "json"
                            , data: {
                                "ids": arry
                            }
                            , traditional: true
                            , success: function (res) {
                                // console.log("in func")
                                if (res.code == 0) {
                                    // obj.del()
                                    window.location.href = window.location.href;
                                } else {
                                    layer.msg(res.msg)
                                    // console.log("in else")
                                }
                            }
                            , error: function () {
                                // console.log("in error")
                                // console.log(res.code)
                            } // 请求失败回调函数
                        })
                        layer.close(index)
                    })
                }
                break;
        }
        ;
    });

    // 监听状态switch开关
    form.on('switch(statusBox)', function (obj) {
        // 向后台发送请求
        // 参数：当前行的数据的id、当前行数据的修改值
        // console.log(obj.elem.checked)
        var value = obj.value;
        var check = obj.elem.checked;
        var stu = check == true ? 1 : 0
        $.ajax({
            // send to root
            type: "post"
            , url: "/demo/videos/update"
            , dataType: 'json'
            , data: {
                'id': value,
                'field': 'status',
                'value': stu
            }
            , success: function (res) {
                layer.msg(res.msg)
            }
        })
        // layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });

    // 监听单元格编辑
    table.on('edit(videotest)', function (obj) {
        var value = obj.value // 得到修改后的值
            , data = obj.data // 得到所在行所有键值
            , field = obj.field; // 得到字段
        // send to root
        $.ajax({
            type: "post"
            , url: "/demo/videos/update"
            , dataType: "json"
            , data: {
                "value": value
                , "id": data.id
                , "field": field
            }
            , success: function (res) {
                layer.msg(res.msg)
            }
        })
        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
    });

    // 实现视频上传
    upload.render({
        elem: '#uplVidBtn'
        , url: '/demo/videos/upload'
        , accept: 'video' //视频
        , done: function (res) {    // done === ajax.success
            console.log(res)
            // debugger
            if (res.code == 0) {
                $("#videoPath").val(res.msg)
                layer.msg('上传成功');
            } else {
                layer.msg(res.msg)
            }
        }
    });

    // 实现封面上传
    var uploadInst = upload.render({
        elem: '#uplPicBtn'
        ,url: 'https://httpbin.org/post' //改成您自己的上传接口
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#pic').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    // 表单提交
    form.on("submit(submit)", function (obj) {
        console.log(obj.field)
        $.ajax({
            type: 'post'
            , url: '/demo/videos/add'
            , dataType: 'json'
            , data: obj.field
            , success: function (res) {
                layer.msg(res.msg)
            }
        })
    })

    // 模糊查询
    $('#search').on("click", function () {
        table.reload('video', {
            url: '/demo/videos/selectByLike'
            , where: { //设定异步数据接口的额外参数，任意设
                "value": $("#demoReload").val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重载数据
    })

    // 日期范围选择
    // laydate.render({
    //
    // })
});

