package group4.feedapp.API.dao;

import java.util.Collection;

import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.IoTVotes;
import group4.feedapp.API.model.Poll;

public interface IoTVotesDAO {
	IoTVotes createIoTVotes(IoTDevice device, Poll votePoll, int noCount, int yesCount);
	IoTVotes createIoTVotes(IoTVotes votes);
	IoTVotes readIoTVotes(Long id);
	Collection<IoTVotes> findIoTVotes(Long pollId, Long deviceId);
	Collection<IoTVotes> readIoTVotes();
	IoTVotes updateIoTVotes(Long id, IoTVotes updatedIoTVotes);
	IoTVotes deleteIoTVotes(Long id);
}
