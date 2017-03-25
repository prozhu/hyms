package cn.itcast.ssm.service.membercard;

import java.util.List;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.model.member.Member;
import cn.itcast.ssm.model.membercard.Membercard;
import cn.itcast.ssm.service.member.MemberService;

/**
 * 会员卡信息
 * @author Administrator
 *
 */
public interface MemberCardService {

    /**
     * @description: 添加会员卡信息
     * @author ：zc
     * @date ：2017-2-13 下午2:19:41 
     * @param ids
     * @param memberService
     * @return ：0：保存失败
     *                    其他：成功
     * @throws Exception 
     */
    void saveMemberCard(String ids, MemberService memberService) throws Exception;
    
  /**
   * @description: 校验是否已经办理了会员卡(根据会员编号存在与否判断)
   * @author ：zc
   * @date ：2017-2-13 下午3:18:53 
   * @param ids：id 的集合（需要对其分割为字符数组）
   * @param memberService
   * @return: 返回没有办理会员卡的id集合
   */
    List<String> isHasMemberCard(String[] ids, MemberService memberService);
    
   /**
    * @description: 根据会员编号查询会员卡信息
    * @author ：zc
    * @date ：2017-2-15 下午7:30:25 
    * @param memberid
    * @return
    */
    public List<Membercard> findMemberCardByMemberId(String memberid);
    
    
    /**
     * @description: 通过主键查询会员卡信息
     * @author ：zc
     * @date ：2017-2-7 下午4:41:52 
     * @param id
     * @return
    public Membercard findMemberCardById(String id);
    
    
    /**
     * @description: 有条件的查询所有会员卡信息(并且分页)
     * @author ：zc
     * @date ：2017-2-13 下午4:25:35 
     * @param pageNow  当前页 
     * @param pageSize 每页大小 
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param keyword 关键字
     *  @param sort  排序的列名称
     * @param order  该字段是降序或者升序
     * @param memberService
     * @return
     */
    public List<Membercard> findMemberCardByCondition(String pageNow, String pageSize, String startTime, String endTime, String keyword, String sort, String order, MemberService memberService);
            

    /**
     * @description: 根据条件查询所有会员卡的记录条数
     * @author ：zc
     * @date ：2017-2-13 下午5:27:00 
     * @param startTime 结束时间
     * @param endTime  开始时间
     * @param keyword 查询的关键字
     * @return
     */
    public Integer getCount(String startTime, String endTime, String keyword);

    /**
     * @description: 修改会员卡信息
     * @author ：zc
     * @date ：2017-2-14 下午6:16:45 
     * @param recharge
     * @param consume
     * @param id
     * @param point : 待调整的积分数
     * @return
     * @throws CustomException 
     */
    Integer updateMemberCard(String recharge, String consume, String id, String point) throws CustomException;

    /**
     * @description: 会员卡激活、挂失
     * @author ：zc
     * @date ：2017-2-15 下午3:48:32 
     * @param ids
     * @param flag : 标记会员卡是激活，还是挂失，
     *                              flag :loss 挂失
     *                              flag: activate 激活
     *                              flag:cancel 注销
     * @throws Exception 
     */
    void updateMemberCard(String ids, String flag) throws Exception;
    
    
}
