package site.itxia.apiservice.enumable;

public enum OrderStatus {

    /**
     * 新创建/未被接单
     */
    CREATED(0),
    ACCEPTED(1),
    COMPLETED(2),
    CANCELED(3),
    /**
     * 已废弃.不可再操作
     */
    ABANDONED(4);

    private final int status;

    private OrderStatus(int status) {
        this.status = status;
    }

}
