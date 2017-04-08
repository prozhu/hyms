package cn.prozhu.ssm.mapper.cardchargerecord;

import cn.prozhu.ssm.model.cardchargerecord.Cardrechargerecord;
import cn.prozhu.ssm.model.cardchargerecord.CardrechargerecordExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CardrechargerecordMapper {
    int countByExample(CardrechargerecordExample example);

    int deleteByExample(CardrechargerecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Cardrechargerecord record);

    int insertSelective(Cardrechargerecord record);

    List<Cardrechargerecord> selectByExampleWithRowbounds(CardrechargerecordExample example, RowBounds rowBounds);

    List<Cardrechargerecord> selectByExample(CardrechargerecordExample example);

    Cardrechargerecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Cardrechargerecord record, @Param("example") CardrechargerecordExample example);

    int updateByExample(@Param("record") Cardrechargerecord record, @Param("example") CardrechargerecordExample example);

    int updateByPrimaryKeySelective(Cardrechargerecord record);

    int updateByPrimaryKey(Cardrechargerecord record);

    /**
     * 按照 “年度” 查询充值图表
     * @return
     */
	List<Map<String, Object>> rechargeChartByYear();

	/**
     * 按照 “季度” 查询充值图表
     * @return
     */
	List<Map<String, Object>> rechargeChartByQuarter(@Param("markYear")String markYear);

	/**
     * 按照 “月度” 查询充值图表
     * @return
     */
	List<Map<String, Object>> rechargeChartByMonth(@Param("markYear")String markYear);

	/**
     * 按照 “周度” 查询充值图表
     * @return
     */
	List<Map<String, Object>> rechargeChartByWeek(@Param("time")String time);

	/**
	 * 查询充值记录表中存在的年份
	 * @return
	 */
	List<String> selectYears();
}