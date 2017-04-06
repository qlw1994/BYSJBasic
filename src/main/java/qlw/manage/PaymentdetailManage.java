package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.DrugorderMapper;
import qlw.mapper.PaymentdetailMapper;
import qlw.model.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wiki on 2017/3/23.
 */
@Service
@Transactional(readOnly = true)
public class PaymentdetailManage extends BaseManage {
    @Autowired
    PaymentdetailMapper paymentdetailMapper;
    @Autowired
    DrugorderManage drugorderManage;
    @Autowired
    DrugorderdetailManage drugorderdetailManage;

    public List<Paymentdetail> list(Integer pageNumber, Integer pageSize, String startdate, String enddate, Paymentdetail paymentdetail) {
        PaymentdetailExample example = new PaymentdetailExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        if (paymentdetail.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(paymentdetail.getDoctorid());
        }
        if (paymentdetail.getPatientid() != null) {
            criteria.andPatientidEqualTo(paymentdetail.getPatientid());
        }
        if (paymentdetail.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(paymentdetail.getDepartmentid());
        }
        if (paymentdetail.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(paymentdetail.getHospitalid());
        }
        if (startdate != null && !"".equals(startdate) && enddate != null && !"".equals(enddate)) {
            //startdate += " 00:00:00";
            //enddate += " 23:59:59";
            criteria.andCreatedateBetween(startdate, enddate);
        } else if (enddate != null && !"".equals(enddate)) {
            //enddate += " 23:59:59";
            criteria.andCreatedateLessThanOrEqualTo(enddate);
        }
        return paymentdetailMapper.selectByExample(example);
    }

    public List<Paymentdetail> listpayed(Integer pageNumber, Integer pageSize, String startdate, String enddate, Paymentdetail paymentdetail) {
        PaymentdetailExample example = new PaymentdetailExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        if (paymentdetail.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(paymentdetail.getDoctorid());
        }
        if (paymentdetail.getPatientid() != null) {
            criteria.andPatientidEqualTo(paymentdetail.getPatientid());
        }
        if (paymentdetail.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(paymentdetail.getDepartmentid());
        }
        if (paymentdetail.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(paymentdetail.getHospitalid());
        }
        if (startdate != null && !"".equals(startdate) && enddate != null && !"".equals(enddate)) {
            startdate += " 00:00:00";
            enddate += " 23:59:59";
            criteria.andCreatedateBetween(startdate, enddate);
        } else if (enddate != null && !"".equals(enddate)) {
            enddate += " 23:59:59";
            criteria.andCreatedateLessThanOrEqualTo(enddate);
        }
        //else {
        //    startdate += " 00:00:00";
        //    criteria.andCreatedateGreaterThanOrEqualTo(startdate);
        //}
        criteria.andStatusEqualTo(1);
        return paymentdetailMapper.selectByExample(example);
    }

