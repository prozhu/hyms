package cn.itcast.ssm.mapper;

import cn.itcast.ssm.model.Cardrechargerecord;
import cn.itcast.ssm.model.CardrechargerecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CardrechargerecordMapper {
    int countByExample(CardrechargerecordExample example);

    int deleteByExample(CardrechargerecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Cardrechargerecord record);

    int insertSelective(Cardrechargerecord record);

    List<Cardrechargerecord> selectByExampleWithRowbounds(CardrechargerecordExample example, RowBounds rowBounds);

    List<Cardrechargerecord> selectByExample(CardrechargerecordExample example);

    Cardrechargerecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Cardrechargerecord record, @Param("example") CardrechargerecordExample example);

    int updateByExample(@Param("record") Cardrechargerecord record, @Param("example") CardrechargerecordExample example);

    int updateByPrimaryKeySelective(Cardrechargerecord record);

    int updateByPrimaryKey(Cardrechargerecord record);
}