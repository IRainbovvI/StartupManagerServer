package org.dev.controller;

import org.dev.model.User;
import org.dev.repository.StartupRepository;
import org.dev.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AppControllerTest {

    @InjectMocks
    private AppController appController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StartupRepository startupRepository;

    @Mock
    private Model model;

    @Test
    void testViewHomePage() {
        String result = appController.viewHomePage();
        assert(result.equals("index"));
    }

    @Test
    void testShowRegistrationForm() {
        String result = appController.showRegistrationForm(model);
        assert(result.equals("signup_form"));
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void testProcessRegister() {
        User user = new User();
        user.setEmail("testUser@gmail.com");
        user.setPassword("testPassword");

        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = appController.processRegister(user);

        assert(result.equals("register_success"));
        verify(userRepository).save(any(User.class));
    }




}