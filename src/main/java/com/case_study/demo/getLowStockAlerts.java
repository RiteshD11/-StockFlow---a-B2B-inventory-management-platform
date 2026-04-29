package com.case_study.demo;
@Service
public class AlertService {

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private InventoryRepository inventoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SupplierRepository supplierRepo;

    @Autowired
    private InventoryTransactionRepository transactionRepo;

    public List<LowStockAlertDTO> getLowStockAlerts(Long companyId) {

        List<Warehouse> warehouses = warehouseRepo.findByCompanyId(companyId);

        List<LowStockAlertDTO> alerts = new ArrayList<>();

        for (Warehouse warehouse : warehouses) {

            List<Inventory> inventories = inventoryRepo.findByWarehouseId(warehouse.getId());

            for (Inventory inv : inventories) {

                Product product = inv.getProduct();

                // 🔹 Skip if not low stock
                if (inv.getQuantity() >= product.getLowStockThreshold()) {
                    continue;
                }

                // 🔹 Check recent sales (last 30 days)
                LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

                List<InventoryTransaction> sales =
                        transactionRepo.findRecentSales(
                                product.getId(),
                                warehouse.getId(),
                                thirtyDaysAgo
                        );

                if (sales.isEmpty()) {
                    continue;
                }

                // 🔹 Calculate avg daily sales
                int totalSold = sales.stream()
                        .mapToInt(InventoryTransaction::getChangeQuantity)
                        .map(Math::abs)
                        .sum();

                double avgDailySales = totalSold / 30.0;

                int daysUntilStockout = avgDailySales > 0
                        ? (int) (inv.getQuantity() / avgDailySales)
                        : 0;

                // 🔹 Get supplier (simplified: first one)
                Supplier supplier = supplierRepo.findFirstByProductId(product.getId());

                // 🔹 Build response
                LowStockAlertDTO dto = new LowStockAlertDTO(
                        product.getId(),
                        product.getName(),
                        product.getSku(),
                        warehouse.getId(),
                        warehouse.getName(),
                        inv.getQuantity(),
                        product.getLowStockThreshold(),
                        daysUntilStockout,
                        supplier
                );

                alerts.add(dto);
            }
        }

        return alerts;
    }
}