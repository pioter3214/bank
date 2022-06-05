package com.example.project1.service;

import com.example.project1.entity.AppUser;
import com.example.project1.entity.VerificationToken;
import com.example.project1.repo.AppUserRepo;
import com.example.project1.repo.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;
    private MailSenderService mailSenderService;
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo, MailSenderService mailSenderService, VerificationTokenRepo verificationTokenRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;
        this.mailSenderService = mailSenderService;
        this.verificationTokenRepo = verificationTokenRepo;
    }

    public void addNewUser(AppUser user, HttpServletRequest request){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepo.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepo.save(verificationToken);

        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                "/" +
                request.getContextPath() +
                "verify-token?token=" + token;

        try {
            mailSenderService.sendMail(user.getUsername(),"Verification Token",url,false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void verifyToken(String token){
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
    }

    public AppUser getAppUserByName(String name){
        return appUserRepo.findAppUserByUsername(name);
    }
}
