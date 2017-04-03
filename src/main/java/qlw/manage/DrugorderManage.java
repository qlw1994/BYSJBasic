package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DrugorderExMapper;
import qlw.model.Drugorder;
import qlw.model.DrugorderExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/15.
 */
@Service
@Transactional(readOnly = true)
public class DrugorderManage extends BaseManage {
    @Autowired
    DrugorderExMapper drugorderExMapper;

    private Long init = 0L;

    /**
     * 药品订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param drugorder
     * @return
     */
    public List<Drugorder> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Drugorder drugorder) {
        DrugorderExample example = new DrugorderExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DrugorderExample.Criteria criteria = example.createCriteria();
        if (drugorder.getHospitalid() != null && !drugorder.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(drugorder.getHospitalid());
        }
        if (drugorder.getDepartmentid() != null && !drugorder.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(drugorder.getDepartmentid());
        }
        if (drugorder.getPatientid() != null && !drugorder.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(drugorder.getPatientid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andCreatedateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andCreatedateLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andCreatedateGreaterThanOrEqualTo(startDate);
        }
        return drugorderExMapper.selectByExample(example);
    }

    /**
     * 药品订单列表一日
     *
     * @param drugorder
     * @return
     */
    public List<Drugorder> listOneDay(String date, Drugorder drugorder) {
        DrugorderExample example = new DrugorderExample();
        DrugorderExample.Criteria criteria = example.createCriteria();
        if (drugorder.getHospitalid() != null && !drugorder.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(drugorder.getHospitalid());
        }
        if (drugorder.getDepartmentid() != null && !drugorder.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(drugorder.getDepartmentid());
        }
        if (drugorder.getPatientid() != null && !drugorder.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(drugorder.getPatientid());
        }
        if (drugorder.getDoctorid() != null && !drugorder.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(drugorder.getDoctorid());
        }
        criteria.andCreatedateEqualTo(date);
        return drugorderExMapper.selectByExample(example);
    }

    /**
     * 药品订单统计
     *
     * @param startDate
     * @param endDate
     * @param drugorder
     * @return
     */
    public Integer count(String startDate, String endDate, Drugorder drugorder) {
        DrugorderExample example = new DrugorderExample();
        DrugorderExample.Criteria criteria = example.createCriteria();

        if (drugorder.getHospitalid() != null && !drugorder.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(drugorder.getHospitalid());
        }
        if (drugorder.getDepartmentid() != null && !drugorder.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(drugorder.getDepartmentid());
        }
        if (drugorder.getPatientid() != null && !drugorder.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(drugorder.getPatientid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andCreatedateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andCreatedateLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andCreatedateGreaterThanOrEqualTo(startDate);
        }
        return drugorderExMapper.countByExample(example);
    }

    /**
     * 药品订单统计 一日
     *
     * @param date
     * @param drugorder
     * @return
     */
    public Integer countOneDay(String date, Drugorder drugorder) {
        DrugorderExample example = new DrugorderExample();
        DrugorderExample.Criteria criteria = example.createCriteria();
        if (drugorder.getHospitalid() != null && !drugorder.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(drugorder.getHospitalid());
        }
        if (drugorder.getDepartmentid() != null && !drugorder.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(drugorder.getDepartmentid());
        }
        if (drugorder.getPatientid() != null && !drugorder.getPatientid().equals(init)) {
            criteria.andPatientidEqualTo(drugorder.getPatientid());
        }
        if (drugorder.getDoctorid() != null && !drugorder.getDoctorid().equals(init)) {
            criteria.andDoctoridEqualTo(drugorder.getDoctorid());
        }
        criteria.andCreatedateEqualTo(date);
        return drugorderExMapper.countByExample(example);
    }


    /**
     * 根据id获取药品订单
     *
     * @param id
     * @return
     */
    public Drugorder getById(Long id) {
        return drugorderExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改药品订单信息
     *
     * @param drugorder
     * @return
     */
    public Integer update(Drugorder drugorder) {
        return drugorderExMapper.updateByPrimaryKeySelective(drugorder);
    }


    /**
     * 删除药品订单
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = drugorderExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存药品订单
     *
     * @param drugorder
     * @return
     */
    public Drugorder save(Drugorder drugorder) {
        long i = drugorderExMapper.insert(drugorder);
        if (i > 0) {
            return drugorder;
        }
        return null;
    }

    /**
     * 保存药品订单
     *
     * @param drugorder
     * @return
     */
    public Drugorder saveBackId(Drugorder drugorder) {
        long i = drugorderExMapper.insertBackId(drugorder);
        if (i > 0) {
            return drugorder;
        }
        return null;
    }


}
