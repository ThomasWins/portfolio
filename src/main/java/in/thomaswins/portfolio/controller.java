package in.thomaswins.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class controller {

    @Autowired
    private ContactRepository contactRepository;

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
    @ResponseBody
    public String handleContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String message) {
        try {
            Contact contact = new Contact(name, email, phone, message);
            contactRepository.save(contact);
            return "{\"success\": true, \"message\": \"Message saved successfully!\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"Error saving message: " + e.getMessage() + "\"}";
        }
    }

    @GetMapping("/admin/submissions")
    public String viewSubmissions(Model model) {
        List<Contact> submissions = contactRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("submissions", submissions);
        model.addAttribute("title", "Form Submissions");
        return "submissions";
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

