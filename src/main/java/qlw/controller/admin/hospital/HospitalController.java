package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.HospitalManage;
import qlw.model.Hospital;
import qlw.model.HospitalExample;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * Created by wiki on 2017/3/8.
 */
@Controller
@RequestMapping(value = "admin/hospitals")
public class HospitalController extends BaseController {
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
    public Map<String, Object> listHospitalLike(String name, @RequestParam(value = "province", defaultValue = "") String province, @RequestParam(value = "city", defaultValue = "") String city, @RequestParam(value = "area", defaultValue = "") String area) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", hospitalManage.countLike(name, province, city, area));
            result.put("data", hospitalManage.getLike(name, province, city, area));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 添加医院
     *
     * @param hospital
     * @return
     */
    @RequestMapping(value = "newHospital")
    @ResponseBody
    public Map<String, Object> addHospital(Hospital hospital, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Hospital hospitalTemp = hospitalManage.getByName(hospital.getName(), hospital.getProvince(), hospital.getCity(), hospital.getArea());

            Date nowDate = MyUtils.SIMPLE_DATE_FORMAT.parse(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            //
            if (hospitalTemp != null) {
                Date date = MyUtils.SIMPLE_DATE_FORMAT.parse(hospitalTemp.getEnddate());
                Calendar calendar = Calendar.getInstance();

                if (date.after(nowDate) || date.equals(nowDate)) {
                    calendar.setTime(date);
                    calendar.add(Calendar.YEAR, 3);
                    String datestr = MyUtils.SIMPLE_DATE_FORMAT.format(calendar.getTime());
                    hospitalTemp.setEnddate(datestr);
                } else {
                    calendar.setTime(nowDate);
                    calendar.add(Calendar.YEAR, 3);
                    String datestr = MyUtils.SIMPLE_DATE_FORMAT.format(calendar.getTime());
                    hospitalTemp.setEnddate(datestr);
                }
                hospitalTemp.setStatus(1);
                hospitalManage.update(hospitalTemp);
            } else {
                hospital.setStartdate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nowDate);
                calendar.add(Calendar.YEAR, 3);
                String datestr = MyUtils.SIMPLE_DATE_FORMAT.format(calendar.getTime());
                hospital.setEnddate(datestr);
                hospital.setStatus(1);
                hospitalManage.save(hospital);
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
     * 修改医院
     *
     * @return
     */
    @RequestMapping(value = "modHospital", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateHospital(Hospital hospital, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            hospital.setArea(request.getParameter("modarea"));
            hospital.setCity(request.getParameter("modcity"));
            hospital.setProvince(request.getParameter("modprovince"));
            hospitalManage.update(hospital);
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
     * 删除医院
     *
     * @return
     */
    @RequestMapping(value = "delHospital/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delHospital(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Hospital hospital = hospitalManage.getById(id);
            hospital.setEnddate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            hospital.setStatus(0);
            hospitalManage.update(hospital);
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
     * 医院首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(int pcode, int subcode, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("admin/hospital/hospital");
        mv.addObject("currentpage", 1);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        return mv;
    }

    /**
     * 检查是否有相同的医院名称
     *
     * @param name
     * @return @return 存在返回false 否则true
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(String name, @RequestParam(value = "province", defaultValue = "") String province, @RequestParam(value = "city", defaultValue = "") String city, @RequestParam(value = "area", defaultValue = "") String area) {
        boolean res = hospitalManage.haveSameName(name, province, city, area);
        return !res;
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
