package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DoctorExMapper;
import qlw.mapper.ex.InspectionreportExMapper;
import qlw.model.Doctor;
import qlw.model.Inspectionreport;
import qlw.model.InspectionreportExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/13.
 */
@Service
@Transactional(readOnly = true)
public class InspectionreportManage extends BaseManage {
    @Autowired
    InspectionreportExMapper inspectionreportExMapper;
    @Autowired
    DoctorExMapper doctorExMapper;
    private Long init = 0L;

    /**
     * 检查表列表
     *
     * @param pageNumber
     * @param pageSize
     * @param inspectionreport
     * @return
     */
    public List<Inspectionreport> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Inspectionreport inspectionreport) {
        InspectionreportExample example = new InspectionreportExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        InspectionreportExample.Criteria criteria = example.createCriteria();
        if (inspectionreport.getDoctorid() != null && !inspectionreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(inspectionreport.getDoctorid());
        }
        if (inspectionreport.getHospitalid() != null && !inspectionreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(inspectionreport.getHospitalid());
        }
        if (inspectionreport.getDepartmentid() != null && !inspectionreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(inspectionreport.getDepartmentid());
        }
        if (inspectionreport.getPatientid() != null && inspectionreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(inspectionreport.getPatientid());
        }
        if (inspectionreport.getAuditorid() != null && inspectionreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(inspectionreport.getAuditorid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andInspecttimeBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andInspecttimeLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andInspecttimeGreaterThanOrEqualTo(startDate);
        }
        criteria.andDeletedateIsNotNull();
        List<Inspectionreport> inspectionreports = inspectionreportExMapper.selectByExample(example);
        for (int i = 0; i < inspectionreports.size(); i++) {
            long auditorid = inspectionreports.get(i).getAuditorid();
            inspectionreports.get(i).setAuditor(doctorExMapper.selectByPrimaryKey(auditorid));
        }
        return inspectionreports;
    }

    /**
     * 检查表列表一日
     *
     * @param pageNumber
     * @param pageSize
     * @param inspectionreport
     * @return
     */
    public List<Inspectionreport> listOneDay(Integer pageNumber, Integer pageSize, String date, Inspectionreport inspectionreport) {
        InspectionreportExample example = new InspectionreportExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        InspectionreportExample.Criteria criteria = example.createCriteria();
        if (inspectionreport.getDoctorid() != null && !inspectionreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(inspectionreport.getDoctorid());
        }
        if (inspectionreport.getHospitalid() != null && !inspectionreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(inspectionreport.getHospitalid());
        }
        if (inspectionreport.getDepartmentid() != null && !inspectionreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(inspectionreport.getDepartmentid());
        }
        if (inspectionreport.getPatientid() != null && inspectionreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(inspectionreport.getPatientid());
        }
        if (inspectionreport.getAuditorid() != null && inspectionreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(inspectionreport.getAuditorid());
        }
        criteria.andDateEqualTo(date);
        criteria.andDeletedateIsNotNull();
        return inspectionreportExMapper.selectByExample(example);
    }

    /**
     * 检查表统计
     *
     * @param startDate
     * @param endDate
     * @param inspectionreport
     * @return
     */
    public Integer count(String startDate, String endDate, Inspectionreport inspectionreport) {
        InspectionreportExample example = new InspectionreportExample();
        InspectionreportExample.Criteria criteria = example.createCriteria();
        if (inspectionreport.getDoctorid() != null && !inspectionreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(inspectionreport.getDoctorid());
        }
        if (inspectionreport.getHospitalid() != null && !inspectionreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(inspectionreport.getHospitalid());
        }
        if (inspectionreport.getDepartmentid() != null && !inspectionreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(inspectionreport.getDepartmentid());
        }
        if (inspectionreport.getPatientid() != null && inspectionreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(inspectionreport.getPatientid());
        }
        if (inspectionreport.getAuditorid() != null && inspectionreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(inspectionreport.getAuditorid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andInspecttimeBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andInspecttimeLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andInspecttimeGreaterThanOrEqualTo(startDate);
        }
        criteria.andDeletedateIsNotNull();
        return inspectionreportExMapper.countByExample(example);
    }

    /**
     * 检查表统计 一日
     *
     * @param date
     * @param inspectionreport
     * @return
     */
    public Integer countOneDay(String date, Inspectionreport inspectionreport) {
        InspectionreportExample example = new InspectionreportExample();
        InspectionreportExample.Criteria criteria = example.createCriteria();
        if (inspectionreport.getDoctorid() != null && !inspectionreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(inspectionreport.getDoctorid());
        }
        if (inspectionreport.getHospitalid() != null && !inspectionreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(inspectionreport.getHospitalid());
        }
        if (inspectionreport.getDepartmentid() != null && !inspectionreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(inspectionreport.getDepartmentid());
        }
        if (inspectionreport.getPatientid() != null && inspectionreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(inspectionreport.getPatientid());
        }
        if (inspectionreport.getAuditorid() != null && inspectionreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(inspectionreport.getAuditorid());
        }
        criteria.andDateEqualTo(date);
        criteria.andDeletedateIsNotNull();
        return inspectionreportExMapper.countByExample(example);
    }


    /**
     * 根据id获取检查表
     *
     * @param id
     * @return
     */
    public Inspectionreport getById(Long id) {
        return inspectionreportExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改检查表信息
     *
     * @param inspectionreport
     * @return
     */
    public Integer update(Inspectionreport inspectionreport) {
        return inspectionreportExMapper.updateByPrimaryKeySelective(inspectionreport);
    }


    /**
     * 删除检查表
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = inspectionreportExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存检查表
     *
     * @param inspectionreport
     * @return
     */
    public Inspectionreport save(Inspectionreport inspectionreport) {
        long i = inspectionreportExMapper.insert(inspectionreport);
        if (i > 0) {
            return inspectionreport;
        }
        return null;
    }

    /**
     * 保存检查表
     *
     * @param inspectionreport
     * @return
     */
    public Inspectionreport saveBackId(Inspectionreport inspectionreport) {
        long i = inspectionreportExMapper.insertBackId(inspectionreport);
        if (i > 0) {
            return inspectionreport;
        }
        return null;
    }
}
