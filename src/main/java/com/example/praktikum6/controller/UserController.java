package com.example.praktikum6.controller;

import com.example.praktikum6.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private static List<User> listMahasiswa = new ArrayList<>();

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        // Password menggunakan NIM Anda
        if ("admin".equals(username) && "20230140063".equals(password)) {
            // Mengirim nama user ke redirect agar bisa dibaca di Home
            redirectAttributes.addFlashAttribute("currentUser", "Auliya");
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("error", "Username atau Password Salah!");
        return "redirect:/";
    }

    @GetMapping("/home")
    public String homePage(Model model, @ModelAttribute("currentUser") String currentUser) {
        model.addAttribute("mahasiswa", listMahasiswa);
        // Jika flash attribute kosong, beri nama default
        model.addAttribute("namaUser", (currentUser == null || currentUser.isEmpty()) ? "Admin" : currentUser);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        listMahasiswa.add(user);
        return "redirect:/home";
    }
}