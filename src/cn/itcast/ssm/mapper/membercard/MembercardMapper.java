package cn.itcast.ssm.mapper.membercard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.itcast.ssm.model.membercard.Membercard;
import cn.itcast.ssm.model.membercard.MembercardExample;

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

    /**
     * 通过 "年度"查询会员卡图表
     * @author ：zc
     * @date ：2017年3月28日 下午7:10:49 
     * @return
     */
	List<Map<String, Object>> memberCardChartByYear();

	/**
     * 通过 "季度"查询会员卡图表
     * @author ：zc
     * @date ：2017年3月28日 下午7:10:49 
     * @return
     */
	List<Map<String, Object>> memberCardChartByQuarter(@Param("markYear")String markYear);

	/**
     * 通过 "月度"查询会员卡图表
     * @author ：zc
     * @date ：2017年3月28日 下午7:10:49 
     * @return
     */
	List<Map<String, Object>> memberCardChartByMonth(@Param("markYear")String markYear);

	/**
     * 通过 "周度"查询会员卡图表
     * @author ：zc
     * @date ：2017年3月28日 下午7:10:49 
     * @return
     */
	List<Map<String, Object>> memberCardChartByWeek(@Param("time")String time);

	/**
	 *  获取会员卡表中的所有年份
	 * @author ：zc
	 * @date ：2017年3月28日 下午7:14:37 
	 * @return
	 */
	List<String> selectYears();
}