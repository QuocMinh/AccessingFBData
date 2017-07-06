package com.facebook.demo.controller;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.facebook.demo.util.Constants;

@Controller
public class HelloFacebookController {
	
	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	
	@Inject
	public HelloFacebookController(Facebook facebook,
			ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }
	
	@GetMapping
	public String helloFacebook(Model model) {
		
		if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			System.out.println("Primary connection = NULL");
			
			return "redirect:/connect/facebook";
		}
		
		User userProfile = facebook.fetchObject("100004063953415", User.class, 
				Constants.VERY_BASIC_USER_PROFILE);
		PagedList<Post> feed = facebook.feedOperations().getFeed();
		
		model.addAttribute("facebookProfile", userProfile);
		model.addAttribute("feed", feed);
		
		return "hello";
	}

}
