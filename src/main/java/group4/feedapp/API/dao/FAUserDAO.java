package group4.feedapp.API.dao;

import java.util.Collection;

import group4.feedapp.API.model.FAUser;

public interface FAUserDAO {
	
	FAUser createUser(FAUser newUser);
	FAUser createUser(String email, String password, String name, boolean isAdmin);
	FAUser readUser(Long id);
	Collection<FAUser> readUsers();
	FAUser updateUser(Long id, FAUser updatedUser);
	FAUser deleteUser(Long id);

}
