package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.InspectionreportManage;
import qlw.manage.InspectitemManage;
import qlw.model.Inspectionreport;
import qlw.model.Inspectitems;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/18.
 */
@Controller
@RequestMapping(value = "/admin/inspectitems")
public class InspectitemController extends BaseController {
    @Autowired
    InspectionreportManage inspectionreportManage;
    @Autowired
    InspectitemManage inspectitemManage;

    /**
     * 药品检验项目列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listInspectitem(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long inspectionid = Long.parseLong(request.getParameter("inspectionid"));
            result.put("total", inspectitemManage.count(inspectionid));
            result.put("data", inspectitemManage.list(page, length, inspectionid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 药品检验项目管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Integer pcode, Integer subcode, long inspectionid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/inspectitem");
        request.getSession().setAttribute("inspectionid", inspectionid);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 药品检验项目管理首页跳转 就诊人入口
     *
     * @return
     */
    @RequestMapping(value = "/patientindex")
    public ModelAndView ViewPatientIndex(Integer pcode, Integer subcode, long inspectionid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/inspectitem");
        request.getSession().setAttribute("inspectionid", inspectionid);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }
    /**
     * 添加药品检验项目
     *
     * @param inspectitems
     * @return
     */
    @RequestMapping(value = "newInspectitem")
    @ResponseBody
    public Map<String, Object> addInspectitem(Inspectitems inspectitems, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            long inspectionid = inspectitems.getInspectionid();
            Inspectionreport inspectionreport = inspectionreportManage.getById(inspectionid);
            inspectionreport.setTotal(inspectionreport.getTotal() + 1);
            inspectionreportManage.update(inspectionreport);
            inspectitemManage.save(inspectitems);
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
     * 药品检验项目查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "inspectitemInfo", method = RequestMethod.POST)
    @ResponseBody
    public Inspectitems getInspectitemInfo(Long id) {
        try {
            return inspectitemManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改药品检验项目
     *
     * @return
     */
    @RequestMapping(value = "modInspectitem", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateInspectitem(Inspectitems inspectitems) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            inspectitemManage.update(inspectitems);
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
     * 删除药品检验项目
     *
     * @return
     */
    @RequestMapping(value = "delInspectitem/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delInspectitem(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Inspectitems inspectitems=inspectitemManage.getById(id);
            Inspectionreport inspectionreport = inspectionreportManage.getById(inspectitems.getInspectionid());
            inspectionreport.setTotal(inspectionreport.getTotal() - 1);
            inspectitemManage.delete(id);
            inspectionreportManage.update(inspectionreport);
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
