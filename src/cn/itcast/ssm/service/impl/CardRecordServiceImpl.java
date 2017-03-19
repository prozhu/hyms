package cn.itcast.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import cn.itcast.ssm.mapper.CardrecordMapper;
import cn.itcast.ssm.model.Cardrecord;
import cn.itcast.ssm.model.CardrecordExample;
import cn.itcast.ssm.service.CardRecordService;
import cn.itcast.ssm.util.RandomUtils;
import cn.itcast.ssm.util.StringUitl;

public class CardRecordServiceImpl implements CardRecordService {

    @Autowired
    private CardrecordMapper cardrecordMapper;


    @Override
    public Integer getCount(String startTime, String endTime, String keyword, String memberid) {
    	List<Object> condition = buildSelectCondition(null, null, startTime, endTime, keyword, null, null, memberid);
        return cardrecordMapper.countByExample((CardrecordExample)condition.get(0));
    }

    /**
     * 为查询会员卡记录、挂失记录构建查询条件（可分页，可关键字搜索）
     * @param pageNow
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param keyword
     * @param sort
     * @param order
     * @return 集合中返回三个参数： 
     *  		1.：  CardrecordExample
     *  		2.：  rowBounds
     *  		3.: criteria
     */
    private List<Object> buildSelectCondition(String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order, String memberid) {
    	ArrayList<Object> condition = new ArrayList<Object>();
        if (pageNow == null) {
            pageNow = "1";
        }
        if (pageSize == null) {
            pageSize = "100000";
        }
        
        CardrecordExample cardrecordExample = new CardrecordExample();
        CardrecordExample.Criteria criteria = cardrecordExample.createCriteria();
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            cardrecordExample.setOrderByClause(sort + " " + order);
        }
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        }
        
        // 会员编号
        if (!StringUitl.isNullOrEmpty(memberid)) {
            criteria.andMemberidEqualTo(memberid);
        }
        
        //会员名称
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) *  Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        condition.add(cardrecordExample);
        condition.add(rowBounds);
        condition.add(criteria);
        return condition;
    }
    
    @Cacheable(value="CardRecord.findCardRecordByMemberIdAndCondition", 
    		key = "'findCardRecordByMemberIdAndCondition'+#sort + #order+#pageNow+#pageSize+#memberid",
    		condition = "null == #startTime and null == #endTime and null == #keyword ")
    @Override
    public List<Cardrecord> findCardRecordByCondition(String memberid, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order) {
    	List<Object> condition = buildSelectCondition(pageNow, pageSize, startTime, endTime, keyword, sort, order, memberid);
        List<Cardrecord> list = cardrecordMapper.selectByExampleWithRowbounds((CardrecordExample)condition.get(0), (RowBounds)condition.get(1));
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }

}
