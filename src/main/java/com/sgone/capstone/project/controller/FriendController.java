package com.sgone.capstone.project.controller;

import com.sgone.capstone.project.model.ApplicationUser;
import com.sgone.capstone.project.model.Friend;
import com.sgone.capstone.project.repository.FriendRepository;
import com.sgone.capstone.project.repository.UserRepository;
import com.sgone.capstone.project.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/friend")
@CrossOrigin("localhost:3000")
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

    @GetMapping("/find_all_friends")
    public ResponseEntity<List<Friend>> findAllFriends() {
        List<Friend> friendList = friendRepository.findAll();
        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    @GetMapping("/friendsByID")
    public ResponseEntity<List<ApplicationUser>> allFriendsById(@RequestParam Long userID) {
        List<Long> allFriends = friendRepository.findFriendsByID(userID);
        List<ApplicationUser> friends = new ArrayList<>();
        for (Long friendID : allFriends) {
            friends.add(userRepository.findById(friendID)
                    .orElseThrow());
        }
        return ResponseEntity.ok().body(friends);
    }

    @GetMapping("/searchNonFriends")
    public ResponseEntity<List<ApplicationUser>> searchNonFriends(@RequestParam Long userID,
                                                                  @RequestParam String search) {
        List<Long> nonFriendList = friendRepository.top10NonFriendsBySearch(userID, '%' +search +'%');

        List<ApplicationUser> searchResult = new ArrayList<>();

        for (Long id : nonFriendList) {
            searchResult.add(userRepository.findById(id)
                    .orElseThrow());
        }

        return ResponseEntity.ok().body(searchResult);
    }

    @GetMapping("/findFriendPairUsername/{username_a}/{username_b}")
    public Friend findFriendPairUsername(String username_a, String username_b) {
        return friendService.findFriendPairUsername(username_a, username_b);
    }


    @DeleteMapping("/deleteFriend/{currentUserUsername}/{friendToAddUsername}")
    public String deleteFriend(@PathVariable("currentUserUsername") String currentUserUsername,
                            @PathVariable("friendToAddUsername") String friendToAddUsername

    ) throws Exception{
        return friendService.deleteFriend(currentUserUsername, friendToAddUsername);
    }


    @PostMapping("/addFriend/{currentUserUsername}/{friendToAddUsername}")
    public String addFriend(@PathVariable("currentUserUsername") String currentUserUsername,
                            @PathVariable("friendToAddUsername") String friendToAddUsername

                            ) throws Exception{
        return friendService.addFriend(currentUserUsername, friendToAddUsername);
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