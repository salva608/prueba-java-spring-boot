package com.talentboard.talentboard.controllers;

import com.talentboard.talentboard.dtos.AuthRequest;
import com.talentboard.talentboard.dtos.ApplicationRequest;
import com.talentboard.talentboard.services.UserService;
import com.talentboard.talentboard.services.VacancyService;
import com.talentboard.talentboard.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final VacancyService vacancyService;
    private final UserService userService;
    private final ApplicationService applicationService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String registerForm() {
        return "auth/register";
    }

    // Registro público: Fuerza a que todo usuario nuevo sea CANDIDATE
    @PostMapping("/auth/register")
    public String processRegister(@ModelAttribute AuthRequest request) {
        request.setRole(com.talentboard.talentboard.models.enums.Role.CANDIDATE);
        userService.register(request);
        return "redirect:/login?registered=true";
    }

    @GetMapping("/vacancies")
    public String listVacancies(Model model) {
        model.addAttribute("vacancies", vacancyService.findAll());
        return "vacancies/list";
    }

    @GetMapping("/vacancies/new")
    public String showCreateVacancyForm() {
        return "vacancies/new";
    }

    @PostMapping("/vacancies/new")
    public String processCreateVacancy(@ModelAttribute com.talentboard.talentboard.dtos.VacancyRequest request) {
        vacancyService.create(request);
        return "redirect:/vacancies?created=true";
    }

    @GetMapping("/vacancies/{id}")
    public String viewVacancyDetail(@PathVariable Long id, Model model) {
        model.addAttribute("vacancy", vacancyService.findById(id));
        return "vacancies/detail";
    }

    @GetMapping("/applications/apply/{id}")
    public String showApplyForm(@PathVariable Long id, Model model) {
        model.addAttribute("vacancy", vacancyService.findById(id));
        return "applications/apply";
    }

    @PostMapping("/applications/apply")
    public String processApplication(@ModelAttribute ApplicationRequest request) {
        applicationService.apply(request);
        return "redirect:/vacancies?applied=true";
    }

    @GetMapping("/recruiter/dashboard")
    public String recruiterDashboard(Model model) {
        model.addAttribute("vacancies", vacancyService.findAll());
        model.addAttribute("applications", applicationService.findAll());
        return "recruiter/dashboard";
    }

    @PostMapping("/vacancies/{id}/status")
    public String changeVacancyStatus(
            @PathVariable Long id,
            @RequestParam("status") String status) {
        try {
            com.talentboard.talentboard.models.enums.VacancyStatus vacancyStatus =
                    com.talentboard.talentboard.models.enums.VacancyStatus.valueOf(status.toUpperCase().trim());
            vacancyService.changeStatus(id, vacancyStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: El estado de vacante '" + status + "' no coincide con tus Enums.");
            e.printStackTrace();
        }
        return "redirect:/recruiter/dashboard?updatedVacancy=true";
    }

    @PostMapping("/applications/{id}/status")
    public String changeApplicationStatus(
            @PathVariable Long id,
            @RequestParam("newStatus") String newStatus) {
        try {
            com.talentboard.talentboard.models.enums.ApplicationStatus applicationStatus =
                    com.talentboard.talentboard.models.enums.ApplicationStatus.valueOf(newStatus.toUpperCase().trim());
            applicationService.updateStatus(id, applicationStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: El estado de postulación '" + newStatus + "' no coincide con tus Enums.");
            e.printStackTrace();
        }
        return "redirect:/recruiter/dashboard?updatedApp=true";
    }

    // VISTA ADMIN: Muestra el formulario exclusivo para crear Reclutadores
    @GetMapping("/admin/register-recruiter")
    public String registerRecruiterForm() {
        return "auth/register-recruiter";
    }

    // ACCIÓN ADMIN: Procesa el registro forzando el rol RECRUITER
    @PostMapping("/admin/register-recruiter")
    public String processRegisterRecruiter(@ModelAttribute AuthRequest request) {
        request.setRole(com.talentboard.talentboard.models.enums.Role.RECRUITER);
        userService.register(request);
        return "redirect:/recruiter/dashboard?recruiterCreated=true";
    }
}