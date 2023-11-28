package org.dev.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dev.model.Startup;
import org.dev.model.User;
import org.dev.repository.StartupRepository;
import org.dev.repository.UserRepository;
import org.dev.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private StartupRepository startupRepo;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }

    @GetMapping("/my_startups")
    public String listMyStartups(Model model, Authentication authentication) {
        model.addAttribute("currentUser", getAuthenticatedUser(authentication));

        return "my_startups";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("startup", new Startup());
        return "add";
    }

    @PostMapping("/add_startup")
    public String addStartup(Model model, Startup startup, Authentication authentication) {
        startupRepo.save(startup);

        User currentUser = getAuthenticatedUser(authentication);
        List<Startup> startups = currentUser.getListStartups();
        startups.add(startup);
        currentUser.setListStartups(startups);
        userRepo.save(currentUser);
        model.addAttribute("currentUser", getAuthenticatedUser(authentication));

        return "redirect:/my_startups";
    }

    @PostMapping("/delete")
    public String deleteStartup(@RequestParam Long id, Authentication authentication, Model model) {
        startupRepo.deleteById(id);

        User currentUser = getAuthenticatedUser(authentication);
        List<Startup> startups = currentUser.getListStartups();
        startups.remove(id);
        currentUser.setListStartups(startups);
        userRepo.save(currentUser);

        model.addAttribute("currentUser", getAuthenticatedUser(authentication));

        return "redirect:/my_startups";
    }

    @PostMapping("/edit")
    public String showEditForm(Model model, @RequestParam Long id) {
        model.addAttribute("startup", startupRepo.findById(id).get());
        model.addAttribute("id", id);
        return "edit";
    }

    @PostMapping("/edit_startup")
    public String editStartup(Model model, Startup startup, Authentication authentication, @RequestParam Long id) {
        Optional<Startup> existingStartupOptional = startupRepo.findById(id);

        if (existingStartupOptional.isPresent()) {
            Startup existingStartup = existingStartupOptional.get();
            existingStartup.setTitle(startup.getTitle());
            existingStartup.setDescription(startup.getDescription());
            startupRepo.save(existingStartup);
        }

        model.addAttribute("currentUser", getAuthenticatedUser(authentication));
        return "redirect:/my_startups";
    }

    @GetMapping("/all_startups")
    public String listAllStartups(Model model) {
        model.addAttribute("startups", startupRepo.findAll());

        return "all_startups";
    }


    public User getAuthenticatedUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        Optional<User> user = userRepo.findById(userId);
        return user.orElseGet(User::new);
    }
}
