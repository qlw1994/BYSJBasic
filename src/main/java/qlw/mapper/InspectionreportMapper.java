package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Inspectionreport;
import qlw.model.InspectionreportExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface InspectionreportMapper {
    int countByExample(InspectionreportExample example);

    int deleteByExample(InspectionreportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Inspectionreport record);

    int insertSelective(Inspectionreport record);

    List<Inspectionreport> selectByExample(InspectionreportExample example);

    Inspectionreport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Inspectionreport record, @Param("example") InspectionreportExample example);

    int updateByExample(@Param("record") Inspectionreport record, @Param("example") InspectionreportExample example);

    int updateByPrimaryKeySelective(Inspectionreport record);

    int updateByPrimaryKey(Inspectionreport record);
}