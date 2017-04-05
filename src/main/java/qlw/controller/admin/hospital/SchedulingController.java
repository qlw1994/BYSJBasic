package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.FixedschedulingManage;
import qlw.manage.NumberManage;
import qlw.manage.SchedulingManage;
import qlw.model.Fixedscheduling;
import qlw.model.Scheduling;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wiki on 2017/3/12.
 */
@Controller
@RequestMapping(value = "/admin/schedulings")
public class SchedulingController extends BaseController {
    @Autowired
    SchedulingManage schedulingManage;
    @Autowired
    NumberManage numberManage;
    @Autowired
    FixedschedulingManage fixedschedulingManage;

    /**
     * 排班列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listScheduling(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "100") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startDate, @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Scheduling scheduling = new Scheduling();
            if (request.getParameter("hospitalid") != null) {
                long hospitalid = Long.parseLong(request.getParameter("hospitalid"));
                scheduling.setHospitalid(hospitalid);
            }

            if (request.getParameter("departmentid") != null) {
                long departmentid = Long.parseLong(request.getParameter("departmentid"));
                scheduling.setDepartmentid(departmentid);
            }
            if (request.getParameter("doctorid") != null) {
                long doctorid = Long.parseLong(request.getParameter("doctorid"));
                scheduling.setDoctorid(doctorid);
            }
            //List<Date> dates = MyUtils.dateToWeek(new Date(), flag);
            result.put("total", schedulingManage.count(startDate, endDate, scheduling));
            result.put("data", schedulingManage.list(page, length, startDate, endDate, scheduling));

        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 排班管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(long doctorid, String doctorname, int pcode, int subcode, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("admin/hospital/scheduling");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss|EEE");

        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);

        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 添加排班
     *
     * @param scheduling
     * @return
     */
    @RequestMapping(value = "newScheduling")
    @ResponseBody
    public Map<String, Object> addScheduling(Scheduling scheduling, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            if (schedulingManage.haveSameScheduling(scheduling)) {
                rtnCode = ResultCode.ERROR;
                rtnMsg = "添加失败";
            } else {
                schedulingManage.save(scheduling);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "添加失败";
            rtnCode = ResultCode.ERROR;
        }

        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 排班查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "schedulingInfo", method = RequestMethod.POST)
    @ResponseBody
    public Scheduling getSchedulingInfo(Long id) {
        try {
            return schedulingManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改排班
     *
     * @return
     */
    @RequestMapping(value = "modScheduling", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateScheduling(Scheduling scheduling) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            schedulingManage.update(scheduling);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 删除排班
     *
     * @return
     */
    @RequestMapping(value = "delScheduling/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delScheduling(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            schedulingManage.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "删除失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }
    /**
     * 获取之后7天的排班
     *
     * @param doctorid
     * @return
     */
    @RequestMapping(value = "/getSchedulings")
    @ResponseBody
    public Map<String, Object> getNumbersnext7day(Long doctorid) {
        Map<String, Object> result = new HashMap<>();
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorid(doctorid);
        try {
            List<Scheduling> schedulingList= schedulingManage.listNext7Day(scheduling);
            result.put("total", schedulingList.size());
            result.put("data", schedulingList);
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 号源池生成 7天
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/generateScheduling")
    @ResponseBody
    public Map<String, Object> generateScheduling(Long doctorid, Long hospitalid, Long departmentid, Integer type, @RequestParam(value = "totalnumber", defaultValue = "50") Integer totalnumber) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "号源生成成功";
        Date nowDate = new Date();

        List<Date> dateList = new ArrayList<Date>();
        long ftime = nowDate.getTime();
        for (int i = 0; i <= 7; i++) {
            Date fdate = new Date();
            fdate.setTime(ftime + (i * 24 * 3600000));
            String fDateStr = MyUtils.SIMPLE_DATE_FORMAT.format(fdate);
            //周日 从0转换为7
            int fweek = fdate.getDay() == 0 ? 7 : fdate.getDay();

            List<Fixedscheduling> fixedschedulings = fixedschedulingManage.getByWeek(doctorid, fweek);
            //Fixedscheduling fixedscheduling = fixedschedulings.get(0);
            for (Fixedscheduling fixedscheduling : fixedschedulings) {
                if (fixedscheduling.getStatus() == 1) {
                    if (!schedulingManage.hasSame(fDateStr, fixedscheduling.getTimeflag())) {
                        Scheduling scheduling = new Scheduling();
                        scheduling.setDoctorid(doctorid);
                        scheduling.setDepartmentid(departmentid);
                        scheduling.setHospitalid(hospitalid);
                        scheduling.setDate(fDateStr);
                        scheduling.setStatus(1);
                        scheduling.setRegfee(fixedscheduling.getRegfee());
                        scheduling.setTimeflag(fixedscheduling.getTimeflag());
                        scheduling.setType(type);
                        scheduling.setTotalnumber(totalnumber);
                        scheduling.setAppointleftcount(totalnumber / 2);
                        scheduling.setOtherleftcount(totalnumber / 2);
                        schedulingManage.save(scheduling);
                    }
                    //List<Numbers> numberss = numberManage.getNumbersByDepartmentidAndtimeflagAndDate(departmentid, fDateStr, fixedscheduling.getTimeflag(), type);
                    //if (numberss.size() > 0) {
                    //    Numbers numbers = numberss.get(0);
                    //    numbers.setAppointleftcount(numbers.getAppointleftcount() + totalnumber / 2);
                    //    numbers.setOtherleftcount(numbers.getOtherleftcount() + totalnumber / 2);
                    //    numbers.setTotalamount(numbers.getTotalamount() + totalnumber);
                    //    numberManage.update(numbers);
                    //} else {
                    //    Numbers numbers = new Numbers();
                    //    numbers.setDate(fDateStr);
                    //    numbers.setStatus(1);
                    //    numbers.setDepartmentid(departmentid);
                    //    numbers.setHospitalid(hospitalid);
                    //    numbers.setAppointleftcount(totalnumber / 2);
                    //    numbers.setOtherleftcount(totalnumber / 2);
                    //    numbers.setTotalamount(totalnumber);
                    //    numbers.setTimeflag(fixedscheduling.getTimeflag());
                    //    numbers.setType(type);
                    //    numberManage.save(numbers);
                    //}
                } else {
                    int status = fixedscheduling.getStatus();
                    if (!schedulingManage.hasSame(fDateStr, fixedscheduling.getTimeflag())) {
                        Scheduling scheduling = new Scheduling();
                        scheduling.setDoctorid(doctorid);
                        scheduling.setDepartmentid(departmentid);
                        scheduling.setHospitalid(hospitalid);
                        scheduling.setDate(fDateStr);
                        scheduling.setStatus(status);
                        scheduling.setRegfee(fixedscheduling.getRegfee());
                        scheduling.setTimeflag(fixedscheduling.getTimeflag());
                        scheduling.setType(type);
                        scheduling.setTotalnumber(0);
                        scheduling.setAppointleftcount(0);
                        scheduling.setOtherleftcount(0);

                        schedulingManage.save(scheduling);
                    }
                }
            }
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;

    }

    /**
     * 号源池生成  每日生成一天
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/generateSchedulingAddOne")
    @ResponseBody
    public Map<String, Object> generateSchedulingAddOne(Long doctorid, Long hospitalid, Long departmentid, int type, @RequestParam(value = "totalnumber", defaultValue = "50") Integer totalnumber) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "号源生成成功";
        Date nowDate = new Date();

        List<Date> dateList = new ArrayList<Date>();
        long ftime = nowDate.getTime();
        Date fdate = new Date();
        fdate.setTime(ftime + (7 * 24 * 3600000));
        String fDateStr = MyUtils.SIMPLE_DATE_FORMAT.format(fdate);
        //周日 从0转换为7
        int fweek = fdate.getDay() == 0 ? 7 : fdate.getDay();

        List<Fixedscheduling> fixedschedulings = fixedschedulingManage.getByWeek(doctorid, fweek);
        //Fixedscheduling fixedscheduling = fixedschedulings.get(0);
        for (Fixedscheduling fixedscheduling : fixedschedulings) {
            if (fixedscheduling.getStatus() == 1) {
                if (!schedulingManage.hasSame(fDateStr, fixedscheduling.getTimeflag())) {
                    Scheduling scheduling = new Scheduling();
                    scheduling.setDoctorid(doctorid);
                    scheduling.setDepartmentid(departmentid);
                    scheduling.setHospitalid(hospitalid);
                    scheduling.setDate(fDateStr);
                    scheduling.setStatus(1);
                    scheduling.setRegfee(fixedscheduling.getRegfee());
                    scheduling.setTimeflag(fixedscheduling.getTimeflag());
                    scheduling.setType(type);
                    scheduling.setTotalnumber(totalnumber);
                    scheduling.setAppointleftcount(totalnumber / 2);
                    scheduling.setOtherleftcount(totalnumber / 2);
                    schedulingManage.save(scheduling);
                }
                //List<Numbers> numberss = numberManage.getNumbersByDepartmentidAndtimeflagAndDate(departmentid, fDateStr, fixedscheduling.getTimeflag(),type);
                //if (numberss.size() > 0) {
                //    Numbers numbers = numberss.get(0);
                //    numbers.setAppointleftcount(numbers.getAppointleftcount() + totalnumber / 2);
                //    numbers.setOtherleftcount(numbers.getOtherleftcount() + totalnumber / 2);
                //    numbers.setTotalamount(numbers.getTotalamount() + totalnumber);
                //    numberManage.update(numbers);
                //} else {
                //    Numbers numbers = new Numbers();
                //    numbers.setDate(fDateStr);
                //    numbers.setStatus(1);
                //    numbers.setDepartmentid(departmentid);
                //    numbers.setHospitalid(hospitalid);
                //    numbers.setAppointleftcount(totalnumber / 2);
                //    numbers.setOtherleftcount(totalnumber / 2);
                //    numbers.setTotalamount(totalnumber);
                //    numbers.setTimeflag(fixedscheduling.getTimeflag());
                //    numbers.setType(type);
                //    numberManage.save(numbers);
                //}
            } else {
                int status = fixedscheduling.getStatus();
                if (!schedulingManage.hasSame(fDateStr, fixedscheduling.getTimeflag())) {
                    Scheduling scheduling = new Scheduling();
                    scheduling.setDoctorid(doctorid);
                    scheduling.setDepartmentid(departmentid);
                    scheduling.setHospitalid(hospitalid);
                    scheduling.setDate(fDateStr);
                    scheduling.setStatus(status);
                    scheduling.setRegfee(fixedscheduling.getRegfee());
                    scheduling.setTimeflag(fixedscheduling.getTimeflag());
                    scheduling.setType(type);
                    scheduling.setTotalnumber(0);
                    scheduling.setAppointleftcount(0);
                    scheduling.setOtherleftcount(0);

                    schedulingManage.save(scheduling);
                }
            }
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;

    }
}
