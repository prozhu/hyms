package cn.prozhu.ssm.mapper.membercard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.prozhu.ssm.model.membercard.MemberCard;
import cn.prozhu.ssm.model.membercard.MemberCardExample;

public interface MemberCardMapper {
    int countByExample(MemberCardExample example);

    int deleteByExample(MemberCardExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    List<MemberCard> selectByExampleWithRowbounds(MemberCardExample example, RowBounds rowBounds);

    List<MemberCard> selectByExample(MemberCardExample example);

    MemberCard selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemberCard record, @Param("example") MemberCardExample example);

    int updateByExample(@Param("record") MemberCard record, @Param("example") MemberCardExample example);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);

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