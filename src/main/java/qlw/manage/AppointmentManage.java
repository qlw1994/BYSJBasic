package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.AppointmentMapper;
import qlw.mapper.NumbersMapper;
import qlw.mapper.SchedulingMapper;
import qlw.mapper.ex.SchedulingExMapper;
import qlw.model.Appointment;
import qlw.model.AppointmentExample;
import qlw.model.Numbers;
import qlw.model.Scheduling;
import qlw.util.MyUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wiki on 2017/3/13.
 */
@Service
@Transactional(readOnly = true)
public class AppointmentManage extends BaseManage {
    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    NumberManage numberManage;
    @Autowired
    SchedulingManage schedulingManage;

    private Long init = 0L;

    /**
     * 预约列表
     *
     * @param pageNumber
     * @param pageSize
     * @param appointment
     * @return
     */
    public List<Appointment> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Appointment appointment) {
        AppointmentExample example = new AppointmentExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        AppointmentExample.Criteria criteria = example.createCriteria();
        if (appointment.getUid() != null) {
            criteria.andUidEqualTo(appointment.getUid());
        }
        if (appointment.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(appointment.getHospitalid());
        }
        if (appointment.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(appointment.getDepartmentid());
        }
        if (appointment.getPatientid() != null) {
            criteria.andPatientidEqualTo(appointment.getPatientid());
        }
        if (appointment.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(appointment.getDoctorid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andDateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andDateLessThanOrEqualTo(endDate);
        }
        //else {
        //    startDate += " 00:00:00";
        //    criteria.andDateGreaterThanOrEqualTo(startDate);
        //}
        return appointmentMapper.selectByExample(example);
    }

    /**
     * 预约列表一日
     *
     * @param pageNumber
     * @param pageSize
     * @param appointment
     * @return
     */
    public List<Appointment> listOneDay(Integer pageNumber, Integer pageSize, String date, Appointment appointment) {
        AppointmentExample example = new AppointmentExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        AppointmentExample.Criteria criteria = example.createCriteria();
        if (appointment.getUid() != null) {
            criteria.andUidEqualTo(appointment.getUid());
        }
        if (appointment.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(appointment.getHospitalid());
        }
        if (appointment.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(appointment.getDepartmentid());
        }
        if (appointment.getPatientid() != null) {
            criteria.andPatientidEqualTo(appointment.getPatientid());
        }
        if (appointment.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(appointment.getDoctorid());
        }
        criteria.andDateEqualTo(date);
        return appointmentMapper.selectByExample(example);
    }

    /**
     * 预约统计
     *
     * @param startDate
     * @param endDate
     * @param appointment
     * @return
     */
    public Integer count(String startDate, String endDate, Appointment appointment) {
        AppointmentExample example = new AppointmentExample();
        AppointmentExample.Criteria criteria = example.createCriteria();
        if (appointment.getUid() != null) {
            criteria.andUidEqualTo(appointment.getUid());
        }
        if (appointment.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(appointment.getHospitalid());
        }
        if (appointment.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(appointment.getDepartmentid());
        }
        if (appointment.getPatientid() != null) {
            criteria.andPatientidEqualTo(appointment.getPatientid());
        }
        if (appointment.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(appointment.getDoctorid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andDateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andDateLessThanOrEqualTo(endDate);
        }
        //else {
        //    startDate += " 00:00:00";
        //    criteria.andDateGreaterThanOrEqualTo(startDate);
        //}
        return appointmentMapper.countByExample(example);
    }

    /**
     * 预约统计 一日
     *
     * @param date
     * @param appointment
     * @return
     */
    public Integer countOneDay(String date, Appointment appointment) {
        AppointmentExample example = new AppointmentExample();
        AppointmentExample.Criteria criteria = example.createCriteria();
        if (appointment.getUid() != null && !appointment.getUid().equals(init)) {
            criteria.andUidEqualTo(appointment.getUid());
        }
        if (appointment.getHospitalid() != null && !appointment.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(appointment.getHospitalid());
        }
        if (appointment.getDepartmentid() != null && !appointment.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(appointment.getDepartmentid());
        }
        if (appointment.getPatientid() != null && !appointment.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(appointment.getPatientid());
        }
        if (appointment.getDoctorid() != null && !appointment.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(appointment.getDoctorid());
        }
        criteria.andDateEqualTo(date);
        return appointmentMapper.countByExample(example);
    }


    /**
     * 根据id获取预约
     *
     * @param id
     * @return
     */
    public Appointment getById(Long id) {
        return appointmentMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改预约信息
     *
     * @param appointment
     * @return
     */
    public Integer update(Appointment appointment) {
        return appointmentMapper.updateByPrimaryKeySelective(appointment);
    }


    /**
     * 删除预约
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = appointmentMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存预约
     *
     * @param appointment
     * @return
     */
    public Appointment save(Appointment appointment) {
        long i = appointmentMapper.insert(appointment);
        if (i > 0) {
            return appointment;
        }
        return null;
    }

    /**
     * 检查当前就诊人是否存在未完成的预约
     *
     * @param patientid
     * @return
     */
    public boolean patientHasAppointment(Long patientid) {
        AppointmentExample example = new AppointmentExample();
        AppointmentExample.Criteria criteria = example.createCriteria();
        criteria.andPatientidEqualTo(patientid);
        List<Integer> integers = new ArrayList<>();
        integers.add(2);//取消
        integers.add(3);//爽约
        integers.add(7);//诊断完毕
        criteria.andStatusNotIn(integers);
        List<Appointment> appointments = appointmentMapper.selectByExample(example);
        if (appointments.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 按医院清理当日过期 预约
     *
     * @param hospitalid
     * @return
     */
    public Appointment clearAppointrment(Long hospitalid, int timeflag) {
        AppointmentExample example = new AppointmentExample();
        AppointmentExample.Criteria criteria = example.createCriteria();
        if (hospitalid != null) {
            criteria.andHospitalidEqualTo(hospitalid);
        }
        String dateStr = MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
        criteria.andDateEqualTo(dateStr);
        criteria.andTimeflagEqualTo(timeflag);
        Appointment appointment = new Appointment();
        List<Appointment> appointments = appointmentMapper.selectByExample(example);
        for (Appointment appoint : appointments) {
            Long doctorid = appoint.getDoctorid();
            //Long departmentid = appoint.getDepartmentid();
            Scheduling scheduling = schedulingManage.getByDateAndTimeflagAndDoctorid(dateStr, timeflag, doctorid);
            scheduling.setOtherleftcount(scheduling.getOtherleftcount() + 1);
            scheduling.setAppointleftcount(0);
            schedulingManage.update(scheduling);
            //Numbers numbers = numberManage.getByTimeflagAndDeptidAndDate(timeflag, departmentid, dateStr);
            //numbers.setAppointleftcount(0);
            //numbers.setOtherleftcount(numbers.getOtherleftcount() + 1);
            //numberManage.update(numbers);
        }
        appointment.setStatus(3);
        long i = appointmentMapper.updateByExampleSelective(appointment, example);
        if (i > 0) {
            return appointment;
        }
        return null;
    }
}
