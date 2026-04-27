package com.example.praktikum6.controller;

import com.example.praktikum6.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        if ("admin".equals(username) && "Admin".equals(password)) {
            // Mengirim nama user ke redirect agar bisa dibaca di Home
            redirectAttributes.addFlashAttribute("currentUser", "admin");
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

    @PostMapping("/upload-profile")
    public String uploadProfile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/home";
        }

        try {
            String fileName = "profile.png";
            String relativeDir = "src/main/resources/static/img/";
            Path srcPath = Paths.get(relativeDir + fileName);
            Files.copy(file.getInputStream(), srcPath, StandardCopyOption.REPLACE_EXISTING);
            
            // Also try to save to target directory for immediate visibility
            Path targetPath = Paths.get("target/classes/static/img/" + fileName);
            if (Files.exists(targetPath.getParent())) {
                Files.copy(srcPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }
}