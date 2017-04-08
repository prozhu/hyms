package cn.prozhu.ssm.service.cardchargerecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import cn.prozhu.ssm.mapper.cardchargerecord.CardrechargerecordMapper;
import cn.prozhu.ssm.model.cardchargerecord.Cardrechargerecord;
import cn.prozhu.ssm.model.cardchargerecord.CardrechargerecordExample;
import cn.prozhu.ssm.util.RandomUtils;
import cn.prozhu.ssm.util.StringUitl;

public class CardRechargeRecordServiceImpl implements CardRechargeRecordService {
    
    @Autowired
    private CardrechargerecordMapper cardrechargerecordMapper;

    @Override
    public Integer getCount(String startTime, String endTime, String keyword, String memberid) {
    	List<Object> condition = buildSelectCondition(null, null, startTime, endTime, keyword, null, null, memberid);
        return cardrechargerecordMapper.countByExample((CardrechargerecordExample)condition.get(0));
    }

    /**
     * 为查询会员卡充值记录构建查询条件（可分页，可关键字搜索）
     * @param pageNow
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param keyword
     * @param sort
     * @param order
     * @return 集合中返回三个参数： 
     *  		1.：  CardrechargerecordExample
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
        CardrechargerecordExample cardrechargerecordExample  = new CardrechargerecordExample();
        CardrechargerecordExample.Criteria criteria = cardrechargerecordExample.createCriteria();
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            cardrechargerecordExample.setOrderByClause(sort + " " + order);
        }
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        }
        //会员编号
        if (!StringUitl.isNullOrEmpty(memberid)) {
            criteria.andMemberidEqualTo(memberid);
        }
        //会员名称
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) *  Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        condition.add(cardrechargerecordExample);
        condition.add(rowBounds);
        condition.add(criteria);
    	return condition;
    }
    
    @Cacheable(value="CardRechargeRecord.findCardRechargeRecordByMemberIdAndCondition", 
    		key = "'CardRechargeRecord.findCardRechargeRecordByCondition'+#sort + #order+#pageNow+#pageSize+#memberid",
    		condition = "null == #startTime and null == #endTime and null == #keyword")
    @Override
    public List<Cardrechargerecord> findCardRechargeRecordByCondition(String memberid, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order) {
        List<Object> condition = buildSelectCondition(pageNow, pageSize, startTime, endTime, keyword, sort, order, memberid);
        List<Cardrechargerecord> list = cardrechargerecordMapper.selectByExampleWithRowbounds((CardrechargerecordExample)condition.get(0), (RowBounds)condition.get(1));
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }

	@Override
	public List<Map<String, Object>> rechargeChart(String mark,
			String markYear, String time) {
		if (StringUitl.isNullOrEmpty(mark.trim())) {
			return null;
		}
		List<Map<String, Object>> map = null;
		if ("year".equals(mark.trim())) {
			map = cardrechargerecordMapper.rechargeChartByYear();
		} else if ("quarter".equals(mark.trim())) {
			map = cardrechargerecordMapper.rechargeChartByQuarter(markYear);
		} else if ("month".equals(mark.trim())) {
			map = cardrechargerecordMapper.rechargeChartByMonth(markYear);
		} else {
				map = cardrechargerecordMapper.rechargeChartByWeek(StringUitl.isNullOrEmpty(time)? RandomUtils.formatTime1(new Date()) : time);
		}
		if (StringUitl.isNullOrEmpty(map)) {
			return null;
		}
		return map;
	}

	@Override
	public List<String> findRechargeYears() {
		List<String> list = cardrechargerecordMapper.selectYears();
		if (StringUitl.isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}
    
}
