package group4.feedapp.API.controller;
import java.util.Collection;

import org.springframework.web.bind.annotation.*;

import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.IoTVotes;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.service.IoTDeviceService;
import group4.feedapp.API.service.PollService;

@RestController
public class IoTController {
	
	private final IoTDeviceService deviceService;
	private final PollService pollService;
	
	public IoTController(IoTDeviceService deviceService, PollService pollService) {
		this.deviceService = deviceService;
		this.pollService = pollService;
	}
	
	@PutMapping("/iot/{id}")
	public Poll connectDeviceToPoll(@PathVariable Long id, @RequestBody Long pollId) {
		Poll poll = pollService.getPoll(pollId);
		return deviceService.connectDeviceToPoll(id, poll);
	}
	
	@GetMapping("/iot")
	public Collection<IoTDevice> getAllDevices() {
		return deviceService.getAllDevices();
	}
	
	@GetMapping("/iot/{id}")
	public IoTDevice getDevice(@PathVariable Long id) {
		return deviceService.getDevice(id);
	}
	
	@GetMapping("/iot/{id}/poll")
	public Poll getConnectedPoll(@PathVariable Long id) {
		return deviceService.getConnectedPoll(id);
	}
	
	@PostMapping("/iot/{id}/vote")
	public IoTVotes iotVoteOnPoll(@PathVariable Long id, @RequestBody IoTVotes votes) {
		return pollService.voteOnPoll(deviceService.getConnectedPoll(id).getId(), id, votes);
	}
	
}
