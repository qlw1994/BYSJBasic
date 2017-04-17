package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DrugManage;
import qlw.model.Drug;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/8.
 */
@Controller
@RequestMapping(value = "/admin/drugs")
public class DrugController extends BaseController {
    @Autowired
    DrugManage drugManage;

    /**
     * 药品列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrug(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long hospitalid =  Long.parseLong(request.getParameter("hospitalid"));
            result.put("total", drugManage.count(hospitalid));
            result.put("data", drugManage.list(page, length, hospitalid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 药品管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Long hospitalid,String hospitalname,Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/drug");
        request.getSession().setAttribute("hospitalid", hospitalid);
        request.getSession().setAttribute("hospitalname", hospitalname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 相似药品名称列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/listDrugLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrugLike(String name, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long hospitalid =  Long.parseLong(request.getParameter("hospitalid"));
            result.put("total", drugManage.countLike(name, hospitalid));
            result.put("data", drugManage.getLike(name, hospitalid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 添加药品
     *
     * @param drug
     * @return
     */
    @RequestMapping(value = "newDrug")
    @ResponseBody
    public Map<String, Object> addDrug(Drug drug, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Drug drugTemp = drugManage.getByName(drug.getName(),drug.getHospitalid());
            if (drugTemp != null) {
                drugTemp.setDeletedate(null);
                drugManage.delete(drugTemp.getId());
                drugManage.save(drugTemp);
            } else {
                drug.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                drugManage.save(drug);
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
     * 药品查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "drugInfo", method = RequestMethod.POST)
    @ResponseBody
    public Drug getDrugInfo(Long id) {
        try {
            return drugManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 药品查询  药品名称  医院编号
     *
     * @param
     * @return
     */
    @RequestMapping(value = "drugInfoByName")
    @ResponseBody
    public Drug getDrugInfoByName(String name,Long hospitalid) {
        try {
            return drugManage.getByName(name,hospitalid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 修改药品
     *
     * @return
     */
    @RequestMapping(value = "modDrug", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDrug(Drug drug) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            drugManage.update(drug);
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
     * 删除药品
     *
     * @return
     */
    @RequestMapping(value = "delDrug/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDrug(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Drug drug = drugManage.getById(id);
            drug.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            drugManage.update(drug);
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
     * 检查是否有相同的药品名称
     *
     * @param name
     * @return 存在返回false 否则true
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(String name, long hospitalid) {
        boolean res = drugManage.haveSameName(name, hospitalid);
        return !res;
    }

    /**
     * 检查=药品名称是否存在
     *
     * @param name
     * @return 存在返回 否则flase
     */
    @RequestMapping(value = "/hasName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasName(String name,long hospitalid)
    {
        boolean res = drugManage.haveSameName(name, hospitalid);
        return res;
    }
}
