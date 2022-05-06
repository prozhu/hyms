package cn.prozhu.ssm.mapper.consumerecord;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.prozhu.ssm.model.consumerecord.ConsumeRecord;
import cn.prozhu.ssm.model.consumerecord.ConsumeRecordExample;

public interface ConsumeRecordMapper {
    int countByExample(ConsumeRecordExample example);

    int deleteByExample(ConsumeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ConsumeRecord record);

    int insertSelective(ConsumeRecord record);

    List<ConsumeRecord> selectByExampleWithRowbounds(ConsumeRecordExample example, RowBounds rowBounds);

    List<ConsumeRecord> selectByExample(ConsumeRecordExample example);

    ConsumeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ConsumeRecord record, @Param("example") ConsumeRecordExample example);

    int updateByExample(@Param("record") ConsumeRecord record, @Param("example") ConsumeRecordExample example);

    int updateByPrimaryKeySelective(ConsumeRecord record);

    int updateByPrimaryKey(ConsumeRecord record);
    
    /**
     * 按 "年度"查询销售图表
     * @author ：zc
     * @date ：2017年3月24日 上午9:25:06 
     * @return
     */
    List<Map<String, Object>>  consumeChartByYear();
    
    /**
     * 按 "季度"查询销售图表
     * @author ：zc
     * @param markYear :年份(2017)
     * @date ：2017年3月24日 上午9:25:06 
     * @return
     */
    List<Map<String, Object>>  consumeChartByQuarter(@Param("markYear")String markYear);
    
    /**
     * 按 "月度"查询销售图表
     * @author ：zc
     * @param markYear : 年份(2017)
     * @date ：2017年3月24日 上午9:25:06 
     * @return
     */
    List<Map<String, Object>>  consumeChartByMonth(@Param("markYear")String markYear);
    
    /**
     * 查询所有的年份
     * @author ：zc
     * @date ：2017年3月24日 上午10:20:24 
     * @return
     */
    List<String> selectYears();

    /**
     * 按 "指定时间的周度"查询销售图表
     * @author ：zc
     * @date ：2017年3月24日 下午3:03:42 
     * @param time
     * @return
     */
	List<Map<String, Object>> consumeChartByWeek(@Param("time")String time);
}