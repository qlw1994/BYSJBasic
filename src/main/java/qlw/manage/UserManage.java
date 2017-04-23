package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.UsersExMapper;
import qlw.model.Users;
import qlw.model.UsersExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/1/24.
 */
@Service
@Transactional(readOnly = true)
public class UserManage extends BaseManage {
    @Autowired
    UsersExMapper usersExMapper;

    public List<Users> list(Integer pageNumber, Integer pageSize) {
        UsersExample example = new UsersExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        return usersExMapper.selectByExample(example);
    }


    public Integer count() {

        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        return usersExMapper.countByExample(example);
    }


    /**
     * 获得相似account 数量
     *
     * @param account
     * @return
     */
    public Integer countLike(String account) {
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDeletedateIsNull();
        return usersExMapper.countByExample(example);
    }

    /**
     * 获得相似 account 账号
     *
     * @param account
     * @return
     */
    public List<Users> getUsersLike(String account) {
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDeletedateIsNull();
        List<Users> users = usersExMapper.selectByExample(example);
        if (users.size() > 0) {
            return users;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 根据用户名得到Users
     *
     * @param account
     * @return
     */
    public Users getUsersByAccount(String account) {
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Users> users = usersExMapper.selectByExample(example);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的用户名 有返回true
     *
     * @param account
     * @return
     */
    public Boolean haveSameAccount(String account) {
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Users> users = usersExMapper.selectByExample(example);
        if (users.size() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */

    public Users getUsersById(Long id) {
        return usersExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改用户信息
     *
     * @param users
     * @return
     */
    @Transactional
    public Integer updateUsers(Users users) {
        return usersExMapper.updateByPrimaryKeySelective(users);
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(Long id) {
        int i = usersExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存后台用户
     *
     * @param users
     * @return
     */
    @Transactional
    public Users save(Users users) {
        long i = usersExMapper.insert(users);
        if (i > 0) {
            return users;
        }
        return null;
    }

    /**
     * 保存后台用户
     *
     * @param users
     * @return
     */
    @Transactional
    public Users saveBackId(Users users) {
        long i = usersExMapper.insertBackId(users);
        if (i > 0) {
            return users;
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
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<Users> list = usersExMapper.selectByExample(example);
        Users Users = list.get(0);
        return Users.getPassword().equals(password) ? true : false;
    }
}
