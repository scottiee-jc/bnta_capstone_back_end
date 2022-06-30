package com.sgone.capstone.project.controller;

import com.sgone.capstone.dto.CustomApplicationUserDto;
import com.sgone.capstone.dto.request.NewFriendDto;
import com.sgone.capstone.project.model.ApplicationUser;
import com.sgone.capstone.project.model.Friend;
import com.sgone.capstone.project.model.TripAssignment;
import com.sgone.capstone.project.repository.FriendRepository;
import com.sgone.capstone.project.repository.UserRepository;
import com.sgone.capstone.project.service.FriendService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private final FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private FriendService friendService;

    public FriendController(FriendRepository friendRepository, UserRepository userRepository, FriendService friendService, UserController userController) {
        this.friendRepository = friendRepository;
        this.friendService = friendService;
        this.userController = userController;
        this.userRepository = userRepository;
    }

    @GetMapping("/friend")
    public ResponseEntity<List<Friend>> findAllFriends() {
        List<Friend> friendList = friendRepository.findAll();
        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    @GetMapping("/findFriendPair/{friend_a_name}/{friend_b_name}")
    public Friend findFriendPair(String friend_a_name, String friend_b_name) {
        return friendService.findFriendPair(friend_a_name, friend_b_name);
    }


//    @DeleteMapping("/friend/{id}")
//    public String deleteFriendById(@PathVariable(value = "id") Long id) {
//        return friendService.deleteFriendById(id);
//    }


    @PostMapping("/addFriend/{currentUserFirstName}/{currentUserLastName}/{friendToAddFirstName}/{friendToAddLastName}")
    public String addFriend(@PathVariable("currentUserFirstName") String currentUserFirstName,
                            @PathVariable("currentUserLastName") String currentUserLastName,
                            @PathVariable("friendToAddFirstName") String friendToAddFirstName,
                            @PathVariable("friendToAddLastName") String friendToAddLastName
                            ) throws Exception{
        return friendService.addFriend(currentUserFirstName, currentUserLastName, friendToAddFirstName, friendToAddLastName);
    }



    @PostMapping("/addFriend/{username}")
    public String addFriendByUsername(@PathVariable("currentUserUsername") String currentUserUsername,
                                      @PathVariable("friendToAddUsername") String friendToAddUsername

    ) throws Exception{
        return friendService.addFriendByUsername(currentUserUsername, friendToAddUsername);
    }

}
//get all friends from friends list
//delete friends from friends list
//add a friend with other person's consent

//For addFriend logic:

//Search through list of application users by name
//Attach id to friend_b
//Attach current logged in user value to friend_a
//create new friend object containing friend