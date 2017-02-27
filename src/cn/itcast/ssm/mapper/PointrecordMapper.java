package cn.itcast.ssm.mapper;

import cn.itcast.ssm.model.Pointrecord;
import cn.itcast.ssm.model.PointrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PointrecordMapper {
    int countByExample(PointrecordExample example);

    int deleteByExample(PointrecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Pointrecord record);

    int insertSelective(Pointrecord record);

    List<Pointrecord> selectByExampleWithRowbounds(PointrecordExample example, RowBounds rowBounds);

    List<Pointrecord> selectByExample(PointrecordExample example);

    Pointrecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Pointrecord record, @Param("example") PointrecordExample example);

    int updateByExample(@Param("record") Pointrecord record, @Param("example") PointrecordExample example);

    int updateByPrimaryKeySelective(Pointrecord record);

    int updateByPrimaryKey(Pointrecord record);
}