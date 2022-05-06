package cn.prozhu.ssm.mapper.discount;

import java.util.List;

import cn.prozhu.ssm.model.discount.Discount;

public interface DiscountMapper {
	
	/**
	 * 根据会员卡级别查询折扣
	 * @param name
	 * @return
	 */
	Discount queyDiscountByGrade(String name);
	
	/**
	 * 插入折扣信息
	 * @param record
	 * @return
	 */
	int insertDiscount(Discount discount);
	
	/**
	 * 更新折扣信息
	 * @param discount
	 * @return
	 */
	int updateDiscount(Discount discount);
	
	/**
	 * 查询所有折扣信息
	 * @return
	 */
	List<Discount> queryAllDiscount();

	/**
	 * 删除折扣信息
	 * @param id
	 * @return
	 */
	Integer delDiscount(String id);

}
