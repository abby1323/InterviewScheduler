package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.Match;
import com.project.MockInterviewScheduler.service.interfaces.EmailServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@Slf4j
public class EmailService implements EmailServiceInterface {

    private final JavaMailSender mailSender;

    @Value("${app.mail.mock:true}")
    private boolean mockMode;

    @Value("${app.mail.from:mock-interview-scheduler@gmail.com}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAcceptanceRequest(Match match){
        String studentEmail = match.getStudent().getEmail();
        String interviewerEmail = match.getInterviewer().getEmail();
        String studentName = match.getStudent().getName();
        String interviewerName = match.getInterviewer().getName();
        String slotTime = match.getSlot().getStartTime().toString();
        Long matchId = match.getId();

        sendAcceptanceEmailToStudent(studentEmail, studentName, interviewerName,slotTime,matchId);
        sendAcceptanceEmailToInterviewer(interviewerEmail,interviewerName,studentName,slotTime,matchId);
    }

    @Override
    public void sendInterviewConfirmation(Match match){
        String studentEmail = match.getStudent().getEmail();
        String interviewerEmail = match.getInterviewer().getEmail();
        String studentName = match.getStudent().getName();
        String interviewerName = match.getInterviewer().getName();
        String slotTime = match.getSlot().getStartTime().toString();
        String meetLink = match.getMeetLink();
        sendConfirmationEmail(
                studentEmail,
                studentName,
                "Your Mock Interview is Confirmed",
                buildConfirmationBody(studentName, interviewerName, slotTime, meetLink,
                        "interviewer")
        );

        sendConfirmationEmail(
                interviewerEmail,
                interviewerName,
                "Mock Interview Scheduled",
                buildConfirmationBody(interviewerName, studentName, slotTime, meetLink, "student")
        );
    }

    @Override
    public void sendFeedbackReminder(Match match) {
        sendSimpleEmail(
                match.getStudent().getEmail(),
                "Please submit your feedback",
                "Hi " + match.getStudent().getName() + ",\n\n" +
                        "Your mock interview is complete. Please log in and submit your feedback.\n\n" +
                        "Thank you,\nMock Interview Scheduler"
        );

        sendSimpleEmail(
                match.getInterviewer().getEmail(),
                "Please submit your feedback",
                "Hi " + match.getInterviewer().getName() + ",\n\n" +
                        "Your mock interview is complete. Please log in and submit your detailed feedback for the student.\n\n" +
                        "Thank you,\nMock Interview Scheduler"
        );
    }

    private void sendAcceptanceEmailToStudent(String to, String studentName,
                                              String interviewerName, String slotTime, Long matchId) {
        String subject = "Mock Interview Match Found!";
        String body = "Hi " + studentName + ",\n\n" +
                "Great news! You have been matched with " + interviewerName + ".\n\n" +
                "Proposed slot: " + slotTime + "\n\n" +
                "Please log in to accept or reject this slot.\n" +
                "Match ID: " + matchId + "\n\n" +
                "This slot will be held for 24 hours pending your response.\n\n" +
                "Thank you,\nMock Interview Scheduler";

        sendSimpleEmail(to, subject, body);
    }

    private void sendAcceptanceEmailToInterviewer(String to, String interviewerName,
                                                  String studentName, String slotTime, Long matchId) {
        String subject = "New Interview Request";
        String body = "Hi " + interviewerName + ",\n\n" +
                "You have been matched with a student: " + studentName + ".\n\n" +
                "Proposed slot: " + slotTime + "\n\n" +
                "Please log in to accept or reject this slot.\n" +
                "Match ID: " + matchId + "\n\n" +
                "This slot will be held for 24 hours pending your response.\n\n" +
                "Thank you,\nMock Interview Scheduler";

        sendSimpleEmail(to, subject, body);
    }

    private void sendConfirmationEmail(String to, String name,
                                       String subject, String body) {
        sendSimpleEmail(to, subject, body);
    }

    private String buildConfirmationBody(String recipientName, String otherPartyName,
                                         String slotTime, String meetLink, String otherPartyRole) {
        return "Hi " + recipientName + ",\n\n" +
                "Your mock interview has been confirmed.\n\n" +
                "With: " + otherPartyName + " (" + otherPartyRole + ")\n" +
                "Time: " + slotTime + "\n" +
                "Google Meet: " + meetLink + "\n\n" +
                "Good luck!\n\nMock Interview Scheduler";
    }

    private void sendSimpleEmail(String to, String subject, String body) {
        if (mockMode) {
            // mock mode: log instead of sending — safe for development/testing
            log.info("===== MOCK EMAIL =====");
            log.info("TO: {}", to);
            log.info("SUBJECT: {}", subject);
            log.info("BODY:\n{}", body);
            log.info("======================");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false); // false = plain text, change to true for HTML
            mailSender.send(message);
            log.info("Email sent to {}", to);
        } catch (MessagingException e) {
            // log and continue — email failure should not break the main flow
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

}
