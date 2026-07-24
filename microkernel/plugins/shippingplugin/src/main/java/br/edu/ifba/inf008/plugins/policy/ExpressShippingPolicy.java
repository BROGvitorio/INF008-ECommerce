public class ExpressShippingPolicy implements ShippingPolicy {

    @Override
    public BigDecimal calculateShipping(Order order) {
        return new BigDecimal("30.00");
    }   
}