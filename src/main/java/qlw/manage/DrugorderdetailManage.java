package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DrugorderdetailExMapper;
import qlw.model.Drugorder;
import qlw.model.DrugorderExample;
import qlw.model.Drugorderdetail;
import qlw.model.DrugorderdetailExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/15.
 */
@Service
@Transactional(readOnly = true)
public class DrugorderdetailManage extends BaseManage {
    @Autowired
    DrugorderdetailExMapper drugorderdetailExMapper;
    @Autowired
    PaymentdetailManage paymentdetailManage;

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
    @Transactional
    public Integer update(Drugorderdetail drugorderdetail) {
        return drugorderdetailExMapper.updateByPrimaryKeySelective(drugorderdetail);
    }


    /**
     * 删除药品详情
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(Long id) {
        paymentdetailManage.deleteByproject(id, 1);
        int i = drugorderdetailExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存药品详情
     *
     * @param drugdetail
     * @return
     */
    @Transactional
    public Drugorderdetail save(Drugorderdetail drugdetail) {
        long i = drugorderdetailExMapper.insert(drugdetail);
        if (i > 0) {
            return drugdetail;
        }
        return null;
    }

    /**
     * 保存药品详情
     *
     * @param drugdetail
     * @return
     */
    @Transactional
    public Drugorderdetail saveBackId(Drugorderdetail drugdetail) {
        long i = drugorderdetailExMapper.insertBackId(drugdetail);
        if (i > 0) {
            return drugdetail;
        }
        return null;
    }

}
