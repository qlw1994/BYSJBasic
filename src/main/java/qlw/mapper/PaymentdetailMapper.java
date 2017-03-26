package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Paymentdetail;
import qlw.model.PaymentdetailExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface PaymentdetailMapper {
    int countByExample(PaymentdetailExample example);

    int deleteByExample(PaymentdetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Paymentdetail record);

    int insertSelective(Paymentdetail record);

    List<Paymentdetail> selectByExample(PaymentdetailExample example);

    Paymentdetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Paymentdetail record, @Param("example") PaymentdetailExample example);

    int updateByExample(@Param("record") Paymentdetail record, @Param("example") PaymentdetailExample example);

    int updateByPrimaryKeySelective(Paymentdetail record);

    int updateByPrimaryKey(Paymentdetail record);
}