package qlw.mapper.ex;

import qlw.mapper.DoctorMapper;
import qlw.model.Doctor;
import qlw.model.DoctorExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

/**
 * Created by wiki on 2017/3/3.
 */
@BatisRepository
public interface DoctorExMapper extends DoctorMapper {
    long insertBackId(Doctor doctor);
    int countByExampleEx(DoctorExample example);
    List<Doctor> selectByExampleEx(DoctorExample example);
}
