package com.ecommerce.orderprocessor.util;

import com.ecommerce.orderprocessor.dto.ResponseDTO;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public final class JdbcUtils {

    public static <T> T getValue(Class<T> returnType, String columnName, ResultSet resultSet) throws SQLException {
        T t = resultSet.getObject(columnName, returnType);
        return !resultSet.wasNull() ? t : null;
    }

    public static Double getDoubleValue(String columnName, ResultSet resultSet) throws SQLException {
        Object obj = resultSet.getObject(columnName);
        return obj != null ? resultSet.getDouble(columnName) : null;
    }

    public static ResponseDTO batchOperationResponse(int[][] affectedRows) {
        List<Integer> errorsIndex = new ArrayList<>();

        for (int i = 0; i < affectedRows.length; i++) {
            if (affectedRows[i][0] != 1) {
                errorsIndex.add(i);
            }
        }

        if (!errorsIndex.isEmpty()) {
            log.error("INDEXES OF UNSUCCESSFUL ENTRIES: {}", errorsIndex);
            return new ResponseDTO(
                    false,
                    affectedRows.length - errorsIndex.size() + " successful entries and " +
                    errorsIndex.size() + " unsuccessful ones were affected",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            return new ResponseDTO(true, affectedRows.length + " records was successfully affected", HttpStatus.OK);
        }
    }

}
