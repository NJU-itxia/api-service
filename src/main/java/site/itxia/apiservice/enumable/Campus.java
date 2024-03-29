package site.itxia.apiservice.enumable;

import site.itxia.apiservice.exception.NoSuchEnumException;

/**
 * 校区信息.
 */
public enum Campus {
    ALL(0, "全部"),
    XIAN_LIN(1, "仙林"),
    GU_LOU(2, "鼓楼"),
    ;

    private final int location;
    private final String locationName;

    private Campus(int location, String locationName) {
        this.location = location;
        this.locationName = locationName;
    }


    public int getLocation() {
        return location;
    }

    public String getLocationName() {
        return locationName;
    }

    public static Campus from(int campus) {
        for (var c : Campus.values()) {
            if (c.getLocation() == campus) {
                return c;
            }
        }
        throw new NoSuchEnumException();
    }
}
