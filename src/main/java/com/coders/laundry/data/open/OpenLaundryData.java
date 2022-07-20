package com.coders.laundry.data.open;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLaundryData {

    @JsonAlias("SIGUN_NM")
    private String sigunNm;

    @JsonAlias("BIZPLC_NM")
    private String bizplcNm;

    @JsonAlias("REFINE_ROADNM_ADDR")
    private String refineRoadnmAddr;

    @JsonAlias("REFINE_LOTNO_ADDR")
    private String refineLotnoAddr;

    @JsonAlias("BSN_STATE_NM")
    private String bsnStateNm;

    @JsonAlias("REFINE_WGS84_LAT")
    private String refineWgs84Lat;

    @JsonAlias("REFINE_WGS84_LOGT")
    private String refineWgs84Logt;
}

/*
{
"SIGUN_NM":"가평군",
"BIZPLC_NM":"뉴세탁나라",
"REFINE_ZIPNO":"12437",
"REFINE_ROADNM_ADDR":"경기도 가평군 조종면 연인산로 20-5",
"REFINE_LOTNO_ADDR":"경기도 가평군 조종면 현리 253-3번지 ",
"LICENSG_DE":"20010725",
"BSN_STATE_NM":"영업",
"CLSBIZ_DE":"",
"MULTI_UTLZ_BIZEST_YN":"N",
"WHM_CNT":"",
"RETRVL_DRY_CNT":"",
"SANITTN_INDUTYPE_NM":"",
"SANITTN_BIZCOND_NM":"빨래방업",
"REFINE_WGS84_LAT":"37.8218966979",
"REFINE_WGS84_LOGT":"127.3517000679"
}
 */
