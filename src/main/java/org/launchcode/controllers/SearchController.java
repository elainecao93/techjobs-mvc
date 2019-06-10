package org.launchcode.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    @RequestMapping(value = "results")
    public String searchResults(Model model, HttpServletRequest request) {

        String searchType = request.getParameter("searchType");
        String searchTerm = request.getParameter("searchTerm");

        ArrayList<HashMap<String, String>> output;

        if (searchTerm == null || searchTerm.length() == 0) {
            output = JobData.findAll();
        }
        else if (searchType.equals("all")) {
            output = JobData.findByValue(searchTerm);
        }
        else {
            output = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        assert(output != null);

        model.addAttribute("searchType", searchType);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("output", output);

        return "results";
    }

}
