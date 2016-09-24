package org.moshe.arad.data.dao.interfaces;

import java.util.List;

import org.moshe.arad.data.entities.User;

public interface UserDao extends Dao<User,Long> {

	public List<User> findByFirstName(String firstName);
}
