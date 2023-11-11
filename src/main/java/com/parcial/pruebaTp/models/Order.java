//package com.parcial.prueba_tp.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@Table(name = "orders")
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @GeneratedValue(generator = "Order")
////    @TableGenerator(name = "Order", table = "sqlite_sequence",
////            pkColumnName = "name", valueColumnName = "seq",
////            pkColumnValue="orderid",
////            initialValue=1, allocationSize=1)
//    private Long orderID;
//
//    @ManyToOne
//    @MapsId(value = "customerid")
//    @JoinColumn(name = "customerid")
//    private Customer customer;
//
//    @ManyToOne
//    @MapsId(value = "employeeid")
//    @JoinColumn(name = "employeeid")
//    private Employee employee;
//
//    @Column(name = "requireddate")
//    private String requiredDate;
//
//    @Column(name = "orderdate")
//    private String orderDate;
//
//    @Column(name = "shippeddate")
//    private String shippedDate;
//
//    @Column(name = "shipvia")
//    private long shipper;
//
//    @Column(name = "freight")
//    private double freight;
//
//    @Column(name = "shipname")
//    private String shipName;
//
//    @Column(name = "shipaddress")
//    private String shipAddress;
//
//    @Column(name = "shipcity")
//    private String shipCity;
//
//    @Column(name = "shipregion")
//    private String shipRegion;
//
//    @Column(name = "shippostalcode")
//    private String shipPostalCode;
//
//    @Column(name = "shipcountry")
//    private String shipCountry;
//
//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
//    private List<OrderDetail> orderDetails;
//
//}
