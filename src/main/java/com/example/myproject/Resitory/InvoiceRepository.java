package com.example.myproject.Resitory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
