package qlw.controller.admin.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.SysuserManage;
import qlw.model.Sysusers;
import qlw.util.MD5Utils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/2.
 */
@Controller
@RequestMapping(value = "/admin/sysusers")
public class SysusersController extends BaseController {
    @Autowired
    SysuserManage sysuserManage;

    /**
     * 账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listSysuser(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", sysuserManage.count());
            result.put("data", sysuserManage.list(page, length));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/listSysusersLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listSysusersLike(String account) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", sysuserManage.countLike(account));
            result.put("data", sysuserManage.getSysusersLike(account));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 添加帐号
     *
     * @param sysusertable
     * @return
     */
    @RequestMapping(value = "newSysuser")
    @ResponseBody
    public Map<String, Object> addSysuser(Sysusers sysusertable, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Sysusers sysusers = (Sysusers) request.getSession().getAttribute("sysuser");
        if (!sysusers.getPower().equals(new Integer(0))) {
            rtnMsg = "你没有权限操作";
            rtnCode = ResultCode.AUTHORITY_FORBID;

        } else {
            try {
                if (this.hasSameName(sysusertable.getAccount())) {
                    sysusertable.setCreatedate(simpleDateFormat.format(new Date()));
                    sysusertable.setPassword(MD5Utils.getMD5(sysusertable.getPassword()));
                    sysuserManage.save(sysusertable);
                } else {
                    rtnMsg = "管理员名已经存在";
                    rtnCode = ResultCode.ERROR;
                }

            } catch (Exception e) {
                e.printStackTrace();
                rtnMsg = "添加失败";
                rtnCode = ResultCode.ERROR;
            }
        }

        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 帐号查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "sysuserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Sysusers getSysuserInfo(Long id) {
        try {
            return sysuserManage.getSysusersById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改账号
     *
     * @return
     */
    @RequestMapping(value = "modSysuser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSysuser(Sysusers sysusers, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        Sysusers sysusersCheck = (Sysusers) request.getSession().getAttribute("sysuser");
        if (!sysusersCheck.getPower().equals(new Integer(0))) {
            rtnMsg = "你没有权限操作";
            rtnCode = ResultCode.AUTHORITY_FORBID;

        } else {
            try {
                sysuserManage.updateSysusers(sysusers);
            } catch (Exception e) {
                e.printStackTrace();
                rtnMsg = "修改失败";
                rtnCode = ResultCode.ERROR;
            }
        }

        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 删除账号
     *
     * @return
     */
    @RequestMapping(value = "delSysuser/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delSysuser(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        Sysusers sysusers = (Sysusers) request.getSession().getAttribute("sysuser");
        if (!sysusers.getPower().equals(new Integer(0))) {
            rtnMsg = "你没有权限操作";
            rtnCode = ResultCode.AUTHORITY_FORBID;

        } else {
            try {
                sysuserManage.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
                rtnMsg = "删除失败";
                rtnCode = ResultCode.ERROR;
            }
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 管理员管理首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/sysuseraccount");
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 检查是否有相同的账号名称
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(String account) {
        Sysusers sysusers = sysuserManage.getSysusersByAccount(account);
        if (sysusers == null) {
            return true;
        }
        return false;
    }

    /**
     * 检查旧密码是否相同
     *
     * @param id
     * @param password
     * @return 相同返回false 否则true
     */
    @RequestMapping(value = "/sameOddPassword", method = RequestMethod.POST)
    @ResponseBody
    public Boolean sameOddPassword(Long id, String password) {
        //password = MD5Utils.getMD5(password);
        return sysuserManage.sameOddPassword(id, password);
    }

    /**
     * 更新密码
     *
     * @param newPassword
     * @param sysusers
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public int updatePassword(String newPassword, Sysusers sysusers) {
        //newPassword = MD5Utils.getMD5(newPassword);
        sysusers.setPassword(newPassword);
        return sysuserManage.updateSysusers(sysusers);
    }
    /**
     * 重置密码
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPWD/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetPWD(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "密码重置成功";
        try {
            Sysusers sysusers = sysuserManage.getSysusersById(id);
            //sysusers.setPassword(MD5Utils.getMD5("123456"));
            sysuserManage.updateSysusers(sysusers);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "密码重置失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }
}
