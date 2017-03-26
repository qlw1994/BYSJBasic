package qlw.mapper.ex;

import qlw.mapper.SchedulingMapper;
import qlw.model.Scheduling;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/3/20.
 */
@BatisRepository
public interface SchedulingExMapper extends SchedulingMapper{
    long insertBackId(Scheduling scheduling);
}
