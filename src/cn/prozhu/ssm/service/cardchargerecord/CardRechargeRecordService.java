package cn.prozhu.ssm.service.cardchargerecord;

import java.util.List;
import java.util.Map;

import cn.prozhu.ssm.model.cardchargerecord.Cardrechargerecord;

public interface CardRechargeRecordService {

    /**
     * @description: 查询所有 充值记录的条数(可以通过指定条件查询)
     * @author ：zc
     * @date ：2017-2-15 上午9:41:06 
     * @param startTime
     * @param endTime
     * @param keyword
     * @param memberid
     * @return
     */
    Integer getCount(String startTime, String endTime, String keyword, String memberid);

    /**
     * @description: 有条件的查询所有充值记录
     * @author ：zc
     * @date ：2017-2-15 上午9:43:41 
     * @param pageNow
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param keyword
     * @param sort
     * @param order
     * @return
     */
    List<Cardrechargerecord> findCardRechargeRecordByCondition(String memberid, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order);

    /**
     * 获取充值图表数据
     * @param mark ：年度、季度、月度显示图表(year, quarter, month)
     * @param markYear ：年份(2017)
     * @param time : 指定时间
     * @return
     */
	List<Map<String, Object>> rechargeChart(String mark, String markYear,
			String time);

	/**
	 * 获取会员卡充值表中的所有年份
	 * @return
	 */
	List<String> findRechargeYears();

}
