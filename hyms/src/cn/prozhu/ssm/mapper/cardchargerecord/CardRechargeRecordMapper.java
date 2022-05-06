package cn.prozhu.ssm.mapper.cardchargerecord;

import cn.prozhu.ssm.model.cardchargerecord.CardRechargeRecord;
import cn.prozhu.ssm.model.cardchargerecord.CardRechargeRecordExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CardRechargeRecordMapper {
    int countByExample(CardRechargeRecordExample example);

    int deleteByExample(CardRechargeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CardRechargeRecord record);

    int insertSelective(CardRechargeRecord record);

    List<CardRechargeRecord> selectByExampleWithRowbounds(CardRechargeRecordExample example, RowBounds rowBounds);

    List<CardRechargeRecord> selectByExample(CardRechargeRecordExample example);

    CardRechargeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CardRechargeRecord record, @Param("example") CardRechargeRecordExample example);

    int updateByExample(@Param("record") CardRechargeRecord record, @Param("example") CardRechargeRecordExample example);

    int updateByPrimaryKeySelective(CardRechargeRecord record);

    int updateByPrimaryKey(CardRechargeRecord record);

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