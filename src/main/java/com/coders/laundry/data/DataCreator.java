package com.coders.laundry.data;

import com.coders.laundry.data.open.OpenLaundryData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataCreator {

    @Value("${data.laundry}")
    private Resource[] rawLaundryDataList;

    @Autowired
    private final ObjectMapper mapper;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (Resource r : rawLaundryDataList) {
            try {
                laundryDataCreate(r.getFile().getPath());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void laundryDataCreate(String rawDataFilePath) throws IOException {
        String fileContent = Files.readString(Paths.get(rawDataFilePath));
        OpenLaundryData[] openLaundryDataList = mapper.readValue(fileContent, OpenLaundryData[].class);

        String query = """
                INSERT INTO laundry (name, jibun_address, doro_address, latitude, longitude, partnership)
                values (?, ?, ?, ?, ?, ?)
                """;

        query(openLaundryDataList, query);
    }

    private void query(OpenLaundryData[] openLaundryDataList, String query) {
        try {
            String url = "jdbc:h2:mem:test;MODE=MYSQL;DATABASE_TO_LOWER=TRUE";
            String user = "sa";
            String password = "";
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = con.prepareStatement(query);

            for (OpenLaundryData data : openLaundryDataList) {
                if (data.getRefineWgs84Lat().isBlank()
                        || data.getRefineWgs84Logt().isBlank()
                        || "폐업".equals(data.getBsnStateNm())) {
                    continue;
                }

                statement.setString(1, data.getBizplcNm());
                statement.setString(2, data.getRefineLotnoAddr());
                statement.setString(3, data.getRefineRoadnmAddr());
                statement.setBigDecimal(4, new BigDecimal(data.getRefineWgs84Lat()));
                statement.setBigDecimal(5, new BigDecimal(data.getRefineWgs84Logt()));
                statement.setBoolean(6, true);

                statement.execute();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
