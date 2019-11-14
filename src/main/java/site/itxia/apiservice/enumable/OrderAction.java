package site.itxia.apiservice.enumable;

import site.itxia.apiservice.exception.NoSuchEnumException;

/**
 * 预约单动作.
 * 用于在orderHistory中记录.
 */
public enum OrderAction {
    /**
     * 接受.
     */
    ACCEPT(0),
    /**
     * 放回.
     */
    PUT_BACK(1),
    /**
     * 完成.
     */
    FINISH(2),
    /**
     * (预约人)取消.
     */
    CANCEL(3),
    /**
     * (管理员)废弃.
     */
    ABANDON(4);

    private final int action;

    private OrderAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public static OrderAction from(int action) {
        for (var oa : OrderAction.values()) {
            if (oa.getAction() == action) {
                return oa;
            }
        }
        throw new NoSuchEnumException();
    }
}
