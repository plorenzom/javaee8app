package es.thefactory.javaee8app.hr.biz.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.thefactory.javaee8app.hr.biz.dto.EmployeeDto;
import es.thefactory.javaee8app.hr.dal.entity.Employee;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Mapper(componentModel = "cdi")
public interface EmployeeMapper {
    
    /**
     * 
     * @param employee
     * @return EmployeeDto
     */
    EmployeeDto toDto(Employee employee);
    
    /**
     * 
     * @param employeeList
     * @return List<EmployeeDto>
     */
    List<EmployeeDto> toDtoList(List<Employee> employeeList);
    
    /**
     * 
     * @param employeeDto
     * @return Employee
     */
    Employee toEntity(EmployeeDto employeeDto);
}
