package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Hospitalpay;
import qlw.model.HospitalpayExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface HospitalpayMapper {
    int countByExample(HospitalpayExample example);

    int deleteByExample(HospitalpayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Hospitalpay record);

    int insertSelective(Hospitalpay record);

    List<Hospitalpay> selectByExample(HospitalpayExample example);

    Hospitalpay selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Hospitalpay record, @Param("example") HospitalpayExample example);

    int updateByExample(@Param("record") Hospitalpay record, @Param("example") HospitalpayExample example);

    int updateByPrimaryKeySelective(Hospitalpay record);

    int updateByPrimaryKey(Hospitalpay record);
}