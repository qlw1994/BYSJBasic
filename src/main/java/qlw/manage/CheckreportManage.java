package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.CheckreportMapper;
import qlw.model.Checkreport;
import qlw.model.CheckreportExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/13.
 */
@Service
@Transactional(readOnly = true)
public class CheckreportManage extends BaseManage {
    @Autowired
    CheckreportMapper checkreportMapper;
    private Long init = 0L;

    /**
     * 检查表列表
     *
     * @param pageNumber
     * @param pageSize
     * @param checkreport
     * @return
     */
    public List<Checkreport> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Checkreport checkreport) {
        CheckreportExample example = new CheckreportExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        CheckreportExample.Criteria criteria = example.createCriteria();
        if (checkreport.getDoctorid() != null && !checkreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(checkreport.getDoctorid());
        }
        if (checkreport.getHospitalid() != null && !checkreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(checkreport.getHospitalid());
        }
        if (checkreport.getDepartmentid() != null && !checkreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(checkreport.getDepartmentid());
        }
        if (checkreport.getPatientid() != null && checkreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(checkreport.getPatientid());
        }
        if (checkreport.getAuditorid() != null && checkreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(checkreport.getAuditorid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andChecktimeBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andChecktimeLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andChecktimeGreaterThanOrEqualTo(startDate);
        }
        criteria.andDeletedateIsNotNull();
        return checkreportMapper.selectByExample(example);
    }

    /**
     * 检查表列表一日
     *
     * @param pageNumber
     * @param pageSize
     * @param checkreport
     * @return
     */
    public List<Checkreport> listOneDay(Integer pageNumber, Integer pageSize, String date, Checkreport checkreport) {
        CheckreportExample example = new CheckreportExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        CheckreportExample.Criteria criteria = example.createCriteria();
        if (checkreport.getDoctorid() != null && !checkreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(checkreport.getDoctorid());
        }
        if (checkreport.getHospitalid() != null && !checkreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(checkreport.getHospitalid());
        }
        if (checkreport.getDepartmentid() != null && !checkreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(checkreport.getDepartmentid());
        }
        if (checkreport.getPatientid() != null && checkreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(checkreport.getPatientid());
        }
        if (checkreport.getAuditorid() != null && checkreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(checkreport.getAuditorid());
        }
        criteria.andDateEqualTo(date);
        criteria.andDeletedateIsNotNull();
        return checkreportMapper.selectByExample(example);
    }

    /**
     * 检查表统计
     *
     * @param startDate
     * @param endDate
     * @param checkreport
     * @return
     */
    public Integer count(String startDate, String endDate, Checkreport checkreport) {
        CheckreportExample example = new CheckreportExample();
        CheckreportExample.Criteria criteria = example.createCriteria();
        if (checkreport.getDoctorid() != null && !checkreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(checkreport.getDoctorid());
        }
        if (checkreport.getHospitalid() != null && !checkreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(checkreport.getHospitalid());
        }
        if (checkreport.getDepartmentid() != null && !checkreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(checkreport.getDepartmentid());
        }
        if (checkreport.getPatientid() != null && checkreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(checkreport.getPatientid());
        }
        if (checkreport.getAuditorid() != null && checkreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(checkreport.getAuditorid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andChecktimeBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andChecktimeLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andChecktimeGreaterThanOrEqualTo(startDate);
        }
        criteria.andDeletedateIsNotNull();
        return checkreportMapper.countByExample(example);
    }

    /**
     * 检查表统计 一日
     *
     * @param date
     * @param checkreport
     * @return
     */
    public Integer countOneDay(String date, Checkreport checkreport) {
        CheckreportExample example = new CheckreportExample();
        CheckreportExample.Criteria criteria = example.createCriteria();
        if (checkreport.getDoctorid() != null && !checkreport.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(checkreport.getDoctorid());
        }
        if (checkreport.getHospitalid() != null && !checkreport.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(checkreport.getHospitalid());
        }
        if (checkreport.getDepartmentid() != null && !checkreport.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(checkreport.getDepartmentid());
        }
        if (checkreport.getPatientid() != null && checkreport.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(checkreport.getPatientid());
        }
        if (checkreport.getAuditorid() != null && checkreport.getAuditorid().equals(init)) {
            criteria.andAuditoridEqualTo(checkreport.getAuditorid());
        }
        criteria.andDeletedateIsNotNull();
        criteria.andDateEqualTo(date);
        return checkreportMapper.countByExample(example);
    }


    /**
     * 根据id获取检查表
     *
     * @param id
     * @return
     */
    public Checkreport getById(Long id) {
        return checkreportMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改检查表信息
     *
     * @param checkreport
     * @return
     */
    public Integer update(Checkreport checkreport) {
        return checkreportMapper.updateByPrimaryKeySelective(checkreport);
    }


    /**
     * 删除检查表
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = checkreportMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存检查表
     *
     * @param checkreport
     * @return
     */
    public Checkreport save(Checkreport checkreport) {
        long i = checkreportMapper.insert(checkreport);
        if (i > 0) {
            return checkreport;
        }
        return null;
    }
}
