package cn.prozhu.ssm.mapper.cardrecord;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.prozhu.ssm.model.cardrecord.Cardrecord;
import cn.prozhu.ssm.model.cardrecord.CardrecordExample;

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