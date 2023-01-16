package com.coursework.car.service;

import com.coursework.car.exceptions.OfferNotFoundException;
import com.coursework.car.model.*;
import com.coursework.car.permission.OfferPermissionService;
import com.coursework.car.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferPermissionService offerPermissionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ImageService imageService;

    private Set<String> lastUploadedImageUrls = new HashSet<>();

    public List<OfferDto> getAllOffers() {
        return offerRepository.findAll().stream().map(this::toOfferDto).collect(Collectors.toList());
    }

    public OfferDto getOfferById(Long offerId) {
        Optional<Offer> foundOffer = offerRepository.findById(offerId);
        if (foundOffer.isEmpty()) {
            throw new OfferNotFoundException("Cannot find offer with id: " + offerId);
        }

        return toOfferDto(foundOffer.get());
    }

    @PreAuthorize("#id == authentication.principal.id")
    public List<OfferDto> getOffersByUserId(Long id) {
        return offerRepository.findByUser_Id(id).stream().map(this::toOfferDto).collect(Collectors.toList());
    }

    @PreAuthorize("#newOffer.user.id == authentication.principal.id")
    public void saveOffer(OfferDto newOffer, Collection<MultipartFile> images) {
        Offer savedOffer;
        if (images == null) {
            savedOffer = offerRepository.save(newOffer.fromDto());
        } else {
            savedOffer = offerRepository.save(uploadImages(images, newOffer.fromDto()));
        }
        offerPermissionService.addToStore(savedOffer.getUser(), savedOffer, "WRITE");
    }

    private Offer uploadImages(Collection<MultipartFile> images, Offer offer) {
        if (!isEmpty(images)) {
            Set<String> uploadedImageUrls = imageService.upload(images, offer.getCar().getModel());
            offer.setImageUrls(uploadedImageUrls);
        }
        return offer;
    }

    @Transactional
    @PreAuthorize("hasPermission(#id, 'com.coursework.car.model.Offer', 'WRITE')")
    public void deleteOffer(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (!optionalOffer.isPresent()) {
            throw new OfferNotFoundException("Cannot find offer with id: " + id);
        }
        offerPermissionService.removeFromStoreByOffer(optionalOffer.get());
        offerRepository.deleteById(id);
    }

    OfferDto toOfferDto(Offer offer) {
        CarDto carDto = toCarDto(offer.getCar());
        UserDto userDto = toUserDto(offer.getUser());
        return new OfferDto(offer.getId(), offer.getTown(), offer.getMinDays(), offer.getMaxDays(), offer.getDayCost(), carDto, userDto, offer.getImageUrls());
    }

    CarDto toCarDto(Car car) {
        return new CarDto(car.getId(), car.getModel(), car.getFuel(), car.isAutomatic(), car.getYear(), car.getAdditionalInformation());
    }

    UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
    }
}
