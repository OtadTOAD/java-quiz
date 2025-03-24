package ge.ibsu.demo.controllers;

import ge.ibsu.demo.dto.AddCustomer;
import ge.ibsu.demo.dto.RequestData;
import ge.ibsu.demo.dto.SearchCustomer;
import ge.ibsu.demo.dto.projections.CustomerFullInfo;
import ge.ibsu.demo.dto.projections.CustomerFullName;
import ge.ibsu.demo.dto.projections.CustomerPhoneInfo;
import ge.ibsu.demo.dto.projections.PhoneInfo;
import ge.ibsu.demo.entities.Customer;
import ge.ibsu.demo.services.CustomerService;
import ge.ibsu.demo.util.GeneralUtil;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {"application/json"})
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/phoneInfo", method = RequestMethod.GET, produces = {"application/json"})
    public List<CustomerPhoneInfo> getPhoneInfo() {
        return customerService.searchCustomerPhones();
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/fullName", method = RequestMethod.GET, produces = {"application/json"})
    public List<CustomerFullName> customerFullNames() {
        return customerService.searchCustomerFullNames();
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public Customer getOne(@PathVariable Long id) {
        return customerService.getOne(id);
    }

    @PreAuthorize("hasRole('CUSTOMER_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json"})
    public Customer add(@RequestBody AddCustomer addCustomer) throws Exception {
        GeneralUtil.checkRequiredProperties(addCustomer, Arrays.asList("firstName", "lastName"));
        return customerService.addEditCustomer(addCustomer, null);
    }

    @PreAuthorize("hasAuthority('customer:add')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = {"application/json"})
    public Customer edit(@RequestBody AddCustomer addCustomer, @PathVariable Long id) throws Exception {
        GeneralUtil.checkRequiredProperties(addCustomer, Arrays.asList("firstName", "lastName"));
        return customerService.addEditCustomer(addCustomer, id);
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = {"application/json"})
    public Page<Customer> search(@RequestBody RequestData<SearchCustomer> rd) {
        return customerService.search(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/searchNative", method = RequestMethod.POST, produces = {"application/json"})
    public Page<Customer> searchNative(@RequestBody RequestData<SearchCustomer> rd) {
        return customerService.searchNative(rd.getData(), rd.getPaging());
    }

    @PreAuthorize("hasAuthority('customer:add')")
    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT, produces = {"application/json"})
    public Boolean changeCustomerStatus(@RequestBody RequestData<Integer> statusData, @PathVariable Long id) throws Exception {
        customerService.changeCustomerStatus(id, statusData.getData());
        return true;
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/searchPhones", method = RequestMethod.POST, produces = {"application/json"})
    public List<PhoneInfo> searchPhones(@RequestBody RequestData<SearchCustomer> rd) {
        return customerService.getPhones(rd.getData().getSearchText());
    }

    @PreAuthorize("hasAuthority('customer:read')")
    @RequestMapping(value = "/searchFullInfo", method = RequestMethod.POST, produces = {"application/json"})
    public Page<CustomerFullInfo> searchFullInfo(@RequestBody RequestData<SearchCustomer> rd) {
        return customerService.searchFullInfo(rd.getData(), rd.getPaging());
    }
}
