package com.ssafy.sub.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UserDto {
	@ApiModelProperty(notes = "유저 아이디", required = true)
	private String uid;
	@ApiModelProperty(notes = "유저 패스워드", required = true)
	private String upw;
	@ApiModelProperty(notes = "유저 닉네임", required = true)
	private String unick;
	@ApiModelProperty(notes = "유저 이메일", required = true)
	private String uemail;
	@ApiModelProperty(notes = "유저 등록일", required = true)
	private Date uregdate;
	@ApiModelProperty(notes = "유저 생일", required = true)
	private Date ubirth;
	@ApiModelProperty(notes = "유저 성별", required = true)
	private int usex;
   
   @JsonCreator
   public UserDto(
         @JsonProperty("uid") String uid, 
         @JsonProperty("upw") String upw, 
         @JsonProperty("unick") String unick, 
         @JsonProperty("uemail") String uemail, 
         @JsonProperty("uregdate") Date uregdate, 
         @JsonProperty("ubirth") Date ubirth, 
         @JsonProperty("usex") int usex) {
      super();
      this.uid = uid;
      this.upw = upw;
      this.unick = unick;
      this.uemail = uemail;
      this.uregdate = uregdate;
      this.ubirth = ubirth;
      this.usex = usex;
   }



   public String getUid() {
      return uid;
   }



   public void setUid(String uid) {
      this.uid = uid;
   }



   public String getUpw() {
      return upw;
   }



   public void setUpw(String upw) {
      this.upw = upw;
   }



   public String getUnick() {
      return unick;
   }



   public void setUnick(String unick) {
      this.unick = unick;
   }



   public String getUemail() {
      return uemail;
   }



   public void setUemail(String uemail) {
      this.uemail = uemail;
   }



   public Date getUregdate() {
      return uregdate;
   }



   public void setUregdate(Date uregdate) {
      this.uregdate = uregdate;
   }



   public Date getUbirth() {
      return ubirth;
   }



   public void setUbirth(Date ubirth) {
      this.ubirth = ubirth;
   }



   public int getUsex() {
      return usex;
   }



   public void setUsex(int usex) {
      this.usex = usex;
   }



   @Override
   public String toString() {
      return "UserDto [uid=" + uid + ", upw=" + upw + ", unick=" + unick + ", uemail=" + uemail + ", uregdate="
            + uregdate + ", ubirth=" + ubirth + ", usex=" + usex + "]";
   }
   
   
   
}