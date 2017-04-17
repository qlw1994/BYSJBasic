package qlw.manage;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.DepartmentExMapper;
import qlw.mapper.ex.DoctorExMapper;
import qlw.mapper.ex.HospitalExMapper;
import qlw.model.Doctor;
import qlw.model.DoctorExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/3.
 */
@Service
@Transactional(readOnly = true)
public class DoctorManage extends BaseManage {
    @Autowired
    DoctorExMapper doctorExMapper;
    @Autowired
    HospitalExMapper hospitalExMapper;
    @Autowired
    DepartmentExMapper departmentExMapper;

    /**
     * 所有医生列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Doctor> list(Integer pageNumber, Integer pageSize) {
        DoctorExample example = new DoctorExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        return doctors;
    }

    /**
     * 医生列表 按科室
     *
     * @param pageNumber
     * @param pageSize
     * @param departmentid
     * @return
     */
    public List<Doctor> listByDepartment(Integer pageNumber, Integer pageSize, Long departmentid, Integer type) {
        DoctorExample example = new DoctorExample();
        example.setOrderByClause(getPage(" level,id desc", pageNumber, pageSize));
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        if (type != null) {
            criteria.andLevelEqualTo(type);
        }
        criteria.andDepartmentidEqualTo(departmentid);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        return doctors;
    }

    /**
     * 医生列表  按医院
     *
     * @param pageNumber
     * @param pageSize
     * @param hospitalid
     * @return
     */
    public List<Doctor> listByHospital(Integer pageNumber, Integer pageSize, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        criteria.andHospitalidEqualTo(hospitalid);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        return doctors;
    }

    /**
     * 医生列表  按医院 医生姓名
     *
     * @param hospitalid
     * @return
     */
    public List<Doctor> getNameLikeByHospital(String name, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        name = name + "% ";
        criteria.andNameLike(name);
        criteria.andDeletedateIsNull();
        criteria.andHospitalidEqualTo(hospitalid);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        return doctors;
    }

    /**
     * 医生列表  按科室 医生姓名
     *
     * @param departmentid
     * @return
     */
    public List<Doctor> getNameLikeByDepartment(Integer pageNumber, Integer pageSize, String name, Long departmentid, Integer type) {
        DoctorExample example = new DoctorExample();
        if (pageNumber != null && pageSize != null) {
            example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        }
        DoctorExample.Criteria criteria = example.createCriteria();
        name = name + "% ";
        criteria.andNameLike(name);
        criteria.andDeletedateIsNull();
        criteria.andDepartmentidEqualTo(departmentid);
        criteria.andLevelEqualTo(type);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        return doctors;
    }

    /**
     * 统计所有医生数量
     *
     * @return
     */
    public Integer count() {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        return doctors.size();
    }

