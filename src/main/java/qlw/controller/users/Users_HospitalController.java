package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.HospitalManage;
import qlw.model.Hospital;
import qlw.model.HospitalExample;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/30.
 */
@Controller
@RequestMapping(value = "/user/hospitals")
public class Users_HospitalController extends BaseController {
    @Autowired
    HospitalManage hospitalManage;

    /**
     * 医院列表数据源
     *
     * @returns
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listHospital(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "province", defaultValue = "") String province, @RequestParam(value = "city", defaultValue = "") String city, @RequestParam(value = "area", defaultValue = "") String area) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", hospitalManage.count(province, city, area));
            result.put("data", hospitalManage.list(page, length, province, city, area));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }


    @RequestMapping(value = "/listLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listHospitalLike(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "length", required = false) Integer length, String name, @RequestParam(value = "province", defaultValue = "") String province, @RequestParam(value = "city", defaultValue = "") String city, @RequestParam(value = "area", defaultValue = "") String area) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", hospitalManage.countLike(name, province, city, area));
            result.put("data", hospitalManage.getLike(page, length, name, province, city, area));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 医院查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "hospitalInfo", method = RequestMethod.POST)
    @ResponseBody
    public Hospital getHospitalInfo(Long id) {
        try {
            return hospitalManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 医院查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "hospitalInfoByName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getHospitalInfoByName(String name) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "查询成功";
        try {
            result.put("data", hospitalManage.getByName(name, null, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "查询失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 医院授权检查
     */
    @RequestMapping(value = "/reflashHospital")
    @ResponseBody
    private Map<String, Object> reflashHospital() {
        HospitalExample example = new HospitalExample();
        HospitalExample.Criteria criteria = example.createCriteria();
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "刷新成功";
        try {
            Date nowDate = MyUtils.SIMPLE_DATE_FORMAT.parse(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            criteria.andEnddateLessThan(MyUtils.SIMPLE_DATE_FORMAT.format(nowDate));
            Hospital hospital = new Hospital();
            hospital.setStatus(0);
            hospitalManage.updateByExampleSelective(hospital, example);
        } catch (ParseException e) {
            e.printStackTrace();
            rtnMsg = "刷新失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }
}
