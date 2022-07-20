package com.coders.laundry.jackson;

import static org.junit.jupiter.api.Assertions.*;

import com.coders.laundry.data.open.OpenLaundryData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class StringLowerCaseDeserializerTest {

  @Test
  public void deserializeTest() throws JsonProcessingException {
    String rawData = """
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
        """;

    ObjectMapper mapper = new ObjectMapper();
    OpenLaundryData openLaundryData = mapper.readValue(rawData, OpenLaundryData.class);

    assertEquals(openLaundryData.getBizplcNm(), "뉴세탁나라");
    assertEquals(openLaundryData.getBsnStateNm(), "영업");
    assertEquals(openLaundryData.getRefineLotnoAddr(), "경기도 가평군 조종면 현리 253-3번지 ");
    assertEquals(openLaundryData.getRefineRoadnmAddr(), "경기도 가평군 조종면 연인산로 20-5");
    assertEquals(openLaundryData.getRefineWgs84Logt(), "127.3517000679");
    assertEquals(openLaundryData.getRefineWgs84Lat(), "37.8218966979");
  }
}
