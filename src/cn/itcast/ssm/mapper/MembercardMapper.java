package cn.itcast.ssm.mapper;

import cn.itcast.ssm.model.Membercard;
import cn.itcast.ssm.model.MembercardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MembercardMapper {
    int countByExample(MembercardExample example);

    int deleteByExample(MembercardExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Membercard record);

    int insertSelective(Membercard record);

    List<Membercard> selectByExampleWithRowbounds(MembercardExample example, RowBounds rowBounds);

    List<Membercard> selectByExample(MembercardExample example);

    Membercard selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Membercard record, @Param("example") MembercardExample example);

    int updateByExample(@Param("record") Membercard record, @Param("example") MembercardExample example);

    int updateByPrimaryKeySelective(Membercard record);

    int updateByPrimaryKey(Membercard record);
}