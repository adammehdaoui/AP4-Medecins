package io.github.mehdaouiadam.devap4.controller;

import io.github.mehdaouiadam.devap4.service.DepartementService;
import io.github.mehdaouiadam.devap4.service.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    final DepartementService departementService;
    final PaysService paysService;

    @Autowired
    public MainController(DepartementService departementService, PaysService paysService) {
        this.departementService = departementService;
        this.paysService = paysService;
    }

    @GetMapping("/")
    public String Homepage() {
        return "Homepage";
    }

    @GetMapping(path = "/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        // Set the "error" attribute to true if the request parameter "error" is present
        if (error != null) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("error", false);
        }

        model.addAttribute("title", "Connexion");
        return "login";
    }

    @GetMapping(path = "/dashboard")
    public String dashboard(Model model){

        Map<String,Long> dataForChartDep = this.departementService.findDepartementCountMedecins();
        Map<String,Long> dataForChartPays = this.paysService.findPaysCountMedecins();

        model.addAttribute("keySet", dataForChartDep.keySet());
        model.addAttribute("values", dataForChartDep.values());

        model.addAttribute("keySetBis", dataForChartPays.keySet());
        model.addAttribute("valuesBis", dataForChartPays.values());

        return "Statistiques";
    }
}
