package nl.zoe.account.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalUtils {
    public static boolean isGreaterThanZero(BigDecimal value) {
        return Objects.nonNull(value) && value.compareTo(BigDecimal.ZERO) > 0;
    }
}
