package com.sidenow.freshgreenish.domain.delivery.controller;

import com.sidenow.freshgreenish.domain.delivery.service.DeliverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliverService deliverService;
}
