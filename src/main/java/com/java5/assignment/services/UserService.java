package com.java5.assignment.services;

import com.java5.assignment.dto.UserInfoDto;
import com.java5.assignment.entities.User;
import com.java5.assignment.entities.UserVoucher;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserInfoDto getUserInfo(String key) {
        User user = userRepository.findByUserKey(key);
        UserInfoDto userDto = new UserInfoDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullname(user.getFullname());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setPhone(user.getPhone());
        userDto.setAvatar(user.getAvatar());
        return userDto;
    }

    public List<Voucher> getSavedVouchers(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getUserVouchers().stream()
                    .map(UserVoucher::getVoucherID)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<Voucher> getNotSavedVouchers(Long userId, List<Voucher> allVouchers) {
        List<Voucher> savedVouchers = getSavedVouchers(userId);
        return allVouchers.stream()
                .filter(voucher -> !savedVouchers.contains(voucher))
                .collect(Collectors.toList());
    }

}
