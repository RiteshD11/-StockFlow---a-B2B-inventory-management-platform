package com.case_study.demo;



@RestController
@RequestMapping("/api/companies")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/{companyId}/alerts/low-stock")
    public ResponseEntity<?> getLowStockAlerts(@PathVariable Long companyId) {
        List<LowStockAlertDTO> alerts = alertService.getLowStockAlerts(companyId);

        Map<String, Object> response = new HashMap<>();
        response.put("alerts", alerts);
        response.put("total_alerts", alerts.size());

        return ResponseEntity.ok(response);
    }
}
