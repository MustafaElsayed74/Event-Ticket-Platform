package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.entities.QrCode;
import com.devtiro.tickets.domain.entities.Ticket;
import com.devtiro.tickets.domain.enums.QrCodeStatusEnum;
import com.devtiro.tickets.exceptions.QrCodeGenerationException;
import com.devtiro.tickets.repositories.QrCodeRepository;
import com.devtiro.tickets.services.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;


    private final QrCodeRepository qrCodeRepository;
    private final QRCodeWriter qrCodeWriter;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
    try{

        UUID uniqueId = UUID.randomUUID();
        String qrCodeImage = generateQrCodeImage(uniqueId);

        QrCode qrCode = new QrCode();

        qrCode.setId(uniqueId);
        qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
        qrCode.setValue(qrCodeImage);
        qrCode.setTicket(ticket);

       return qrCodeRepository.saveAndFlush(qrCode);

    }catch (WriterException| IOException ex){
        throw new QrCodeGenerationException("Failed to generate QrCode");
    }



    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH, QR_HEIGHT
        );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(qrCodeImage, "PNG", byteArrayOutputStream);

            byte[] imageByte = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageByte);
        }


    }
}
