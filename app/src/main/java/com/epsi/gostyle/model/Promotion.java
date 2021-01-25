package com.epsi.gostyle.model;

import java.io.Serializable;
import java.util.Date;

public class Promotion implements Serializable {

    private int id;
    private String discount;
    private String description;
    private String link;
    private String image_path;
    private Date validate_start_date;
    private Date validate_end_date;
    private int code_id;
    private String created_at;
    private String updated_at;
    private Boolean isValid;

    public Promotion(String description, String image_path, Date validate_start_date, Date validate_end_date) {
        this.description = description;
        this.image_path = image_path;
        this.validate_start_date = validate_start_date;
        this.validate_end_date = validate_end_date;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Date getValidate_start_date() {
        return validate_start_date;
    }

    public void setValidate_start_date(Date validate_start_date) {
        this.validate_start_date = validate_start_date;
    }

    public Date getValidate_end_date() {
        return validate_end_date;
    }

    public void setValidate_end_date(Date validate_end_date) {
        this.validate_end_date = validate_end_date;
    }

    public int getCode_id() {
        return code_id;
    }

    public void setCode_id(int code_id) {
        this.code_id = code_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
