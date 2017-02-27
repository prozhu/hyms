package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.model.Consumerecord;

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
    List<Consumerecord> findConsumeRecordByCondition(String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order);

    /**
     * @description: 通过会员编号查询所有消费记录(可分页、可搜索)
     * @author ：zc
     * @date ：2017-2-15 下午7:42:34 
     * @param memberid
     * @return
     */
    List<Consumerecord> findConsumeRecordByMemberIdAndCondition(String memberid
    		, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order);
}
