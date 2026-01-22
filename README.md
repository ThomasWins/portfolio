# Thomas Winslow Portfolio

A full-stack portfolio website built with Spring Boot and Thymeleaf, deployed on an Ubuntu home server with Nginx reverse proxy and Cloudflare CDN.

## Tech Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 4.0.1** - Application framework
  - Spring Boot Starter Web MVC - Web layer
  - Spring Boot Starter Thymeleaf - Template engine
  - Spring Boot Starter Data JPA - Data persistence
  - Spring Boot Starter Mail - Email functionality
  - Spring Boot DevTools - Development utilities
- **Maven** - Build and dependency management
- **MySQL 8.0.33** - Relational database (hosted on Ubuntu home server)

### Frontend
- **HTML5** - Markup
- **CSS3** - Styling ([styles.css](src/main/resources/static/assets/css/styles.css))
- **JavaScript** - Client-side scripting ([scripts.js](src/main/resources/static/assets/js/scripts.js))
- **Thymeleaf** - Server-side templating

### Infrastructure & Deployment
- **Ubuntu Server** - Host operating system (application and database)
- **Nginx** - Reverse proxy and web server
- **Cloudflare** - CDN, SSL/TLS, and DNS management
- **systemd** - Service management

### Development Tools
- **DotEnv Java** - Environment variable management
- **Git** - Version control

## Project Structure

```
portfolio/
├── src/
│   ├── main/
│   │   ├── java/in/thomaswins/portfolio/
│   │   │   ├── PortfolioApplication.java    # Main application entry point
│   │   │   ├── controller.java              # Web controllers
│   │   │   ├── Contact.java                 # Contact entity model
│   │   │   ├── ContactRepository.java       # JPA repository
│   │   │   ├── EmailService.java            # Email service layer
│   │   │   └── DotenvConfig.java            # Environment configuration
│   │   └── resources/
│   │       ├── application.properties       # Spring configuration
│   │       ├── static/                      # Static assets
│   │       │   └── assets/
│   │       │       ├── css/
│   │       │       ├── images/
│   │       │       └── js/
│   │       └── templates/                   # Thymeleaf templates
│   │           ├── master.html              # Master layout
│   │           ├── header.html              # Header component
│   │           ├── navbar.html              # Navigation component
│   │           ├── footer.html              # Footer component
│   │           ├── home.html                # Homepage
│   │           ├── projects.html            # Projects page
│   │           ├── projectsSection.html     # Projects section
│   │           ├── resume.html              # Resume page
│   │           ├── contact.html             # Contact page
│   │           ├── about.html               # About page
│   │           ├── privacy.html             # Privacy policy
│   │           └── terms.html               # Terms of service
├── target/                                  # Compiled artifacts
├── pom.xml                                  # Maven configuration
├── mvnw / mvnw.cmd                          # Maven wrapper scripts
└── README.md                                # This file
```

## Features

- **Responsive Design** - Mobile-first, fully responsive layout
- **Dynamic Content** - Server-side rendered pages with Thymeleaf
- **Contact Form** - Contact form with email notification and database persistence
- **Project Showcase** - Portfolio projects display
- **Resume Section** - Professional experience and skills
- **Database Integration** - MySQL database for storing contact submissions
- **Email Notifications** - Automatic email alerts for new contact form submissions

## Security & Infrastructure

- Environment variables managed securely (not committed to version control)
- Cloudflare DDoS protection and Web Application Firewall
- Nginx reverse proxy configuration
- SSL/TLS encryption through Cloudflare
- MySQL database hosted locally on Ubuntu home server
- systemd service for automatic application startup and monitoring
- Honeypots for botted contact form submissions

## Database Schema

### Contact Table
```sql
CREATE TABLE contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## License

This project is private and proprietary.

## Author

**Thomas Winslow**
- Email: thomaswins128@gmail.com
- Portfolio: https://thomaswins.com

## Acknowledgments

- Spring Boot documentation
- Thymeleaf documentation
- Nginx documentation
- Cloudflare documentation
 for contact form notifications.