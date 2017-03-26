package qlw.mapper.ex;

import qlw.mapper.InspectionreportMapper;
import qlw.model.Inspectionreport;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/3/18.
 */
@BatisRepository
public interface InspectionreportExMapper extends InspectionreportMapper {
    long insertBackId(Inspectionreport inspectionreport);
}
