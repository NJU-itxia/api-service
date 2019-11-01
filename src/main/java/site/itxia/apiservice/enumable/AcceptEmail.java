package site.itxia.apiservice.enumable;

/**
 * 是否接收邮件.
 * 用于成员个人信息.
 */
public enum AcceptEmail {

    /**
     * 接收邮件.
     */
    ACCEPT(0),
    /**
     * 不接收邮件.
     */
    NOT_ACCEPT(1);

    private final int accept;

    private AcceptEmail(int accept) {
        this.accept = accept;
    }

    public int getAccept() {
        return accept;
    }
}
