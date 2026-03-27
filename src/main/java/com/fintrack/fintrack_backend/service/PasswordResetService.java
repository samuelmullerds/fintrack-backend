package com.fintrack.fintrack_backend.service;

import com.fintrack.fintrack_backend.dto.ForgotPasswordRequest;
import com.fintrack.fintrack_backend.dto.ResetPasswordRequest;
import com.fintrack.fintrack_backend.model.PasswordResetToken;
import com.fintrack.fintrack_backend.model.User;
import com.fintrack.fintrack_backend.repository.PasswordResetTokenRepository;
import com.fintrack.fintrack_backend.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    private static final String RESET_LINK_BASE = "fintrack://reset-password?token=";
    private static final int TOKEN_EXPIRATION_MINUTES = 30;

    public PasswordResetService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            JavaMailSender mailSender,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {

            tokenRepository.deleteAllByUserId(user.getId());

            String rawToken = UUID.randomUUID().toString();

            String hashedToken = passwordEncoder.encode(rawToken);

            PasswordResetToken resetToken = new PasswordResetToken(
                    hashedToken,
                    user,
                    LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES)
            );

            tokenRepository.save(resetToken);

            sendResetEmail(user, rawToken);
        });
    }

    private void sendResetEmail(User user, String token) {
        String link = RESET_LINK_BASE + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("FinTrack — Redefinição de senha");
        message.setText(
            "Olá, " + user.getName() + "!\n\n" +
            "Recebemos uma solicitação para redefinir a senha da sua conta FinTrack.\n\n" +
            "Toque no link abaixo no seu celular para criar uma nova senha:\n" +
            link + "\n\n" +
            "O link é válido por " + TOKEN_EXPIRATION_MINUTES + " minutos.\n\n" +
            "Se você não solicitou isso, ignore este e-mail.\n\n" +
            "Caso suspeite de atividade indevida, entre em contato com o suporte.\n\n" +
            "— Equipe FinTrack"
        );

        mailSender.send(message);
    }

    public void resetPassword(ResetPasswordRequest request) {

        // Busca todos tokens não utilizados
        List<PasswordResetToken> tokens = tokenRepository.findAllByUsedFalse();

        PasswordResetToken validToken = null;

        for (PasswordResetToken token : tokens) {
            if (passwordEncoder.matches(request.getToken(), token.getToken())) {
                validToken = token;
                break;
            }
        }

        if (validToken == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido");
        }

        if (validToken.isExpired()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expirado");
        }

        validatePassword(request.getNewPassword());

        User user = validToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        validToken.setUsed(true);
        tokenRepository.save(validToken);
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A senha deve ter pelo menos 6 caracteres"
            );
        }
    }
}