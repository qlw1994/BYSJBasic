package qlw.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.controller.alipayNotify.Diagnosispayment;
import qlw.mapper.PaymentdetailMapper;
import qlw.model.*;
import qlw.util.MyUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    HospitalizationManage hospitalizationManage;
    @Autowired
    HospitalpayManage hospitalpayManage;
    protected static final Logger log = LoggerFactory.getLogger(PaymentdetailManage.class);
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
     * 根据id获取支付详情
     *
     * @param paynumber
     * @return
     */
    public Paymentdetail getByPaynumber(String paynumber) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        criteria.andPaynumberEqualTo(paynumber);
        List<Paymentdetail> paymentdetails= paymentdetailMapper.selectByExample(example);
        if(paymentdetails.size()>0){
            return paymentdetails.get(0);
        }
        return null;
    }


    /**
     * 修改支付详情信息
     *
     * @param paymentdetail
     * @return
     */
    @Transactional
    public Integer update(Paymentdetail paymentdetail) {
        return paymentdetailMapper.updateByPrimaryKeySelective(paymentdetail);
    }

    /**
     * 修改支付详情信息
     *
     * @param paymentdetail
     * @return
     */
    @Transactional
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
    @Transactional
    public boolean delete(Long id) {
        int i = paymentdetailMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除支付详情 项目类型  项目编号
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean deleteByproject(Long id, Integer type) {
        PaymentdetailExample example = new PaymentdetailExample();
        PaymentdetailExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(id);
        criteria.andProjecttypeEqualTo(type);
        int i = paymentdetailMapper.deleteByExample(example);
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public boolean paymentConfirm(String paymentdetailidsTemp, Integer paytype, String invoicenumber, String paynumber) {
        String[] paymentdetailids = paymentdetailidsTemp.split("\\|");
        try{
            log.info("paymentdetailids--------------"+paymentdetailidsTemp);
            log.info("id length--------------"+ paymentdetailids.length);
        for (int i = 0; i < paymentdetailids.length; i++) {
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setStatus(1);
            paymentdetail.setPaytype(paytype);
            paymentdetail.setPaynumber(paynumber);
            paymentdetail.setPaydate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            paymentdetail.setInvoicenumber(invoicenumber);
            PaymentdetailExample example = new PaymentdetailExample();
            PaymentdetailExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(Long.parseLong(paymentdetailids[i]));
            paymentdetailMapper.updateByExampleSelective(paymentdetail, example);
            Paymentdetail paymentdetail_store = paymentdetailMapper.selectByPrimaryKey(Long.parseLong(paymentdetailids[i]));
            log.info("id string--------------"+paymentdetailids[i]);
            log.info("id long--------------"+Long.parseLong(paymentdetailids[i]));
            log.info("id--------------"+paymentdetail_store.getId());
            //记录药品订单编号
            if (paymentdetail_store.getProjecttype().equals(new Integer(0))) {
                Drugorderdetail drugorderdetail = drugorderdetailManage.getById(paymentdetail_store.getProjectid());
                Drugorder drugorder = drugorderManage.getById(drugorderdetail.getDrugorderid());
                drugorder.setNeedpay(drugorder.getNeedpay() - 1);
                //检查药品订单中是否全部支付完毕  (默认全部支付 即药品订单改为支付状态)
                if (drugorder.getNeedpay().equals(new Integer(0))) {
                    drugorder.setStatus(1);
                }
                drugorderManage.update(drugorder);
            }
            //住院消费
            if (paymentdetail_store.getProjecttype().equals(new Integer(1))) {
                Long hospitalpayid = paymentdetail_store.getProjectid();
                Hospitalpay hospitalpay = hospitalpayManage.getById(hospitalpayid);
                hospitalpay.setPaytype(1);
                hospitalpay.setStatus(1);
                hospitalpayManage.update(hospitalpay);
                Long hospitalizationid = hospitalpay.getHospitalizationid();
                Hospitalization hospitalization = hospitalizationManage.getById(hospitalizationid);
                if (!hospitalizationManage.needPay(hospitalizationid)) {
                    hospitalization.setStatus(1);
                    hospitalizationManage.update(hospitalization);
                }
            }

        }
        }catch (Exception e){
            log.info("Error------"+e);
        }


        return true;
    }
}
