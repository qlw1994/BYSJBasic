package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.SysusersMapper;
import qlw.model.Sysusers;
import qlw.model.SysusersExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/2.
 */
@Service
@Transactional(readOnly = true)
public class SysuserManage extends BaseManage{
    @Autowired
    SysusersMapper sysusersMapper;

    public List<Sysusers> list(Integer pageNumber, Integer pageSize) {
        SysusersExample example = new SysusersExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        return sysusersMapper.selectByExample(example);
    }


    public Integer count() {
        return sysusersMapper.countByExample(null);
    }


    /**
     * 获得相似account 数量
     *
     * @param account
     * @return
     */
    public Integer countLike(String account) {
        SysusersExample example = new SysusersExample();
        SysusersExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        return sysusersMapper.countByExample(example);
    }

    /**
     * 获得相似 account 账号
     *
     * @param account
     * @return
     */
    public List<Sysusers> getSysusersLike(String account) {
        SysusersExample example = new SysusersExample();
        SysusersExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        List<Sysusers> sysusers = sysusersMapper.selectByExample(example);
        if (sysusers.size() > 0) {
            return sysusers;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 根据管理员名得到Sysusers
     *
     * @param account
     * @return
     */
    public Sysusers getSysusersByAccount(String account) {
        SysusersExample example = new SysusersExample();
        SysusersExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Sysusers> sysusers = sysusersMapper.selectByExample(example);
        if (sysusers.size() > 0) {
            return sysusers.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的管理员名 有返回false
     *
     * @param account
     * @return
     */
    public Boolean haveSameAccount(String account) {
        SysusersExample example = new SysusersExample();
        SysusersExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Sysusers> sysusers = sysusersMapper.selectByExample(example);
        if (sysusers.size() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 根据id获取管理员
     *
     * @param id
     * @return
     */
    public Sysusers getSysusersById(Long id) {
        return sysusersMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改管理员信息
     *
     * @param sysusers
     * @return
     */
    public Integer updateSysusers(Sysusers sysusers) {
        return sysusersMapper.updateByPrimaryKeySelective(sysusers);
    }


    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = sysusersMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存后台管理员
     *
     * @param sysusers
     * @return
     */
    public Sysusers save(Sysusers sysusers) {
        long i = sysusersMapper.insert(sysusers);
        if (i > 0) {
            return sysusers;
        }
        return null;
    }

    /**
     * 检查旧密码是否相同
     *
     * @param id
     * @param password
     * @return 相同返回true 否则false
     */
    public boolean sameOddPassword(Long id, String password) {
        SysusersExample example = new SysusersExample();
        SysusersExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<Sysusers> list = sysusersMapper.selectByExample(example);
        Sysusers Sysusers = list.get(0);
        return Sysusers.getPassword().equals(password) ? true : false;
    }
}
