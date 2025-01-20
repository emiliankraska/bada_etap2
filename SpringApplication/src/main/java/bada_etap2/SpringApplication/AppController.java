package bada_etap2.SpringApplication;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest; // For HttpServletRequest
import org.springframework.context.annotation.Configuration; // For @Configuration
import org.springframework.stereotype.Controller; // For @Controller
import org.springframework.web.bind.annotation.RequestMapping; // For @RequestMapping
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry; // For ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // For WebMvcConfigurer

@Configuration
public class AppController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index").setViewName("index");
//        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
    }

    @Controller
    public class DashboardController {

        @RequestMapping("/main")
        public String defaultAfterLogin(HttpServletRequest request) {
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/admin/main";
            } else if (request.isUserInRole("USER")) {
                return "redirect:/user/main";
            } else {
                return "dawdadwda";
            }
        }

        @RequestMapping("/admin/main")
        public String showAdminPage(Model model) {
            return "admin/main_admin"; // Corresponds to admin-specific Thymeleaf template
        }

        @RequestMapping("/user/main")
        public String showUserPage(Model model) {
            return "user/main_user"; // Corresponds to user-specific Thymeleaf template
        }
    }
}
