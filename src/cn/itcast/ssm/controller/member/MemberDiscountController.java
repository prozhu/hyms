package cn.itcast.ssm.controller.member;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.ssm.controller.base.BaseController;
import cn.itcast.ssm.model.discount.Discount;
import cn.itcast.ssm.service.member.DiscountService;
import cn.itcast.ssm.util.JSONUtil;

@Controller
public class MemberDiscountController extends BaseController{
	
	@Autowired
	private DiscountService discountService;
	
	/**
	 * 查询所有折扣信息
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/queryAllDiscount")
	public String queryAllDiscount(HttpServletResponse response) {
		List<Discount> list = discountService.queryAllDiscount();
		return this.writeAjaxResponse(JSONUtil.getJson(list), response);
	}
	
	/**
	 * 添加折扣信息
	 * @param response
	 * @return
	 */
	@RequestMapping("/addDiscount")
	public String addDiscount(HttpServletResponse response, Discount discount) {
		Integer flag = discountService.addDiscount(discount);
		if (flag > 0) {
			return writeAjaxResponse(JSONUtil.result(true, "添加成功！", "", ""), response);
		}
		return writeAjaxResponse(JSONUtil.result(false, "添加失败！", "", ""), response);
	}
	
	/**
	 * 修改折扣信息
	 * @param response
	 * @return
	 */
	@RequestMapping("/editDiscount")
	public String editDiscount(HttpServletResponse response, Discount discount) {
		Integer flag = discountService.editDiscount(discount);
		if (flag > 0) {
			return writeAjaxResponse(JSONUtil.result(true, "修改成功！", "", ""), response);
		}
		return writeAjaxResponse(JSONUtil.result(false, "修改失败！", "", ""), response);
	}

}
