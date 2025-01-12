package com.tehila.coupons.logic;

import com.tehila.coupons.dal.IUserDal;
import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.UserLoginData;
import com.tehila.coupons.entities.CompanyEntity;
import com.tehila.coupons.entities.UserEntity;
import com.tehila.coupons.utils.JWTUtils;
import com.tehila.coupons.utils.StatisticsUtils;
import com.tehila.coupons.dto.User;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersLogic {

    private IUserDal usersDal;
    private CustomersLogic customersLogic;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Autowired
    public UsersLogic(IUserDal UserDal) {
        this.usersDal = UserDal;
    }

    public UserEntity createUser(User user) throws ServerException {
        if (isUserNameExist(user.getUsername())) {
            throw new ServerException(ErrorType.USER_NAME_ALREADY_EXISTS, user.toString());
        }
        validateUser(user);
        UserEntity userEntity = convertUserToUserEntity(user);
        usersDal.save(userEntity);
        return userEntity;
    }

    private UserEntity convertUserToUserEntity(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getUsername(), user.getPassword(), user.getUserType(), user.getCompanyId());
        return userEntity;
    }

    public List<User> getUsers(String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        List<UserEntity> userEntities = (List<UserEntity>) usersDal.findAll();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(convertUserEntityToUser(userEntity));
        }

        return users;
    }

    public User getUser(int id) throws ServerException {
        User user = convertUserEntityToUser(usersDal.findById(id).get());
        return user;
    }

    private User convertUserEntityToUser(UserEntity userEntity) {
        User user = new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getUserType(),  Optional.ofNullable(userEntity.getCompany())
                .map(CompanyEntity::getId)
                .orElse(null));
        return user;
    }

    public void deleteUser(int id, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        if (id != successfulLoginDetails.getId() && !successfulLoginDetails.getUserType().equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        if (!isUserIdExist(id)) {
            throw new ServerException(ErrorType.USER_NOT_EXIST);
        }
        this.usersDal.deleteById(id);
    }

    public void updateUser(User user) throws ServerException {
        if (!isUserIdExist(user.getId())) {
            throw new ServerException(ErrorType.USER_NOT_EXIST, user.toString());
        }
        validateUser(user);
        UserEntity userBeforeUpdate = usersDal.findById(user.getId()).get();
        if (!userBeforeUpdate.getUsername().equals(user.getUsername())) {
            if (isUserNameExist(user.getUsername())) {
                throw new ServerException(ErrorType.USER_NAME_ALREADY_EXISTS, user.toString());
            }
        }
        UserEntity userEntity = convertUserToUserEntity(user);
        this.usersDal.save(userEntity);
    }


    boolean isUserNameExist(String username) {
        return usersDal.getUserByUsername(username);
    }

    boolean isUserIdExist(int id) {
        return this.usersDal.existsById(id);
    }

    public String login(UserLoginData userLoginData) throws Exception {
        UserEntity userEntity = usersDal.login(userLoginData.getUsername(), userLoginData.getPassword());
        if (userEntity == null) {
            throw new ServerException(ErrorType.UNAUTHORIZED);
        }

        StatisticsUtils statisticsUtils = new StatisticsUtils();
        statisticsUtils.sendStatistics(userLoginData.getUsername(), "login");

        Integer companyOfTheUser;
        if(userEntity.getCompany() == null){
            companyOfTheUser = null;
        }
        else {
            companyOfTheUser = userEntity.getCompany().getId();
        }

        SuccessfulLoginDetails successfulLoginDetails = new SuccessfulLoginDetails(userEntity.getId(), userEntity.getUserType(), companyOfTheUser);
        String token = JWTUtils.createJWT(successfulLoginDetails);
        return token;
    }

        private void validateUser(User user) throws ServerException {
        if (user.getUsername() == null) {
            throw new ServerException(ErrorType.UNAUTHORIZED, user.toString());
        }

        if (!isValidUserName(user.getUsername())) {
            throw new ServerException(ErrorType.UNAUTHORIZED, user.toString());
        }

        if (user.getUsername().length() > 45) {
            throw new ServerException(ErrorType.USERNAME_TOO_LONG, user.toString());
        }

        if(!isValidPassword(user.getPassword())) {
            throw new ServerException(ErrorType.UNAUTHORIZED, user.toString());
        }

        if (user.getUserType() == null) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, user.toString());
        }
    }

    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() > 45) {
            return false;
        }
        return true;
    }

    private boolean isValidUserName(String username) {
        Matcher matcher = EMAIL_PATTERN.matcher(username);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}
