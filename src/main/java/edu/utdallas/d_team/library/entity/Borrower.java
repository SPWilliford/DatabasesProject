package edu.utdallas.d_team.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "borrower")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Card_id", nullable = false)
    private Integer id;

    @Column(name = "Bname", nullable = false, length = 100)
    private String bname;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Phone", length = 15)
    private String phone;

    @Column(name = "Ssn", nullable = false, length = 11)
    private String ssn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

}