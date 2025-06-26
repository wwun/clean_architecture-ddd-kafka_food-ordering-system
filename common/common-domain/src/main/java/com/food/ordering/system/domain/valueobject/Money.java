package com.food.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);   // Constant representing zero money, this is possible because Money is immutable (static final, this owns to the class, not to an instance)

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return amount != null && money != null && amount.compareTo(money.amount) > 0;
    }

    public Money add(Money money) {
        if (money == null || money.getAmount() == null) {
            return this;
        }
        return new Money(this.amount.add(money.getAmount()));
    }

    public Money subtract(Money money) {
        if (money == null || money.getAmount() == null) {
            return this;
        }
        return new Money(this.amount.subtract(money.getAmount()));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return amount != null ? amount.equals(money.amount) : money.amount == null;
    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }

    private BigDecimal setScale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_UP);
    }
}
