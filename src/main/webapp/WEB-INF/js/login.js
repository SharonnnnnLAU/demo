layui.use(['form', 'layer', 'jquery','carousel'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        carousel = layui.carousel,
         $ = layui.jquery;


    //表单输入效果
    $(".loginBody .input-item").click(function (e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function () {
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function () {
        $(this).parent().removeClass("layui-input-focus");
        if ($(this).val() != '') {
            $(this).parent().addClass("layui-input-active");
        } else {
            $(this).parent().removeClass("layui-input-active");
        }
    })


    form.on('submit(login)', function (date) {
        $.ajax({
            type: 'post'
            , url: '/demo/home/login'
            , dataType: 'json'
            , data: date.field
            , success: function (res) {
                if (res.code == 0) {
                    window.location.href = "/demo/home/main"
                } else {
                    layer.msg(res.msg, {icon:2})
                }
            }
        })
    })
})

function changeCode() {
    var img = document.getElementById("codeImg")
    // 加/表示从根目录开始
    img.src = "/demo/home/getCode?tt=" + new Date().getTime()
}

