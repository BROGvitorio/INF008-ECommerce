public interface ShippingPolicy {
    BigDecimal calculateShipping(Order order);
}