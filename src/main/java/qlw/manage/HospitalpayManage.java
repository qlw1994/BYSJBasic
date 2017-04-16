package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.HospitalpayExMapper;
import qlw.model.HospitalizationExample;
import qlw.model.Hospitalpay;
import qlw.model.HospitalpayExample;

import java.util.List;

/**
 * Created by wiki on 2017/4/16.
 */
@Service
@Transactional(readOnly = true)
public class HospitalpayManage extends BaseManage {

    @Autowired
    HospitalpayExMapper hospitalpayExMapper;
    @Autowired
    PaymentdetailManage paymentdetailManage;

    public List<Hospitalpay> list(Integer pageNumber, Integer pageSize, Long hospitalizationid) {
        HospitalpayExample example = new HospitalpayExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        HospitalpayExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalizationidEqualTo(hospitalizationid);
        return hospitalpayExMapper.selectByExample(example);
    }


    public Integer count(long hospitalizationid) {
        HospitalpayExample example = new HospitalpayExample();
        HospitalpayExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalizationidEqualTo(hospitalizationid);
        return hospitalpayExMapper.countByExample(example);
    }


    /**
     * 根据id获取住院消费详情
     *
     * @param id
     * @return
     */
    public Hospitalpay getById(Long id) {
        return hospitalpayExMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据hospitalizationid获取住院消费详情
     *
     * @param hospitalizationid
     * @return
     */
    public List<Hospitalpay> getByHospitalizationid(Long hospitalizationid) {
        HospitalpayExample example = new HospitalpayExample();
        HospitalpayExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalizationidEqualTo(hospitalizationid);
        return hospitalpayExMapper.selectByExample(example);
    }

    /**
     * 修改住院消费详情信息
     *
     * @param hospitalpay
     * @return
     */
    public Integer update(Hospitalpay hospitalpay) {
        return hospitalpayExMapper.updateByPrimaryKeySelective(hospitalpay);
    }


    /**
     * 删除住院消费详情
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        paymentdetailManage.deleteByproject(id, 1);
        int i = hospitalpayExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存住院消费详情
     *
     * @param hospitalpay
     * @return
     */
    public Hospitalpay save(Hospitalpay hospitalpay) {
        long i = hospitalpayExMapper.insert(hospitalpay);
        if (i > 0) {
            return hospitalpay;
        }
        return null;
    }

    /**
     * 删除住院消费详情表
     *
     * @param id
     * @return
     */
    public boolean deleteByHospitalizationid(Long id) {
        HospitalpayExample example = new HospitalpayExample();
        HospitalpayExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalizationidEqualTo(id);
        List<Hospitalpay> hospitalpays = hospitalpayExMapper.selectByExample(example);
        //清理支付表
        for (Hospitalpay hospitalpay : hospitalpays) {
            paymentdetailManage.deleteByproject(hospitalpay.getId(), 1);
        }
        int i = hospitalpayExMapper.deleteByExample(example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存住院消费详情
     *
     * @param hospitalpay
     * @return
     */
    public Hospitalpay saveBackId(Hospitalpay hospitalpay) {
        long i = hospitalpayExMapper.insertBackId(hospitalpay);
        if (i > 0) {
            return hospitalpay;
        }
        return null;
    }
}
