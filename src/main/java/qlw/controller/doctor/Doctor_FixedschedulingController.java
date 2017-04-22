package qlw.controller.doctor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.FixedschedulingManage;
import qlw.model.Fixedscheduling;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wiki on 2017/3/26.
 */
@Controller
@RequestMapping(value = "/doctor/fixedschedulings")
public class Doctor_FixedschedulingController extends BaseController {
    @Autowired
    FixedschedulingManage fixedschedulingManage;

    /**
     * 固定排班列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listFixedscheduling(Long doctorid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Fixedscheduling> fixedschedulings = fixedschedulingManage.list(doctorid);
            //List<List<Integer>> lists = new ArrayList<>();
            //int[][] timeflag = new int[3][8];
            //if (fixedschedulings != null) {
            //    for (Fixedscheduling f : fixedschedulings) {
            //        timeflag[f.getTimeflag()][f.getWeek()] = f.getStatus();
            //    }
            //    for (int j = 1; j < 3; j++) {
            //        List<Integer> temp = new ArrayList<>();
            //        for (int i = 1; i < 8; i++) {
            //            temp.add(timeflag[j][i]);
            //        }
            //        lists.add(temp);
            //    }
            //} else {
            //    for (int j = 1; j < 3; j++) {
            //        List<Integer> temp = new ArrayList<>();
            //        for (int i = 1; i < 8; i++) {
            //            temp.add(0);
            //        }
            //        lists.add(temp);
            //    }
            //}
            result.put("total", fixedschedulings.size());
            result.put("data", fixedschedulings);
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 固定排班管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Integer pcode, Integer subcode,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("doctor/fixedscheduling");
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 固定排班文件上传
     *
     * @return
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fileupload(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "上传成功";
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = new Date().getTime() + ".csv";
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String[] FILE_HEADER = {"挂号费", "时间", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fixedschedulingManage.deleteByDoctorid((Long) request.getSession().getAttribute("doctorid"));
        String FILE_NAME = path + "/" + fileName;
        // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (Reader in = new FileReader(FILE_NAME)) {
            Iterable<CSVRecord> records = format.parse(in);
            String regfee = "";
            String date = MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
            for (CSVRecord record : records) {
                regfee = record.get("挂号费").trim();
                int timeflag = record.get("时间").equals("上午") ? 1 : 2;
                int status1 = Integer.parseInt(record.get("星期一"));
                int status2 = Integer.parseInt(record.get("星期二"));
                int status3 = Integer.parseInt(record.get("星期三"));
                int status4 = Integer.parseInt(record.get("星期四"));
                int status5 = Integer.parseInt(record.get("星期五"));
                int status6 = Integer.parseInt(record.get("星期六"));
                int status7 = Integer.parseInt(record.get("星期日"));
                Fixedscheduling fixedscheduling = new Fixedscheduling();
                fixedscheduling.setDoctorid((Long) request.getSession().getAttribute("doctorid"));
                fixedscheduling.setDepartmentid((Long) request.getSession().getAttribute("departmentid"));
                fixedscheduling.setHospitalid((Long) request.getSession().getAttribute("hospitalid"));
                fixedscheduling.setTimeflag(timeflag);
                fixedscheduling.setRegfee(new BigDecimal(regfee));
                fixedscheduling.setDate(date);
                fixedscheduling.setStatus(status1);
                fixedscheduling.setWeek(1);
                fixedschedulingManage.save(fixedscheduling);
                fixedscheduling.setStatus(status2);
                fixedscheduling.setWeek(2);
                fixedschedulingManage.save(fixedscheduling);
                fixedscheduling.setStatus(status3);
                fixedscheduling.setWeek(3);
                fixedschedulingManage.save(fixedscheduling);
                fixedscheduling.setStatus(status4);
                fixedscheduling.setWeek(4);
                fixedschedulingManage.save(fixedscheduling);
                fixedscheduling.setStatus(status5);
                fixedscheduling.setWeek(5);
                fixedschedulingManage.save(fixedscheduling);
                fixedscheduling.setStatus(status6);
                fixedscheduling.setWeek(6);
                fixedschedulingManage.save(fixedscheduling);
                fixedscheduling.setStatus(status7);
                fixedscheduling.setWeek(7);
                fixedschedulingManage.save(fixedscheduling);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        targetFile.delete();
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 添加固定排班
     *
     * @param fixedscheduling
     * @return
     */
    @RequestMapping(value = "newFixedscheduling")
    @ResponseBody
    public Map<String, Object> addFixedscheduling(Fixedscheduling fixedscheduling, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            fixedschedulingManage.save(fixedscheduling);
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
     * 固定排班查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "fixedschedulingInfo", method = RequestMethod.POST)
    @ResponseBody
    public Fixedscheduling getFixedschedulingInfo(Long id) {
        try {
            return fixedschedulingManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 修改固定排班
     *
     * @return
     */
    @RequestMapping(value = "modFixedscheduling", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateFixedscheduling(Fixedscheduling fixedscheduling) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            fixedschedulingManage.update(fixedscheduling);
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
     * 删除固定排班
     *
     * @return
     */
    @RequestMapping(value = "delFixedscheduling/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delFixedscheduling(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            fixedschedulingManage.delete(id);
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
