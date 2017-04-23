package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DepartmentExMapper;
import qlw.model.Department;
import qlw.model.DepartmentExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/6.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentManage extends BaseManage {
    @Autowired
    DepartmentExMapper departmentExMapper;

    /**
     * 医院科室列表
     *
     * @param pageNumber
     * @param pageSize
     * @param hospitalid
     * @return
     */
    public List<Department> list(Integer pageNumber, Integer pageSize, long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        if (pageNumber != null && pageSize != null) {
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));}
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        return departmentExMapper.selectByExample(example);
    }

    public List<Department> listAll(Long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        return departmentExMapper.selectByExample(example);
    }

    public Integer count(long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        return departmentExMapper.countByExample(example);
    }


    /**
     * 获得相似 科室名 数量
     *
     * @param name
     * @param hospitalid
     * @return
     */
    public Integer countLike(String name, long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andNameLike(name);
        criteria.andDeletedateIsNull();
        return departmentExMapper.countByExample(example);
    }

    /**
     * 获得相似 科室名
     *
     * @param name
     * @param hospitalid
     * @return
     */
    public List<Department> getLike(Integer pageNumber, Integer pageSize, String name, Long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        if (pageNumber != null && pageSize != null) {
            example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        }
        DepartmentExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        List<Department> departments = departmentExMapper.selectByExample(example);
        if (departments.size() > 0) {
            return departments;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 根据科室名 和医院id得到Department
     *
     * @param hospitalid
     * @param name
     * @return
     */
    public Department getByName(String name, long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andNameEqualTo(name);
        List<Department> departments = departmentExMapper.selectByExample(example);
        if (departments.size() > 0) {
            return departments.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的科室名 有返回 true
     *
     * @param hospitalid
     * @param name
     * @return
     */
    public Boolean haveSameName(String name, long hospitalid) {
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andNameEqualTo(name);
        List<Department> departments = departmentExMapper.selectByExample(example);
        if (departments.size() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 根据id获取科室
     *
     * @param id
     * @return
     */
    public Department getById(Long id) {
        return departmentExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改科室信息
     *
     * @param department
     * @return
     */
    @Transactional
    public Integer update(Department department) {
        return departmentExMapper.updateByPrimaryKeySelective(department);
    }


    /**
     * 删除科室
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(Long id) {
        int i = departmentExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存科室
     *
     * @param department
     * @return
     */
    @Transactional
    public Department save(Department department) {
        long i = departmentExMapper.insert(department);
        if (i > 0) {
            return department;
        }
        return null;
    }

    /**
     * 保存科室 返回id
     *
     * @param department
     * @return
     */
    @Transactional
    public Department saveBackId(Department department) {
        long i = departmentExMapper.insertBackId(department);
        if (i > 0) {
            return department;
        }
        return null;
    }

}
