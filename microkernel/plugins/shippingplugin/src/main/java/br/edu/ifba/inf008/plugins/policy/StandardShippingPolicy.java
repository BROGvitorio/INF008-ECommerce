public class StandardShippingPolicy implements ShippingPolicy {
    public BigDecimal calculateShipping(Order order) {
        if(order.getSubtotal().compareTo(new BigDecimal("100")) >= 0)
            return BigDecimal.ZERO;

        return new BigDecimal("15");
    }
}