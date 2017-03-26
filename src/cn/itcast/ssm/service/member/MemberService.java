package cn.itcast.ssm.service.member;

import java.util.List;
import java.util.Map;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.model.member.Member;

/**
 * 会员信息
 */
public interface MemberService {

    /**
     * 
     * @description:保存会员信息
     * @author ：zc
     * @date ：2017-2-6 下午5:15:17 
     * @param member
     * @return 保存成功返回：非0整数
     *                  保存失败返回：0
     */
    public Integer save(Member member);


    /**
     * 
     * @description: 通过主键查询会员信息
     * @author ：zc
     * @date ：2017-2-7 下午4:41:52 
     * @param id
     * @return
     */
    public Member findMemberById(String id);

    /**
     * 
     * @description: 删除会员信息
     * @author ：zc
     * @date ：2017-2-7 下午4:58:46 
     * @param ids
     * @return 返回删除的记录的条数
     * @throws CustomException 
     */
    public Integer deleteMember(String ids) throws CustomException;

    /**
     * @description: 查询所有会员信息(可以选择分页)
     *                          不分页 ： pageNow 和  pageSize 设置为null
     *                          分页： 传递相应的参数
     * @author ：zc
     * @date ：2017-2-10 下午7:56:33 
     * @param pageNow 当前页
     * @param pageSize ： 每页显示的大小
     * @return 查询不到，返回null
     */
    public List<Member> findAll(String pageNow, String pageSize);

    /**
     * @description: 对会员信息进行更新
     * @author ：zc
     * @date ：2017-2-11 下午1:36:42 
     * @param member
     * @return 修改成功：返回非0整数
     *                                 返回2：表示用户修改了密码（ 有一个特殊情况是：用户如果修改了密码，就强制用户重新登录系统）
     *                  修改失败：返回0
     *                 
     */
    public Integer updateMember(Member member);

    /**
     * @description: 有条件的查询所有信息(并且分页)
     * @author ：zc
     * @date ：2017-2-13 下午4:25:35 
     * @param pageNow  当前页 
     * @param pageSize 每页大小 
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param keyword 关键字
     *  @param sort  排序的列名称
     * @param order  该字段是降序或者升序
     * @return
     */
    public List<Member> findMemberByCondition(String pageNow, String pageSize, String startTime, String endTime, String keyword, String sort, String order);
            

    /**
     * @description: 根据条件查询所有的记录条数(查询没有被删除的数据)
     * @author ：zc
     * @date ：2017-2-13 下午5:27:00 
     * @param startTime 结束时间
     * @param endTime  开始时间
     * @param keyword 查询的关键字
     * @return
     */
    public Integer getCount(String startTime, String endTime, String keyword);


    /**
     * 获取会员表中的信息
     * @param mark ：年度、季度、月度显示图表(year, quarter, month)
     * @param markYear ：年份(2017)
     * @param time : 指定时间
     * @return
     */
	public List<Map<String, Object>> memberChart(String mark, String markYear,
			String time);

	
	/**
	 * 获取会员表中的所有年份
	 * @return
	 */
	public List<String> findMemberYears();


	/**
	 * 按照年龄查询会员图表
	 * @return
	 */
	public List<Map<String, Object>> memberChartByAge();


}
