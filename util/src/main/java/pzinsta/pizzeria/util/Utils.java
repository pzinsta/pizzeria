package pzinsta.pizzeria.util;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

public class Utils {
	public static MonetaryAmount fromBigDecimal(BigDecimal bigDecimal) {
		return Money.of(bigDecimal, "USD");
	}
}
