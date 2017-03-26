package qlw.mapper.ex;

import qlw.mapper.PaymentMapper;
import qlw.model.Payment;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/3/22.
 */
@BatisRepository
public interface PaymentExMapper extends PaymentMapper {
    long insertBackId(Payment payment);
}
