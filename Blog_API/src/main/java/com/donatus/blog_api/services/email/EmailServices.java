package com.donatus.blog_api.services.email;

public interface EmailServices {
    void sendSimpleMessage(String to, String subject, String text);

}
