package com.java5.assignment.services;

import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.Rating;
import com.java5.assignment.entities.RatingPicture;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.RatingPictureRepository;
import com.java5.assignment.jpa.RatingRepository;
import com.java5.assignment.model.RatingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingPictureRepository ratingPictureRepository;

    @Autowired
    UploadService uploadService;

    @Transactional
    public boolean saveAllRatingAndPicture(ProductVersion productVersion, User user, String comment, int  rate, List<MultipartFile> ratingPictures) {
        Rating rating = new Rating();
        rating.setRatingValue(rate);
        rating.setComment(comment);
        rating.setProductVersionID(productVersion);
        rating.setUserID(user);
        rating.setRatingDate(LocalDate.now());
        Rating ratingSave = ratingRepository.save(rating);
        for (MultipartFile picture : ratingPictures) {
            RatingPicture ratingPicture = new RatingPicture();
            ratingPicture.setRatingID(ratingSave);
            String fileName = uploadService.uploadFile(picture, "images/rating-picture/");
            if(fileName!=null){
                ratingPicture.setPicture(fileName);
                ratingPictureRepository.save(ratingPicture);
            }else{
                return false;
            }
        }
        return true;
    }
}
