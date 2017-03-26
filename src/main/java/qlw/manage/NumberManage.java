package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.NumbersMapper;
import qlw.model.Numbers;
import qlw.model.NumbersExample;
import qlw.model.Scheduling;

import java.util.List;

/**
 * Created by wiki on 2017/3/13.
 */
@Service
@Transactional(readOnly = true)
public class NumberManage extends BaseManage {
    @Autowired
    NumbersMapper numbersMapper;
    private Long init = 0L;

    /**
     * 号源列表
     *
     * @param pageNumber
     * @param pageSize
     * @param numbers
     * @return
     */
    public List<Numbers> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Numbers numbers) {
        NumbersExample example = new NumbersExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        NumbersExample.Criteria criteria = example.createCriteria();
        if (numbers.getHospitalid() != null && !numbers.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(numbers.getHospitalid());
        }
        if (numbers.getDepartmentid() != null && !numbers.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(numbers.getDepartmentid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andDateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andDateLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andDateGreaterThanOrEqualTo(startDate);
        }
        return numbersMapper.selectByExample(example);
    }

    /**
     * 号源列表一日
     *
     * @param pageNumber
     * @param pageSize
     * @param numbers
     * @return
     */
    public List<Numbers> listOneDay(Integer pageNumber, Integer pageSize, String date, Numbers numbers) {
        NumbersExample example = new NumbersExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        NumbersExample.Criteria criteria = example.createCriteria();
        if (numbers.getHospitalid() != null && !numbers.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(numbers.getHospitalid());
        }
        if (numbers.getDepartmentid() != null && !numbers.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(numbers.getDepartmentid());
        }
        criteria.andDateEqualTo(date);
        return numbersMapper.selectByExample(example);
    }

    /**
     * 号源统计
     *
     * @param startDate
     * @param endDate
     * @param numbers
     * @return
     */
    public Integer count(String startDate, String endDate, Numbers numbers) {
        NumbersExample example = new NumbersExample();
        NumbersExample.Criteria criteria = example.createCriteria();

        if (numbers.getHospitalid() != null && !numbers.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(numbers.getHospitalid());
        }
        if (numbers.getDepartmentid() != null && !numbers.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(numbers.getDepartmentid());
        }
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            criteria.andDateBetween(startDate, endDate);
        } else if (endDate != null && !"".equals(endDate)) {
            endDate += " 23:59:59";
            criteria.andDateLessThanOrEqualTo(endDate);
        } else {
            startDate += " 00:00:00";
            criteria.andDateGreaterThanOrEqualTo(startDate);
        }
        return numbersMapper.countByExample(example);
    }

    /**
     * 号源统计 一日
     *
     * @param date
     * @param numbers
     * @return
     */
    public Integer countOneDay(String date, Numbers numbers) {
        NumbersExample example = new NumbersExample();
        NumbersExample.Criteria criteria = example.createCriteria();
        if (numbers.getHospitalid() != null && !numbers.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(numbers.getHospitalid());
        }
        if (numbers.getDepartmentid() != null && !numbers.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(numbers.getDepartmentid());
        }
        criteria.andDateEqualTo(date);
        return numbersMapper.countByExample(example);
    }

    /**
     * 根据 timeflag ,departmentid, date 获取号源
     *
     * @param timeflag
     * @param departmentid
     * @param date
     * @return
     */
    public Numbers getByTimeflagAndDeptidAndDate(int timeflag, Long departmentid, String date) {
        NumbersExample example = new NumbersExample();
        NumbersExample.Criteria criteria = example.createCriteria();
        criteria.andTimeflagEqualTo(timeflag);
        criteria.andDepartmentidEqualTo(departmentid);
        criteria.andDateEqualTo(date);
        return numbersMapper.selectByExample(example).get(0);
    }

    /**
     * 根据id获取号源
     *
     * @param id
     * @return
     */
    public Numbers getById(Long id) {
        return numbersMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改号源信息
     *
     * @param numbers
     * @return
     */
    public Integer update(Numbers numbers) {
        return numbersMapper.updateByPrimaryKeySelective(numbers);
    }


    /**
     * 删除号源
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = numbersMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存号源
     *
     * @param numbers
     * @return
     */
    public Numbers save(Numbers numbers) {
        long i = numbersMapper.insert(numbers);
        if (i > 0) {
            return numbers;
        }
        return null;
    }

    /**
     * 是否存在重复的号源
     *
     * @param date
     * @param departmentid
     * @param timeflag
     * @return 存在true  否则 false
     */
    public boolean hasSame(Long departmentid, String date, int timeflag) {
        NumbersExample example = new NumbersExample();
        NumbersExample.Criteria criteria = example.createCriteria();
        criteria.andDepartmentidEqualTo(departmentid);
        criteria.andDateEqualTo(date);
        criteria.andTimeflagEqualTo(timeflag);
        List<Numbers> numberss = numbersMapper.selectByExample(example);
        if (numberss.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取号源
     *
     * @param departmentid
     * @param date
     * @param timeflag
     * @return 存在true  否则 false
     */
    public List<Numbers> getNumbersByDepartmentidAndtimeflagAndDate(Long departmentid, String date, int timeflag) {
        NumbersExample example = new NumbersExample();
        NumbersExample.Criteria criteria = example.createCriteria();
        criteria.andDepartmentidEqualTo(departmentid);
        criteria.andDateEqualTo(date);
        criteria.andTimeflagEqualTo(timeflag);
        List<Numbers> numberss = numbersMapper.selectByExample(example);
        return numberss;
    }
}
