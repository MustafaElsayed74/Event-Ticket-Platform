package com.devtiro.tickets.services;


import com.devtiro.tickets.domain.entities.QrCode;
import com.devtiro.tickets.domain.entities.Ticket;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
}
