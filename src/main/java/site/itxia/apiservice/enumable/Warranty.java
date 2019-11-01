package site.itxia.apiservice.enumable;

/**
 * 保修期信息.
 * 用于在order中记录.
 * */
public enum Warranty {
    /**
     * 不确定.
     * */
    UNCERTAN(0),
    /**
     * 在保.
     * */
    UNDER_WARRANTY(1),
    /**
     * 过保.
     * */
    EXPIRED(2);
    private final int warranty;

    private Warranty(int warranty) {
        this.warranty = warranty;
    }

    public int getWarranty() {
        return warranty;
    }
}