    public Integer count(String startdate, String enddate, Paymentdetail paymentdetail) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        if (paymentdetail.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(paymentdetail.getDoctorid());
        }
        if (paymentdetail.getPatientid() != null) {
            criteria.andPatientidEqualTo(paymentdetail.getPatientid());
        }
        if (paymentdetail.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(paymentdetail.getDepartmentid());
        }
        if (paymentdetail.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(paymentdetail.getHospitalid());
        }
        if (startdate != null && !"".equals(startdate) && enddate != null && !"".equals(enddate)) {
            startdate += " 00:00:00";
            enddate += " 23:59:59";
            criteria.andCreatedateBetween(startdate, enddate);
        } else if (enddate != null && !"".equals(enddate)) {
            enddate += " 23:59:59";
            criteria.andCreatedateLessThanOrEqualTo(enddate);
        }
        //else {
        //    startdate += " 00:00:00";
        //    criteria.andCreatedateGreaterThanOrEqualTo(startdate);
        //}
        return paymentdetailMapper.countByExample(example);
    }

    public Integer countpayed(String startdate, String enddate, Paymentdetail paymentdetail) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        if (paymentdetail.getDoctorid() != null) {
            criteria.andDoctoridEqualTo(paymentdetail.getDoctorid());
        }
        if (paymentdetail.getPatientid() != null) {
            criteria.andPatientidEqualTo(paymentdetail.getPatientid());
        }
        if (paymentdetail.getDepartmentid() != null) {
            criteria.andDepartmentidEqualTo(paymentdetail.getDepartmentid());
        }
        if (paymentdetail.getHospitalid() != null) {
            criteria.andHospitalidEqualTo(paymentdetail.getHospitalid());
        }
        if (startdate != null && !"".equals(startdate) && enddate != null && !"".equals(enddate)) {
            startdate += " 00:00:00";
            enddate += " 23:59:59";
            criteria.andCreatedateBetween(startdate, enddate);
        } else if (enddate != null && !"".equals(enddate)) {
            enddate += " 23:59:59";
            criteria.andCreatedateLessThanOrEqualTo(enddate);
        }
        //else {
        //    startdate += " 00:00:00";
        //    criteria.andCreatedateGreaterThanOrEqualTo(startdate);
        //}
        criteria.andStatusEqualTo(1);
        return paymentdetailMapper.countByExample(example);
    }

    /**
     * 根据id获取支付详情
     *
     * @param id
     * @return
     */
    public Paymentdetail getById(Long id) {
        return paymentdetailMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改支付详情信息
     *
     * @param paymentdetail
     * @return
     */
    public Integer update(Paymentdetail paymentdetail) {
        return paymentdetailMapper.updateByPrimaryKeySelective(paymentdetail);
    }

    /**
     * 修改支付详情信息
     *
     * @param paymentdetail
     * @return
     */
    public Integer updateByPaymentdetail(Paymentdetail paymentdetail, Paymentdetail newpaymentdetail) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        if (paymentdetail.getDepartmentid() != null)
            criteria.andDepartmentidEqualTo(paymentdetail.getDepartmentid());
        if (paymentdetail.getHospitalid() != null)
            criteria.andHospitalidEqualTo(paymentdetail.getHospitalid());
        if (paymentdetail.getDoctorid() != null)
            criteria.andDoctoridEqualTo(paymentdetail.getDoctorid());
        if (paymentdetail.getPatientid() != null)
            criteria.andPatientidEqualTo(paymentdetail.getPatientid());
        if (paymentdetail.getCreatedate() != null)
            criteria.andCreatedateEqualTo(paymentdetail.getCreatedate());
        if (paymentdetail.getFormat() != null)
            criteria.andFormatEqualTo(paymentdetail.getFormat());
        if (paymentdetail.getMoney() != null)
            criteria.andMoneyEqualTo(paymentdetail.getMoney());
        if (paymentdetail.getCount() != null)
            criteria.andCountEqualTo(paymentdetail.getCount());
        paymentdetail = paymentdetailMapper.selectByExample(example).get(0);
        if (paymentdetail.getStatus() == 1) {
            return -100;
        }
        return paymentdetailMapper.updateByExampleSelective(newpaymentdetail, example);
    }

    /**
     * 删除支付详情
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
     * 删除支付详情
     *
     * @param paymentdetail
     * @return
     */
    public boolean deleteByPaymentdetail(Paymentdetail paymentdetail) {

        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        if (paymentdetail.getDepartmentid() != null)
            criteria.andDepartmentidEqualTo(paymentdetail.getDepartmentid());
        if (paymentdetail.getHospitalid() != null)
            criteria.andHospitalidEqualTo(paymentdetail.getHospitalid());
        if (paymentdetail.getDoctorid() != null)
            criteria.andDoctoridEqualTo(paymentdetail.getDoctorid());
        if (paymentdetail.getPatientid() != null)
            criteria.andPatientidEqualTo(paymentdetail.getPatientid());
        if (paymentdetail.getCreatedate() != null)
            criteria.andCreatedateEqualTo(paymentdetail.getCreatedate());
        if (paymentdetail.getFormat() != null)
            criteria.andFormatEqualTo(paymentdetail.getFormat());
        if (paymentdetail.getMoney() != null)
            criteria.andMoneyEqualTo(paymentdetail.getMoney());
        if (paymentdetail.getCount() != null)
            criteria.andCountEqualTo(paymentdetail.getCount());
        Paymentdetail paymentdetail_store = paymentdetailMapper.selectByExample(example).get(0);
        int i = paymentdetailMapper.deleteByPrimaryKey(paymentdetail_store.getId());

        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存支付详情
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

    /**
     * 支付确认
     *
     * @param paymentdetailidsTemp
     * @return
     */
    public boolean paymentConfirm(String paymentdetailidsTemp, Integer paytype, String invoicenumber, String paynumber) {
        String[] paymentdetailids = paymentdetailidsTemp.split("|");
        for (int i = 0; i < paymentdetailids.length; i++) {
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setStatus(1);
            paymentdetail.setPaytype(paytype);
            paymentdetail.setPaynumber(paynumber);
            paymentdetail.setInvoicenumber(invoicenumber);
            PaymentdetailExample example = new PaymentdetailExample();
            PaymentdetailExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(Long.parseLong(paymentdetailids[i]));
            paymentdetailMapper.updateByExampleSelective(paymentdetail, example);
            Paymentdetail paymentdetail_store = paymentdetailMapper.selectByPrimaryKey(Long.parseLong(paymentdetailids[i]));
            //记录药品订单编号
            if (paymentdetail_store.getProjecttype().equals(0)) {
                Drugorderdetail drugorderdetail = drugorderdetailManage.getById(paymentdetail_store.getProjectid());
                Drugorder drugorder = drugorderManage.getById(drugorderdetail.getDrugorderid());
                drugorder.setNeedpay(drugorder.getNeedpay() - 1);
                //检查药品订单中是否全部支付完毕  (默认全部支付 即药品订单改为支付状态)
                if (drugorder.getNeedpay().equals(0)) {
                    drugorder.setStatus(1);
                }
                drugorderManage.update(drugorder);
            }

        }


        return true;
    }
}
