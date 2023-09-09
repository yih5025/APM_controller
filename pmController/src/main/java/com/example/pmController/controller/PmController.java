package com.example.pmController.controller;

import com.example.pmController.api.ExternalServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class PmController {

    @Autowired
    private ExternalServiceClient externalServiceClient;

    @GetMapping("/")
    public String index() {
        System.out.println("index");
        return "index";
    }

    @PostMapping("/top")
    public String top(Model model) throws IOException {
        System.out.println("top method");

        String response = externalServiceClient.sendDegreeToExternalService("pm1_1");
        System.out.println("response = " + response);
        model.addAttribute("response", response);

        return "index";
    }

    @PostMapping("/middle")
    public String middle(Model model) throws IOException {
        System.out.println("middle method");

        String response = externalServiceClient.sendDegreeToExternalService("pm1_2");
        System.out.println("response = " + response);
        model.addAttribute("response", response);

        return "index";
    }

    @PostMapping("/bottom")
    public String bottom(Model model) throws IOException {
        System.out.println("bottom method");

        String response = externalServiceClient.sendDegreeToExternalService("pm1_3");
        System.out.println("response = " + response);
        model.addAttribute("response", response);

        return "index";
    }

    @PostMapping("/degree")
    public String degree(@RequestParam("degree") String degree, Model model) {
        System.out.println("degree = " + degree);

        try {
            int degreeValue = Integer.parseInt(degree);

            // degree 값이 0에서 360 사이에 있는지 확인
            if (degreeValue >= 0 && degreeValue <= 360) {
                // 유효한 경우
                String bottomDegree = "bottom_" + degreeValue;
                String response = externalServiceClient.sendDegreeToExternalService(bottomDegree);
                model.addAttribute("response", response);
                System.out.println("response = " + response);
                return "index";
            } else {
                // 유효하지 않은 경우, 사용자에게 에러 메시지 전달
                model.addAttribute("errorMessage", "Degree must be a value between 0 and 360.");
                return "index";
            }
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 경우, 사용자에게 에러 메시지 전달
            model.addAttribute("errorMessage", "Degree must be a number.");
            return "index";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
