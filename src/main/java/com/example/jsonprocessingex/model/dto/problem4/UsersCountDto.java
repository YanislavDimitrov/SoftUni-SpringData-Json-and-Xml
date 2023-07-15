package com.example.jsonprocessingex.model.dto.problem4;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersCountDto {
    @Expose
    @XmlAttribute
    private Integer count;
    @Expose
    @XmlElement(name = "user")
    private List<UserDto> users;

    public UsersCountDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
