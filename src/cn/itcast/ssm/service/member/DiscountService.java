package cn.itcast.ssm.service.member;

import java.util.List;

import cn.itcast.ssm.model.discount.Discount;

public interface DiscountService {

	/**
	 * 查询所有的折扣信息
	 * @return
	 */
	List<Discount> queryAllDiscount();

	/**
	 * 添加折扣信息
	 * @param discount
	 * @return
	 */
	Integer addDiscount(Discount discount);

	/**
	 * 修改折扣信息
	 * @param discount
	 * @return
	 */
	Integer editDiscount(Discount discount);

}
