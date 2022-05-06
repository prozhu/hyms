package cn.prozhu.ssm.service.cardrecord;

import java.util.List;

import cn.prozhu.ssm.model.cardrecord.CardRecord;

public interface CardRecordService {

    /**
     * @description: 查询所有会员卡激活或者挂失记录条数(可以通过指定条件查询)
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
     * @description: 有条件的查询会员卡激活或者挂失记录信息
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
    List<CardRecord> findCardRecordByCondition(String memberid, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order);

}
