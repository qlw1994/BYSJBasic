package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.DepartmentqueuedetailMapper;
import qlw.model.Departmentqueuedetail;
import qlw.model.DepartmentqueuedetailExample;

import java.util.List;

/**
 * Created by wiki on 2017/4/3.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentqueuedetailManage extends BaseManage {
    @Autowired
    DepartmentqueuedetailMapper departmentqueuedetailMapper;

    public List<Departmentqueuedetail> list(Integer pageNumber, Integer pageSize, long departmentqueueid) {
        DepartmentqueuedetailExample example = new DepartmentqueuedetailExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DepartmentqueuedetailExample.Criteria criteria = example.createCriteria();
        criteria.andDepartmentqueueidEqualTo(departmentqueueid);
        return departmentqueuedetailMapper.selectByExample(example);
    }


    public Integer count(long departmentqueueid) {
        DepartmentqueuedetailExample example = new DepartmentqueuedetailExample();
        DepartmentqueuedetailExample.Criteria criteria = example.createCriteria();
        criteria.andDepartmentqueueidEqualTo(departmentqueueid);
        return departmentqueuedetailMapper.countByExample(example);
    }


    /**
     * 根据id获取科室队列详情
     *
     * @param id
     * @return
     */
    public Departmentqueuedetail getById(Long id) {
        return departmentqueuedetailMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id获取科室队列详情
     *
     * @param patientid
     * @return
     */
    public List<Departmentqueuedetail> getByPatientid(Long patientid) {
        DepartmentqueuedetailExample example = new DepartmentqueuedetailExample();
        DepartmentqueuedetailExample.Criteria criteria = example.createCriteria();
        criteria.andPatientidEqualTo(patientid);
        return departmentqueuedetailMapper.selectByExample(example);
    }


    /**
     * 修改科室队列详情信息
     *
     * @param drugorderdetail
     * @return
     */
    public Integer update(Departmentqueuedetail drugorderdetail) {
        return departmentqueuedetailMapper.updateByPrimaryKeySelective(drugorderdetail);
    }


    /**
     * 删除科室队列详情
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = departmentqueuedetailMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除科室队列详情
     *
     * @return
     */
    public boolean deleteAll() {
        DepartmentqueuedetailExample example = new DepartmentqueuedetailExample();
        DepartmentqueuedetailExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        int i = departmentqueuedetailMapper.deleteByExample(example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除科室队列详情
     *
     * @param patientid
     * @return
     */
    public boolean deleteByPatienid(Long patientid) {
        DepartmentqueuedetailExample example = new DepartmentqueuedetailExample();
        DepartmentqueuedetailExample.Criteria criteria = example.createCriteria();
        criteria.andPatientidEqualTo(patientid);
        int i = departmentqueuedetailMapper.deleteByExample(example);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存科室队列详情
     *
     * @param departmentqueuedetail
     * @return
     */
    public Departmentqueuedetail save(Departmentqueuedetail departmentqueuedetail) {
        long i = departmentqueuedetailMapper.insert(departmentqueuedetail);
        if (i > 0) {
            return departmentqueuedetail;
        }
        return null;
    }

    /**
     * 获取科室队列中下一个人
     *
     * @param departmentqueueid
     * @return
     */
    public Departmentqueuedetail getNext(Long departmentqueueid) {
        DepartmentqueuedetailExample example = new DepartmentqueuedetailExample();
        DepartmentqueuedetailExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(getPage("id asc", 1, 1));
        criteria.andDepartmentqueueidEqualTo(departmentqueueid);
        Departmentqueuedetail departmentqueuedetail = departmentqueuedetailMapper.selectByExample(example).get(0);
        return departmentqueuedetail;
    }


}
