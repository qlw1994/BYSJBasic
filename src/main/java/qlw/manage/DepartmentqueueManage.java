package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DepartmentqueueExMapper;
import qlw.model.Departmentqueue;
import qlw.model.DepartmentqueueExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/22.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentqueueManage extends BaseManage {
    @Autowired
    DepartmentqueueExMapper departmentqueueExMapper;

    public List<Departmentqueue> list(Integer pageNumber, Integer pageSize, Long hospitalid, Long departmentid) {
        DepartmentqueueExample example = new DepartmentqueueExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DepartmentqueueExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDepartmentidEqualTo(departmentid);
        return departmentqueueExMapper.selectByExample(example);
    }


    public Integer count(Long hospitalid, Long departmentid) {
        DepartmentqueueExample example = new DepartmentqueueExample();
        DepartmentqueueExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDepartmentidEqualTo(departmentid);
        return departmentqueueExMapper.countByExample(example);
    }

    /**
     * 根据id获取科室队列
     *
     * @param id
     * @return
     */
    public Departmentqueue getById(Long id) {
        return departmentqueueExMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id获取科室队列
     *
     * @param departmentid
     * @return
     */
    public Departmentqueue getByDepartmentid(Long departmentid) {
        DepartmentqueueExample example = new DepartmentqueueExample();
        DepartmentqueueExample.Criteria criteria = example.createCriteria();
        criteria.andDepartmentidEqualTo(departmentid);
        return departmentqueueExMapper.selectByExample(example).get(0);
    }


    /**
     * 修改科室队列信息
     *
     * @param drug
     * @return
     */
    public Integer update(Departmentqueue drug) {
        return departmentqueueExMapper.updateByPrimaryKeySelective(drug);
    }


    /**
     * 删除科室队列
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = departmentqueueExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除科室队列
     *
     * @param hospitalid
     * @return
     */
    public boolean deleteByHospitalid(Long hospitalid) {
        DepartmentqueueExample example = new DepartmentqueueExample();
        DepartmentqueueExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        int i = departmentqueueExMapper.deleteByExample(example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存科室队列
     *
     * @param cities
     * @return
     */
    public Departmentqueue save(Departmentqueue cities) {
        long i = departmentqueueExMapper.insert(cities);
        if (i > 0) {
            return cities;
        }
        return null;
    }
}
