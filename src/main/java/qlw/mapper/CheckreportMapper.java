package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Checkreport;
import qlw.model.CheckreportExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface CheckreportMapper {
    int countByExample(CheckreportExample example);

    int deleteByExample(CheckreportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Checkreport record);

    int insertSelective(Checkreport record);

    List<Checkreport> selectByExample(CheckreportExample example);

    Checkreport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Checkreport record, @Param("example") CheckreportExample example);

    int updateByExample(@Param("record") Checkreport record, @Param("example") CheckreportExample example);

    int updateByPrimaryKeySelective(Checkreport record);

    int updateByPrimaryKey(Checkreport record);
}