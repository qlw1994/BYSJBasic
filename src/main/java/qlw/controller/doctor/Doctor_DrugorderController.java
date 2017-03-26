package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DrugorderManage;
import qlw.manage.DrugorderdetailManage;
import qlw.model.Drugorder;
import qlw.model.Drugorderdetail;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wiki on 2017/3/25.
 */
@Controller
@RequestMapping(value = "/doctor/drugorders")
public class Doctor_DrugorderController extends BaseController {
    @Autowired
    DrugorderManage drugorderManage;
    @Autowired
    DrugorderdetailManage drugorderdetailManage;

    /**
     * 药品订单列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrugorder(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startDate, @RequestParam(value = "endDate", defaultValue = "") String endDate, long patientid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //long patientid = (Long) request.getSession().getAttribute("patientid");
            Drugorder drugorder = new Drugorder();
            drugorder.setPatientid(patientid);
            result.put("total", drugorderManage.count(startDate, endDate, drugorder));
            result.put("data", drugorderManage.list(page, length, startDate, endDate, drugorder));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }
    /**
     * 药品订单列表数据源   当日
     *
     * @return
     */
    @RequestMapping(value = "listToday", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrugorderToday(Long patientid,Long doctorid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String dateStr=MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
            Drugorder drugorder = new Drugorder();
            drugorder.setPatientid(patientid);
            drugorder.setDoctorid(doctorid);
            result.put("total", drugorderManage.countOneDay(dateStr, drugorder));
            result.put("data", drugorderManage.listOneDay(dateStr, drugorder));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }
    /**
     * 药品订单管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(int pcode, int subcode, long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("doctor/drugorder");
        if (request.getParameter("uid") != null) {
            request.getSession().setAttribute("uid", Long.parseLong(request.getParameter("uid")));
        }
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }
    

    /**
     * 添加药品订单
     *
     * @param drugorder
     * @return
     */
    @RequestMapping(value = "newDrugorder")
    @ResponseBody
    public Map<String, Object> addDrugorder(Drugorder drugorder, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            drugorder.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            drugorderManage.saveBackId(drugorder);
            List<Drugorderdetail> drugorderdetails = drugorder.getDrugorderdetails();
            for (Drugorderdetail d : drugorderdetails) {
                d.setDrugorderid(drugorder.getId());
                drugorderdetailManage.save(d);
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
     * 药品订单查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "drugorderInfo", method = RequestMethod.POST)
    @ResponseBody
    public Drugorder getDrugorderInfo(Long id) {
        try {
            return drugorderManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改药品订单
     *
     * @return
     */
    @RequestMapping(value = "modDrugorder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDrugorder(Drugorder drugorder) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            drugorderManage.update(drugorder);
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
     * 删除药品订单
     *
     * @return
     */
    @RequestMapping(value = "delDrugorder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDrugorder(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Drugorder drugorder = drugorderManage.getById(id);
            drugorder.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            drugorderManage.update(drugorder);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "删除失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

}
