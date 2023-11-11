//package com.parcial.prueba_tp.services;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.parcial.prueba_tp.repositories.OrderRepository;
//import com.parcial.prueba_tp.models.Order;
//
//@Service
//public class OrderService {
//
//    // usamos el repositorio para acceder a la bd
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public List<OrderDto> findAll() {
//        List<Order> orderList = orderRepository.findAll();
//        return orderList.stream().map(this::convertToDto).toList();
//    }
//
//    public OrderDto findById(Long id) {
//        Optional<Order> optionalOrder = orderRepository.findById(id);
//        System.out.println(optionalOrder);
//
////        return optionalOrder.map(this::convertToDto).orElseThrow(
////                () -> new NoSuchElementException("No se encontro la Orden!")
////        );
//        return optionalOrder.map(this::convertToDto).orElse(null);
//    }
//
//    public OrderDto save(OrderDto orderDto) {
//        Order order = convertToEntity(orderDto);
//        orderRepository.save(order);
//        return this.convertToDto(order);
//    }
//
//    public OrderDto update(Long id, OrderDto orderDto) {
//
//        Optional<Order> optionalOrder = orderRepository.findById(id);
//
//
//
//        if (optionalOrder.isPresent()) {
//            Order order = convertToEntity(orderDto);
//            order.setOrderID(id);
//            Order updatedOrder = orderRepository.save(order);
//            return this.convertToDto(updatedOrder);
//
//        } else {
//            return null;
//        }
//
//    }
//
//    public OrderDto deleteById(Long id) {
//
//        Optional<Order> optionalOrder = orderRepository.findById(id);
//
//        if (optionalOrder.isEmpty()) {
//            throw new NoSuchElementException("No se encontro la orden");
//        }
//
//        orderRepository.deleteById(id);
//        return this.convertToDto(optionalOrder.get());
//    }
//
////    @Override
////    public List<OrderDetail> findDetailsByOrderId(long orderId) {
////        Order order = orderRepository.findById(orderId).orElseThrow();
////        return order.getOrderDetails();
////    }
//
//
//    // Mapper Entity --> Dto
//    private OrderDto convertToDto(Order order) {
//
//        OrderDto orderDto = new OrderDto();
//
//        orderDto.setOrderID(order.getOrderID());
////        orderDto.setCustomerID(order.getCustomerID());
////        orderDto.setEmployeeID(order.getEmployeeID());
//        orderDto.setOrderDate(order.getOrderDate());
//        orderDto.setRequiredDate(order.getRequiredDate());
//        orderDto.setShippedDate(order.getShippedDate());
//        orderDto.setShipperID(order.getShipper());
//        orderDto.setFreight(order.getFreight());
//        orderDto.setShipName(order.getShipName());
//        orderDto.setShipAddress(order.getShipAddress());
//        orderDto.setShipCity(order.getShipCity());
//        orderDto.setShipRegion(order.getShipRegion());
//        orderDto.setShipPostalCode(order.getShipPostalCode());
//        orderDto.setShipCountry(order.getShipCountry());
//
//        return orderDto;
//    }
//
//    // Mapper Dto --> Entity
//    private Order convertToEntity(OrderDto orderDto) {
//
//        Order order = new Order();
//
//        order.setOrderID(orderDto.getOrderID());
//        // orderEntity.setEmployeeID(orderDto.getEmployeeID());
//        order.setOrderDate(orderDto.getOrderDate());
//        order.setRequiredDate(orderDto.getRequiredDate());
//        order.setShippedDate(orderDto.getShippedDate());
//        // orderEntity.setShipVia(orderDto.getShipVia());
//        order.setFreight(orderDto.getFreight());
//        order.setShipName(orderDto.getShipName());
//        order.setShipAddress(orderDto.getShipAddress());
//        order.setShipCity(orderDto.getShipCity());
//        order.setShipRegion(orderDto.getShipRegion());
//        order.setShipPostalCode(orderDto.getShipPostalCode());
//        order.setShipCountry(orderDto.getShipCountry());
//
//        return order;
//    }
//
//}
