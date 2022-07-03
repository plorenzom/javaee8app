package es.thefactory.javaee8app.hr.biz.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.thefactory.javaee8app.hr.biz.dto.EmployeeDto;
import es.thefactory.javaee8app.hr.biz.mapper.EmployeeMapper;
import es.thefactory.javaee8app.hr.dal.entity.Employee;
import es.thefactory.javaee8app.hr.dal.repository.EmployeeRepository;
import es.thefactory.javaee8app.util.dal.DataAccessException;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Stateless
public class EmployeeBean implements EmployeeBeanLocal {
    
    /**
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeBean.class.getName());
    
    /**
     * 
     */
    @Inject
    private EmployeeMapper employeeMapper;
    
    /**
     * 
     */
    @Inject
    private EmployeeRepository employeeRepo;
    
    /**
     * 
     */
    @Override
    public void deleteById(Short employeeId) throws DataAccessException {
        LOGGER.info("Entering method deleteById(employeeId)");
        employeeRepo.deleteById(employeeId);
        LOGGER.info("Exiting method deleteById(employeeId)");
    }
    
    /**
     * 
     */
    @Override
    public List<EmployeeDto> findAll() {
        LOGGER.info("Entering method findAll()");
        List<Employee> employeeList = employeeRepo.findAll();
        List<EmployeeDto> employeeDtoList = employeeMapper.toDtoList(employeeList);
        LOGGER.info("Exiting method findAll()");
        
        return employeeDtoList;
    }
    
    /**
     * 
     */
    @Override
    public EmployeeDto findById(Short employeeId) {
        LOGGER.info("Entering method findById(employeeId)");
        Employee employee = employeeRepo.findById(employeeId);
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        LOGGER.info("Exiting method findById(employeeId)");
        
        return employeeDto;
    }
    
    /**
     * 
     */
    @Override
    public void save(EmployeeDto employeeDto) {
        LOGGER.info("Entering method save(employeeDto)");
        Employee employee = employeeMapper.toEntity(employeeDto);
        
        if (employee.getEmployeeId() == null) {
            employeeRepo.insert(employee);
            employeeDto.setEmployeeId(employee.getEmployeeId());
        } else {
            employeeRepo.update(employee);
        }
        
        LOGGER.info("Exiting method save(employeeDto)");
    }
}
