package qlw.mapper.ex;

import qlw.mapper.HospitalMapper;
import qlw.model.Hospital;
import qlw.model.HospitalExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

/**
 * Created by wiki on 2017/3/14.
 */
@BatisRepository
public interface HospitalExMapper extends HospitalMapper{
    int countByExampleEx(HospitalExample example);

    List<Hospital> selectByExampleEx(HospitalExample example);
}
