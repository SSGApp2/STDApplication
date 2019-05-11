package com.soft.app.entity.vcc.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "MainSensor")
public class MainSensor {
    @Id
    private String _id;

    @Field("DeviceName")
    private String deviceName;

    @Field("Status")
    private String status;

    @Field("Date")
    private String date;

    @Field("Time")
    private String time;

    @Field("Temp")
    private String temp;

    @Field("Pressu")
    private String pressu;

    @Field("Acust")
    private String acust;

    @Field("Humid")
    private String humid;

    @Field("Light")
    private String light;

    @Field("Vibra")
    private ArrayList<Object> vibra;

    @Field("MStatus")
    private String mStatus;

}
