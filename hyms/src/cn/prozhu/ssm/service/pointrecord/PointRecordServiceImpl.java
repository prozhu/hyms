package cn.prozhu.ssm.service.pointrecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import cn.prozhu.ssm.mapper.pointrecord.PointRecordMapper;
import cn.prozhu.ssm.model.pointrecord.PointRecord;
import cn.prozhu.ssm.model.pointrecord.PointRecordExample;
import cn.prozhu.ssm.util.RandomUtils;
import cn.prozhu.ssm.util.StringUitl;

public class PointRecordServiceImpl implements PointRecordService {

    @Autowired
    private PointRecordMapper pointRecordMapper;

    @Override
    public Integer getCount(String startTime, String endTime, String keyword, String memberid) {
    	List<Object> condition = buildSelectCondition(null,null, startTime, endTime, keyword,null, null, memberid);
        return pointRecordMapper.countByExample((PointRecordExample)condition.get(0));
    }
    
    /**
     * 为查询积分记录构建查询条件（可分页，可关键字搜索）
     * @param pageNow
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param keyword
     * @param sort
     * @param order
     * @return 集合中返回三个参数： 
     *  		1.：  PointRecordExample
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
       
        PointRecordExample pointRecordExample = new PointRecordExample();
        PointRecordExample.Criteria criteria = pointRecordExample.createCriteria();
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            pointRecordExample.setOrderByClause(sort + " " + order);
        }
        //会员编号
        if (!StringUitl.isNullOrEmpty(memberid)) {
        	criteria.andMemberidEqualTo(memberid);
        }
        
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andChangetimeGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        }
        
        //会员名称
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        condition.add(pointRecordExample);
        condition.add(rowBounds);
        condition.add(criteria);
    	return condition;
    }
    
    @Cacheable(value="PointRecord.findPointRecordByMemberIdAndCondition", 
    		key = "'findPointRecordByMemberIdAndCondition'+#sort + #order+#pageNow+#pageSize+#memberid",
    		condition = "null == #startTime and null == #endTime and null == #keyword ")
    @Override
    public List<PointRecord> findPointRecordByCondition(String memberid,String pageNow,
            String pageSize, String startTime, String endTime, String keyword,
            String sort, String order) {
    	List<Object> condition = buildSelectCondition(pageNow, pageSize, startTime, endTime, keyword, sort, order, memberid);
        List<PointRecord> list = pointRecordMapper.selectByExampleWithRowbounds((PointRecordExample)condition.get(0), (RowBounds)condition.get(1));
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }

	@Override
	public List<String> findPointYears() {
		List<String> list = pointRecordMapper.selectYears();
		if (StringUitl.isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> pointChart(String mark, String markYear, String time) {
		if (StringUitl.isNullOrEmpty(mark.trim())) {
			return null;
		}
		List<Map<String, Object>> map = null;
		if ("year".equals(mark.trim())) {
			map = pointRecordMapper.pointChartByYear();
		} else if ("quarter".equals(mark.trim())) {
			map = pointRecordMapper.pointChartByQuarter(markYear);
		} else if ("month".equals(mark.trim())) {
			map = pointRecordMapper.pointChartByMonth(markYear);
		} else {
				map = pointRecordMapper.pointChartByWeek(StringUitl.isNullOrEmpty(time)? RandomUtils.formatTime1(new Date()) : time);
		}
		if (StringUitl.isNullOrEmpty(map)) {
			return null;
		}
		return map;
	}
}
