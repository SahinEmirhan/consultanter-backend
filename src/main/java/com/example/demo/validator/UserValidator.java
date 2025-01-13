package com.example.demo.validator;

import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.exception.ValidationException;

import java.util.regex.Pattern;
/*

public class UserValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,13}$");

    public static void validateUser(User user) {
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        validateFields(user);
        validateUserType(user.getUserType());
    }

    private static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Geçersiz e-posta adresi.");
        }
    }

    private static void validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Geçersiz telefon numarası.");
        }
    }

    private static void validateFields(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ValidationException("Ad alanı boş olamaz.");
        }
        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            throw new ValidationException("Soyad alanı boş olamaz.");
        }
        if (user.getBirthDate() == null) {
            throw new ValidationException("Doğum tarihi boş olamaz.");
        }
        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            throw new ValidationException("Ülke alanı boş olamaz.");
        }
        if (user.getCity() == null || user.getCity().isEmpty()) {
            throw new ValidationException("Şehir alanı boş olamaz.");
        }
    }

    private static void validateUserType(UserType userType) {
        if (userType == null) {
            throw new ValidationException("Geçersiz kullanıcı tipi.");
        }
        try {
            UserType.valueOf(userType.name());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Geçersiz kullanıcı tipi: " + userType);
        }
    }
}
*/