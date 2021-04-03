/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewExistException;
import util.exception.ReviewNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author fengyuan
 */
@Local
public interface ReviewSessionBeanLocal {
    
    public Review createNewReview(Review newReview, Long restaurantId, Long customerId) 
            throws UnknownPersistenceException, InputDataValidationException, CreateNewReviewException, ReviewExistException;
    
    public List<Review> retrieveAllReviews();
    
    public Review retrieveReviewById(Long reviewId) throws ReviewNotFoundException;
    
    public List<Review> retrieveReviewsByRestaurantId(Long restaurantId);
    
    public List<Review> retrieveReviewsByCustomerId(Long customerId);
    
    public void deleteReview(Long reviewId) throws ReviewNotFoundException;

}
