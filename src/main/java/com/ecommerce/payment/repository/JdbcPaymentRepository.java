package com.ecommerce.payment.repository;

import com.ecommerce.payment.dto.payment.PaymentSummary;
import com.ecommerce.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class JdbcPaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public Payment save(Payment payment){
        LocalDateTime now=LocalDateTime.now();
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);
        String sql="INSERT INTO payment.payments(user_id,product_id,price,created_at,updated_at,status,method) VALUES(?,?,?,?,?,?,?)" ;
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            System.out.println(payment);
            ps.setLong(1, payment.getUserId());
            ps.setLong(2, payment.getProductId());
            ps.setBigDecimal(3, payment.getPrice());
            ps.setObject(4, payment.getCreatedAt());
            ps.setObject(5, payment.getUpdatedAt());
            ps.setString(6, payment.getStatus().name());
            ps.setString(7, payment.getMethod().name());
            return ps;
        }, keyHolder);

        Long generatedId=keyHolder.getKey().longValue();
        payment.setId(generatedId);
        return payment;
    }

    public void updatePaymentId(String paymentId,Long id){
        String sql="UPDATE payment.payments SET payment_id=? , updated_at=? WHERE id=?";
        jdbcTemplate.update(sql,
                paymentId,LocalDateTime.now(),id);
    }

    public PaymentSummary findPaymentId(String paymentOrderId) {
        String sql="SELECT id, payment_id FROM payment.payments WHERE payment_id= ?";

        try {


            PaymentSummary paymentSummary = jdbcTemplate.queryForObject(sql, new Object[]{paymentOrderId}, (rs, rowNum) -> {
                PaymentSummary p = new PaymentSummary();
                p.setId(rs.getLong("id"));
                p.setPayment_id(rs.getString("payment_id"));
                return p;

            });
            return paymentSummary;

        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException("No payment found with payment_id: "+paymentOrderId);
        }
    }
    public void updatePaymentStatus(String paymentId, String status){
        String sql="UPDATE payment.payments SET status=? , updated_at=? WHERE payment_id=?";
        jdbcTemplate.update(sql,
                status,LocalDateTime.now(),paymentId);
    }
}
