package it.uniroma3.siw.calcio.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.security.oauth2.core.user.OAuth2User;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute("userDetails")
    public Object getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return principal;
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) principal;
                // Creiamo un oggetto finto con il nome (es. da Google o GitHub)
                return new Object() {
                    public String getUsername() {
                        String name = oauth2User.getAttribute("name");
                        if (name == null) name = oauth2User.getAttribute("login"); // per GitHub
                        return name != null ? name : "Utente OAuth2";
                    }
                };
            }
        }
        return null;
    }
}