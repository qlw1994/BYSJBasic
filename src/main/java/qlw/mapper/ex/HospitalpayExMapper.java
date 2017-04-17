package qlw.mapper.ex;

import qlw.mapper.HospitalpayMapper;
import qlw.model.Hospitalpay;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/4/16.
 */
@BatisRepository
public interface HospitalpayExMapper extends HospitalpayMapper {
    long insertBackId(Hospitalpay hospitalpay);
}
