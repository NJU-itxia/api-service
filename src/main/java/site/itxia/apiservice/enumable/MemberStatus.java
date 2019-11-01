package site.itxia.apiservice.enumable;

/**
 * 成员状态.
 */
public enum MemberStatus {
    /**
     * 禁用.
     */
    DISABLE(0),
    /**
     * 启用(正常).
     */
    ENABLE(1);
    private final int status;

    MemberStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
