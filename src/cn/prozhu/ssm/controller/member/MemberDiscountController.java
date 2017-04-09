package cn.prozhu.ssm.controller.member;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.prozhu.ssm.controller.base.BaseController;
import cn.prozhu.ssm.model.discount.Discount;
import cn.prozhu.ssm.service.member.DiscountService;
import cn.prozhu.ssm.util.JSONUtil;

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
	
	/**
	 * 删除折扣信息
	 * @param response
	 * @param id ：折扣信息编号
	 * @return
	 */
	@RequestMapping("/delDiscount")
	public String delDiscount(HttpServletResponse response, String id) {
		Integer flag = discountService.delDiscount(id);
		if (flag > 0) {
			return writeAjaxResponse(JSONUtil.result(true, "删除成功！", "", ""), response);
		}
		return writeAjaxResponse(JSONUtil.result(false, "删除失败！", "", ""), response);
	}

}
