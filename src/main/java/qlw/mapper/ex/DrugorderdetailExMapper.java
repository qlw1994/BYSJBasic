package qlw.mapper.ex;

import qlw.mapper.DrugorderdetailMapper;
import qlw.model.Drugorderdetail;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/3/15.
 */
@BatisRepository
public interface DrugorderdetailExMapper extends DrugorderdetailMapper {
    long insertBackId(Drugorderdetail drugorderdetail);
}
