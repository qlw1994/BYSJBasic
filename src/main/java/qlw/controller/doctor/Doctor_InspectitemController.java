package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.InspectitemManage;
import qlw.model.Inspectitems;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/doctor/inspectitems")
public class Doctor_InspectitemController extends BaseController{
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
    public ModelAndView View(Integer pcode, Integer subcode, Long inspectionid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("doctor/inspectitem");
        request.getSession().setAttribute("inspectionid", inspectionid);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
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

}
