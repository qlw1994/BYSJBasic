package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Appointment;
import qlw.model.AppointmentExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface AppointmentMapper {
    int countByExample(AppointmentExample example);

    int deleteByExample(AppointmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Appointment record);

    int insertSelective(Appointment record);

    List<Appointment> selectByExample(AppointmentExample example);

    Appointment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Appointment record, @Param("example") AppointmentExample example);

    int updateByExample(@Param("record") Appointment record, @Param("example") AppointmentExample example);

    int updateByPrimaryKeySelective(Appointment record);

    int updateByPrimaryKey(Appointment record);
}