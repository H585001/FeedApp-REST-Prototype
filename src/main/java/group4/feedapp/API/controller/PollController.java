package group4.feedapp.API.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;
import group4.feedapp.API.service.FAUserService;
import group4.feedapp.API.service.PollService;

@RestController
public class PollController {
	private final PollService pollService;
	private final FAUserService userService;

	public PollController(PollService pollService, FAUserService userService) {
		this.pollService = pollService;
		this.userService = userService;
	}
	
	@GetMapping("/polls")
    public Collection<Poll> getPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/polls/{id}")
    public Poll getPoll(@PathVariable Long id) {

        Poll poll = pollService.getPoll(id);

        if (poll == null) {
            System.out.println(String.format("Poll with the id  \"%s\" not found!", id));
            // TODO Exception
        }

        return poll;
    }

    @PutMapping("/polls/{id}")
    public Poll updatePoll(@RequestBody Poll updatedPoll, @PathVariable Long id) {

        Poll poll = pollService.updatePoll(id, updatedPoll);

        if (poll == null) {
            System.out.println(String.format("Poll with the id  \"%s\" not found!", id));
            // TODO Exception
        }

        return poll;
    }
    
    @PostMapping("/polls/{pollId}/{userId}")
    public Poll voteOnPoll(@RequestBody Vote vote, @PathVariable Long pollId, @PathVariable Long userId) {

        Vote userVote = pollService.voteOnPoll(pollId, userId, vote);

        if (userVote != null) {
          return pollService.getPoll(pollId);
        }
        
        return null;
    }
    
    @PostMapping("/polls/{id}")
    public Poll voteOnPoll(@RequestBody Vote vote, @PathVariable Long id) {

        Vote userVote = pollService.voteOnPoll(id, vote);

        if (userVote != null) {
        	return pollService.getPoll(id);
        }
        
        return null;
    }
    
    @GetMapping("/polls/{pollId}/{userId}")
    public Vote getUserVoteOnPoll(@PathVariable Long pollId, @PathVariable Long userId) {

        Vote vote = pollService.getUserVote(pollId, userId);

        if (vote != null) {
        	return vote;
        }
        
        return null;
    }

    @DeleteMapping("/poll/{id}")
    public Poll deletePoll(@PathVariable Long id) {

        Poll poll = pollService.deletePoll(id);

        if (poll == null) {
            System.out.println(String.format("Poll with the id  \"%s\" not found!", id));
            // TODO Exception
        }

        return poll;
    }	
	
}
