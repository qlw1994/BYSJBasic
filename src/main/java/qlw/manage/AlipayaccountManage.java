package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.AlipayaccountMapper;
import qlw.model.Alipayaccount;
import qlw.model.AlipayaccountExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/26.
 */
@Service
@Transactional(readOnly = true)
public class AlipayaccountManage extends BaseManage {
    @Autowired
    AlipayaccountMapper alipayaccountMapper;

    public List<Alipayaccount> list(Integer pageNumber, Integer pageSize, String hospitalname) {
        AlipayaccountExample example = new AlipayaccountExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
            AlipayaccountExample.Criteria criteria = example.createCriteria();
            criteria.andHospitalnameLike("%"+hospitalname+"%");

        return alipayaccountMapper.selectByExample(example);
    }


    public Integer count(String hospitalname) {
        AlipayaccountExample example = new AlipayaccountExample();
        AlipayaccountExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalnameLike("%"+hospitalname+"%");
        return alipayaccountMapper.countByExample(example);
    }


    /**
     * 根据支付宝名得到Alipayaccount
     *
     * @param account
     * @return
     */
    public Alipayaccount getByName(String account, Long hospitalid) {
        AlipayaccountExample example = new AlipayaccountExample();
        AlipayaccountExample.Criteria criteria = example.createCriteria();
        if (account != null && !account.equals("")) {
            criteria.andAccountnameEqualTo(account);
        }
        criteria.andHospitalidEqualTo(hospitalid);
        List<Alipayaccount> cities = alipayaccountMapper.selectByExample(example);
        if (cities.size() > 0) {
            return cities.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的支付宝名 有返回true
     *
     * @param name
     * @return
     */
    public Boolean haveSameName(String name, long hospitalid) {
        AlipayaccountExample example = new AlipayaccountExample();
        AlipayaccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountnameEqualTo(name);
        criteria.andHospitalidEqualTo(hospitalid);
        List<Alipayaccount> Alipayaccount = alipayaccountMapper.selectByExample(example);
        if (Alipayaccount.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 是否存在支付宝账号 有返回true
     *
     * @return
     */
    public Boolean hasAlipay(long hospitalid) {
        AlipayaccountExample example = new AlipayaccountExample();
        AlipayaccountExample.Criteria criteria = example.createCriteria();
        criteria.andHospitalidEqualTo(hospitalid);
        List<Alipayaccount> Alipayaccount = alipayaccountMapper.selectByExample(example);
        if (Alipayaccount.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 根据id获取支付宝
     *
     * @param id
     * @return
     */
    public Alipayaccount getById(Long id) {
        return alipayaccountMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改支付宝信息
     *
     * @param alipayaccount
     * @return
     */
    public Integer update(Alipayaccount alipayaccount) {
        return alipayaccountMapper.updateByPrimaryKeySelective(alipayaccount);
    }


    /**
     * 删除支付宝
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = alipayaccountMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存支付宝
     *
     * @param cities
     * @return
     */
    public Alipayaccount save(Alipayaccount cities) {
        long i = alipayaccountMapper.insert(cities);
        if (i > 0) {
            return cities;
        }
        return null;
    }
}
