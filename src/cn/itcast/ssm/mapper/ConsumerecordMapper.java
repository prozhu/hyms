package cn.itcast.ssm.mapper;

import cn.itcast.ssm.model.Consumerecord;
import cn.itcast.ssm.model.ConsumerecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ConsumerecordMapper {
    int countByExample(ConsumerecordExample example);

    int deleteByExample(ConsumerecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Consumerecord record);

    int insertSelective(Consumerecord record);

    List<Consumerecord> selectByExampleWithRowbounds(ConsumerecordExample example, RowBounds rowBounds);

    List<Consumerecord> selectByExample(ConsumerecordExample example);

    Consumerecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Consumerecord record, @Param("example") ConsumerecordExample example);

    int updateByExample(@Param("record") Consumerecord record, @Param("example") ConsumerecordExample example);

    int updateByPrimaryKeySelective(Consumerecord record);

    int updateByPrimaryKey(Consumerecord record);
}