package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.enumable.ErrorCode;
import site.itxia.apiservice.service.OrderService;
import site.itxia.apiservice.vo.HandleOrderVo;
import site.itxia.apiservice.vo.RequestOrderVo;

import javax.validation.Valid;

/**
 * @author zhenxi
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResultWrapper requestNewOrder(@Valid @RequestBody RequestOrderVo requestOrderVo) {
        return ResultWrapper.wrapSuccess(orderService.addOrder(requestOrderVo));
    }

    @GetMapping("")
    public ResultWrapper getOrder(@RequestParam(required = false) Integer id) {
        if (id != null) {
            //有qs，查询一个
            return ResultWrapper.wrapSuccess(orderService.getOrder(id));
        }
        return ResultWrapper.wrapSuccess(orderService.getAllOrder());
    }

    @PostMapping("/{orderID}/handle")
    public ResultWrapper handleOrder(@PathVariable(name = "orderID") int orderID,
                                     @Valid @RequestBody HandleOrderVo handleOrderVo,
                                     @RequestHeader int memberID) {
        if (orderID != handleOrderVo.getOrderID()) {
            return ResultWrapper.wrap(ErrorCode.INVALID_ARGUMENTS, "url和body中的预约单号不匹配");
        }
        var result = orderService.handleOrder(memberID, handleOrderVo);
        return ResultWrapper.wrapSuccess(result);
    }

}
