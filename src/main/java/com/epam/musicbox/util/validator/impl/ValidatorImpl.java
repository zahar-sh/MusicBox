package com.epam.musicbox.util.validator.impl;

import com.epam.musicbox.util.validator.Validator;

import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {

    private static final String LOGIN_REGEX = "^([A-Za-z\\d ]{4,32})$";
    private static final String PASSWORD_REGEX = "^([A-Za-z\\d@$!%*#?&]{8,32})$";
    private static final String EMAIL_REGEX =
            "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
    private static final String NAME_REGEX = "^([A-Za-z\\d\\[\\]() -@$!%*#?&]{4,64})$";
    private static final String AUDIO_FILE_NAME_REGEX = ".*\\.(?:wav|mp3)$";
    private static final String IMAGE_FILE_NAME_REGEX = ".*\\.(gif|jpe?g|png)$";

    private static final ValidatorImpl instance = new ValidatorImpl();

    private final Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    private final Pattern namePattern = Pattern.compile(NAME_REGEX);
    private final Pattern imageFileNamePattern = Pattern.compile(IMAGE_FILE_NAME_REGEX);
    private final Pattern audioFileNamePattern = Pattern.compile(AUDIO_FILE_NAME_REGEX);

    private ValidatorImpl() {
    }

    public static ValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isValidLogin(String login) {
        return login != null &&
               loginPattern.matcher(login).matches();
    }

    @Override
    public boolean isValidPassword(String password) {
        return password != null &&
               passwordPattern.matcher(password).matches();
    }

    @Override
    public boolean isValidEmail(String email) {
        return email != null &&
               emailPattern.matcher(email).matches();
    }

    @Override
    public boolean isValidName(String name) {
        return name != null &&
               namePattern.matcher(name).matches();
    }

    @Override
    public boolean isValidAudioFileName(String fileName) {
        return fileName != null &&
               audioFileNamePattern.matcher(fileName).matches();
    }

    @Override
    public boolean isValidImageFileName(String fileName) {
        return fileName != null &&
               imageFileNamePattern.matcher(fileName).matches();
    }
}