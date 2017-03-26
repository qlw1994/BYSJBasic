package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DrugorderdetailExMapper;
import qlw.model.Drugorderdetail;
import qlw.model.DrugorderdetailExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/15.
 */
@Service
@Transactional(readOnly = true)
public class DrugorderdetailManage extends BaseManage{
    @Autowired
    DrugorderdetailExMapper drugorderdetailExMapper;

    public List<Drugorderdetail> list(Integer pageNumber, Integer pageSize, long drugorderid) {
        DrugorderdetailExample example = new DrugorderdetailExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DrugorderdetailExample.Criteria criteria = example.createCriteria();
        criteria.andDrugorderidEqualTo(drugorderid);
        return drugorderdetailExMapper.selectByExample(example);
    }


    public Integer count(long drugorderid) {
        DrugorderdetailExample example = new DrugorderdetailExample();
        DrugorderdetailExample.Criteria criteria = example.createCriteria();
        criteria.andDrugorderidEqualTo(drugorderid);
        return drugorderdetailExMapper.countByExample(example);
    }
    

    /**
     * 根据id获取药品详情
     *
     * @param id
     * @return
     */
    public Drugorderdetail getById(Long id) {
        return drugorderdetailExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改药品详情信息
     *
     * @param drugorderdetail
     * @return
     */
    public Integer update(Drugorderdetail drugorderdetail) {
        return drugorderdetailExMapper.updateByPrimaryKeySelective(drugorderdetail);
    }


    /**
     * 删除药品详情
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = drugorderdetailExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存药品详情
     *
     * @param cities
     * @return
     */
    public Drugorderdetail save(Drugorderdetail cities) {
        long i = drugorderdetailExMapper.insert(cities);
        if (i > 0) {
            return cities;
        }
        return null;
    }
}
