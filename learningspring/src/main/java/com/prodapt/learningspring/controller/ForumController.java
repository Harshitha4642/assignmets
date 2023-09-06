package com.prodapt.learningspring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prodapt.learningspring.controller.binding.AddPostForm;
import com.prodapt.learningspring.controller.exception.ResourceNotFoundException;
import com.prodapt.learningspring.entity.LikeRecord;
import com.prodapt.learningspring.entity.LikeId;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.repository.LikeCRUDRepository;
import com.prodapt.learningspring.repository.LikeCountRepository;
import com.prodapt.learningspring.repository.PostRepository;
import com.prodapt.learningspring.repository.RegisteredUsersRepository;
import com.prodapt.learningspring.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/forum")
public class ForumController {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PostRepository postRepository;
  
  @Autowired
  private LikeCountRepository likeCountRepository;
  
  @Autowired
  private LikeCRUDRepository likeCRUDRepository;
  
  @Autowired 
  private RegisteredUsersRepository registeredRepo;
  
  private List<User> userList;
  private String user_name;
  Optional<HttpSession> session;
  
  
  @PostConstruct
  public void init() {
    userList = new ArrayList<>();
  }
  
  @GetMapping("/login")
  public String login()
  {
	  return "forum/loginPage";
  }
  @GetMapping("/post/form")
  public String getPostForm(Model model) {
	  try {
	  if(session.get().getAttribute(user_name).equals("logged in")) {
		    model.addAttribute("postForm", new AddPostForm());
		    userRepository.findAll().forEach(user -> userList.add(user));
		    model.addAttribute("userList", userList);
		    model.addAttribute("authorid", 0);
		    return "forum/postForm";
	   }
	  }
	   catch(NullPointerException e) {}
	  return "redirect:/forum/login";
  }
  
  @GetMapping("/logout")
  public String logout()
  {
	  System.out.println("entered function to achieve something");
	  try {
	  if(session.get().getAttribute(user_name).equals("logged in"))
	  {
		  session.get().removeAttribute(user_name);
		  System.out.println("Session removed");
	  }
	  }
	  catch(NullPointerException e) {}
	  return "redirect:/forum/login";
	  
  }
  
  @GetMapping("/checkLogin")
  public String checkLogin(Model model, String username, String password, HttpServletRequest req, HttpServletResponse res)
  {
	  user_name = username;
	  int count = registeredRepo.checkRegisteredUserOrNot(username, password);
	  if(count>0)
	  {
		  session = Optional.ofNullable(req.getSession());  
		  session.get().setAttribute(username, "logged in");
		  System.out.println(session.get().getAttribute(username));
		  return "redirect:/forum/post/form";
	  }
	  else
	  {
		  model.addAttribute("message","login failed");
		  return "forum/loginPage";
	  }
  }
  
  @PostMapping("/post/add")
  public String addNewPost(@ModelAttribute("postForm") AddPostForm postForm, BindingResult bindingResult, RedirectAttributes attr) throws ServletException { 
	try {
    if(session.get().getAttribute(user_name)=="logged in") {
    	System.out.println("Hey session is there");
	  if (bindingResult.hasErrors()) {
      System.out.println(bindingResult.getFieldErrors());
      attr.addFlashAttribute("org.springframework.validation.BindingResult.post", bindingResult);
      attr.addFlashAttribute("post", postForm);
      return "redirect:/forum/post/form";
    }
    Optional<User> user = userRepository.findById(postForm.getUserId());
    if (user.isEmpty()) {
      throw new ServletException("Something went seriously wrong and we couldn't find the user in the DB");
    }
    Post post = new Post();
    post.setAuthor(user.get());
    post.setContent(postForm.getContent());
    postRepository.save(post);
    
    return String.format("redirect:/forum/post/%d", post.getId());
  }
	}
	catch(NullPointerException e) {}
    return "forum/loginPage";

 }
  @GetMapping("/post/{id}")
  public String postDetail(@PathVariable int id, Model model) throws ResourceNotFoundException {
    Optional<Post> post = postRepository.findById(id);
    if (post.isEmpty()) {
      throw new ResourceNotFoundException("No post with the requested ID");
    }
    model.addAttribute("post", post.get());
    model.addAttribute("userList", userList);
    //int numLikes = likeCountRepository.countByPostId(id);
    int numLikes = likeCRUDRepository.countByLikeIdPost(post.get());
    model.addAttribute("likeCount", numLikes);
    return "forum/postDetail";
  }
  
  @PostMapping("/post/{id}/like")
  public String postLike(@PathVariable int id, Integer likerId, RedirectAttributes attr) {
    LikeId likeId = new LikeId();
    likeId.setUser(userRepository.findById(likerId).get());
    likeId.setPost(postRepository.findById(id).get());
    LikeRecord like = new LikeRecord();
    like.setLikeId(likeId);
    likeCRUDRepository.save(like);
    return String.format("redirect:/forum/post/%d", id);
  }
  
}