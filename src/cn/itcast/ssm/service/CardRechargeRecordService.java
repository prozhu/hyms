package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.model.Cardrechargerecord;

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

}
