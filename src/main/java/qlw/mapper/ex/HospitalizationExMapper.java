package qlw.mapper.ex;

import qlw.mapper.HospitalizationMapper;
import qlw.model.Hospitalization;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/4/16.
 */
@BatisRepository
public interface HospitalizationExMapper extends HospitalizationMapper{
    long insertBackId(Hospitalization hospitalization);
}
