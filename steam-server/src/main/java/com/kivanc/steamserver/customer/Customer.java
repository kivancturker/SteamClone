package com.kivanc.steamserver.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kivanc.steamserver.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private LocalDate birthDate;
    private LocalDate createDate;
    private LocalDate lastLogin;
}
