package group4.feedapp.API.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.IoTDeviceDAO;
import group4.feedapp.API.dao.IoTVotesDAO;
import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.Poll;

@Service
public class IoTDeviceService {
	
	private IoTDeviceDAO iotDao;
	
	@Autowired
	public IoTDeviceService(IoTDeviceDAO iotDao) {
		this.iotDao = iotDao;
	}
	
	public Poll connectDeviceToPoll(Long id, Poll poll) {
		IoTDevice device = iotDao.readIoTDevice(id);
		device.setLinkedPoll(poll);
		iotDao.updateIoTDevice(id, device);
		return poll;
	}
	
	public Poll getConnectedPoll(Long id) {
		return iotDao.readIoTDevice(id).getLinkedPoll();
	}
	
	public Collection<IoTDevice> getAllDevices() {
		return iotDao.readIoTDevices();
	}

	public IoTDevice getDevice(Long id) {
		return iotDao.readIoTDevice(id);
	}
	
	

}
