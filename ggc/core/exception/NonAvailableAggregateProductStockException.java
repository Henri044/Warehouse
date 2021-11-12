package ggc.core.exception;

public class NonAvailableAggregateProductStockException extends Exception {

    private String _idProduct;
    private int _requestAmount;
    private int _availableAmount;

    public NonAvailableAggregateProductStockException(String idProduct, int requestAmount, int availableAmount) {

        _idProduct = idProduct;
        _requestAmount = requestAmount;
        _availableAmount = availableAmount;

    }

    public String getIdProduct() {
        return _idProduct;
    }

    public int getRequestAmount() {
        return _requestAmount;
    }

    public int getAvailableAmount() {
        return _availableAmount;
    }
}