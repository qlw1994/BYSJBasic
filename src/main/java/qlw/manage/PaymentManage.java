package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.PaymentExMapper;
import qlw.model.Payment;
import qlw.model.PaymentExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/23.
 */
@Service
@Transactional(readOnly = true)
public class PaymentManage extends BaseManage{
    @Autowired
    PaymentExMapper paymentExMapper;

    private Long init = 0L;

    /**
     * 支付订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param payment
     * @return
     */
    public List<Payment> list(Integer pageNumber, Integer pageSize, String startDate, String endDate, Payment payment) {
        PaymentExample example = new PaymentExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        PaymentExample.Criteria criteria = example.createCriteria();
        if (payment.getHospitalid()!=null&&!payment.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(payment.getHospitalid());
        }
        if (payment.getDepartmentid()!=null&&!payment.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(payment.getDepartmentid());
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
        return paymentExMapper.selectByExample(example);
    }

    /**
     * 支付订单列表一日
     *
     * @param pageNumber
     * @param pageSize
     * @param payment
     * @return
     */
    public List<Payment> listOneDay(Integer pageNumber, Integer pageSize, String date, Payment payment) {
        PaymentExample example = new PaymentExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        PaymentExample.Criteria criteria = example.createCriteria();
        if (payment.getHospitalid()!=null&&!payment.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(payment.getHospitalid());
        }
        if (payment.getDepartmentid()!=null&&!payment.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(payment.getDepartmentid());
        }
        criteria.andDateEqualTo(date);
        return paymentExMapper.selectByExample(example);
    }

    /**
     * 支付订单统计
     *
     * @param startDate
     * @param endDate
     * @param payment
     * @return
     */
    public Integer count(String startDate, String endDate, Payment payment) {
        PaymentExample example = new PaymentExample();
        PaymentExample.Criteria criteria = example.createCriteria();

        if (payment.getHospitalid()!=null&&!payment.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(payment.getHospitalid());
        }
        if (payment.getDepartmentid()!=null&&!payment.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(payment.getDepartmentid());
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
        return paymentExMapper.countByExample(example);
    }

    /**
     * 支付订单统计 一日
     *
     * @param date
     * @param payment
     * @return
     */
    public Integer countOneDay(String date, Payment payment) {
        PaymentExample example = new PaymentExample();
        PaymentExample.Criteria criteria = example.createCriteria();
        if (payment.getHospitalid()!=null&&!payment.getHospitalid().equals(init)) {
            criteria.andHospitalidEqualTo(payment.getHospitalid());
        }
        if (payment.getDepartmentid()!=null&&!payment.getDepartmentid().equals(init)) {
            criteria.andDepartmentidEqualTo(payment.getDepartmentid());
        }
        criteria.andDateEqualTo(date);
        return paymentExMapper.countByExample(example);
    }


    /**
     * 根据id获取支付订单
     *
     * @param id
     * @return
     */
    public Payment getById(Long id) {
        return paymentExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改支付订单信息
     *
     * @param payment
     * @return
     */
    public Integer update(Payment payment) {
        return paymentExMapper.updateByPrimaryKeySelective(payment);
    }


    /**
     * 删除支付订单
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = paymentExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存支付订单
     *
     * @param payment
     * @return
     */
    public Payment save(Payment payment) {
        long i = paymentExMapper.insert(payment);
        if (i > 0) {
            return payment;
        }
        return null;
    }
    /**
     * 保存支付订单
     *
     * @param payment
     * @return
     */
    public Payment saveBackId(Payment payment) {
        long i = paymentExMapper.insertBackId(payment);
        if (i > 0) {
            return payment;
        }
        return null;
    }
}
