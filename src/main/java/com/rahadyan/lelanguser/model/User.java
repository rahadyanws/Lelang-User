package com.rahadyan.lelanguser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(length = 128, nullable = false)
    private String fullname;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 16)
    private String ktpNumber;

    @Column(length = 12)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column
    private String address;

    public User(String id, String fullname, String email, String address) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
    }
}