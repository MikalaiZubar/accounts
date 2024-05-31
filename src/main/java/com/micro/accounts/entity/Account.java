package com.micro.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column
    private Long accountNumber;

    @Column
    private String accountType;

    @Column
    private String branchAddress;

}
