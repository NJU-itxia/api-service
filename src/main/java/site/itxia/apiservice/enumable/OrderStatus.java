package site.itxia.apiservice.enumable;

public enum OrderStatus {

    /**
     * 新创建/未被接单.
     */
    CREATED(0),
    /**
     * 已被接单.
     */
    ACCEPTED(1),
    /**
     * 处理完成.
     */
    COMPLETED(2),
    /**
     * 预约(被预约人)取消.
     */
    CANCELED(3),
    /**
     * 已(被管理员)废弃.不可再操作.
     */
    ABANDONED(4);

    private final int status;

    private OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
