package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Alipayaccount;
import qlw.model.AlipayaccountExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;
@BatisRepository
public interface AlipayaccountMapper {
    int countByExample(AlipayaccountExample example);

    int deleteByExample(AlipayaccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Alipayaccount record);

    int insertSelective(Alipayaccount record);

    List<Alipayaccount> selectByExample(AlipayaccountExample example);

    Alipayaccount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Alipayaccount record, @Param("example") AlipayaccountExample example);

    int updateByExample(@Param("record") Alipayaccount record, @Param("example") AlipayaccountExample example);

    int updateByPrimaryKeySelective(Alipayaccount record);

    int updateByPrimaryKey(Alipayaccount record);
}