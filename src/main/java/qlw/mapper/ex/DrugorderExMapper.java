package qlw.mapper.ex;

import qlw.mapper.DrugorderMapper;
import qlw.model.Drugorder;
import qlw.model.DrugorderExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

/**
 * Created by wiki on 2017/3/15.
 */
@BatisRepository
public interface DrugorderExMapper extends DrugorderMapper {
    int countByExampleEx(DrugorderExample example);

    List<Drugorder> selectByExampleEx(DrugorderExample example);

    long insertBackId(Drugorder drugorder);
}
