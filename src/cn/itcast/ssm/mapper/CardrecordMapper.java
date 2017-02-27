package cn.itcast.ssm.mapper;

import cn.itcast.ssm.model.Cardrecord;
import cn.itcast.ssm.model.CardrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CardrecordMapper {
    int countByExample(CardrecordExample example);

    int deleteByExample(CardrecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Cardrecord record);

    int insertSelective(Cardrecord record);

    List<Cardrecord> selectByExampleWithRowbounds(CardrecordExample example, RowBounds rowBounds);

    List<Cardrecord> selectByExample(CardrecordExample example);

    Cardrecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Cardrecord record, @Param("example") CardrecordExample example);

    int updateByExample(@Param("record") Cardrecord record, @Param("example") CardrecordExample example);

    int updateByPrimaryKeySelective(Cardrecord record);

    int updateByPrimaryKey(Cardrecord record);
}