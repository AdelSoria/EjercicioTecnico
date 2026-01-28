package com.example.account_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.account_service.dto.AccountStatementReportDTO;
import com.example.account_service.services.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
	@Autowired
	private ReportService service;

	@GetMapping("/{clientId}")
	public List<AccountStatementReportDTO> generateReport(@PathVariable Long clientId,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		return service.generateReport(clientId, startDate, endDate);
	}
}
