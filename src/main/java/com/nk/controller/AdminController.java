package com.nk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nk.model.ContactForm;
import com.nk.model.ServiceForm;
import com.nk.service.AdminCredentialsService;
import com.nk.service.BookingFormService;
import com.nk.service.ContactFormService;
import com.nk.service.ServiceFormService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	private ContactFormService contactFormService;
	private AdminCredentialsService adminCredentialsService;
	private BookingFormService bookingFormService;
	private ServiceFormService  serviceFormService;
	
	
	@Autowired
	public void setServiceFormService(ServiceFormService serviceFormService) {
		this.serviceFormService = serviceFormService;
	}

	@Autowired
	public void setBookingFormService(BookingFormService bookingFormService) {
		this.bookingFormService = bookingFormService;
	}

	@Autowired
	 public void setAdminCredentialsService(AdminCredentialsService adminCredentialsService) {
		this.adminCredentialsService = adminCredentialsService;
	}

	@Autowired
	 public void setContactFormService(ContactFormService contactFormService) {
		this.contactFormService = contactFormService;
	}

	@GetMapping("dashboard")
     public String adminDashboard()
     {
    	 return "admin/dashboard";
     }
	 
	 @GetMapping("readAllContacts")
	 public String readAllContacts(Model model)
	 {
		 model.addAttribute("allcontacts",contactFormService.readAllContactsService());
		 
		 return "admin/readallcontacts";
      }
	 
	 
	 @GetMapping("deleteContact/{id}")
	 public String deleteContact(@PathVariable int id,RedirectAttributes redirectAttributes)
	 {
		 contactFormService.deleteContactService(id);
		 redirectAttributes.addFlashAttribute("message","CONTACT DELETED SUCCESSFULLY");
		 
		 return "redirect:/admin/readAllContacts";
      }
	 
	 
	 @GetMapping("changeCredentials")
	 public String changeCredentialsView()
	 {
		 return "admin/changecredentials";
      }
	 
	 @PostMapping("changeCredentials")
	 public String changeCredentials(
			 @RequestParam("oldusername") String oldusername,
			 @RequestParam("oldpassword") String oldpassword,
			 @RequestParam("newusername") String newusername,
			 @RequestParam("newpassword") String newpassword,
			 RedirectAttributes redirectAttributes
			 ){
		 
		 String result=adminCredentialsService.checkAdminCredentials(oldusername, oldpassword);
		 if(result.equals("SUCCESS"))
		 {
			 result=adminCredentialsService.updateAdminCredentials(newusername, newpassword, oldusername);
		     
		     redirectAttributes.addFlashAttribute("message",result);
		   
		 }
		 else
		 {
			 redirectAttributes.addFlashAttribute("message",result);
		 }
		 
		 return "redirect:/admin/dashboard";
      }
	 
	 @GetMapping("readAllBookings")
	 public String readAllBookings(Model model)
	 {
		 model.addAttribute("allBookings",bookingFormService.readAllBookingsService());
		 
		 return "admin/readallbookings";
      }
	 
	 @GetMapping("deleteBooking/{id}")
	 public String deleteBooking(@PathVariable int id,RedirectAttributes redirectAttributes)
	 {
		 bookingFormService.deleteBookingsService(id);
		 redirectAttributes.addFlashAttribute("message","BOOKING DELETED SUCCESSFULLY");
		 
		 return "redirect:/admin/readAllBookings";
      }
	 
	 @GetMapping("addService")
	 public String addServiceView()
	 {
		 return "admin/addservice";
      }
	 
	 @InitBinder
	 public void stopBinding(WebDataBinder webDataBinder)
	 {
		 webDataBinder.setDisallowedFields("image");
	 }
	 
	 @PostMapping("addService")
	 public String addService(@ModelAttribute ServiceForm serviceForm ,@RequestParam("image") MultipartFile multipartFile,RedirectAttributes redirectAttributes)
	 {
		 String originalFilename=multipartFile.getOriginalFilename();
		 serviceForm.setImage(originalFilename);
		 
		 try
		 {
			 ServiceForm service=serviceFormService.addService(serviceForm,multipartFile);
			 if(service!=null)
			 {
				 redirectAttributes.addFlashAttribute("msg","Service added Successfully");
			 }
			 else
			 {
				 redirectAttributes.addFlashAttribute("msg","Something Went Wrong");
			 }
		 } 
		 catch (Exception e)
		 {
			 redirectAttributes.addFlashAttribute("msg","Something Went Wrong");
		 }
		 return "redirect:/admin/addService";
      }
	 
	 @GetMapping("readAllServices")
	 public String readAllServices(Model model)
	 {
		 model.addAttribute("allServices",serviceFormService.readAllServices());
		 
		 return "admin/readallservices";
      }
	 
	 @GetMapping("deleteServices/{id}")
	 public String deleteServices(@PathVariable int id,RedirectAttributes redirectAttributes)
	 {
		 serviceFormService.deleteService(id);
		 redirectAttributes.addFlashAttribute("message","BOOKING DELETED SUCCESSFULLY");
		 
		 return "redirect:/admin/readAllServices";
      }
	 
}
