package site.itxia.apiservice.enumable;

/**
 * 成员角色.
 * 用于权限控制.
 */
public enum MemberRole {
    /**
     * 游客，无权限.
     */
    GUEST(0),
    /**
     * it侠成员.
     */
    MEMBER(1),
    /**
     * 管理员.
     */
    ADMIN(2);

    private int role;

    private MemberRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
