package cn.prozhu.ssm.mapper.pointrecord;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.prozhu.ssm.model.pointrecord.PointRecord;
import cn.prozhu.ssm.model.pointrecord.PointRecordExample;

public interface PointRecordMapper {
    int countByExample(PointRecordExample example);

    int deleteByExample(PointRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PointRecord record);

    int insertSelective(PointRecord record);

    List<PointRecord> selectByExampleWithRowbounds(PointRecordExample example, RowBounds rowBounds);

    List<PointRecord> selectByExample(PointRecordExample example);

    PointRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PointRecord record, @Param("example") PointRecordExample example);

    int updateByExample(@Param("record") PointRecord record, @Param("example") PointRecordExample example);

    int updateByPrimaryKeySelective(PointRecord record);

    int updateByPrimaryKey(PointRecord record);

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