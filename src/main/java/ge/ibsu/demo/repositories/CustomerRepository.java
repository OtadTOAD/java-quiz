package ge.ibsu.demo.repositories;

import ge.ibsu.demo.dto.projections.CustomerFullInfo;
import ge.ibsu.demo.dto.projections.PhoneInfo;
import ge.ibsu.demo.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("From Customer where concat(concat(firstName, ' '), lastName) like :searchText")
    Page<Customer> searchCustomers(@Param("searchText") String searchText, Pageable pageable);

    @Query(value = "select * from customer " +
            "where concat(concat(first_name, ' '), last_name) like :searchText",
    countQuery = "select count(*) from customer " +
            "where concat(concat(first_name, ' '), last_name) like :searchText",
    nativeQuery = true)
    Page<Customer> searchCustomersViaNativeQuery(@Param("searchText") String searchText, Pageable pageable);

    @Modifying
    @Query("Update Customer c set c.active = :active where c.id = :id")
    void updateStatus(@Param("active") Integer active, @Param("id") Long id);

    List<PhoneInfo> findAllByFirstName(String firstName);

    <T> List<T> findAllByActive(Integer active, Class<T> type);

    @Query("select new ge.ibsu.demo.dto.projections.CustomerFullInfo(c.firstName, c.lastName, c.phone, c.address.address) " +
            "From Customer c where concat(concat(c.firstName, ' '), c.lastName) like :searchText")
    Page<CustomerFullInfo> searchCustomerFullInfo(@Param("searchText") String searchText, Pageable pageable);

}
