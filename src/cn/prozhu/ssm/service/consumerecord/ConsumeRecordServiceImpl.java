package cn.prozhu.ssm.service.consumerecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import cn.prozhu.ssm.mapper.consumerecord.ConsumerecordMapper;
import cn.prozhu.ssm.model.consumerecord.Consumerecord;
import cn.prozhu.ssm.model.consumerecord.ConsumerecordExample;
import cn.prozhu.ssm.util.RandomUtils;
import cn.prozhu.ssm.util.StringUitl;

public class ConsumeRecordServiceImpl implements ConsumeRecordService {

    @Autowired
    private ConsumerecordMapper consumerecordMapper;
   

    @Override
    public Integer getCount(String startTime, String endTime, String keyword, String memberId) {
    	List<Object> condition = buildSelectCondition(null,null, startTime, endTime, keyword,null, null, memberId);
        return consumerecordMapper.countByExample((ConsumerecordExample)condition.get(0));
    }

    /**
     * 为查询消费记录构建查询条件（可分页，可关键字搜索）
     * @param pageNow
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param keyword
     * @param sort
     * @param order
     * @return 集合中返回三个参数： 
     *  		1.：  consumerecordExample
     *  		2.：  rowBounds
     *  		3.: criteria
     */
    private List<Object> buildSelectCondition(String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order, String memberId) {
    	ArrayList<Object> condition = new ArrayList<Object>();
    	if (pageNow == null) {
            pageNow = "1";
        }
        if (pageSize == null) {
            pageSize = "100000";
        }
        ConsumerecordExample consumerecordExample = new ConsumerecordExample();
        ConsumerecordExample.Criteria criteria = consumerecordExample.createCriteria();
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            consumerecordExample.setOrderByClause(sort + " " + order);
        }
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andConsumetimeBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andConsumetimeLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andConsumetimeGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        }
        
        //会员名称
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        //memberid存在即查询用户个人信息
        if (!StringUitl.isNullOrEmpty(memberId)) {
        	criteria.andMemberidEqualTo(memberId);
        }
        
        RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) *  Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        condition.add(consumerecordExample);
        condition.add(rowBounds);
        condition.add(criteria);
        return condition;
	 
    }
    
    
    @Cacheable(value="ConsumeRecord.findConsumeRecordByMemberIdAndCondition", 
    		key = "'findConsumeRecordByMemberIdAndCondition'+#sort + #order+#pageNow+#pageSize+#memberid",
    		condition = "null == #startTime and null == #endTime and null == #keyword ")
    @Override
    public List<Consumerecord> findConsumeRecordByCondition(String memberid, String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order) {
    	List<Object> condition = buildSelectCondition(pageNow, pageSize, startTime, endTime, keyword, sort, order, memberid);
        List<Consumerecord> list = consumerecordMapper.selectByExampleWithRowbounds((ConsumerecordExample)condition.get(0), (RowBounds)condition.get(1));
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }


	@Override
	public List<Map<String, Object>> consumeChart(String mark, String markYear, String time) {
		if (StringUitl.isNullOrEmpty(mark.trim())) {
			return null;
		}
		List<Map<String, Object>> map = null;
		if ("year".equals(mark.trim())) {
			map = consumerecordMapper.consumeChartByYear();
		} else if ("quarter".equals(mark.trim())) {
			map = consumerecordMapper.consumeChartByQuarter(markYear);
		} else if ("month".equals(mark.trim())) {
			map = consumerecordMapper.consumeChartByMonth(markYear);
		} else {
				map = consumerecordMapper.consumeChartByWeek(StringUitl.isNullOrEmpty(time)? RandomUtils.formatTime1(new Date()) : time);
		}
		if (StringUitl.isNullOrEmpty(map)) {
			return null;
		}
		return map;
	}

	@Override
	public List<String> findConsumeYears() {
		List<String> list = consumerecordMapper.selectYears();
		if (StringUitl.isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}

}
