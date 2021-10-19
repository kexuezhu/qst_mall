package com.qst.qstmall.domin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;

/*
    订单实体类
 */
public class Order {
    //对应订单商品信息集合
    private ArrayList<OrderItem> orderItems;

    //订单表主键id
    private long order_id;
    //订单号
    private String order_no;
    //用户主键id
    private long user_id;
    //订单总价
    private int total_price;
    //支付状态:0.未支付,1.支付成功,-1:支付失败
    private int pay_status;
    //'0.无 1.支付宝支付 2.微信支付
    private int pay_type;
    //支付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pay_time;
    //订单状态:0.待支付 1.已支付 2.配货完成 3.出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
    private int order_status;
    //订单body
    private String extra_info;
    //收货人姓名
    private String user_name;
    //收货人手机号
    private String user_phone;
    //收货人收货地址
    private String user_address;
    //删除标识字段(0-未删除 1-已删除)
    private boolean is_deleted;
    //创建时间
    private String create_time;
    //最新修改时间
    private String update_time;

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderItems=" + orderItems +
                ", order_id=" + order_id +
                ", order_no='" + order_no + '\'' +
                ", user_id=" + user_id +
                ", total_price=" + total_price +
                ", pay_status=" + pay_status +
                ", pay_type=" + pay_type +
                ", pay_time=" + pay_time +
                ", order_status=" + order_status +
                ", extra_info='" + extra_info + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_address='" + user_address + '\'' +
                ", is_deleted=" + is_deleted +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
