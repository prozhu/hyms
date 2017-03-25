package cn.itcast.ssm.mapper;

import cn.itcast.ssm.model.Pointrecord;
import cn.itcast.ssm.model.PointrecordExample;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询积分表中所有的年份
     * @author ：zc
     * @date ：2017年3月24日 下午7:29:50 
     * @return
     */
	List<String> selectYears();
	/**
	 * "年度" 分组查询图表
	 * @author ：zc
	 * @date ：2017年3月24日 下午7:30:45 
	 * @return
	 */
	List<Map<String, Object>> pointChartByYear();

	/**
	 * "季度" 分组查询图表
	 * @author ：zc
	 * @date ：2017年3月24日 下午7:31:27 
	 * @param markYear
	 * @return
	 */
	List<Map<String, Object>> pointChartByQuarter(@Param("markYear")String markYear);

	/**
	 * "月度" 分组查询图表
	 * @author ：zc
	 * @date ：2017年3月24日 下午7:31:51 
	 * @param markYear
	 * @return
	 */
	List<Map<String, Object>> pointChartByMonth(@Param("markYear")String markYear);

	/**
	 * "周度" 分组查询图表
	 * @author ：zc
	 * @date ：2017年3月24日 下午7:32:11 
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> pointChartByWeek(@Param("time")String time);
}