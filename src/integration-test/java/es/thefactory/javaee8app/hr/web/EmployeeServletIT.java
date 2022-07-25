package es.thefactory.javaee8app.hr.web;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.thefactory.javaee8app.util.web.URLBuilder;
import es.thefactory.javaee8app.util.web.WebActions;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@RunWith(Arquillian.class)
public class EmployeeServletIT {
    
    /**
     * 
     */
    private static final String SERVLET_PATH = "/employee";
    
    /**
     * 
     */
    private static final Short EMPLOYEE_ID = 1;
    
    /**
     * 
     */
    private static final String NAME = "James Arthur";
    
    /**
     * 
     */
    private static final String LAST_NAME = "Gosling";
    
    /**
     * 
     */
    private static final String POSITION = "Ingeniero Software";
    
    /**
     * 
     */
    private static final int GROSS_SALARY = 60000;
    
    /**
     * 
     */
    @ArquillianResource
    private URL baseURL;
    
    /**
     * 
     * @return WebArchive
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final String WEBAPP_ROOT = "src/main/webapp";
        File commonsBeanUtilsLib = Maven.resolver()
            .loadPomFromFile("pom.xml")
            .resolve("commons-beanutils:commons-beanutils")
            .withoutTransitivity()
            .asSingleFile();
        WebArchive warFile = ShrinkWrap.create(WebArchive.class, "javaee8app-test.war");
        warFile.addClass("es.thefactory.javaee8app.hr.biz.dto.EmployeeDto")
            .addClass("es.thefactory.javaee8app.hr.biz.mapper.EmployeeMapper")
            .addClass("es.thefactory.javaee8app.hr.biz.mapper.EmployeeMapperImpl")
            .addClass("es.thefactory.javaee8app.hr.biz.service.EmployeeBean")
            .addClass("es.thefactory.javaee8app.hr.biz.service.EmployeeBeanLocal")
            .addClass("es.thefactory.javaee8app.hr.dal.entity.Employee")
            .addClass("es.thefactory.javaee8app.hr.dal.repository.EmployeeRepository")
            .addClass("es.thefactory.javaee8app.hr.web.EmployeeServlet")
            .addClass("es.thefactory.javaee8app.util.dal.DataAccessException")
            .addClass("es.thefactory.javaee8app.util.web.WebActions")
            .addAsWebResource(new File(WEBAPP_ROOT, "WEB-INF/employee/form.jsp"), "WEB-INF/employee/form.jsp")
            .addAsWebResource(new File(WEBAPP_ROOT, "WEB-INF/employee/list.jsp"), "WEB-INF/employee/list.jsp")
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsResource("scripts/javaee8app-test-db.sql")
            .addAsResource("scripts/javaee8app-test-data.sql")
            .addAsLibrary(commonsBeanUtilsLib);
        
        return warFile;
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void addShouldReturnOkStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.ADD);
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);
        
        String contentType = conn.getContentType();
        assertEquals("text/html;charset=UTF-8", contentType);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void deleteShouldRedirectToListView() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.DELETE);
        params.put("employeeId", String.valueOf(EmployeeServletIT.EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setInstanceFollowRedirects(false);
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_MOVED_TEMPORARILY, responseCode);
        
        params = new HashMap<String, String>();
        params.put("action", WebActions.LIST);
        url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        String expectedLocation = url.toString();
        String location = conn.getHeaderField("Location");
        assertEquals(expectedLocation, location);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void deleteShouldReturnBadRequestStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.DELETE);
        params.put("employeeId", "");
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setInstanceFollowRedirects(false);
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void deleteShouldReturnNotFoundStatusCode() throws IOException {
        final Short EMPLOYEE_ID = 2;
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.DELETE);
        params.put("employeeId", String.valueOf(EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setInstanceFollowRedirects(false);
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doGetShouldReturnBadRequestStatusCode() throws IOException {
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void doPostShouldRedirectToListView() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("employeeId", String.valueOf(EmployeeServletIT.EMPLOYEE_ID));
        params.put("name", EmployeeServletIT.NAME);
        params.put("lastName", EmployeeServletIT.LAST_NAME);
        params.put("position", EmployeeServletIT.POSITION);
        params.put("grossSalary", String.valueOf(EmployeeServletIT.GROSS_SALARY));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        byte[] postData = url.getQuery().getBytes();
        url = new URL(url.toString().split("\\?")[0]);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(false);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postData);
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_MOVED_TEMPORARILY, responseCode);
        
        params = new HashMap<String, String>();
        params.put("action", WebActions.LIST);
        url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        String expectedLocation = url.toString();
        String location = conn.getHeaderField("Location");
        assertEquals(expectedLocation, location);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void doPostShouldReturnInternalServerErrorCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("employeeId", String.valueOf(EmployeeServletIT.EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        byte[] postData = url.getQuery().getBytes();
        url = new URL(url.toString().split("\\?")[0]);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(false);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postData);
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void editShouldReturnBadRequestStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.EDIT);
        params.put("employeeId", "");
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void editShouldReturnNotFoundStatusCode() throws IOException {
        final Short EMPLOYEE_ID = 2;
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.EDIT);
        params.put("employeeId", String.valueOf(EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void editShouldReturnOkStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.EDIT);
        params.put("employeeId", String.valueOf(EmployeeServletIT.EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);
        
        String contentType = conn.getContentType();
        assertEquals("text/html;charset=UTF-8", contentType);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void listShouldReturnOkStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.LIST);
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.SERVLET_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);
        
        String contentType = conn.getContentType();
        assertEquals("text/html;charset=UTF-8", contentType);
    }
}
