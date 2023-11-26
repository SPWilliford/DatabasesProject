package edu.utdallas.d_team.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BORROWER")
public class Borrower {
    @Id
    @Column(name = "Card_id", nullable = false, length = 8)
    private String cardId;

    @Column(name = "Ssn", nullable = false, unique = true, length = 11)
    private String ssn;

    @Column(name = "Bname", nullable = false, length = 100)
    private String bname;

    @Column(name = "Address", nullable = false, length = 255)
    private String address;

    @Column(name = "Phone", length = 15)
    private String phone;

    // Constructors, getters, and setters

    public Borrower() {}

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    @Override
    public String toString() {
        return cardId + " ," + ssn + " ," + bname + " ," + address + " ," + phone;
    }
}