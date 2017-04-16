package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.DrugMapper;
import qlw.model.Drug;
import qlw.model.DrugExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/8.
 */
@Service
@Transactional(readOnly = true)
public class DrugManage extends BaseManage{
    @Autowired
    DrugMapper drugMapper;

    public List<Drug> list(Integer pageNumber, Integer pageSize, long hospitalid) {
        DrugExample example = new DrugExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DrugExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        return drugMapper.selectByExample(example);
    }


    public Integer count(long hospitalid) {
        DrugExample example = new DrugExample();
        DrugExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        return drugMapper.countByExample(example);
    }


    /**
     * 获得相似数量
     *
     * @param name
     * @return
     */
    public Integer countLike(String name, long hospitalid) {
        DrugExample example = new DrugExample();
        DrugExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        return drugMapper.countByExample(example);
    }

    /**
     * 获得相似药品
     *
     * @param name
     * @return
     */
    public List<Drug> getLike(String name, long hospitalid) {
        DrugExample example = new DrugExample();
        DrugExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        List<Drug> drugs = drugMapper.selectByExample(example);
        if (drugs.size() > 0) {
            return drugs;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 根据药品名得到Drug
     *
     * @param name
     * @return
     */
    public Drug getByName(String name,Long hospitalid) {
        DrugExample example = new DrugExample();
        DrugExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andHospitalidEqualTo(hospitalid);
        List<Drug> drugs = drugMapper.selectByExample(example);
        if (drugs.size() > 0) {
            return drugs.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的药品名 有返回true
     *
     * @param name
     * @return
     */
    public Boolean haveSameName(String name,long hospitalid) {
        DrugExample example = new DrugExample();
        DrugExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andHospitalidEqualTo(hospitalid);
        List<Drug> Drug = drugMapper.selectByExample(example);
        if (Drug.size() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 根据id获取药品
     *
     * @param id
     * @return
     */
    public Drug getById(Long id) {
        return drugMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改药品信息
     *
     * @param drug
     * @return
     */
    public Integer update(Drug drug) {
        return drugMapper.updateByPrimaryKeySelective(drug);
    }


    /**
     * 删除药品
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = drugMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存药品
     *
     * @param drugs
     * @return
     */
    public Drug save(Drug drugs) {
        long i = drugMapper.insert(drugs);
        if (i > 0) {
            return drugs;
        }
        return null;
    }
}
