package com.example.demo.srt.sd77.infrastructure.configs.mail;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.repository.IKhachHangRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;


@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Autowired
    private IKhachHangRepository khachHangRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) {
        try {
            String body = templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    public String calculateRank(int tichDiem) {
        double averagePoints = calculateAveragePoints();

        if (tichDiem == 0) {
            return "Không có điểm";
        } else if (tichDiem <= averagePoints * 0.5) {
            return "Đồng";
        } else if (tichDiem <= averagePoints) {
            return "Bạc";
        } else if (tichDiem <= averagePoints * 1.5) {
            return "Vàng";
        } else if (tichDiem <= averagePoints * 2) {
            return "Bạch Kim";
        } else {
            return "Kim Cương";
        }
    }

    public double calculateAveragePoints() {
        List<KhachHang> customers = khachHangRepository.findAll();
        double totalPoints = customers.stream().mapToInt(KhachHang::getTichDiem).sum();
        return customers.isEmpty() ? 0 : totalPoints / customers.size();
    }

    public String getDiscountByRank(String rank) {
        switch (rank) {
            case "Đồng":
                return "2%";
            case "Bạc":
                return "3%";
            case "Vàng":
                return "10%";
            case "Bạch Kim":
                return "15%";
            case "Kim Cương":
                return "20%";
            default:
                return "0%";
        }
    }


}