package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Drugorder;
import qlw.model.DrugorderExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface DrugorderMapper {
    int countByExample(DrugorderExample example);

    int deleteByExample(DrugorderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Drugorder record);

    int insertSelective(Drugorder record);

    List<Drugorder> selectByExample(DrugorderExample example);

    Drugorder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Drugorder record, @Param("example") DrugorderExample example);

    int updateByExample(@Param("record") Drugorder record, @Param("example") DrugorderExample example);

    int updateByPrimaryKeySelective(Drugorder record);

    int updateByPrimaryKey(Drugorder record);
}