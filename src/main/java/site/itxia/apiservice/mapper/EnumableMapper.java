package site.itxia.apiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.enumable.MemberStatus;
import site.itxia.apiservice.enumable.OrderAction;
import site.itxia.apiservice.enumable.OrderWarranty;
import site.itxia.apiservice.exception.NoSuchEnumException;

@Mapper
public interface EnumableMapper {

    default MemberStatus mapMemberStatus(int memberStatus) {
        for (var status : MemberStatus.values()) {
            if (status.getStatus() == memberStatus) {
                return status;
            }
        }
        throw new NoSuchEnumException();
    }

    default int mapMemberStatus(MemberStatus memberStatus) {
        return memberStatus.getStatus();
    }

    default OrderWarranty mapOrderWarranty(int w) {
        for (var warranty : OrderWarranty.values()) {
            if (warranty.getWarranty() == w) {
                return warranty;
            }
        }
        throw new NoSuchEnumException();
    }

    default int mapOrderWarranty(OrderWarranty orderWarranty) {
        return orderWarranty.getWarranty();
    }

    default MemberRole mapMemberRole(int role) {
        for (var memberRole : MemberRole.values()) {
            if (memberRole.getRole() == role) {
                return memberRole;
            }
        }
        throw new NoSuchEnumException();
    }

    default int mapMemberRole(MemberRole memberRole) {
        return memberRole.getRole();
    }

    default OrderAction mapOrderAction(int action) {
        for (var orderAction : OrderAction.values()) {
            if (orderAction.getAction() == action) {
                return orderAction;
            }
        }
        throw new NoSuchEnumException();
    }

    default int mapOrderAction(OrderAction orderAction) {
        return orderAction.getAction();
    }


}
