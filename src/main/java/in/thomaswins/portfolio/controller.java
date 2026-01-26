package in.thomaswins.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class controller {

    private static final Logger logger = LoggerFactory.getLogger(controller.class);

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping({"/", "", "/home"})
    public String showHomePage(Model model) {
        model.addAttribute("title", "Home");
        return "master";
    }

    @GetMapping("/projects")
    public String showProjectsPage(Model model) {
        model.addAttribute("title", "Projects");
        return "master";
    }

    @GetMapping("/resume")
    public String showResumePage(Model model) {
        model.addAttribute("title", "Resume");
        return "master";
    }

    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("title", "Contact");
        return "master";
    }

    @PostMapping("/contact")
    public String handleContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String message,
            @RequestParam(required = false) String botTrap,
            @RequestParam(required = false) String formLoadTime,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            // Bot protection disabled temporarily
            /*
            if (botTrap != null && botTrap.length() > 0) {
                String ipAddress = getClientIpAddress(request);
                logger.warn("BOT TRAP TRIGGERED! IP: {} | BotTrap: {} | Name: {} | Email: {} | User-Agent: {}", 
                    ipAddress, botTrap, name, email, request.getHeader("User-Agent"));
                redirectAttributes.addFlashAttribute("errorMessage", "Spam detected.");
                return "redirect:/contact";
            }
            */
            
            /*
            // Bot protection: Check form submission timing
            if (formLoadTime != null && !formLoadTime.isEmpty()) {
                try {
                    long loadTime = Long.parseLong(formLoadTime);
                    long currentTime = System.currentTimeMillis();
                    long timeDiff = currentTime - loadTime;
                    
                    // If form submitted in less than 2 seconds, likely a bot
                    if (timeDiff < 2000) {
                        redirectAttributes.addFlashAttribute("errorMessage", "Form submitted too quickly. Please try again.");
                        return "redirect:/contact";
                    }
                } catch (NumberFormatException e) {
                    // Invalid timestamp format
                }
            }
            */
            
            // Basic validation
            if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                message == null || message.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "All fields are required.");
                return "redirect:/contact";
            }
            
            // Save the contact with optional phone number
            String phoneNumber = (phone != null && !phone.trim().isEmpty()) ? phone : "";
            Contact contact = new Contact(name, email, phoneNumber, message);
            contactRepository.save(contact);
            emailService.sendContactFormEmail(name, email, phoneNumber, message);
            redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully! Thank you for reaching out.");
            return "redirect:/contact";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sending message. Please try again later.");
            return "redirect:/contact";
        }
    }

    // Helper method to get client IP address (handles proxies and load balancers)
    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // Handle multiple IPs (take the first one)
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        return ipAddress;
    }

    @GetMapping("/privacy")
    public String showPrivacyPage(Model model) {
        model.addAttribute("title", "Privacy");
        return "master";
    }

    @GetMapping("/terms")
    public String showTermsPage(Model model) {
        model.addAttribute("title", "Terms");
        return "master";
    }
}

