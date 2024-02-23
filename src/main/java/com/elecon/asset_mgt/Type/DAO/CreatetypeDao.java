package com.elecon.asset_mgt.Type.DAO;

import lombok.Data;

@Data
public class CreatetypeDao {
    private boolean status; // true -> ACTIVE, false -> INACTIVE
    private String type_name;
    private String type_desc;
    private Integer class_id;
    private Integer category_id;
}
