package com.example.rakus_blog_backend.form;

import lombok.Data;

@Data
public class UpdateUserForm {
    private String name;
    private String introduction;
    private String fileName;
}
