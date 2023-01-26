package br.com.fiap.sportconnection.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException{

    private static final String PRODUCT_NOTFOUND = "Produto não encontrado!";

    public ProductNotFoundException() {
        super(PRODUCT_NOTFOUND);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

}
