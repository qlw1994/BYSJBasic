define(function (require, exports, module) {
    var $h1 = $("#h1_text");

    function gerQueue() {
        $.ajax({
            url: ROOTPAth + "/user/departmentqueues/getQueue",
            type: "post",
            dataType: "json",
            data: {
                patientid: patientid
            },
            success: function (res) {
                if (res.total == 0) {
                    $h1.html("该就诊人不在任何科室队列中！");
                } else(
                    $h1.html("当前就诊人序号为  " + "<big>" + res.data + "</big>")
                )
            }
        })
    };
    setInterval(gerQueue(), 5000);  //每隔5秒循环执行过程函数！
})