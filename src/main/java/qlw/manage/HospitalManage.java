package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.ex.HospitalExMapper;
import qlw.model.Hospital;
import qlw.model.HospitalExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/7.
 */
@Service
@Transactional(readOnly = true)
public class HospitalManage extends BaseManage {
    @Autowired
    HospitalExMapper hospitalExMapper;

    /**
     * 医院列表   按(省份 城市 地区）
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Hospital> list(Integer pageNumber, Integer pageSize, String province, String city, String area) {
        HospitalExample example = new HospitalExample();
        if (pageNumber != null && pageSize != null) {
            example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        }
        HospitalExample.Criteria criteria = example.createCriteria();
        if (province != null && !"".equals(province)) {
            criteria.andProvinceEqualTo(province);
        }
        if (city != null && !"".equals(city)) {
            criteria.andCityEqualTo(city);
        }
        if (area != null && !"".equals(area)) {
            criteria.andAreaEqualTo(area);
        }
        criteria.andStatusEqualTo(1);
        return hospitalExMapper.selectByExample(example);
    }

    /**
     * 所有医院数量 按(省份 城市 地区）
     *
     * @return
     */
    public Integer count(String province, String city, String area) {
        HospitalExample example = new HospitalExample();
        HospitalExample.Criteria criteria = example.createCriteria();
        if (province != null && !"".equals(province)) {
            criteria.andProvinceEqualTo(province);
        }
        if (city != null && !"".equals(city)) {
            criteria.andCityEqualTo(city);
        }
        if (area != null && !"".equals(area)) {
            criteria.andAreaEqualTo(area);
        }
        criteria.andStatusEqualTo(1);
        return hospitalExMapper.countByExample(example);
    }


    /**
     * 获得相似 医院名 数量 按(省份 城市 地区）
     *
     * @param name
     * @return
     */
    public Integer countLike(String name, String province, String city, String area) {
        HospitalExample example = new HospitalExample();
        HospitalExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        if (province != null && !"".equals(province)) {
            criteria.andProvinceEqualTo(province);
        }
        if (city != null && !"".equals(city)) {
            criteria.andCityEqualTo(city);
        }
        if (area != null && !"".equals(area)) {
            criteria.andAreaEqualTo(area);
        }
        criteria.andStatusEqualTo(1);
        return hospitalExMapper.countByExample(example);
    }

    /**
     * 获得相似 医院名 按(省份 城市 地区）
     *
     * @param name
     * @param area
     * @return
     */
    public List<Hospital> getLike(Integer pageNumber, Integer pageSize, String name, String province, String city, String area) {
        HospitalExample example = new HospitalExample();
        if (pageNumber != null && pageSize != null) {
            example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        }
        HospitalExample.Criteria criteria = example.createCriteria();
        name = name + "%";
        criteria.andNameLike(name);
        if (province != null && !"".equals(province)) {
            criteria.andProvinceEqualTo(province);
        }
        if (city != null && !"".equals(city)) {
            criteria.andCityEqualTo(city);
        }
        if (area != null && !"".equals(area)) {
            criteria.andAreaEqualTo(area);
        }
        criteria.andStatusEqualTo(1);
        List<Hospital> hospitals = hospitalExMapper.selectByExample(example);
        if (hospitals.size() > 0) {
            return hospitals;
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 得到Hospital   按(省份 城市 地区）
     *
     * @param area
     * @param name
     * @return
     */
    public Hospital getByName(String name, String province, String city, String area) {
        HospitalExample example = new HospitalExample();
        HospitalExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if (province != null && !"".equals(province)) {
            criteria.andProvinceEqualTo(province);
        }
        if (city != null && !"".equals(city)) {
            criteria.andCityEqualTo(city);
        }
        if (area != null && !"".equals(area)) {
            criteria.andAreaEqualTo(area);
        }
        criteria.andStatusEqualTo(1);
        List<Hospital> hospitals = hospitalExMapper.selectByExample(example);
        if (hospitals.size() > 0) {
            return hospitals.get(0);
        } else {
            return null;
        }
    }


    /**
     * 是否有相同的医院名 有返回false
     *
     * @param area
     * @param name
     * @return 存在返回true 否则false
     */
    public Boolean haveSameName(String name, String province, String city, String area) {
        HospitalExample example = new HospitalExample();
        HospitalExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if (province != null && !"".equals(province)) {
            criteria.andProvinceEqualTo(province);
        }
        if (city != null && !"".equals(city)) {
            criteria.andCityEqualTo(city);
        }
        if (area != null && !"".equals(area)) {
            criteria.andAreaEqualTo(area);
        }
        criteria.andStatusEqualTo(1);
        List<Hospital> hospitals = hospitalExMapper.selectByExample(example);
        if (hospitals.size() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 根据id获取医院
     *
     * @param id
     * @return
     */
    public Hospital getById(Long id) {
        return hospitalExMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改医院信息
     *
     * @param hospital
     * @return
     */
    public Integer update(Hospital hospital) {
        return hospitalExMapper.updateByPrimaryKeySelective(hospital);
    }


    /**
     * 删除医院
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = hospitalExMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存医院
     *
     * @param hospital
     * @return
     */
    public Hospital save(Hospital hospital) {
        long i = hospitalExMapper.insert(hospital);
        if (i > 0) {
            return hospital;
        }
        return null;
    }

    /**
     * 按条件更新授权标签
     *
     * @param hospital
     * @param example
     * @return
     */
    public Integer updateByExampleSelective(Hospital hospital, HospitalExample example) {
        return hospitalExMapper.updateByExampleSelective(hospital, example);
    }
}
