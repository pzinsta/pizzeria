package pzinsta.pizzeria.util;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

import pzinsta.pizzeria.model.User;
import pzinsta.pizzeria.model.UserRole;

public class Utils {
	public static MonetaryAmount fromBigDecimal(BigDecimal bigDecimal) {
		return Money.of(bigDecimal, "USD");
	}
	
	public static boolean isRegisteredUser(User user) {
		return user.getRoles().contains(UserRole.REGISTERED);
	}
}
