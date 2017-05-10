package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.HospitalizationExMapper;
import qlw.model.*;
import qlw.model.Hospitalization;

import java.util.List;

/**
 * Created by wiki on 2017/4/16.
 */
@Service
@Transactional(readOnly = true)
public class HospitalizationManage extends BaseManage {
    @Autowired
    HospitalizationExMapper hospitalizationExMapper;
    @Autowired
    HospitalpayManage hospitalpayManage;

    /**
     * 住院订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param hospitalization
     * @return
     */
    public List<Hospitalization> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Hospitalization hospitalization) {
        HospitalizationExample example = new HospitalizationExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        HospitalizationExample.Criteria criteria = example.createCriteria();
        if (hospitalization.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(hospitalization.getHospitalid());
        }
        if (hospitalization.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(hospitalization.getDepartmentid());
        }
        if (hospitalization.getPatientid() != null) {
            criteria.andPatientidEqualTo(hospitalization.getPatientid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {

            criteria.andStartdateGreaterThanOrEqualTo(startDate);
            criteria.andEnddateLessThanOrEqualTo(endDate);
        } else if (endDate != null && !"".equals(endDate)) {

            criteria.andEnddateLessThanOrEqualTo(endDate);
        }
        //else {
        //    startDate += " 00:00:00";
        //    criteria.andStartdateGreaterThanOrEqualTo(startDate);
        //}
        return hospitalizationExMapper.selectByExample(example);
    }

    /**
     * 住院订单列表一日
     *
     * @param hospitalization
     * @return
     */
    public List<Hospitalization> listOneDay(Integer pageNumber, Integer pageSize, String date, Hospitalization hospitalization) {
        HospitalizationExample example = new HospitalizationExample();
        HospitalizationExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        if (hospitalization.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(hospitalization.getHospitalid());
        }
        if (hospitalization.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(hospitalization.getDepartmentid());
        }
        if (hospitalization.getPatientid() != null) {
            criteria.andPatientidEqualTo(hospitalization.getPatientid());
        }
        criteria.andStartdateEqualTo(date);
        return hospitalizationExMapper.selectByExample(example);
    }

    /**
     * 住院订单统计
     *
     * @param startDate
     * @param endDate
     * @param hospitalization
     * @return
     */
    public Integer count(String startDate, String endDate, Hospitalization hospitalization) {
        HospitalizationExample example = new HospitalizationExample();
        HospitalizationExample.Criteria criteria = example.createCriteria();

        if (hospitalization.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(hospitalization.getHospitalid());
        }
        if (hospitalization.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(hospitalization.getDepartmentid());
        }
        if (hospitalization.getPatientid() != null) {
            criteria.andPatientidEqualTo(hospitalization.getPatientid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            criteria.andStartdateGreaterThanOrEqualTo(startDate);
            criteria.andEnddateLessThanOrEqualTo(endDate);
        } else if (endDate != null && !"".equals(endDate)) {

            criteria.andEnddateLessThanOrEqualTo(endDate);
        }
        //else {
        //    startDate += " 00:00:00";
        //    criteria.andStartdateGreaterThanOrEqualTo(startDate);
        //}
        return hospitalizationExMapper.countByExample(example);
    }


    /**
     * 根据id获取住院表
     *
     * @param id
     * @return
     */
    public Hospitalization getById(Long id) {
        return hospitalizationExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改住院表信息
     *
     * @param hospitalization
     * @return
     */
    @Transactional
    public Integer update(Hospitalization hospitalization) {
        return hospitalizationExMapper.updateByPrimaryKeySelective(hospitalization);
    }


    /**
     * 删除住院表
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(Long id) {
        int i = hospitalizationExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }




    /**
     * 保存住院表
     *
     * @param hospitalization
     * @return
     */
    @Transactional
    public Hospitalization save(Hospitalization hospitalization) {
        long i = hospitalizationExMapper.insert(hospitalization);
        if (i > 0) {
            return hospitalization;
        }
        return null;
    }

    /**
     * 保存住院表
     *
     * @param hospitalization
     * @return
     */
    @Transactional
    public Hospitalization saveBackId(Hospitalization hospitalization) {
        long i = hospitalizationExMapper.insertBackId(hospitalization);
        if (i > 0) {
            return hospitalization;
        }
        return null;
    }

    /**
     * 判断该住院表是否还需要支付  需要true 否则flase
     *
     * @param hospitalizationid
     * @return
     */
    public boolean needPay(Long hospitalizationid) {
        List<Hospitalpay> hospitalpays = hospitalpayManage.getByHospitalizationid(hospitalizationid);
        boolean res = false;
        for (Hospitalpay pay : hospitalpays) {
            if (pay.getStatus().equals(new Integer(0))) {
                res = true;
                break;
            }
        }
        return res;
    }
}
