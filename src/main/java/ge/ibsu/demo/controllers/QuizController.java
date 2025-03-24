package ge.ibsu.demo.controllers;

import ge.ibsu.demo.dto.QuizDTO;
import ge.ibsu.demo.dto.RequestData;
import ge.ibsu.demo.dto.SearchCustomer;
import ge.ibsu.demo.entities.Customer;
import ge.ibsu.demo.services.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QuizController {
    CustomerService customerService;
    public QuizController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/quizCustomerGetter", produces = "application/json")
    public Page<QuizDTO> searchCustomers(@RequestBody RequestData<SearchCustomer> rd) {
        return customerService.getCustomerDetails(rd.getPaging());
    }
}
