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

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResultWrapper requestNewOrder(@Valid @RequestBody RequestOrderVo requestOrderVo) {
        return ResultWrapper.wrapSuccess(orderService.addOrder(requestOrderVo));
    }

    @GetMapping("/{orderID}")
    public ResultWrapper getOrder(@PathVariable(name = "orderID") int orderID) {
        return ResultWrapper.wrapSuccess(orderService.getOrderDto(orderID));
    }

    @GetMapping("")
    public ResultWrapper getOrder(@RequestParam(required = false) Integer page) {
        if (page == null) {
            page = 0;
        }
        return ResultWrapper.wrapSuccess(orderService.getOrderDtoByPage(page));
    }

    @GetMapping("/count")
    public ResultWrapper getOrderCount(){
        return ResultWrapper.wrapSuccess(orderService.getOrderCount());
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
