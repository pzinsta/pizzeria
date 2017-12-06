package pzinsta.pizzeria.service.impl;

import pzinsta.pizzeria.dao.UserDao;
import pzinsta.pizzeria.service.SignUpValidationService;

public class SignUpValidationServiceImpl implements SignUpValidationService {

	private UserDao userDao;
	
	public SignUpValidationServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public boolean isEmailValid(String email) {
		return !userDao.isEmailPresent(email);
	}

}
