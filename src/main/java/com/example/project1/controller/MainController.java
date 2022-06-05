package com.example.project1.controller;

import com.example.project1.entity.AppUser;
import com.example.project1.entity.BankAccount;
import com.example.project1.model.LimitModel;
import com.example.project1.model.TransferModel;
import com.example.project1.service.BankAccountService;
import com.example.project1.service.TransferHistoryService;
import com.example.project1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController {

    private UserService userService;
    private BankAccountService bankAccountService;
    private TransferHistoryService transferHistoryService;

    @Autowired
    public MainController(UserService userService, BankAccountService bankAccountService,TransferHistoryService transferHistoryService) {
        this.userService = userService;
        this.transferHistoryService = transferHistoryService;
        this.bankAccountService = bankAccountService;
    }

    @RequestMapping("")
    public String redirect(){
        return "redirect:/start";
    }

    @RequestMapping("/start")
    public String start(){
        return "start";

    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/singup")
    public ModelAndView singup(){
        return new ModelAndView("registration","user",new AppUser());
    }

    @RequestMapping("/registration")
    public ModelAndView registration(AppUser user,HttpServletRequest request){
        userService.addNewUser(user, request);
        bankAccountService.createBankAccout(user);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken(@RequestParam String token){
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/account")
    public String account(Principal principal, Model model){
        AppUser appUser = userService.getAppUserByName(principal.getName());
        BankAccount bankAccount = bankAccountService.findBankAccountByUser(appUser);
        model.addAttribute("user",appUser);
        model.addAttribute("account",bankAccount);
        model.addAttribute("history",transferHistoryService.getTransferHistory(bankAccount.getAccNumber()));
        return "account";
    }

    @RequestMapping("/transfer-done")
    public ModelAndView transfer(Principal principal, TransferModel transferModel, HttpServletRequest request){
        AppUser appUser = userService.getAppUserByName(principal.getName());
        BankAccount bankAccount = bankAccountService.findBankAccountByUser(appUser);
        if (bankAccountService.transfer(bankAccount.getAccNumber(), Long.valueOf(transferModel.getAccNumber()),transferModel.getSum())){
            return new ModelAndView("redirect:/account");
        }
        return new ModelAndView("/transfer");
    }

    @RequestMapping("/transfer")
    public ModelAndView transferPage(){
        return new ModelAndView("/transfer", "transferModel", new TransferModel());
    }

    @RequestMapping("/set-limit")
    public ModelAndView setlimit(Principal principal,LimitModel limit,HttpServletRequest request){
        AppUser appUser = userService.getAppUserByName(principal.getName());
        BankAccount bankAccount = bankAccountService.findBankAccountByUser(appUser);

        bankAccountService.setTransferLimit(bankAccount,limit.getLimit());
        return new ModelAndView("redirect:/account");
    }

    @RequestMapping("/limit")
    public ModelAndView limit(){
        return new ModelAndView("/limit","limit",new LimitModel());
    }
}
