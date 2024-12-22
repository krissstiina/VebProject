package veb.cinema.demo.controllers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @GetMapping("/index")
    public String showHomePage(Model model) {
        LOG.log(Level.INFO,"Open home page");
        return "home/index";
    }
}

