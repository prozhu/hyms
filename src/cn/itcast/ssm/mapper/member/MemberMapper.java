package cn.itcast.ssm.mapper.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.itcast.ssm.model.member.Member;
import cn.itcast.ssm.model.member.MemberExample;

public interface MemberMapper {
    int countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExampleWithRowbounds(MemberExample example, RowBounds rowBounds);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /**
     * 查询会员表中所有的年份
     * @return
     */
	List<String> selectYears();

	/**
	 * 按照 “年度”查询会员人数
	 * @return
	 */
	List<Map<String, Object>> memberChartByYear();

	/**
	 * 按照 “季度”查询会员人数
	 * @param markYear
	 * @return
	 */
	List<Map<String, Object>> memberChartByQuarter(@Param("markYear")String markYear);

	/**
	 * 按照 “月度”查询会员人数
	 * @param markYear
	 * @return
	 */
	List<Map<String, Object>> memberChartByMonth(@Param("markYear")String markYear);

	/**
	 * 按照 “周”查询会员人数
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> memberChartByWeek(@Param("time")String time);

	/**
	 * 按照年龄查询会员图表
	 * @return
	 */
	List<Map<String, Object>> memberChartByAge();
}