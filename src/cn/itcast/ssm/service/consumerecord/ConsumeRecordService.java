package cn.itcast.ssm.service.consumerecord;

import java.util.List;
import java.util.Map;

import cn.itcast.ssm.model.consumerecord.Consumerecord;

public interface ConsumeRecordService {

    /**
     * @description: 查询所有 消费记录的条数(可以通过指定条件查询)
     * @author ：zc
     * @date ：2017-2-15 上午9:41:06 
     * @param startTime
     * @param endTime
     * @param keyword
     * @return
     */
    Integer getCount(String startTime, String endTime, String keyword, String memberId);

    /**
     * @description: 有条件的查询所有消费记录
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
    List<Consumerecord> findConsumeRecordByCondition(String memberid, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order);

    /**
     * 销售图表查询
     * @author ：zc
     * @date ：2017年3月24日 上午9:36:35 
     * @param mark : 年度、季度、月度显示图表(year, quarter, month)
     * @param markYear : 年份(2017)
     * @param time : 指定时间
     * @return
     */
    List<Map<String, Object>> consumeChart(String mark, String markYear, String time);

	List<String> findConsumeYears();

}
