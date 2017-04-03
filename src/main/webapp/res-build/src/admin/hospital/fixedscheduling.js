define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $schedulingList = $("#scheduling-list");
    var $UploadForm = $("#scheduling_upload");
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；
    $('body').tooltip({
        selector: '.has-tooltip'
    });


    var listTpl = juicer(
        [
            '{@if total === 0}',
            '<tr>',
            '<td colspan="8" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index==0}',
            '<tr role="row" class="odd" ><td>上午</td>',
            '{@/if}',
            '{@if index==7}',
            '<tr role="row" class="even"><td>下午</td>',
            '{@/if}',
            '{@if index<7}',
            '{@if item.status==1}',
            '    <td id="1_${index*1+1}">上班</td>',
            '{@/if}',
            '{@if item.status==2}',
            '    <td id="1_${index*1+1}">停诊</td>',
            '{@/if}',
            '{@if item.status==0}',
            '    <td id="1_${index*1+1}">休息</td>',
            '{@/if}',
            '{@/if}',
            '{@if index>6}',
            '{@if item.status==1}',
            '    <td id="2_${index*1-6}">上班</td>',
            '{@/if}',
            '{@if item.status==2}',
            '    <td id="2_${index*1-6}">停诊</td>',
            '{@/if}',
            '{@if item.status==0}',
            '    <td id="2_${index*1-6}">休息</td>',
            '{@/if}',
            '{@/if}',
            '{@if index==6}',
            '</tr>',
            '{@/if}',
            '{@if index==13}',
            '</tr>',
            '{@/if}',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {

            tool.startPageLoading();
            this.bind();
            var windowurl = window.location.href;
            var returnUrl = windowurl.indexOf("currentpage=");

            if (returnUrl == -1 || returnUrl == "") {
                pageIndex.resetgoto(1);
            } else {
                var returnUrl_val = parseInt(windowurl.substring(returnUrl + 12));
                if (returnUrl_val != 1) {
                    pageIndex.resetgoto(returnUrl_val);
                } else {
                    pageIndex.reset()
                }
            }
        },
        bind: function () {
            var self = this;
            //列表分页
            pageIndex = new Page({
                ajax: {
                    url: ROOTPAth + '/admin/fixedschedulings/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            doctorid:doctorid,
                            length: pagelength
                        };
                        return data;
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $schedulingList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        // $("#scheduling_edit").live('click', function () {
                        //     for (var i = 1; i <= 7; i++) {
                        //         $("#1_" + i).toggle(
                        //             function () {
                        //                 $(this).html("上班")
                        //                 $(this).html("停诊")
                        //                 $(this).html("休息")
                        //             }
                        //         );
                        //         $("#2_" + i).toggle(
                        //             function () {
                        //                 $(this).html("上班")
                        //                 $(this).html("停诊")
                        //                 $(this).html("休息")
                        //             }
                        //         );
                        //     }
                        //     $(this).trigger('click');
                        // });
                        // $("#scheduling_edit").on("click",function () {
                        //     if($(this.html())=="开始编辑"){
                        //         $()
                        //     }
                        // });
                    }
                },
                pageName: "page",
                /*tpl: {
                 go: true //隐藏跳转到第几页
                 },*/
                getTotalPage: function (res) {
                    //返回总页数
                    return Math.ceil(res.total / pagelength);
                },
                pageWrapper: '.table-page'
            });
            pageIndex.reset();
            //分页，修改每页显示数据
            $schedulingList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $schedulingList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            jQuery.validator.addMethod("checkCsv", function(value, element) {
                var filepath=$("#fixedschedulingfile").val();
                //获得上传文件名
                var fileArr=filepath.split("\\");
                var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");
                var filetype=fileTArr[fileTArr.length-1];
                //切割出后缀文件名
                if(filetype != "csv"){
                    return false;
                }else{
                    return true;
                }
            }, "上传文件格式不适合");
            $("#scheduling_upload").validate({

                rules: {
                   file:{
                       required:true,
                       checkCsv:true
                   }


                },
                messages: {
                    file:{
                        required: "请选择文件"
                    }
                },
                errorElement: 'span', //default input error message container
                    errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input


                invalidHandler: function (event, validator) { //display error alert on form submit
                //	                $('.alert-danger', $('.login-form')).show();
            },
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                },
                submitHandler: function () {



                    var formData = new FormData($("#scheduling_upload")[0]);

                    var updatePath = ROOTPAth + '/admin/fixedschedulings/fileupload';
                    $.ajax({
                       url: updatePath,
                        type:"post",
                        dataType:"json",
                        data: formData,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success:function (data) {
                            if (data.code === 1) {
                                // $('#modifyModal').modal('hide');
                                $addRoletipModal.find(".dialogtip-msg").html("文件上传成功");
                                $addRoletipModal.modal('show');
                                pageIndex.reset();
                            }
                            else {
                                $("#ajax_fail").find("h4").html(data.message);
                                $("#ajax_fail").modal("show")
                            }
                        }
                    });

                }
                });

        },
    };
    Utilitiy.init();
});