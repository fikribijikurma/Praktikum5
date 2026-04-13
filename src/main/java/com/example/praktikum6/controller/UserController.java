package com.example.praktikum6.controller;

import com.example.praktikum6.Mahasiswa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // List statis untuk menyimpan data sementara
    private static List<Mahasiswa> daftarMahasiswa = new ArrayList<>();

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password) {
        // Logika sederhana: username admin, password bebas (sesuai instruksi: nim masing-masing)
        if ("admin".equals(username)) {
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("listMhs", daftarMahasiswa);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(Model model) {
        model.addAttribute("mahasiswa", new Mahasiswa());
        return "form";
    }

    @PostMapping("/save")
    public String saveData(@ModelAttribute Mahasiswa mahasiswa) {
        daftarMahasiswa.add(mahasiswa);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}