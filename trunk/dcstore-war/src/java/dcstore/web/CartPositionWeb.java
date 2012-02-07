// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import dcstore.jpa.ProductEntity;

/**
 *
 * @author dcebula
 */
public class CartPositionWeb {

    private ProductEntity product;
    private int qty;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public CartPositionWeb(ProductEntity product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ProductEntity) {
            ProductEntity p = (ProductEntity) o;
            return this.product.equals(p);
        } else if (o instanceof ProductEntity) {
            CartPositionWeb c = (CartPositionWeb) o;
            return this.product.equals(c.product);
        } else {
            return false;
        }
    }
    
    public void incQty() {
        ++this.qty;
    }
}