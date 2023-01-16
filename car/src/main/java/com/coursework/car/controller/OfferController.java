package com.coursework.car.controller;

import com.coursework.car.model.Offer;
import com.coursework.car.model.OfferDto;
import com.coursework.car.repositories.OfferRepository;
import com.coursework.car.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Validated
@RestController
@RequestMapping(value = "offer")
public class OfferController {

    @Autowired
    OfferService offerService;

    @GetMapping
    public List<OfferDto> getOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/{id}")
    public OfferDto getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id);
    }

    @GetMapping("/user/{id}")
    public List<OfferDto> getOffersByUser(@PathVariable Long id) {
        return offerService.getOffersByUserId(id);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public void saveOffer(@Valid @RequestPart OfferDto newOffer, @RequestPart(value = "images", required = false) Collection<MultipartFile> images) {
        offerService.saveOffer(newOffer, images);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }

}
