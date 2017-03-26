package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Inspectitems;
import qlw.model.InspectitemsExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface InspectitemsMapper {
    int countByExample(InspectitemsExample example);

    int deleteByExample(InspectitemsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Inspectitems record);

    int insertSelective(Inspectitems record);

    List<Inspectitems> selectByExample(InspectitemsExample example);

    Inspectitems selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Inspectitems record, @Param("example") InspectitemsExample example);

    int updateByExample(@Param("record") Inspectitems record, @Param("example") InspectitemsExample example);

    int updateByPrimaryKeySelective(Inspectitems record);

    int updateByPrimaryKey(Inspectitems record);
}