package group4.feedapp.API.dao;

import java.util.Collection;

import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;

public interface VoteDAO {
	Vote createVote(FAUser voter, Poll votePoll, boolean answer);
	Vote createVote(Vote vote);
	Vote readVote(Long id);
	Vote findUserVote(Poll poll, FAUser user);
	Collection<Vote> readVotes();
	Vote updateVote(Long id, Vote updatedVote);
	Vote deleteVote(Long id);
}
