package tn.globalnet.webscraper.models;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(value = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    public static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    @Id
    private String id;
    private String fullName;
    private String pack;
    private String status;
    private String bandwidth;
    private String address;
    private Date contractEndTime;
    private Date contractStartTime;


    public Subscription(String[] splitData) throws ParseException {
        this.id = splitData[0];
        this.fullName = splitData[1];
        this.pack = splitData[2];
        this.status = splitData[3];
        this.bandwidth = splitData[4];
        this.address = splitData[5];
        this.contractEndTime = df.parse(splitData[6]);
        this.contractStartTime = df.parse(splitData[7]);
    }
}
