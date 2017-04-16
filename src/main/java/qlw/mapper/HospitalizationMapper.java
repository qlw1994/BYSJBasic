package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Hospitalization;
import qlw.model.HospitalizationExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface HospitalizationMapper {
    int countByExample(HospitalizationExample example);

    int deleteByExample(HospitalizationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Hospitalization record);

    int insertSelective(Hospitalization record);

    List<Hospitalization> selectByExample(HospitalizationExample example);

    Hospitalization selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Hospitalization record, @Param("example") HospitalizationExample example);

    int updateByExample(@Param("record") Hospitalization record, @Param("example") HospitalizationExample example);

    int updateByPrimaryKeySelective(Hospitalization record);

    int updateByPrimaryKey(Hospitalization record);
}