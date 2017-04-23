package qlw.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qlw.mapper.InspectitemsMapper;
import qlw.model.Inspectitems;
import qlw.model.InspectitemsExample;

import java.util.List;

/**
 * Created by wiki on 2017/3/18.
 */
@Service
@Transactional(readOnly =true )
public class InspectitemManage extends BaseManage{
    @Autowired
    InspectitemsMapper inspectitemsMapper;

    public List<Inspectitems> list(Integer pageNumber, Integer pageSize, long inspectionid) {
        InspectitemsExample example = new InspectitemsExample();
        example.setOrderByClause(getPage("id desc", pageNumber, pageSize));
        InspectitemsExample.Criteria criteria = example.createCriteria();
        criteria.andInspectionidEqualTo(inspectionid);
        return inspectitemsMapper.selectByExample(example);
    }


    public Integer count(long inspectionid) {
        InspectitemsExample example = new InspectitemsExample();
        InspectitemsExample.Criteria criteria = example.createCriteria();
        criteria.andInspectionidEqualTo(inspectionid);
        return inspectitemsMapper.countByExample(example);
    }


    /**
     * 根据id获取检验项目
     *
     * @param id
     * @return
     */
    public Inspectitems getById(Long id) {
        return inspectitemsMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改检验项目信息
     *
     * @param inspectitems
     * @return
     */
    @Transactional
    public Integer update(Inspectitems inspectitems) {
        return inspectitemsMapper.updateByPrimaryKeySelective(inspectitems);
    }


    /**
     * 删除检验项目
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(Long id) {
        int i = inspectitemsMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存检验项目
     *
     * @param cities
     * @return
     */
    @Transactional
    public Inspectitems save(Inspectitems cities) {
        long i = inspectitemsMapper.insert(cities);
        if (i > 0) {
            return cities;
        }
        return null;
    }
}
