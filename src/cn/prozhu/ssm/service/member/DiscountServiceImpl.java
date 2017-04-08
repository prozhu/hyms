package cn.prozhu.ssm.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.prozhu.ssm.mapper.discount.DiscountMapper;
import cn.prozhu.ssm.model.discount.Discount;
import cn.prozhu.ssm.util.StringUitl;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountMapper discountMapper;
	@Override
	public List<Discount> queryAllDiscount() {
		return discountMapper.queryAllDiscount();
	}
	@Override
	public Integer addDiscount(Discount discount) {
		if (StringUitl.isNullOrEmpty(discount)) {
			return 0;
		}
		return discountMapper.insertDiscount(discount);
	}
	@Override
	public Integer editDiscount(Discount discount) {
		if (StringUitl.isNullOrEmpty(discount)) {
			return 0;
		}
		return discountMapper.updateDiscount(discount);
	}

}
