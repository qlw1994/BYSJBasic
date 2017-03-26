package qlw.mapper.ex;

import qlw.mapper.DrugMapper;
import qlw.model.Drug;
import qlw.model.DrugExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

/**
 * Created by wiki on 2017/3/14.
 */
@BatisRepository
public interface DrugExMapper extends DrugMapper{
    int countByExampleEx(DrugExample example);
    List<Drug> selectByExampleEx(DrugExample example);
}
