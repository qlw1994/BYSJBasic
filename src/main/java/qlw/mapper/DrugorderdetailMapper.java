package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Drugorderdetail;
import qlw.model.DrugorderdetailExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface DrugorderdetailMapper {
    int countByExample(DrugorderdetailExample example);

    int deleteByExample(DrugorderdetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Drugorderdetail record);

    int insertSelective(Drugorderdetail record);

    List<Drugorderdetail> selectByExample(DrugorderdetailExample example);

    Drugorderdetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Drugorderdetail record, @Param("example") DrugorderdetailExample example);

    int updateByExample(@Param("record") Drugorderdetail record, @Param("example") DrugorderdetailExample example);

    int updateByPrimaryKeySelective(Drugorderdetail record);

    int updateByPrimaryKey(Drugorderdetail record);
}