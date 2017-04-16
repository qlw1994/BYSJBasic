package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.AlipayaccountManage;
import qlw.model.Alipayaccount;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/26.
 */
@Controller
@RequestMapping(value = "/admin/alipayaccounts")
public class AlipayaccountController extends BaseController {
    @Autowired
    AlipayaccountManage alipayaccountManage;

    /**
     * 支付宝账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listAlipayaccount(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length,String hospitalname, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", alipayaccountManage.count(hospitalname));
            result.put("data", alipayaccountManage.list(page, length,hospitalname));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 支付宝账号管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/alipayaccount");
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 添加支付宝账号
     *
     * @param alipayaccount
     * @return
     */
    @RequestMapping(value = "newAlipayaccount")
    @ResponseBody
    public Map<String, Object> addAlipayaccount(Alipayaccount alipayaccount, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            alipayaccountManage.save(alipayaccount);
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
     * 支付宝账号查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "alipayaccountInfo", method = RequestMethod.POST)
    @ResponseBody
    public Alipayaccount getAlipayaccountInfo(Long id) {
        try {
            return alipayaccountManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 支付宝账号查询  支付宝账号名称  医院编号
     *
     * @param
     * @return
     */
    @RequestMapping(value = "alipayaccountInfoByName")
    @ResponseBody
    public Alipayaccount getAlipayaccountInfoByName(String name,Long hospitalid) {
        try {
            return alipayaccountManage.getByName(name,hospitalid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 修改支付宝账号
     *
     * @return
     */
    @RequestMapping(value = "modAlipayaccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAlipayaccount(Alipayaccount alipayaccount) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            alipayaccountManage.update(alipayaccount);
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
     * 删除支付宝账号
     *
     * @return
     */
    @RequestMapping(value = "delAlipayaccount/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delAlipayaccount(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            alipayaccountManage.delete(id);
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
     * 检查是否有相同的支付宝账号名称
     *
     * @return 存在返回false 否则true
     */
    @RequestMapping(value = "/hasAlipay", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName( long hospitalid) {
        boolean res = alipayaccountManage.hasAlipay(hospitalid);
        return !res;
    }

    /**
     * 检查=支付宝账号名称是否存在
     *
     * @param name
     * @return 存在返回 否则flase
     */
    @RequestMapping(value = "/hasName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasName(String name,long hospitalid)
    {
        boolean res = alipayaccountManage.haveSameName(name, hospitalid);
        return res;
    }
}
