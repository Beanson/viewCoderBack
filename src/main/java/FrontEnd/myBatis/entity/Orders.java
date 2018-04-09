package FrontEnd.myBatis.entity;

import com.sun.star.util.DateTime;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/11.
 */
public class Orders {

    private int id;
    private String order_id;
    private int user_id;
    private int service_id;
    private int service_num;
    private String order_date;
    private String pay_date;
    private String expire_date;
    private int pay_status;
    private int pay_way;
    private String price;
    private String subject;

    public Orders() {
    }

    public Orders(int id, String order_id, int user_id, int service_id, int service_num, String order_date, String pay_date, String expire_date, int pay_status, int pay_way, String price,String subject) {
        this.id = id;
        this.order_id=order_id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.service_num=service_num;
        this.order_date = order_date;
        this.pay_date = pay_date;
        this.expire_date = expire_date;
        this.pay_status=pay_status;
        this.pay_way=pay_way;
        this.price = price;
        this.subject=subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getService_num() {
        return service_num;
    }

    public void setService_num(int service_num) {
        this.service_num = service_num;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public int getPay_way() {
        return pay_way;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public void setPay_way(int pay_way) {
        this.pay_way = pay_way;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", user_id=" + user_id +
                ", service_id=" + service_id +
                ", service_num=" + service_num +
                ", order_date=" + order_date +
                ", pay_date=" + pay_date +
                ", expire_date=" + expire_date +
                ", pay_status=" + pay_status +
                ", pay_way=" + pay_way +
                ", price='" + price + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
