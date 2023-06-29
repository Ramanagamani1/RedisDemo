package com.example.redisdemo.dtos;

import com.example.redisdemo.models.Address;
import com.example.redisdemo.models.Person;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonCreateRequest {

    @NotBlank
    private String name;

    private String age;

    private Address address;

    public Person to() {
        return Person.builder()
                .name(this.name)
                .age(this.age)
                .address(this.address)
                .id(UUID.randomUUID().toString())
                .build();
    }
}
