package main.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import main.entities.repoInterfaces.CustomerRepo;
import main.entities.tables.Customer;
import webComponents.WebCustomer;

@RestController
@RequestMapping("api/login")
public class LoginApi {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> loginCheck(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("auth") != null && (boolean) session.getAttribute("auth")) {
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(true);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
	}
	
	/**
	 * Try to authenticate a Customer.
	 * 
	 * @param request
	 * @param response
	 * @return status: true on success. false and error status on failure.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> login(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody WebCustomer webCustomer) {
		
		String user = webCustomer.getCustName();
		String password = webCustomer.getPassword();
		Customer customer = customerRepo.findCustomerByNameAndPassword(user, password);
		
		if (customer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		// Login successful, set session data
		HttpSession session = request.getSession();
		session.setAttribute("auth", true);
		session.setAttribute("id", customer.getId());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(true);
	}
	
}
