package com.case_study.demo;
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    @Query("SELECT t FROM InventoryTransaction t " +
            "WHERE t.product.id = :productId " +
            "AND t.warehouse.id = :warehouseId " +
            "AND t.type = 'SALE' " +
            "AND t.createdAt >= :date")
    List<InventoryTransaction> findRecentSales(Long productId, Long warehouseId, LocalDateTime date);
}