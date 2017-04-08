package cn.prozhu.ssm.mapper.consumerecord;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.prozhu.ssm.model.consumerecord.Consumerecord;
import cn.prozhu.ssm.model.consumerecord.ConsumerecordExample;

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