    /**
     * 统计医生数量 按科室 ;(医生类型)
     *
     * @param departmentid
     * @return
     */
    public Integer countByDepartment(Long departmentid, Integer type) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        if (type != null) {
            criteria.andLevelEqualTo(type);
        }
        criteria.andDeletedateIsNull();
        criteria.andDepartmentidEqualTo(departmentid);
        return doctorExMapper.countByExample(example);
    }

    /**
     * 统计医生数量 按医院
     *
     * @param hospitalid
     * @return
     */
    public Integer countByHospital(Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        criteria.andHospitalidEqualTo(hospitalid);
        return doctorExMapper.countByExample(example);
    }

    /**
     * 获得相似account 数量
     *
     * @param account
     * @return
     */
    public Integer countLike(String account) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDeletedateIsNull();
        return doctorExMapper.countByExample(example);
    }

    /**
     * 获得相似account 数量 按科室
     *
     * @param account
     * @return
     */
    public Integer countLikeByDepartment(String account, Long departmentid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDepartmentidEqualTo(departmentid);
        criteria.andDeletedateIsNull();
        return doctorExMapper.countByExample(example);
    }

    /**
     * 获得相似account 数量 安医院
     *
     * @param account
     * @return
     */
    public Integer countLikeByHospital(String account, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDeletedateIsNull();
        criteria.andHospitalidEqualTo(hospitalid);
        return doctorExMapper.countByExample(example);
    }

    /**
     * 获得相似 name 数量 安医院
     *
     * @param name
     * @return
     */
    public Integer countNameLikeByHospital(String name, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        criteria.andDeletedateIsNull();
        criteria.andHospitalidEqualTo(hospitalid);
        return doctorExMapper.countByExample(example);
    }

    /**
     * 获得相似 name 数量 按科室 医生姓名
     *
     * @param name
     * @return
     */
    public Integer countNameLikeByDepartment(String name, Long departmentid, Integer type) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        criteria.andLevelEqualTo(type);
        criteria.andDeletedateIsNull();
        criteria.andDepartmentidEqualTo(departmentid);
        return doctorExMapper.countByExample(example);
    }

    /**
     * 获得相似 account 账号
     *
     * @param account
     * @return
     */
    public List<Doctor> getDoctorLike(String account) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        if (doctors.size() > 0) {
            return doctors;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 获得相似 account 账号  按科室
     *
     * @param account
     * @return
     */
    public List<Doctor> getDoctorLikeByDepartment(String account, Long departmentid) {
        DoctorExample example = new DoctorExample();

        DoctorExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andDepartmentidEqualTo(departmentid);
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        if (doctors.size() > 0) {
            return doctors;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 获得相似 account 账号  按医院
     *
     * @param account
     * @return
     */
    public List<Doctor> getDoctorLikeByHospital(String account, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        account = account + "%";
        criteria.andAccountLike(account);
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        if (doctors.size() > 0) {
            return doctors;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 根据用户名得到Doctor
     *
     * @param account
     * @return
     */
    public Doctor getDoctorByAccount(String account) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        if (doctors.size() > 0) {
            return doctors.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户名得到Doctor 按科室
     *
     * @param account
     * @return
     */
    public Doctor getDoctorByAccountAndDepartment(String account, Long departmentid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andDepartmentidEqualTo(departmentid);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        if (doctors.size() > 0) {
            return doctors.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户名得到Doctor 按医院
     *
     * @param account
     * @return
     */
    public Doctor getDoctorByAccountAndHospital(String account, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andHospitalidEqualTo(hospitalid);
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).setHospital(hospitalExMapper.selectByPrimaryKey(doctors.get(i).getHospitalid()));
            doctors.get(i).setDepartment(departmentExMapper.selectByPrimaryKey(doctors.get(i).getDepartmentid()));
        }
        if (doctors.size() > 0) {
            return doctors.get(0);
        } else {
            return null;
        }
    }

    /**
     * 是否有相同的用户名 有返回false   单家医院范围
     *
     * @param account
     * @param hospitalid
     * @return 相同true, 否则false
     */
    public Boolean haveSameAccountAndHospital(String account, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        if (doctors.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否存在名字  单家医院范围
     *
     * @param name
     * @param hospitalid
     * @return 相同true, 否则false
     */
    public Boolean haveSameName(String name, Long hospitalid) {
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andHospitalidEqualTo(hospitalid);
        criteria.andDeletedateIsNull();
        List<Doctor> doctors = doctorExMapper.selectByExample(example);
        if (doctors.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    public Doctor getDoctorById(Long id) {
        return doctorExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改用户信息
     *
     * @param doctor
     * @return
     */
    public Integer updateDoctor(Doctor doctor) {
        return doctorExMapper.updateByPrimaryKeySelective(doctor);
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = doctorExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存后台用户
     *
     * @param doctor
     * @return
     */
    public Doctor save(Doctor doctor) {
        long i = doctorExMapper.insert(doctor);
        if (i > 0) {
            return doctor;
        }
        return null;
    }
    /**
     * 保存后台用户
     *
     * @param doctor
     * @return
     */
    public Doctor saveBackId(Doctor doctor) {
        long i = doctorExMapper.insertBackId(doctor);
        if (i > 0) {
            return doctor;
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
        DoctorExample example = new DoctorExample();
        DoctorExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<Doctor> list = doctorExMapper.selectByExample(example);
        Doctor doctor = list.get(0);
        return doctor.getPassword().equals(password) ? true : false;
    }
}
