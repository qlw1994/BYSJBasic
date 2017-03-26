package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.FixedschedulingMapper;
import qlw.model.Fixedscheduling;
import qlw.model.FixedschedulingExample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiki on 2017/3/20.
 */
@Service
@Transactional(readOnly = true)
public class FixedschedulingManage extends BaseManage {
    @Autowired
    FixedschedulingMapper fixedschedulingMapper;

    public List<Fixedscheduling> list(long doctorid) {
        FixedschedulingExample example = new FixedschedulingExample();
        FixedschedulingExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("timeflag asc,week asc");
        criteria.andDoctoridEqualTo(doctorid);
        return fixedschedulingMapper.selectByExample(example);
    }


    public Integer count(long doctorid) {
        FixedschedulingExample example = new FixedschedulingExample();
        FixedschedulingExample.Criteria criteria = example.createCriteria();
        criteria.andDoctoridEqualTo(doctorid);
        return fixedschedulingMapper.countByExample(example);
    }


    /**
     * 根据固定排班名得到Fixedscheduling
     *
     * @param doctorid
     * @return
     */
    public Fixedscheduling getByDoctorid(Long doctorid) {
        FixedschedulingExample example = new FixedschedulingExample();
        FixedschedulingExample.Criteria criteria = example.createCriteria();
        criteria.andDoctoridEqualTo(doctorid);
        List<Fixedscheduling> cities = fixedschedulingMapper.selectByExample(example);
        if (cities.size() > 0) {
            return cities.get(0);
        } else {
            return null;
        }
    }


    /**
     * 根据id获取固定排班
     *
     * @param id
     * @return
     */
    public Fixedscheduling getById(Long id) {
        return fixedschedulingMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据日期获取固定排班
     *
     * @param week
     * @return
     */
    public List<Fixedscheduling> getByWeek(Long doctorid, int week) {
        FixedschedulingExample example = new FixedschedulingExample();
        FixedschedulingExample.Criteria criteria = example.createCriteria();
        criteria.andDoctoridEqualTo(doctorid);
        criteria.andWeekEqualTo(week);
        return fixedschedulingMapper.selectByExample(example);
    }

    /**
     * 修改固定排班信息
     *
     * @param fixedscheduling
     * @return
     */
    public Integer update(Fixedscheduling fixedscheduling) {
        return fixedschedulingMapper.updateByPrimaryKeySelective(fixedscheduling);
    }


    /**
     * 删除固定排班
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        int i = fixedschedulingMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除固定排班
     *
     * @param doctorid
     * @return
     */
    public boolean deleteByDoctorid(Long doctorid) {
        FixedschedulingExample example = new FixedschedulingExample();
        FixedschedulingExample.Criteria criteria = example.createCriteria();
        criteria.andDoctoridEqualTo(doctorid);
        int i = fixedschedulingMapper.deleteByExample(example);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存固定排班
     *
     * @param cities
     * @return
     */
    public Fixedscheduling save(Fixedscheduling cities) {
        long i = fixedschedulingMapper.insert(cities);
        if (i > 0) {
            return cities;
        }
        return null;
    }

    /**
     * 检查重复
     *
     * @param fixedscheduling
     * @return
     */
    public boolean hasSame(Fixedscheduling fixedscheduling) {
        FixedschedulingExample example = new FixedschedulingExample();
        FixedschedulingExample.Criteria criteria = example.createCriteria();
        criteria.andStarttimeEqualTo(fixedscheduling.getStarttime());
        criteria.andEndtimeEqualTo(fixedscheduling.getEndtime());
        criteria.andDateEqualTo(fixedscheduling.getDate());
        List<Fixedscheduling> fixedschedulings = fixedschedulingMapper.selectByExample(example);
        if (fixedschedulings.size() > 0) {
            return true;
        }
        return false;
    }
}
