package in.thomaswins.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class controller {

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
            @RequestParam String phone,
            @RequestParam String message,
            @RequestParam(required = false) String website,
            @RequestParam(required = false) String formLoadTime,
            RedirectAttributes redirectAttributes) {
        try {
            // Bot protection: Check honeypot field
            if (website != null && !website.isEmpty()) {
                // Honeypot field was filled - likely a bot
                redirectAttributes.addFlashAttribute("errorMessage", "Spam detected. Please try again.");
                return "redirect:/contact";
            }
            
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
            
            // Basic validation
            if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                message == null || message.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "All fields are required.");
                return "redirect:/contact";
            }
            
            // Save the contact
            Contact contact = new Contact(name, email, phone, message);
            contactRepository.save(contact);
            emailService.sendContactFormEmail(name, email, phone, message);
            redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully! Thank you for reaching out.");
            return "redirect:/contact";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sending message. Please try again later.");
            return "redirect:/contact";
        }
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

