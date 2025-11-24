package com.mycompany.warehouse_desktop.db.user;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.util.List;

/**
    duong_td
 */
public class UserService implements ServiceInterface<UserEntity, Long> {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    @Override
    public UserEntity findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<UserEntity> getList(Integer sttPage, Integer sizePage) {
        return this.userRepository.getList(sttPage, sizePage);
    }

    @Override
    public UserEntity create(UserEntity user) {
        return this.userRepository.create(user);
    }

    @Override
    public UserEntity update(Long id, UserEntity user) {
        return this.userRepository.update(id, user);
    }

    @Override
    public Boolean delete(Long id) {
        return this.userRepository.delete(id);
    }

    // Logic (Login / Check Username)
    public UserEntity findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public UserEntity findByUsernamePassword(UserDto userDto) {
        return this.userRepository.findByUsernamePassword(userDto);
    }
}
