package es.thefactory.javaee8app.hr.biz.service;

import java.util.List;

import javax.ejb.Local;

import es.thefactory.javaee8app.hr.biz.dto.EmployeeDto;
import es.thefactory.javaee8app.util.dal.DataAccessException;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Local
public interface EmployeeBeanLocal {
    
    /**
     * 
     * @param employeeId
     * @throws DataAccessException
     */
    void deleteById(Short employeeId) throws DataAccessException;
    
    /**
     * 
     * @return List<EmployeeDto>
     */
    List<EmployeeDto> findAll();
    
    /**
     * 
     * @param employeeId
     * @return EmployeeDto
     */
    EmployeeDto findById(Short employeeId);
    
    /**
     * 
     * @param employeeDto
     */
    void save(EmployeeDto employeeDto);
}
