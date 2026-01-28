package com.example.account_service.services;

import java.time.LocalDate;
import java.util.List;

import com.example.account_service.dto.AccountStatementReportDTO;

public interface ReportService {
	List<AccountStatementReportDTO> generateReport(Long clientId, LocalDate startDate, LocalDate endDate);
}
