package com.example.myproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myproject.Resitory.IInvoiceService;
import com.example.myproject.exception.InvoiceNotFoundException;
import com.example.myproject.models.Invoice;

@Controller
public class InvoiceController {

	@Autowired
	private IInvoiceService service;

	@RequestMapping("/")
	public String showHomePage() {
		return "homePage";
	}

	@RequestMapping("/register")
	public String showRegistration() {
		return "registerInvoicePage";
	}

	@RequestMapping("/save")
	public String saveInvoice(@ModelAttribute Invoice invoice, Model model) {
		service.saveInvice(invoice);
		Long id = service.saveInvice(invoice).getId();
		String message = "Record with id : '" + id + "' is saved successfully !";
		model.addAttribute("message", message);
		return "registerInvoicePage";
	}

	@RequestMapping("/getAllInvoices")
	public String getAllInvoices(@RequestParam(value = "message", required = false) String message, Model model) {
		List<Invoice> invoices =  service.getAllInvoices();
		model.addAttribute("list", invoices);
		model.addAttribute("message", message);
		return "allInvoicesPage";
	}

	@RequestMapping("/edit")
	public String getEditPage(Model model, RedirectAttributes attributes, @RequestParam Long id) {
		String page = null;
		try {
			Invoice invoice = service.getInvoiceById(id);
			model.addAttribute("invoice", invoice);
			page = "editInvoicePage";
		} catch (InvoiceNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:getAllInvoices";
		}
		return page;
	}

	@RequestMapping("/update")
	public String updateInvoice(@ModelAttribute Invoice invoice, RedirectAttributes attributes) {
		service.updateInvoice(invoice);
		Long id = invoice.getId();
		attributes.addAttribute("message", "Invoice with id: '" + id + "' is updated successfully !");
		return "redirect:getAllInvoices";
	}

	@RequestMapping("/delete")
	public String deleteInvoice(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.deleteInvoiceById(id);
			attributes.addAttribute("message", "Invoice with Id : '" + id + "' is removed successfully!");
		} catch (InvoiceNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:getAllInvoices";
	}
}