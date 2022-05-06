package cn.prozhu.ssm.mapper.cardrecord;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.prozhu.ssm.model.cardrecord.CardRecord;
import cn.prozhu.ssm.model.cardrecord.CardRecordExample;

public interface CardRecordMapper {
    int countByExample(CardRecordExample example);

    int deleteByExample(CardRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CardRecord record);

    int insertSelective(CardRecord record);

    List<CardRecord> selectByExampleWithRowbounds(CardRecordExample example, RowBounds rowBounds);

    List<CardRecord> selectByExample(CardRecordExample example);

    CardRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CardRecord record, @Param("example") CardRecordExample example);

    int updateByExample(@Param("record") CardRecord record, @Param("example") CardRecordExample example);

    int updateByPrimaryKeySelective(CardRecord record);

    int updateByPrimaryKey(CardRecord record);
}