package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.PaymentdetailMapper;
import qlw.model.Payment;
import qlw.model.PaymentExample;
import qlw.model.Paymentdetail;
import qlw.model.PaymentdetailExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/23.
 */
@Service
@Transactional(readOnly = true)
public class PaymentdetailManage extends BaseManage {
    @Autowired
    PaymentdetailMapper paymentdetailMapper;

    public List<Paymentdetail> list(Integer pageNumber, Integer pageSize, long paymentid) {
        PaymentdetailExample example = new PaymentdetailExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        criteria.andPaymentidEqualTo(paymentid);
        return paymentdetailMapper.selectByExample(example);
    }


    public Integer count(long paymentid) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        criteria.andPaymentidEqualTo(paymentid);
        return paymentdetailMapper.countByExample(example);
    }


    /**
     * 根据id获取药品详情
     *
     * @param id
     * @return
     */
    public Paymentdetail getById(Long id) {
        return paymentdetailMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改药品详情信息
     *
     * @param paymentdetail
     * @return
     */
    public Integer update(Paymentdetail paymentdetail) {
        return paymentdetailMapper.updateByPrimaryKeySelective(paymentdetail);
    }


    /**
     * 删除药品详情
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = paymentdetailMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除药品详情
     *
     * @param paymentid
     * @return
     */
    public boolean deleteByPaymentid(Long paymentid) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        criteria.andPaymentidEqualTo(paymentid);
        int i = paymentdetailMapper.deleteByExample(example);
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
    public Paymentdetail save(Paymentdetail cities) {
        long i = paymentdetailMapper.insert(cities);
        if (i > 0) {
            return cities;
        }
        return null;
    }
}
