package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.SchedulingExMapper;
import qlw.model.Scheduling;
import qlw.model.SchedulingExample;
import qlw.model.Sysusers;
import qlw.util.MyUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by wiki on 2017/3/12.
 */
@Service
@Transactional(readOnly = true)
public class SchedulingManage extends BaseManage {
    @Autowired
    SchedulingExMapper schedulingExMapper;
    private Long init = 0L;

    /**
     * 排班列表
     *
     * @param pageNumber
     * @param pageSize
     * @param scheduling
     * @return
     */
    public List<Scheduling> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Scheduling scheduling) {
        SchedulingExample example = new SchedulingExample();
        example.setOrderByClause(getPage("date asc,timeflag asc", pageNumber, pageSize));
        SchedulingExample.Criteria criteria = example.createCriteria();
        if (scheduling.getHospitalid() != null && !scheduling.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(scheduling.getHospitalid());
        }
        if (scheduling.getDoctorid() != null && !scheduling.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(scheduling.getDoctorid());
        }
        if (scheduling.getDepartmentid() != null && !scheduling.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(scheduling.getDepartmentid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate = startDate += " 00:00:00";
            endDate = endDate += " 23:59:59";
            criteria.andDateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate = endDate += " 23:59:59";
            criteria.andDateLessThanOrEqualTo(endDate);
        } else {
            startDate = startDate += " 00:00:00";
            criteria.andDateGreaterThanOrEqualTo(startDate);
        }
        return schedulingExMapper.selectByExample(example);
    }

    /**
     * 排班列表  后7天
     *
     * @param scheduling
     * @return
     */
    public List<Scheduling> listNext7Day(Scheduling scheduling) {
        SchedulingExample example = new SchedulingExample();
        example.setOrderByClause("date, timeflag");
        SchedulingExample.Criteria criteria = example.createCriteria();
        if (scheduling.getHospitalid() != null && !scheduling.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(scheduling.getHospitalid());
        }
        if (scheduling.getDoctorid() != null && !scheduling.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(scheduling.getDoctorid());
        }
        if (scheduling.getDepartmentid() != null && !scheduling.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(scheduling.getDepartmentid());
        }
        Date now = new Date();
        String nowDateStr = MyUtils.SIMPLE_DATE_FORMAT.format(now);
        Long fTime = now.getTime() + (7 * 24 * 3600000);
        Date end = new Date();
        end.setTime(fTime);
        String endDateStr = MyUtils.SIMPLE_DATE_FORMAT.format(end);
        criteria.andDateLessThanOrEqualTo(endDateStr);
        criteria.andDateGreaterThan(nowDateStr);
        return schedulingExMapper.selectByExample(example);
    }

    /**
     * 排班列表统计
     *
     * @param startDate
     * @param endDate
     * @param scheduling
     * @return
     */
    public Integer count(String startDate, String endDate, Scheduling scheduling) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        if (scheduling.getHospitalid() != null && !scheduling.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(scheduling.getHospitalid());
        }
        if (scheduling.getDoctorid() != null && !scheduling.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(scheduling.getDoctorid());
        }
        if (scheduling.getDepartmentid() != null && !scheduling.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(scheduling.getDepartmentid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate = startDate += " 00:00:00";
            endDate = endDate += " 23:59:59";
            criteria.andDateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate = endDate += " 23:59:59";
            criteria.andDateLessThanOrEqualTo(endDate);
        } else {
            startDate = startDate += " 00:00:00";
            criteria.andDateGreaterThanOrEqualTo(startDate);
        }
        return schedulingExMapper.countByExample(example);
    }

    /**
     * 当前日期是否已经排班
     *
     * @param scheduling
     * @return 有返回true 否则false
     */
    public Boolean haveSameScheduling(Scheduling scheduling) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(scheduling.getDate());
        criteria.andTimeflagEqualTo(scheduling.getTimeflag());
        if (scheduling.getHospitalid() != null && !scheduling.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(scheduling.getHospitalid());
        }
        if (scheduling.getDoctorid() != null && !scheduling.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(scheduling.getDoctorid());
        }
        if (scheduling.getDepartmentid() != null && !scheduling.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(scheduling.getDepartmentid());
        }
        List<Scheduling> schedulings = schedulingExMapper.selectByExample(example);
        if (schedulings.size() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 根据id获取排班
     *
     * @param id
     * @return
     */
    public Scheduling getById(Long id) {
        return schedulingExMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据date 和 timeflag doctorid获取排班
     *
     * @return
     */
    public Scheduling getByDateAndTimeflagAndDoctorid(String date, Integer timeflag, Long doctorid) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        criteria.andTimeflagEqualTo(timeflag);
        criteria.andDateEqualTo(date);
        criteria.andDoctoridEqualTo(doctorid);
        return schedulingExMapper.selectByExample(example).get(0);
    }

    /**
     * 根据date 和 tiemflag  （医生类型）获取排班
     *
     * @param date
     * @param timeflag
     * @return
     */
    public List<Scheduling> getByDateAndTimeflag(String date, Integer timeflag, Integer type) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andTimeflagEqualTo(timeflag);
        criteria.andDateEqualTo(date);
        List<Scheduling> schedulings = schedulingExMapper.selectByExample(example);
        return schedulings;
    }

    /**
     * 修改排班信息
     *
     * @param scheduling
     * @return
     */
    @Transactional
    public Integer update(Scheduling scheduling) {
        return schedulingExMapper.updateByPrimaryKeySelective(scheduling);
    }


    /**
     * 删除排班
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(Long id) {
        int i = schedulingExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存排班
     *
     * @param scheduling
     * @return
     */
    @Transactional
    public Scheduling save(Scheduling scheduling) {
        long i = schedulingExMapper.insert(scheduling);
        if (i > 0) {
            return scheduling;
        }
        return null;
    }

    /**
     * 保存排班 返回Id
     *
     * @param scheduling
     * @return
     */
    @Transactional
    public Scheduling saveBackiId(Scheduling scheduling) {
        long i = schedulingExMapper.insertBackId(scheduling);
        if (i > 0) {
            return scheduling;
        }
        return null;
    }

    /**
     * 判断是否已经存在相应的排班
     *
     * @param date
     * @return 是返回true 否则返回false
     */
    public boolean hasSame(String date, int timeflag) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        criteria.andTimeflagEqualTo(timeflag);
        List<Scheduling> schedulings = schedulingExMapper.selectByExample(example);
        if (schedulings.size() > 0) {
            return true;
        }
        return false;
    }
}
