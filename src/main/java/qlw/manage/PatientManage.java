package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.PatientMapper;
import qlw.model.Patient;
import qlw.model.PatientExample;
import qlw.model.Paymentdetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/11.
 */
@Service
@Transactional(readOnly = true)
public class PatientManage extends BaseManage {
    @Autowired
    PatientMapper patientMapper;

    public List<Patient> list(Integer pageNumber, Integer pageSize, long uid) {
        PatientExample example = new PatientExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        PatientExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andDeletedateIsNull();
        return patientMapper.selectByExample(example);
    }
    public List<Patient> listAll( long uid) {
        PatientExample example = new PatientExample();
        PatientExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andDeletedateIsNull();
        return patientMapper.selectByExample(example);
    }

    public Integer count(long uid) {
        PatientExample example = new PatientExample();
        PatientExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedateIsNull();
        criteria.andUidEqualTo(uid);
        return patientMapper.countByExample(example);
    }


    /**
     * 获得相似name 数量
     *
     * @param name
     * @param uid
     * @return
     */
    public Integer countLike(String name, long uid) {
        PatientExample example = new PatientExample();
        PatientExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameEqualTo(name);
        criteria.andUidEqualTo(uid);
        criteria.andDeletedateIsNull();
        return patientMapper.countByExample(example);
    }

    /**
     * 获得相似 name 账号
     *
     * @param name
     * @return
     */
    public List<Patient> getLike(Integer pageNumber, Integer pageSize, String name, long uid) {
        PatientExample example = new PatientExample();
        PatientExample.Criteria criteria = example.createCriteria();
        if(pageNumber!=null&&pageSize!=null){
            example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        }
        name = name + "%";
        criteria.andNameEqualTo(name);
        criteria.andUidEqualTo(uid);
        criteria.andDeletedateIsNull();
        List<Patient> patient = patientMapper.selectByExample(example);
        if (patient.size() > 0) {
            return patient;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 根据就诊人名得到Patient
     *
     * @param name
     * @param uid
     * @return
     */
    public Patient getByName(String name, long uid) {
        PatientExample example = new PatientExample();
        PatientExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andUidEqualTo(uid);
        List<Patient> patient = patientMapper.selectByExample(example);
        if (patient.size() > 0) {
            return patient.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的就诊人名 有返回false
     *
     * @param name
     * @return
     */
    public Boolean haveSameName(String name, String idnumber, String guardianidnumber, long uid) {
        PatientExample example = new PatientExample();
        PatientExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if (idnumber != null && !"".equals(idnumber)){
            criteria.andIdnumberEqualTo(idnumber);
        }
        if (guardianidnumber != null && !"".equals(guardianidnumber)){
            criteria.andGuardianidnumberEqualTo(guardianidnumber);
        }
        criteria.andUidEqualTo(uid);
        List<Patient> patient = patientMapper.selectByExample(example);
        if (patient.size() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 根据id获取就诊人
     *
     * @param id
     * @return
     */
    public Patient getById(Long id) {
        return patientMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改就诊人信息
     *
     * @param patient
     * @return
     */
    public Integer update(Patient patient) {
        return patientMapper.updateByPrimaryKeySelective(patient);
    }


    /**
     * 删除就诊人
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = patientMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }




    /**
     * 保存就诊人
     *
     * @param patient
     * @return
     */
    public Patient save(Patient patient) {
        long i = patientMapper.insert(patient);
        if (i > 0) {
            return patient;
        }
        return null;
    }


}
