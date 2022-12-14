package group4.feedapp.API.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.dao.IoTDeviceDAO;
import group4.feedapp.API.dao.IoTVotesDAO;
import group4.feedapp.API.dao.PollDAO;
import group4.feedapp.API.dao.VoteDAO;
import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.IoTVotes;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;


@Service
public class PollService {
	private final PollDAO pollDAO;
	private final FAUserDAO userDAO;
	private final IoTDeviceDAO deviceDAO;
	private final VoteDAO voteDAO;
	private final IoTVotesDAO IoTVotesDAO;
	
	@Autowired
	public PollService(PollDAO pollDAO, FAUserDAO userDAO, IoTDeviceDAO deviceDAO, VoteDAO voteDAO, IoTVotesDAO IoTVotesDAO) {
		this.pollDAO = pollDAO;
		this.userDAO = userDAO;
		this.deviceDAO = deviceDAO;
		this.voteDAO = voteDAO;
		this.IoTVotesDAO = IoTVotesDAO;
	}

	public Poll addPoll(String question, int noCount, int yesCount, LocalDateTime startTime, LocalDateTime endTime,
			boolean isPublic, int status, String accessCode, FAUser creator) {
		Poll poll = new Poll(question, noCount, yesCount, startTime, endTime, isPublic, status, accessCode, creator);
		FAUser owner = userDAO.readUser(creator.getId());
		if(owner != null) {
			// Updating the creator's list of polls
			owner.getCreatedPolls().add(poll);
			userDAO.updateUser(owner.getId(), owner);
			return pollDAO.createPoll(poll);
		}
		
		return null;
			
	}
	
	public Collection<Poll> getAllPolls(){
		return pollDAO.readPolls();
	}
	
	public Poll getPoll(Long id){
		return pollDAO.readPoll(id);
	}
	
	public Poll deletePoll(Long id) {
		return pollDAO.deletePoll(id);
	}
	
	public Poll updatePoll(Long id, Poll updatedPoll) {
		// TODO check if owner changed ?
		return pollDAO.updatePoll(id, updatedPoll);
	}
	
	public Vote getUserVote(Long pollId, Long userId) {
		Poll poll = getPoll(pollId);
		FAUser user = userDAO.readUser(userId);
		return voteDAO.findUserVote(poll, user);
	}
	
	public Vote voteOnPoll(Long pollId, Long userId, Vote vote) {
		// TODO : check if the poll is open (status = 1)
		
		Poll poll = getPoll(pollId);
		FAUser voter = userDAO.readUser(userId);
		if(vote!= null && poll!= null && voter != null && voteDAO.findUserVote(poll, voter) == null) {
			poll.getUserVotes().add(vote);
			if(vote.getAnswer()) {
				poll.setYesCount(poll.getYesCount() + 1);
			}else {
				poll.setNoCount(poll.getNoCount() + 1);
			}
			voter.getVotes().add(vote);
			vote.setVotePoll(poll);
			vote.setVoter(voter);
			voteDAO.createVote(vote);
			userDAO.updateUser(voter.getId(), voter);
			pollDAO.updatePoll(poll.getId(), poll);
			return vote;
		}else {
			System.out.println("Vote error");
		}
		return null;
	}
	
	public Vote voteOnPoll(Long pollId, Vote vote) {
		// TODO : check if the poll is open (status = 1)
		
		Poll poll = getPoll(pollId);
		if(vote!= null && poll!= null && poll.isPublic()) {
			if(vote.getAnswer()) {
				poll.setYesCount(poll.getYesCount() + 1);
			}else {
				poll.setNoCount(poll.getNoCount() + 1);
			}
			pollDAO.updatePoll(poll.getId(), poll);
			return vote;
		}else {
			System.out.println("Vote error");
			System.out.println("Vote: " + vote);
			System.out.println("Poll: " + poll);
		}
		return null;
	}
	
	public IoTVotes voteOnPoll(Long pollId, Long deviceId, IoTVotes votes) {
		Poll poll = getPoll(pollId);
		IoTDevice device = deviceDAO.readIoTDevice(deviceId);
		if(poll != null && device != null) {
			poll.getIotVotes().add(votes);
			poll.setNoCount(poll.getNoCount() + votes.getNoCount());
			poll.setYesCount(poll.getYesCount() + votes.getYesCount());
			votes.setIotVotePoll(poll);
			votes.setDevice(device);
			IoTVotesDAO.createIoTVotes(votes);
			pollDAO.updatePoll(poll.getId(), poll);
			return votes;
		}
		return null;
	}
	
	public Collection<Poll> getUserPolls(Long userId) {
		FAUser user = userDAO.readUser(userId);
		return pollDAO.getUserPolls(user);
	}
}
